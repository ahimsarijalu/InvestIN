import { Request, Response, NextFunction } from "express";
import { admin } from "../config/firebase";
import HTTPError from "../utils/HTTPError";

// https://firebase.google.com/docs/auth/admin/verify-id-tokens#web

const isAuthorizedUser = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  const authorization = req.headers.authorization;
  const token = authorization?.split("Bearer ")[1] ?? "";
  if (!token) {
    next(new HTTPError(401, "Empty token"));
  }
  try {
    const decodedToken: admin.auth.DecodedIdToken = await admin
      .auth()
      .verifyIdToken(token);
    console.log(req.params);
    req.params.userId = decodedToken.uid;
    next();
  } catch (error) {
    next(error);
  }
};

export default isAuthorizedUser;
