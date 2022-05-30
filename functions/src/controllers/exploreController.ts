import { Request, Response, NextFunction } from "express";

import { db } from "../config/firebase";
import { Explore } from "../interfaces/explore.interface";
import HTTPError from "../utils/HTTPError";
import returnSuccess from "../utils/successHandler";

const addExplore = async (req: Request, res: Response, next: NextFunction) => {
  const { text, imageUrl } = req.body;

  try {
    // let userID = firebase.auth().currentUser?.getIdToken(true);
    const querySnapshot = db.collection("explore").doc();
    const exploreObject: Explore = {
      // userId: userID,
      commentCount: 0,
      text,
      imageUrl,
      createdAt: new Date().toISOString(),
      isLike: false,
      likeCount: 0,
    };
    querySnapshot.set(exploreObject);
    returnSuccess(200, res, "Explore added successfully", exploreObject);
  } catch (error) {
    return next(error);
  }
};

const getAllExplore = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  try {
    const querySnapshot = await db.collection("explore").get();
    let result: Array<Explore> = [];
    querySnapshot.forEach((doc: any) => {
      const docId = doc.id;
      const docObj: Explore = { ...doc.data(), ["id"]: docId };
      return result.push(docObj);
    });
    returnSuccess(200, res, "Successfully fetched explore list", result);
  } catch (error) {
    return next(error);
  }
};

const getExplore = async (req: Request, res: Response, next: NextFunction) => {
  const { docId } = req.params;

  try {
    const querySnapshot = await db.collection("explore").doc(docId).get();
    let result: any = [];
    if (querySnapshot.exists) {
      result.push(querySnapshot.data());
    }
    returnSuccess(200, res, "Successfully fetched explore", result);
  } catch (error) {
    return next(error);
  }
};

const updateExplore = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  const { docId } = req.params;
  const { commentCount, text, imageUrl, isLike, likeCount } = req.body;

  try {
    const querySnapshot = db.collection("explore").doc(docId);

    const exploreObject: Explore = {
      commentCount,
      text,
      imageUrl,
      isLike,
      likeCount,
    };
    await querySnapshot.update(exploreObject).catch((error) => {
      if (error.message.includes("NOT_FOUND")) {
        throw new HTTPError(404, "No entity to update");
      } else {
        throw new HTTPError(400, error.message);
      }
    });
    returnSuccess(200, res, "Explore updated successfully", exploreObject);
  } catch (error) {
    return next(error);
  }
};

const deleteExplore = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  const { docId } = req.params;

  try {
    const querySnapshot = db.collection("explore").doc(docId);
    await querySnapshot.delete({ exists: true }).catch((error) => {
      if (error.message.includes("NOT_FOUND")) {
        throw new HTTPError(404, "No entity to update");
      } else {
        throw new HTTPError(400, error.message);
      }
    });

    returnSuccess(200, res, "Explore deleted successfully", null);
  } catch (error) {
    return next(error);
  }
};

export { addExplore, getAllExplore, getExplore, updateExplore, deleteExplore };
