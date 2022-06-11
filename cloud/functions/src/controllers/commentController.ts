import { Request, Response, NextFunction } from "express";

import { db } from "../config/firebase";
import { CommentContent } from "../interfaces/comment.interface";
import returnSuccess from "../utils/successHandler";

const addComment = async (req: Request, res: Response, next: NextFunction) => {
  const { authorId, author, avatar, text } = req.body;
  const { exploreId } = req.params;

  try {
    const querySnapshot = db.collection("comment").doc();
    const commentObject: CommentContent = {
      id: querySnapshot.id,
      authorId,
      author,
      avatar,
      text,
      exploreId,
      createdAt: new Date().toISOString(),
    };
    await querySnapshot.create(commentObject);
    returnSuccess(201, res, "Successfully added a comment", commentObject);
  } catch (error) {
    return next(error);
  }
};

const getPaginatedComment = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  const { exploreId } = req.params;

  try {
    let result: any = [];
    const querySnapshot = await db.collection("comment").doc(exploreId).get();
    // await commentRef.get().then((documentSnapshots) => {
    //   // Get the last visible document
    //   const lastVisible =
    //     documentSnapshots.docs[documentSnapshots.docs.length - 1];
    //   console.log("last", lastVisible);

    //   // Construct a new query starting at this document,
    //   // get the next 25 cities.
    //   return db.collection("comment").startAfter(lastVisible).limit(5);
    // });
    if (querySnapshot.exists) {
      result.push(querySnapshot.data());
    }
    returnSuccess(200, res, "Successfully fetched explore", result);
  } catch (error) {
    next(error);
  }
};
export { addComment, getPaginatedComment };
