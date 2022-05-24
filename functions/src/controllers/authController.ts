import { Request, Response, NextFunction } from "express";
import HTTPError from "../utils/HTTPError";
import { firebase } from "../config/firebase";
import returnSuccess from "../utils/successHandler";

const createUser = async (req: Request, res: Response, next: NextFunction) => {
  const { email, password } = req.body;
  try {
    if (!email || !password) {
      throw new HTTPError(400, "Email/password is empty");
    }
    const user = await firebase
      .auth()
      .createUserWithEmailAndPassword(email, password);
    const mess = "Account successfully created";
    returnSuccess(201, res, mess, user);
  } catch (error) {
    next(error);
  }
};

const login = async (req: Request, res: Response, next: NextFunction) => {
  const { email, password } = req.body;
  try {
    if (!email || !password) {
      throw new HTTPError(400, "Email/password is empty");
    }
    await firebase.auth().signInWithEmailAndPassword(email, password);
    const mess = "Account successfully logged in";
    let token = await firebase.auth().currentUser?.getIdToken(true);
    returnSuccess(201, res, mess, token);
  } catch (error) {
    next(error);
  }
};

export { createUser, login };
