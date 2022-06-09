import { db } from "../../config/firebase";
import { User } from "../../interfaces/user.interface";
import { UserRecord } from "firebase-functions/lib/providers/auth";

const placeholder =
  "https://firebasestorage.googleapis.com/v0/b/investin-350603.appspot.com/o/avatar%2Favatar_placeholder.png?alt=media&token=7057cc7a-194f-4420-9f81-c906d681e21d";

export const createUserInFirestore = async (userDoc: UserRecord) => {
  try {
    let newUser: User = {
      userId: userDoc.uid,
      email: userDoc.email,
      displayName: userDoc.displayName,
      avatar: placeholder,
      city: "",
      province: "",
      bio: "",
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
