import * as functions from "firebase-functions";
import * as express from "express";
import errorMiddleware from "./middlewares/errorHandler";
import router from "./routes/index";
import * as AuthenticationTrigger from "./triggers/authTrigger";
import * as FirestoreTrigger from "./triggers/userTrigger";

// // Start writing Firebase Functions
// // https://firebase.google.com/docs/functions/typescript
//

const app = express();

app.use(router);
app.use(errorMiddleware);
app.use(express.json());
app.use(
  express.urlencoded({
    extended: true,
  })
);

export default {
  ...FirestoreTrigger,
  ...AuthenticationTrigger,
  api: functions.https.onRequest(app),
};