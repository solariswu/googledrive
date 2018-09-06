Android practise - google drive

To compile and execute the source code, you will need 
 
1. Create a project with Google Auth enabled first with following page's button "CONFIGURE A PROJECT"
https://developers.google.com/identity/sign-in/android/start

2. Input your Android Studio's keystore SHA1 value. The package name should be com.solariswu.gdrive.

3. Open Google API console, select the created project, then enable Youtube Data API and Google Drive API with APIKEY to the project.

4. Update the SERVER_CLIENT_ID and GCP_API_KEY values in the file ./app/src/main/java/com/solariswu/gdrive/utils/GdriveConstant.java

All done.


