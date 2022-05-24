# InvestIN - Cloud
Backend branch with the aim of providing a working use of Firebase Authentication + Cloud Firestore services.
## Setting Up
1. Include `serviceAccount.json` and `firebaseConfig.json` which has been renamed from the initial Firebase Admin SDK key and `google-services.json` under the Project Settings.
2. As this directory contains the whole Cloud Function.
   ```
   cd functions/
   ``` 
3. To install the necessary packages in `package.json`.
   ```
   npm i
   ``` 
4. To begin deploying into the logged in Firebase account.
    ```
    npm run deploy
    ```
5. (Optional) Emulators can be used to debug and run Firebase services locally.
    ```
    npm run serve
    ```
## Branches
This `cloud` repository is a sub-branch from `main` along with other branches, `ML` and `mobile` respectively.

- `main` contains all the documentation but none of the program and code will be present.
- `cloud` contains backend development and deployments.
- `ML` contains recommendation implementation towards a dataset.
- `mobile` contains the Android mobile development.