import * as admin from 'firebase-admin'
import * as serviceAccount from './serviceAccount.json'

// import * as functions from 'firebase-functions'

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount as object),
  databaseURL: 'https://investin350603.firebaseio.com'
})

const db = admin.firestore()
export { admin, db } 