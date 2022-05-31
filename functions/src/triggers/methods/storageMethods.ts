import { db } from "../../config/firebase";
import { EventContext } from "firebase-functions";
import { Explore } from "../../interfaces/explore.interface";
import { ObjectMetadata } from "firebase-functions/lib/providers/storage";

const updateURLOnExplore = async (
  objectMetadata: ObjectMetadata,
  context: EventContext
) => {
  const imageUrl = objectMetadata.mediaLink;
  const batch = db.batch();
  try {
    const explores = await db
      .collection("explore")
      .where("imageUrl", "==", imageUrl)
      .get();

    const exploreObject: Explore = {
      imageUrl: undefined,
    };
    explores.forEach((explore: any) => batch.update(explore, exploreObject));
    await batch.commit();
  } catch (error) {
    console.log(error);
  }
};

export default updateURLOnExplore;
