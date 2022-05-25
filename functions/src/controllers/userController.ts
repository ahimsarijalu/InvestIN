import { Request, Response, NextFunction } from "express";
import { db } from "../config/firebase";
import { User } from "../interfaces/user.interface";
import returnSuccess from "../utils/successHandler";

const updateUser = async (req: Request, res: Response, next: NextFunction) => {
  const { bio, city, province, investorRole, organization, category } =
    req.body;
  const { docId } = req.params;

  try {
    const entry = db.collection("users").doc(docId);
    const userObject: User = {
      bio: bio,
      city: city,
      province: province,
      investorRole: investorRole,
      organization: organization,
      category: category,
    };

    await entry.update(userObject).catch((error) => {
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
    returnSuccess(200, res, "Successfully fetched lists of users", result);
  } catch (error) {
    next(error);
  }
};

export { updateUser, getAllUsers };
