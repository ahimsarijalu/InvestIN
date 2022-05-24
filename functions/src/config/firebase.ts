import * as admin from "firebase-admin";
import { ServiceAccount } from "firebase-admin";
import firebase from "firebase";
import * as serviceAccount from "./serviceAccount.json";
import * as firebaseConfig from "./firebaseConfig.json";

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount as ServiceAccount),
  databaseURL: "https://investin-350603.firebaseio.com",
});

firebase.initializeApp(firebaseConfig);

const db = admin.firestore();
export { admin, db, firebase };
