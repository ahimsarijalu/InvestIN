import * as functions from "firebase-functions";

export const onCreate = functions.firestore
  .document("/users/{userId}")
  .onCreate(async (snapshot, context) => {
    console.log(`user ${context.params.userId} created.`);
  });

export const onUpdate = functions.firestore
  .document("/users/{userId}")
  .onUpdate(async (change, context) => {
    console.log(`user ${context.params.userId} updated.`);
  });

export const onDelete = functions.firestore
  .document("/users/{userId}")
  .onDelete(async (snapshot, context) => {
    console.log(`user ${context.params.userId} deleted.`);
  });

export const onWrite = functions.firestore
  .document("/users/{userId}")
  .onWrite(async (change, context) => {
    console.log(`user ${context.params.userId} written.`);
  });
