# Reednit
A simple Reddit app for Android, still very much a work in progress

The initial upload was the final result of my capstone project from the Android Development Nanodegree by Google & Udacity. I have since continued development and introduced new elements such as Android Architecture Components as well as manual translation to Kotlin.

Please excuse the current lack of documentation and testing, I hope to remedy these deficiencies soon.

### Kotlin
Currently all usage of Kotlin can be found in the following directory:  
[`/app/src/main/java/com/reednit/android/repository/*`](https://github.com/vwaber/Reednit/tree/master/app/src/main/java/com/reednit/android/repository)  
I intend to eventually convert the entire project to Kotlin.

## Setup
This project should be able to be readily be imported, built, and ran within Android Studio 3.1.2, with one exception:  
either [`/app/google-services.json`](https://support.google.com/firebase/answer/7015592?hl=en) must be provided  
*or*  
the following line within [`/app/build.gradle`](https://github.com/vwaber/Reednit/blob/master/app/build.gradle) must be disabled: 
```
apply plugin: 'com.google.gms.google-services'
```

## Notable libraries used
* Android Architecture Components
	* [Room](https://developer.android.com/topic/libraries/architecture/room.html) - persistent storage
	* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html) - observable data
	* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html) - store and manage UI-related data
	* [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle.html) - lifecycle-aware components
* Square Open Source
	* [OkHttp](http://square.github.io/okhttp/) - HTTP client
	* [Retrofit](http://square.github.io/retrofit/) - REST consumption
	* [Moshi](https://github.com/square/moshi) - JSON parsing
* [Glide](https://bumptech.github.io/glide/) - image loading
