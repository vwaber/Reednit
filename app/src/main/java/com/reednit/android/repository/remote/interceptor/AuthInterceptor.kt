package com.reednit.android.repository.remote.interceptor

import com.reednit.android.repository.remote.NetworkClient
import com.reednit.android.repository.remote.json.AuthTokenJson
import com.squareup.moshi.JsonAdapter
import okhttp3.*
import java.util.UUID

private const val HTTP_STATUS_FORBIDDEN: Int = 403
private const val TOKEN_REQUEST_URL: String = "https://www.reddit.com/api/v1/access_token"
private const val GRANT_TYPE: String = "https://oauth.reddit.com/grants/installed_client"
//TODO: Protect/hide APP_ID id to prevent abuse
private const val APP_ID: String = "pheWpomhF7Mm3Q"


class AuthInterceptor: Interceptor {

    //TODO: Move mDeviceId to permanent storage
    private val mDeviceId: String = UUID.randomUUID().toString()

    private var mAuthToken: AuthTokenJson? = null
    private var mTokenRetrievalTime: Long = 0

    override fun intercept(chain: Interceptor.Chain): Response {

        fun updateToken(){
            mTokenRetrievalTime = System.currentTimeMillis()
            mAuthToken = retrieveAuthToken(chain, mDeviceId)
        }

        val builder: Request.Builder = chain.request().newBuilder()

        if (
                mAuthToken == null ||
                ((System.currentTimeMillis() - mTokenRetrievalTime) / 1000)
                >= mAuthToken!!.expiresIn
        ) {
            updateToken()
        }

        addAuthHeaderToRequestBuilder(builder, mAuthToken!!)

        var response: Response = chain.proceed(builder.build())

        if(response.code() == HTTP_STATUS_FORBIDDEN){
            updateToken()
            addAuthHeaderToRequestBuilder(builder, mAuthToken!!)
            response = chain.proceed(builder.build())
        }

        return response
    }

    private fun addAuthHeaderToRequestBuilder(builder: Request.Builder, token: AuthTokenJson){
        builder.header(
                "Authorization",
                "Bearer " + token.token
        )
    }

    private fun retrieveAuthToken(chain: Interceptor.Chain, deviceId: String): AuthTokenJson{

        val builder: Request.Builder = Request.Builder()
        val credentials: String = Credentials.basic(APP_ID, "")

        builder.url(TOKEN_REQUEST_URL)
        builder.header("Authorization", credentials)

        val body: RequestBody  = FormBody.Builder()
                .add("grant_type", GRANT_TYPE)
                .add("device_id", deviceId)
                .build()
        builder.post(body)

        val response: Response  = chain.proceed(builder.build())
        val tokenString: String = response.body()!!.string()

        val adapter: JsonAdapter<AuthTokenJson> =
                NetworkClient.moshi.adapter(AuthTokenJson::class.java)
        val token: AuthTokenJson  = adapter.fromJson(tokenString)!!

        return(token)
    }

}