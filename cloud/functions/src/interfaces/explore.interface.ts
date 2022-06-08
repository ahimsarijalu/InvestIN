import { CommentContent } from "./comment.interface";

export type Explore = {
  //userId?: Promise<string>;
  text?: string;
  imageUrl?: string[];
  createdAt?: string;
  likeUsers?: string[];
  comments?: { [commentId: string]: CommentContent };
  commentCounter?: number;
};
