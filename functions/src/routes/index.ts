import * as express from "express";
import {
  addExplore,
  deleteExplore,
  getAllExplore,
  getExplore,
  updateExplore,
} from "../controllers/exploreController";
import {
  getAllUsers,
  getUser,
  updateUser,
} from "../controllers/userController";
import isAuthorizedUser from "../middlewares/authHandler";

const router = express.Router();
router.use(express.json());

router.use(express.urlencoded({ extended: true }));

router.get("/explore", isAuthorizedUser, getAllExplore);
router.get("/explore/:docId", isAuthorizedUser, getExplore);
router.post("/explore", isAuthorizedUser, addExplore);
router.patch("/explore/:docId", isAuthorizedUser, updateExplore);
router.delete("/explore/:docId", isAuthorizedUser, deleteExplore);

router.get("/user", isAuthorizedUser, getAllUsers);
router.get("/user/:docId", isAuthorizedUser, getUser);
router.patch("/user/:docId", isAuthorizedUser, updateUser);

export default router;
