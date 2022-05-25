import { Request, Response, NextFunction } from "express";
import { db } from "../config/firebase";
import { User } from "../interfaces/user.interface";
import returnSuccess from "../utils/successHandler";

const updateUser = async (req: Request, res: Response, next: NextFunction) => {
  const {
    email,
    bio,
    city,
    province,
    investorRole,
    organization,
    category,
    imageUrl,
  } = req.body;
  const { docId } = req.params;

  try {
    const querySnapshot = db.collection("users").doc(docId);
    const userObject: User = {
      email: email,
      bio: bio,
      city: city,
      province: province,
      investorRole: investorRole,
      organization: organization,
      category: category,
      imageUrl: imageUrl,
    };

    await querySnapshot.update(userObject).catch((error) => {
      return res.status(400).json({
        status: "error",
        message: error.message,
      });
    });

    returnSuccess(200, res, "User updated successfully", userObject);
  } catch (error: any) {
    return next(error);
  }
};

const getAllUsers = async (req: Request, res: Response, next: NextFunction) => {
  try {
    const querySnapshot = await db.collection("users").get();
    let result: any = [];
    querySnapshot.docs.forEach((data) => {
      let docId = data.id;
      let docObj = { ...data.data(), ["id"]: docId };
      result.push(docObj);
    });
    returnSuccess(200, res, "Successfully fetched users list", result);
  } catch (error) {
    next(error);
  }
};

const getUser = async (req: Request, res: Response, next: NextFunction) => {
  const { docId } = req.params;

  try {
    const querySnapshot = await db.collection("users").doc(docId).get();
    let result: any = [];
    if (querySnapshot.exists) {
      result.push(querySnapshot.data());
    }
    returnSuccess(200, res, `Successfully fetched user.`, result);
  } catch (error) {
    next(error);
  }
};

export { updateUser, getAllUsers, getUser };
