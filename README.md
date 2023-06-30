# **Android Quick Start Guide**
This guide will help you quickly set up your Android application using the provided code snippet.

## **Prerequisites**
Before you begin, ensure that you have the following:

- Android Studio installed on your machine.
- Basic knowledge of Android development.
- Access to the necessary libraries and dependencies.

## **Step 1: Create a New Project**
1. Open Android Studio.

2. Click on "Start a new Android Studio project" or go to File > New > New Project.
 
3. Follow the prompts to configure your project settings, such as choosing a project name, package name, and minimum SDK version.
 
4. Click Finish to create the project.
 
## **Step 2: Add Libraries and Dependencies**
1. Open your project's build.gradle file (usually found in the root directory).

2. Add the following code snippet inside the repositories block in the allprojects section:
   
```kotlin
allprojects {
    repositories {
        // Other repositories...
        maven { url 'https://jitpack.io' }
    }
}
```
This will include the JitPack repository in your project, allowing you to fetch dependencies from it.

Open your app module's build.gradle file (usually found in the app directory).

Add the following code snippet inside the dependencies block:

```kotlin
implementation 'com.github.m800andteam:visitor_sdk:1.0.8'
```
This line specifies the dependency on the visitor_sdk library from the JitPack repository. Make sure to replace 1.0.8 with the desired version of the library.

Sync your project with the Gradle files by clicking on the "Sync Now" button or selecting File > Sync Project with Gradle Files.
Congratulations! You have successfully updated the dependencies using JitPack. The visitor_sdk library is now included in your Android project.

Please note that the provided repository URL and library version are examples. Make sure to replace them with the correct values based on your specific library and version requirements.

## **Step 3: Update the MainActivity**
1. Open the MainActivity.kt file .

2. Add the serviceId of the MainActivity class with the provided code snippet.
```kotlin
const val serviceId = "xxxx.cinnox.com"
```
3. Init CinnoxVisitorCore  
```kotlin
val core = CinnoxVisitorCore.initialize(this, serviceId)
```

4. Add CinnoxVisitorCoreListener and register it when you need to know initialize end.
```kotlin
 private val mCoreListener: CinnoxVisitorCoreListener = object : CinnoxVisitorCoreListener{
        override fun onInitializationEnd(success: Boolean, throwable: Throwable?) {
            Log.d(TAG, "onInitializationEnd, isSuccess: $success, throwable: $throwable")
        }
    }
 core.registerListener(mCoreListener)
```

## **Step 4: Add CinnoxVisitorWidget in the activity_main.xml**

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

## **Step 5: Support Firebase Clould Message**
To integrate Firebase Cloud Messaging (FCM) into your application and provide the necessary information to our library, you will need to obtain the following details:

Package Name:
The package name is a unique identifier for your Android application. It is typically defined in the AndroidManifest.xml file of your project. To find the package name:

1. Open your Android project in Android Studio.
2. In the Project view, navigate to the "app" folder.
3. Expand the "app" folder and locate the "AndroidManifest.xml" file.
4. Open the "AndroidManifest.xml" file and find the "package" attribute in the "manifest" tag.
5. The value of the "package" attribute is your package name. It usually follows a pattern like "com.example.myapp".

Please provide our library with the package name associated with your Android application.

FCM Service Key:
The FCM Service Key, also known as the server key, is a unique identifier used to authenticate requests from the server to the FCM API. To generate the FCM Service Key:

1. Go to the Firebase console (console.firebase.google.com).
2. Select your Firebase project or create a new one.
3. Navigate to the "Project settings" by clicking on the gear icon.
4. In the "Project settings" page, select the "Cloud Messaging" tab.
5. Scroll down to the "Server Key" section.
6. If you haven't generated a server key before, click on the "Create Server Key" button. If you have an existing server key, you can use that.
7. A dialog box will appear displaying your FCM Service Key. Copy the key and provide it to our library.

Please ensure that you keep your FCM Service Key secure and avoid sharing it publicly or storing it in insecure locations. It grants access to send messages to your application.

Once you have obtained the package name and FCM Service Key, provide them to our library as instructed.

If you have any further questions or need additional assistance, please let us know.


## **Conclusion**

Congratulations! You have successfully added and configured the "CinnoxVisitorSDK" library in your Android project. Make sure to refer to the library's documentation for any additional setup or usage instructions.

Please note that the provided repository URL, username, and library version are examples. Make sure to replace them with the correct values based on your specific library and version requirements.

Refer to the library's documentation for any additional setup or usage instructions specific to the "maaiiconnectmobileclient" library

If you have any questions or need further assistance, feel free to ask!

