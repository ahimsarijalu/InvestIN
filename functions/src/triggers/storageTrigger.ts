import * as functions from "firebase-functions";
import updateURLOnExplore from "./methods/storageMethods";

export const onArchive = functions.storage
  .object()
  .onArchive((_objectMetadata, _context) => {
    console.log("storage archived");
  });

export const onDelete = functions.storage
  .object()
  .onDelete((_objectMetadata, _context) => {
    console.log("storage deleted");
    updateURLOnExplore(_objectMetadata);
  });

export const onFinalize = functions.storage
  .object()
  .onFinalize((_objectMetadata, _context) => {
    console.log("storage finalized");
  });

export const onMetadataUpdate = functions.storage
  .object()
  .onMetadataUpdate((_objectMetadata, _context) => {
    console.log("storage metadata updated");
  });
