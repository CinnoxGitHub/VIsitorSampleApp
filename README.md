# **Android Quick Start Guide**
This guide will help you know how to use the CINNOX Visitor SDK on Android application through the sample app demonstration.
 
## **Step 1: Add Libraries and Dependencies**
1. Add the following code snippet inside the repositories block to the app/build.gradle section:
   
```kotlin
buildscript {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
This will include the JitPack repository in your project, allowing you to fetch dependencies from it.

2. Add the following code snippet inside the dependencies block:

```kotlin
implementation 'com.github.CinnoxGitHub:visitor_sdk:1.0.12'
```
This line specifies the dependency on the visitor_sdk library from the JitPack repository. 

Sync your project with the Gradle files by clicking on the "Sync Now" button or selecting File > Sync Project with Gradle Files.
Congratulations! You have successfully updated the dependencies using JitPack. The visitor_sdk library is now included in your Android project.

## **Step 2: Set Up and Initialisation**

1. Create a new class called MainApplication that extends Application & Add CinnoxVisitorCoreListener and register it when you need to know the initialisation end.
 ```kotlin
 class MainApplication : Application() {
     companion object {
         const val serviceName = "xxxx.cinnox.com" // Replace with your CINNOX subdomain
     }

     private val mCoreListener: CinnoxVisitorCoreListener = object : CinnoxVisitorCoreListener{
         override fun onInitializationEnd(success: Boolean, throwable: Throwable?) {
             Log.d(TAG, "onInitializationEnd, isSuccess: $success, throwable: $throwable")
         }
     }

     override fun onCreate() {
         super.onCreate()
         val core = CinnoxVisitorCore.initialize(this, serviceName)
         core.registerListener(mCoreListener)
     }
 }
 ```
 Remember to replace "xxxx.cinnox.com" with your actual CINNOX subdomain, which you can find in your CINNOX Dashboard under Administration > Widget > Installation.

 Now, with CinnoxVisitorCore properly initialized in the MainApplication class, the SDK will be ready to use throughout the entire lifecycle of your Android application. Make sure to update your AndroidManifest.xml to use the MainApplication class as the application name:
 
 ```kotlin
 <application
     android:name=".MainApplication"
     <!-- Other attributes -->
 >
     <!-- Activities, services, receivers, etc. -->

 </application>
 ```

## **Step 3: Show your CINNOX Widget to Users**
Add CinnoxVisitorWidget in the layout activity_main.xml

```kotlin
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.m800.maaiiconnect.mobile.client.CinnoxVisitorWidget
        android:id="@+id/floating_button"
        android:layout_width="72dp"
        android:layout_height="72dp"
        android:gravity="center"
        android:scaleType="fitCenter"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

## **Step 4: Support Firebase Clould Message**
1. To integrate Firebase Cloud Messaging (FCM) of your application with our library, you have to provide us with the following info:
   `Package Name`:
   The package name is a unique identifier for your Android application. It is typically defined in the AndroidManifest.xml file of your project. To find the package name:
   
   1. Open your Android project in Android Studio.
   2. In the Project view, navigate to the "app" folder.
   3. Expand the "app" folder and locate the "AndroidManifest.xml" file.
   4. Open the "AndroidManifest.xml" file and find the "package" attribute in the "manifest" tag.
   5. The value of the "package" attribute is your package name. It usually follows a pattern like "com.example.myapp".
   
   `FCM Server Key`:
   The FCM Server key is a unique identifier used to authenticate requests from the server to the FCM API. To generate the FCM Server Key:
   
   1. Go to the Firebase console (console.firebase.google.com).
   2. Select your Firebase project or create a new one.
   3. Navigate to the "Project settings" by clicking on the gear icon.
   4. In the "Project settings" page, select the "Cloud Messaging" tab.
   5. Scroll down to the "Server Key" section.
   6. If you haven't generated a server key before, click on the "Create Server Key" button. If you have an existing server key, you can use it.
   7. A dialog box will appear displaying your FCM Service Key. Copy the key and provide it to our library.
    
   Please send us your info via email at support@cinnox.com with "Info for FCM" as the email subject.

 
