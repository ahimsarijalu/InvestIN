import { db } from "../../config/firebase";
import { User } from "../../interfaces/user.interface";
import { UserRecord } from "firebase-functions/lib/providers/auth";

export const createUserInFirestore = async (userDoc: UserRecord) => {
  try {
    let newUser: User = {
      userId: userDoc.uid,
      email: userDoc.email,
      createdAt: new Date().toISOString(),
    };
    const user = db.collection("users").doc(userDoc.uid);
    await user.set(newUser);
  } catch (error) {
    throw error;
  }
};

export const deleteUserInFirestore = async (userDoc: UserRecord) => {
  try {
    const user = db.collection("users").doc(userDoc.uid);
    return await user.delete();
  } catch (error) {
    throw error;
  }
};
