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

- `main` contains all the documentation and a version release from all branches.
- `cloud` contains backend development and deployments.
- `ML` contains recommendation implementation towards a dataset.
- `mobile` contains the Android mobile development.

----
## Open Endpoints

Open endpoints require no Authentication.

* [Sign Up](): `POST https://identitytoolkit.googleapis.com/v1/accounts:signUp?key={{firebase_web_api}}`
* [Sign In](login.md) : `POST https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key={{firebase_web_api}}`

## Closed Endpoints

Closed endpoints require a valid Token to be included in the header of the
request. A Token can be acquired from the Sign In view above.

### Base URL
* `https://us-central1-investin-350603.cloudfunctions.net/default-api`
### Current User related

Each endpoint manipulates or displays information related to the User whose
Token is provided with the request:

* [Show All Signed Up User Info]() : `GET /user`
* [Show User info]() : `GET /user/:userId`
* [Update User Info]() : `PATCH /user/:userId`

### Explore related

Endpoints for viewing and manipulating the explore feeds that the Authenticated User has permissions to access.

* [Show All Explore Feed]() : `GET /explore`
* [Show An Explore]() : `GET /explore/:docId`
* [Create Explore]() : `POST /explore`
* [Update Explore]() : `PUT /explore/:docId`
* [Delete Explore]() : `DELETE /explore/:docId`
* [Upload Image]() : `POST /explore/upload/:docId`

### Comment related

Endpoints for viewing and manipulating the comment section on a specific explore post, and that the Authenticated User has permissions to access.

* [Show All Comment]() : `GET /comment`
* [Create Comment]() : `POST /comment`