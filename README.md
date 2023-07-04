# **Android Quick Start Guide**
This guide will help you know how to use Cinnox Visitor SDK on Android application through the sample app demonstration.
 
## **Step 1: Add Libraries and Dependencies**
1. Add the following code snippet inside the repositories block in the app/build.gradle section:
   
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
implementation 'com.github.CinnoxGitHub:visitor_sdk:1.0.1'
```
This line specifies the dependency on the visitor_sdk library from the JitPack repository. 

Sync your project with the Gradle files by clicking on the "Sync Now" button or selecting File > Sync Project with Gradle Files.
Congratulations! You have successfully updated the dependencies using JitPack. The visitor_sdk library is now included in your Android project.

## **Step 2: Set Up and Initialization**
1. Open the MainActivity.kt file.

2. Replace the value of serviceId with your Cinnox service.
```kotlin
const val serviceId = "xxxx.cinnox.com"
```
3. Init CinnoxVisitorCore  
```kotlin
val core = CinnoxVisitorCore.initialize(this, serviceId)
```

4. Add CinnoxVisitorCoreListener and register it when you need to know initialization end.
```kotlin
 private val mCoreListener: CinnoxVisitorCoreListener = object : CinnoxVisitorCoreListener{
        override fun onInitializationEnd(success: Boolean, throwable: Throwable?) {
            Log.d(TAG, "onInitializationEnd, isSuccess: $success, throwable: $throwable")
        }
    }
 core.registerListener(mCoreListener)
```

## **Step 3: Show Cinnox Widget to User**
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
To integrate Firebase Cloud Messaging (FCM) of your application with our library, you have to provide us the following info:

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
6. If you haven't generated a server key before, click on the "Create Server Key" button. If you have an existing server key, you can use that.
7. A dialog box will appear displaying your FCM Service Key. Copy the key and provide it to our library.


# **API documentation**
```kotlin
/**
 * The core functionality for the Cinnox Visitor SDK
 */
class CinnoxVisitorCore

/**
 * Initializes the `CinnoxVisitorCore` instance.
 *
 * @param context The application context.
 * @param serviceId The name of your service.
 * @return The initialized `CinnoxVisitorCore` instance.
 */
fun initialize(context: Context, serviceId: String): CinnoxVisitorCore

/**
 * Uninitializes the `CinnoxVisitorCore` instance.
 */
fun uninitialize()

/**
 * Registers a listener for `CinnoxVisitorCore` events.
 *
 * @param listener The listener to be registered.
 */
fun registerListener(listener: CinnoxVisitorCoreListener)

/**
 * The listener interface for CinnoxVisitorCore events.
 */
interface CinnoxVisitorCoreListener

/**
 * Called when the initialization of CinnoxVisitorCore ends.
 *
 * @param success Indicates whether the initialization was successful or not.
 * @param throwable The Throwable object containing an error, if any occurred during initialization.
 */
fun onInitializationEnd(success: Boolean, throwable: Throwable?)
```
