import * as functions from "firebase-functions";
import {
  createUserInFirestore,
  deleteUserInFirestore,
} from "./methods/authMethods";

export const onCreateAuth = functions.auth
  .user()
  .onCreate((userRecord, _context) => {
    createUserInFirestore(userRecord);
  });

export const onDeleteAuth = functions.auth
  .user()
  .onDelete((userRecord, _context) => {
    deleteUserInFirestore(userRecord);
  });
