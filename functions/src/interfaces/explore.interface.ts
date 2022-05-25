export type Explore = {
  userId?: Promise<string>;
  commentCount?: number;
  text?: string;
  imageUrl?: string[];
  createdAt?: string;
  isLike?: boolean;
  likeCount?: number;
};
