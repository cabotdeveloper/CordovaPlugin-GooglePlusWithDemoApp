CordovaPlugin-GooglePlusLogin
=============================

This is a Cordova Plugin for Google Plus integration in iOS and Android.
This plugin for Apache Cordova allows you to use the same Javascript code in Cordova application as you in your cross platform mobile applications. 

 Supported on PhoneGap (Cordova) v3.3.0 and above.

 This plugin is built for the platforms

      1.      iOS 

      2.      Android 

## Requirements and Setup

  To use this plugin you will need to make sure you've registered your App in Google Developer Console and have a CLIENT ID.

  Developer Console: [https://console.developers.google.com/](https://console.developers.google.com/)

  This plugin requires Cordova CLI in your machine for the installation.

**Installation:**

**iOS:**

  To install the plugin in your app, execute the following (replace variables where necessary)

      1. cordova create <AppDirectoryName> <PackageName> <AppName>

      2. cd <AppDirectoryName>

      3. cordova platform add ios

      4. cordova plugin add https://github.com/cabotdeveloper/CordovaPlugins-GooglePlusPlugin --variable   CLIENT_ID="39879107009-398cp2qn3bkqtlhmd8vp712lkedmkqqq.apps.googleusercontent.com"

The CLIENT ID should be given correctly which is created using the package <PackageName>  in Google Developer Console.  The steps are below.

First, you must [create a Google Developers Console project](https://developers.google.com/+/mobile/ios/getting-started)

##  Creating the Google Developers Console project for iOS

  To enable the Google+ API for your app, you need to create a Google Developers Console project, enable the Google+ API then create a client ID.

  1. Go to the [Google Developers Console](https://console.developers.google.com/).
    
  2. Select a project, or create a new one.

  3. In the sidebar on the left, select **APIs & auth** (the **APIs** sub-element is automatically selected).

  4. In the displayed list of APIs, make sure the Google+ API status is set to **ON**, as well as any other APIs that your app requires. If you plan to use other APIs, turn those on as well.
  
  5. In the sidebar on the left, select **Credentials**.

  6. In the **OAuth** section of the page, select **Create New Client ID**.
  
  7. In the resulting **Create Client ID** dialog box, do the following:

      1. Select **Installed application** for the Application type.

      2. Select **iOS**.

      3. In the **Bundle ID** field, enter the bundle identifier from your application's project summary.

      4. If your app is published in the Apple iTunes App Store, enter your app's App Store identifier in the **App Store ID** field. You can determine the ID from the "id" part of your app's URL on the iTunes website: http://itunes.apple.com/us/app/id<YOUR_APP_STORE_ID>.

      5. (Optional) If you plan to build [deep linking](https://developers.google.com/+/mobile/ios/share) into shared posts made from your app, enable **Deep Linking**.

      6. Click the **Create client ID** button.

      7. The CLIENT ID for iOS can be used while adding the plugin to the project.

Android:

To install the plugin in your app, execute the following (replace variables where necessary)

    1. cordova create <AppDirectoryName> <PackageName> <AppName>

    2. cd <AppDirectoryName>

    3. cordova platform add android

    4. cordova plugin add https://github.com/cabotdeveloper/CordovaPlugins-GooglePlusPlugin --variable   CLIENT_ID="39879107009-398cp2qn3bkqtlhmd8vp712lkedmkqqq.apps.googleusercontent.com" 

The CLIENT ID should be given correctly from Google Developer Console which is created using the package <PackageName> 

 The CLIENT ID can be obtained by the following the below steps.

  To authenticate and communicate with the Google+ APIs, first you must create a Google Developers Console project, enable the Google+ API, create an OAuth 2.0 Client ID, and register your digitally signed .apk file's public certificate:

  1. Go to the [Google Developers Console](https://console.developers.google.com/).

  2. **Note:** Create a single project for the Android, iOS and web versions of your app.

  3. Click **Create Project**:

      a. In the **Project name** field, type in a name for your project.

      b. In the **Project ID** field, optionally type in a project ID for your project or use the one that the console has created for you. This ID must be unique world-wide.

  4. Click the **Create** button and wait for the project to be created. **_Note:_*** There may be short delay of up to 30 seconds before the project is created.*Once the project is created, the name you gave it appears at the top of the left sidebar.

  5. In the left sidebar, select **APIs & auth** (the **APIs** sub-item is automatically selected).

      a. Find the **Google+ API** and set its status to ON—notice that this action moves Google+ API to the top of the list; you can scroll up to see it.
  
      b. Enable any other APIs that your app requires.

  6. In the left sidebar, select **Credentials**.

      a. Click **Create a new Client ID**—the **Create Client ID** appears, as shown further below.

      b. Select **Installed application** for the application type.

      c. Select **Android** as the installed application type.

      d. Enter your Android app's package name into the **Package name** field.

      e. In a terminal, run the the Keytool utility to get the SHA-1 fingerprint of the certificate. For the debug.keystore, the password is **android**.

          keytool -exportcert -alias androiddebugkey -keystore <path-to-debug-or-production-keystore> -list -v

      f. Paste the SHA-1 fingerprint hash into the **Signing certificate fingerprint** field shown below.

      g. To activate interactive posts, enable the **Deep Linking** option.

      h. Click the **Create client ID** button.

Example Apps

platforms/android and platforms/ios contain example projects and all the native code for the plugin for both Android and iOS platforms. They also include versions of the Google Play Services for Android and Google Plus Frameworks for iOS. These are used during automatic installation.

**Usage of Plugin Methods:**

**Login:**

This is a method for attempting google plus login and retrieving the profile information.

navigator.googleConnectPlugin.googleLogin(Function success, Function failure)

Success Function will return an object containing the profile information like:

{

	Name:"Tester",

	Email:"tester@testing.com"

	Gender:"1"

}

Failure function returns an error String.

