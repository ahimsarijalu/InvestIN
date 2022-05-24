import { Request, Response, NextFunction } from "express";
import { db } from "../config/firebase";
import { Explore } from "../interfaces/explore.interface";
import HTTPError from "../utils/HTTPError";
import returnSuccess from "../utils/successHandler";

const addExplore = async (req: Request, res: Response, next: NextFunction) => {
  const {
    body: { text, imageUrl },
  } = req;

  try {
    const entry = db.collection("explore").doc();
    const entryObject: Explore = {
      id: entry.id,
      text,
      imageUrl,
    };
    entry.set(entryObject);
    returnSuccess(200, res, "Entry added successfully", entryObject);
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
    const entryDocs = await db.collection("explore").get();
    let result: Array<Explore> = [];

    entryDocs.forEach((doc: any) => {
      const entryObject: Explore = doc.data();
      return result.push(entryObject);
    });
    returnSuccess(200, res, "Fetched list", result);
  } catch (error) {
    return next(error);
  }
};

const updateExplore = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  const { entryId } = req.params;
  const { isLike, commentCount, likeCount } = req.body;
  try {
    const entry = db.collection("explore").doc(entryId);

    const entryObject = {
      isLike: isLike,
      commentCount: commentCount,
      likeCount: likeCount,
    };
    await entry.update(entryObject).catch((error) => {
      if (error.message.includes("NOT_FOUND")) {
        throw new HTTPError(404, "No entity to update");
      } else {
        throw new HTTPError(400, error.message);
      }
    });
    returnSuccess(200, res, "Entry updated successfully", entryObject);
  } catch (error) {
    return next(error);
  }
};

const deleteExplore = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  const { entryId } = req.params;

  try {
    const entry = db.collection("explore").doc(entryId);
    // exists: true: return error if NOT_FOUND
    await entry.delete({ exists: true }).catch((error) => {
      if (error.message.includes("NOT_FOUND")) {
        throw new HTTPError(404, "No entity to update");
      } else {
        throw new HTTPError(400, error.message);
      }
    });

    returnSuccess(200, res, "Entry deleted successfully", null);
  } catch (error) {
    return next(error);
  }
};

export { addExplore, getAllExplore, updateExplore, deleteExplore };
