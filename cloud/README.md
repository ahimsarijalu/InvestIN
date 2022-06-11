# InvestIN - Cloud
Backend branch with the aim of providing a working use of Firebase + Cloud services (Function, Firestore, Storage, Identity Platform).

## Tech Stacks
- Firebase
- Cloud Firestore
- Identity Platform
- Cloud Storage
- Cloud Function
- Node.js Express
- Typescript
- Busboy
- Lodash
- Prettier

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
- `ML` contains the revenue growth model.
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

* [Show All Signed Up User Info](docs/user/get.md) : `GET /user`
* [Show User Info](docs/user/get.md) : `GET /user/:userId`
* [Update User Info](docs/user/patch.md) : `PATCH /user/:userId`
* [Upload User Avatar](docs/user/post.md) : `POST /user/upload/:userId`

### Explore related

Endpoints for viewing and manipulating the explore feeds that the Authenticated User has permissions to access.

* [Show All Explore Feed](docs/explore/get.md) : `GET /explore`
* [Show An Explore](docs/explore/:docId/get.md) : `GET /explore/:docId`
* [Create Explore](docs/explore/post.md) : `POST /explore`
* [Update Explore](docs/explore/:docId/patch.md) : `PATCH /explore/:docId`
* [Delete Explore](docs/explore/:docId/delete.md) : `DELETE /explore/:docId`
* [Upload Image](docs/explore/upload/:docId/post.md) : `POST /explore/upload/:docId`

### Comment related

Endpoints for viewing and manipulating the comment section on a specific explore post, and that the Authenticated User has permissions to access.

* [Show All Comment](docs/comment/get.md) : `GET /comment`
* [Create Comment](docs/comment/post.md) : `POST /comment`