3. If you have already implemented FCM service in your app, please follow these steps:

   1. Here's an example of the FcmPushService class:
   ```kotlin
   class FcmPushService : FirebaseMessagingService() {
       // ...
   
       override fun onNewToken(token: String) {
           CinnoxVisitorCore.getInstance().getPushManager().updateToken(
               CinnoxPushType.FCM,
               token
           )
       }
   
       override fun onMessageReceived(remoteMessage: RemoteMessage) {
           val data = genFcmRemoteMessagePushData(remoteMessage)
           data?.let {
               CinnoxVisitorCore.getInstance().getPushManager().handlePushNotification(
                   CinnoxPushType.FCM,
                   it
               )
           }
       }
   
       // ...
   }
   ```
   (1). You should pass the received token to the updateToken api of the CinnoxPushManager to update the token.
   
   (2). To handle incoming push notifications. Extract the necessary data from the RemoteMessage object and pass it to the handlePushNotification api of the CinnoxPushManager.

   (3). You can use the method genFcmRemoteMessagePushData in PushHelper file for extract the JSONObject data for the handlePushNotification api.
   
   2. To handle system notifications when the user clicks on them, you can follow this example using the LAUNCHER MainActivity class:
   ```kotlin
   class MainActivity : AppCompatActivity() {
       // ...
   
       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setContentView(R.layout.activity_main)
           handleClickedSystemNotification()
       }
   
       override fun onNewIntent(intent: Intent?) {
           super.onNewIntent(intent)
           setIntent(intent)
           handleClickedSystemNotification()
       }
   
       private fun handleClickedSystemNotification() {
           genFcmIntentPushData(intent)?.let { data ->
               if (data.length() == 0) {
                   Log.i(TAG, "handleClickedSystemNotification data is empty")
                   return
               }
               CinnoxVisitorCore.getInstance().getPushManager().handleClickedSystemNotification(
                   CinnoxPushType.FCM,
                   data
               )
           } ?: run {
               Log.i(TAG, "handleClickedSystemNotification data is null")
           }
       }
   
       // ...
   }
   ```
   (1). In this example, when the user clicks on a system notification, the handleClickedSystemNotification method is called. Extract the necessary data from the intent object and pass it to the handleClickedSystemNotification api of the CinnoxPushManager for further processing.

   (2). If the notification is not the type which handleClickedSystemNotification api can process, the api will return false to let your app switch to default click system notification flow.

   (3). You can use the method genFcmIntentPushData in PushHelper file for extract the JSONObject data for the handleClickedSystemNotification api.

# **Compatibility**
Android 7 or later


# **API Documentation**
```kotlin
/**
 * The core functionality for the CINNOX Visitor SDK
 */
class CinnoxVisitorCore

/**
 * Initialises the `CinnoxVisitorCore` instance.
 *
 * @param context The application context.
 * @param serviceId The name of your service.
 * @return The initialised `CinnoxVisitorCore` instance.
 */
fun initialize(context: Context, serviceId: String): CinnoxVisitorCore

/**
 * Uninitialises the `CinnoxVisitorCore` instance.
 */
fun uninitialize()

/**
 * Registers a listener for `CinnoxVisitorCore` events.
 *
 * @param listener The listener to be registered.
 */
fun registerListener(listener: CinnoxVisitorCoreListener)

/**
 * Returns the CinnoxPushManager instance.
 *
 * @return The CinnoxPushManager instance.
 */
fun getPushManager(): CinnoxPushManager 

/**
 * The listener interface for CinnoxVisitorCore events.
 */
interface CinnoxVisitorCoreListener

/**
 * Called when the initialisation of CinnoxVisitorCore ends.
 *
 * @param success Indicates whether the initialisation was successful or not.
 * @param throwable The Throwable object contains an error, if any occurred during initialisation.
 */
fun onInitializationEnd(success: Boolean, throwable: Throwable?)

/**
 * App integrate the push and use this interface for handle push notification.
 */
interface CinnoxPushManager {

    /**
     * Updates the push notification token for the specified push notification type.
     *
     * @param type The type of the push notification.
     * @param token The new token value.
     */
    fun updateToken(type: CinnoxPushType, token: String)

    /**
     * Handles the incoming push notification of the specified type.
     *
     * @param type The type of the push notification.
     * @param data The JSON object containing the push notification data.
     */
    fun handlePushNotification(type: CinnoxPushType, data: JSONObject)

    /**
     * Handles the scenario when a system notification of the specified type is clicked.
     *
     * @param type The type of the push notification.
     * @param data The JSON object containing the notification data.
     * @return True if the handling was successful, false otherwise.
     */
    fun handleClickedSystemNotification(type: CinnoxPushType, data: JSONObject): Boolean

    /**
     * Releases any resources held by the CinnoxPushManager instance and revoke the push token.
     */
    fun release()
}

```
