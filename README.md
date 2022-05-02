# BookishApp
Book application is a friendly mobile application for people who enjoy reading where you can share/keep track of their readings, and easily find what books to read next.  
Users are able to search for books or research papers since this application uses both OpenLibrary and IEEEXplore API. Bookish uses Firebase Realtime Database to store the
users' saved books. This app has a "tag feature" to organize your books and categorize them with a tag you submit to each one. Bookish also uses shared preferences since 
you can configure your profile page and its configuration will be saved even after redeploying the app.

This application was created using emulator API 28 to launch the application 
This application needs google-play-services installed:
For this, Tools > SDK Manager > SDK Tools and check and apply google-play-services

For developers:

To access the Firebase database follow the next steps:

1. Navigate to the left hand side of the screen and where all the folders are listed. Navigate to Gradle Scripts and select the first one. Double click it to open it. 
2. Once it is open, navigate to the right hand side of the screen. You should see the Gradle button underneath the account icon. It is located on the top right hand corner of the screen. Once you click it, navigate to Bookdate > tasks > android > signingreport
3. Double click to execute and at the bottom you should see something running. Once you do, scroll to where you see SHA1: xxxxxxxxx . Copy the SHA1 key and keep it with you. We will use it later. 

Next up is getting you ready for the firebase. Go to this link: https://firebase.google.com/ 
4. If you do not already have an account then create one!
5. Once you are logged in, navigate to the BookDate project!
6. Once you are in and you can see the dashboard, go to the left hand side of the screen where you can see Project Overview. Select the settings icon on the right hand side of Project Overview. 
7. Then select Project settings. There you should scroll down and see SDK Set up and configuration
8. Next, you should see SHA Certificate fingerprints. We are going to add yours to it
9. Select “add fingerprint” and paste the fingerprint you copied earlier


The Manifest needs the following permissions:
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
  
Sources
https://firebase.google.com/docs/database/android/read-and-write
https://guides.codepath.com/android/Book-Search-Tutorial#add-searchview-to-actionbar. 
