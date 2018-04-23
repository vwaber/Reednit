package com.reednit.android.repository.remote.interceptor

import com.reednit.android.repository.remote.NetworkClient
import com.reednit.android.repository.remote.json.AuthTokenJson
import com.squareup.moshi.JsonAdapter
import okhttp3.*
import java.util.*

const val TOKEN_REQUEST_URL: String = "https://www.reddit.com/api/v1/access_token"
const val GRANT_TYPE: String = "https://oauth.reddit.com/grants/installed_client"

class AuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {

        val request = chain!!.request()
        val response: Response = chain.proceed(request)

        if(response.code() != 403) return response

        val tokenRequsetBuilder: Request.Builder = Request.Builder()
        tokenRequsetBuilder.url(TOKEN_REQUEST_URL)
        setAuthHeader(tokenRequsetBuilder)
        setAuthPost(tokenRequsetBuilder)

        val tokenResponse: Response  = chain.proceed(tokenRequsetBuilder.build())
        val tokenBody: ResponseBody  = tokenResponse.body()!!

        val adapter: JsonAdapter<AuthTokenJson> = NetworkClient.moshi.adapter(AuthTokenJson::class.java)
        val authTokenJson: AuthTokenJson  = adapter.fromJson(tokenBody.string())!!

        val authBuilder: Request.Builder = request.newBuilder()
        authBuilder.header(
                "Authorization",
                "Bearer " + authTokenJson.token
        )

        return chain.proceed(authBuilder.build())

    }

    private fun setAuthHeader(builder: Request.Builder) {
        val credentials: String = Credentials.basic("pheWpomhF7Mm3Q", "")
        builder.header("Authorization", credentials)
    }

    private fun setAuthPost(builder: Request.Builder) {
        val formBody: RequestBody  = FormBody.Builder()
                .add("grant_type", GRANT_TYPE)
                .add("device_id", UUID.randomUUID().toString())
                .build()
        builder.post(formBody)
    }

}