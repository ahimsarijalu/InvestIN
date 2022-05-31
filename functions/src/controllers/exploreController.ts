import { Request, Response, NextFunction } from "express";
import { v4 as uuidv4 } from "uuid";
import * as path from "path";
import * as os from "os";
import * as fs from "fs";
import Busboy from "busboy";

import { db, bucket } from "../config/firebase";
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
      text,
      imageUrl,
      createdAt: new Date().toISOString(),
    };
    querySnapshot.set(exploreObject);
    returnSuccess(201, res, "Explore added successfully", exploreObject);
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
  const { text, imageUrl, likeUsers } = req.body;

  try {
    const querySnapshot = db.collection("explore").doc(docId);

    const exploreObject: Explore = {
      text,
      imageUrl,
      likeUsers,
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

const uploadImage = async (req: Request, res: Response, next: NextFunction) => {
  try {
    const bb = Busboy({
      headers: req.headers,
      limits: {
        fileSize: 2 * 1024 * 1024, // max image size to 1 MB
        files: 2, // Limit file upload
      },
    });

    const tmpdir = os.tmpdir();
    let imageFileName: string;
    //let imagePath: string;

    // This object will accumulate all the fields, keyed by their name
    const fields: any = {};

    // This object will accumulate all the uploaded files, keyed by their name.
    const uploads: any = {};

    // This code will process each non-file field in the form.
    bb.on("field", (fieldname, val) => {
      /**
       *  TODO(developer): Process submitted field values here
       */
      console.log(`Processed field ${fieldname}: ${val}.`);
      fields[fieldname] = val;
    });

    const fileWrites: any = [];
    const imageUrls: string[] = [];

    // This code will process each file uploaded.
    bb.on("file", (fieldname, file, { filename }) => {
      // Note: os.tmpdir() points to an in-memory file system on GCF
      // Thus, any files in it must fit in the instance's memory.
      console.log(`Processed file ${filename}`);
      const filepath = path.join(tmpdir, filename);
      imageFileName = filename;
      //imagePath = filepath;
      uploads[fieldname] = filepath;

      const writeStream = fs.createWriteStream(filepath);
      file.pipe(writeStream);

      // File was processed by Busboy; wait for it to be written.
      // Note: GCF may not persist saved files across invocations.
      // Persistent files must be kept in other locations
      // (such as Cloud Storage buckets).
      const promise = new Promise((resolve, reject) => {
        file.on("end", () => {
          writeStream.end();
        });
        writeStream.on("finish", resolve);
        writeStream.on("error", reject);
      });
      fileWrites.push(promise);
    });

    // Triggered once all uploaded files are processed by Busboy.
    // We still need to wait for the disk writes (saves) to complete.
    bb.on("finish", async () => {
      await Promise.all(fileWrites);

      for (const file in uploads) {
        await bucket.upload(uploads[file], {
          destination: `explore/${imageFileName}`,
          resumable: false,
          metadata: {
            firebaseStorageDownloadTokens: uuidv4(),
            cacheControl: "public, max-age=31536000",
          },
        });
        imageUrls.push(
          `https://firebasestorage.googleapis.com/v0/b/investin-350603.appspot.com/o/explore%2F${imageFileName}?alt=media`
        );
        await db
          .collection(`explore`)
          .doc(req.params.docId)
          .update({ imageUrl: imageUrls });
        fs.unlinkSync(uploads[file]);
      }
      returnSuccess(201, res, `Image(s) successfully uploaded`, {
        imageUrl: imageUrls,
      });
    });

    // @ts-ignore
    bb.end(req.rawBody);
  } catch (err) {
    next(err);
  }
};

// export const deleteFile = async (
//   req: Request,
//   res: Response,
//   next: NextFunction
// ) => {
//   const srcFilename = req.params.filename;
//   try {
//     await bucket.file(srcFilename).delete();
//     returnSuccess(200, res, "Deleted Image", null);
//   } catch (error) {
//     next(error);
//   }
// };

export {
  addExplore,
  getAllExplore,
  getExplore,
  updateExplore,
  deleteExplore,
  uploadImage,
};
