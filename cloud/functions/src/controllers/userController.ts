import { Request, Response, NextFunction } from "express";
import { v4 as uuidv4 } from "uuid";
import * as path from "path";
import * as os from "os";
import * as fs from "fs";
import Busboy from "busboy";

import { bucket, db } from "../config/firebase";
import { User } from "../interfaces/user.interface";
import returnSuccess from "../utils/successHandler";

const updateUser = async (req: Request, res: Response, next: NextFunction) => {
  const {
    displayName,
    avatar,
    bio,
    city,
    province,
    organization,
    category,
    postsLiked,
  } = req.body;
  const { docId } = req.params;

  try {
    const querySnapshot = db.collection("users").doc(docId);
    const userObject: User = {
      displayName,
      avatar,
      bio,
      city,
      province,
      organization,
      category,
      postsLiked,
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

const uploadPhotoURL = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  try {
    const bb = Busboy({
      headers: req.headers,
      limits: {
        fileSize: 2 * 1024 * 1024, // max image size to 1 MB
        files: 1, // Limit file upload
      },
    });

    const tmpdir = os.tmpdir();
    let imageFileName: string;
    const uuid = uuidv4();

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
    let photoURL: string = "";

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
      const storageFolder = "avatar";
      for (const file in uploads) {
        await bucket.upload(uploads[file], {
          destination: `${storageFolder}/${imageFileName}`,
          resumable: false,
          metadata: {
            metadata: {
              firebaseStorageDownloadTokens: uuid,
              cacheControl: "public, max-age=31536000",
            },
          },
        });
        photoURL = `https://firebasestorage.googleapis.com/v0/b/investin-350603.appspot.com/o/${storageFolder}%2F${imageFileName}?alt=media&token=${uuid}`;
        await db
          .collection(`users`)
          .doc(req.params.docId) // user id
          .update({ avatar: photoURL.toString() });
        fs.unlinkSync(uploads[file]);
      }
      returnSuccess(201, res, `Image(s) successfully uploaded`, {
        avatar: photoURL,
      });
    });

    // @ts-ignore
    bb.end(req.rawBody);
  } catch (err) {
    next(err);
  }
};

export { updateUser, getAllUsers, getUser, uploadPhotoURL };
