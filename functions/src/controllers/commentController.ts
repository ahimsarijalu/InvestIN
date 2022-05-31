import { Request, Response, NextFunction } from "express";

import { db } from "../config/firebase";
import { Comment } from "../interfaces/comment.interface";
import HTTPError from "../utils/HTTPError";
import returnSuccess from "../utils/successHandler";

const addComment = async (req: Request, res: Response, next: NextFunction) => {
  const { text } = req.body;

  try {
    const querySnapshot = db.collection("comment").doc();
    const commentObject: Comment = {
      comment: text,
    };
    await querySnapshot.set(commentObject).catch((error) => {
      if (error.message.includes("NOT_FOUND")) {
        throw new HTTPError(404, "No entity to update");
      } else {
        throw new HTTPError(400, error.message);
      }
    });
    returnSuccess(200, res, "Successfully added a comment", commentObject);
  } catch (error) {
    return next(error);
  }
};

const getPaginatedComment = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  try {
    const commentRef = db.collection("comment").limit(5);
    await commentRef.get().then((documentSnapshots) => {
      // Get the last visible document
      const lastVisible =
        documentSnapshots.docs[documentSnapshots.docs.length - 1];
      console.log("last", lastVisible);

      // Construct a new query starting at this document,
      // get the next 25 cities.
      return db.collection("comment").startAfter(lastVisible).limit(5);
    });
    returnSuccess(200, res, "Successfully fetched comment", commentRef);
  } catch (error) {
    next(error);
  }
};
export { addComment, getPaginatedComment };
