import * as functions from "firebase-functions";
import _ from "lodash";

import { db } from "../config/firebase";
import { CommentContent } from "../interfaces/comment.interface";

export const onCreate = functions.firestore
  .document("/comment/{commentId}")
  .onCreate(async (snapshot, context) => {
    let newComment = snapshot.data() as CommentContent;
    const commentId: string = context.params.commentId;
    const exploreId = newComment.exploreId;

    if (newComment) {
      const exploreRef = db.doc(`explore/${exploreId}`);

      // Increase comment counter and create three comments' slide preview
      return db.runTransaction(async (transaction) => {
        const exploreDoc = await transaction.get(exploreRef);
        if (exploreDoc.exists) {
          const exploreData = exploreDoc.data();
          const commentCount = exploreData?.commentCounter + 1 || 0;
          transaction.update(exploreRef, { commentCounter: commentCount });
          let comment = exploreData?.comment;
          if (!comment) {
            comment = {};
          }
          if (commentCount < 4) {
            transaction.update(exploreRef, {
              comment: { ...comment, [commentId]: newComment },
            });
          } else {
            let sortedObjects = { ...comment, [commentId]: newComment };
            // Sort with creation date
            sortedObjects = _.fromPairs(
              _.toPairs(sortedObjects)
                .sort(
                  (a: any, b: any) =>
                    parseInt(b[1].createdAt, 10) - parseInt(a[1].createdAt, 10)
                )
                .slice(0, 3)
            );

            transaction.update(exploreRef, { comment: { ...sortedObjects } });
          }
        }
      });
    }
  });

export const onDelete = functions.firestore
  .document("/comment/{commentId}")
  .onDelete(async (snapshot, _context) => {
    return new Promise<void>((resolve, reject) => {
      const delComment = snapshot.data() as CommentContent;
      const exploreId = delComment.exploreId;

      const exploreRef = db.doc(`explore/${exploreId}`);
      db.collection(`comment`)
        .where(`exploreId`, `==`, exploreId)
        .orderBy("createdAt")
        .get()
        .then((result) => {
          let parsedData: { [commentId: string]: CommentContent } = {};
          let index = 0;
          result.forEach((comment) => {
            if (index < 3) {
              const commentData = comment.data() as CommentContent;
              commentData.id = comment.id;

              parsedData = {
                ...parsedData,
                [comment.id]: {
                  ...commentData,
                },
              };
            }
            index++;
          });
          exploreRef
            .update({ comment: parsedData, commentCounter: result.size })
            .then(() => {
              resolve();
            });
        })
        .catch(reject);
    });
  });
