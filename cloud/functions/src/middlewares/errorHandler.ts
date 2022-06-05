import { Request, Response, NextFunction } from "express";
import HTTPError from "../utils/HTTPError";

export default (
  err: HTTPError,
  req: Request,
  res: Response,
  next: NextFunction
) => {
  console.log(err);
  return res.status(err.status || 500).json({
    isSuccess: false,
    message: err.message || "Unknown error happened",
  });
};
