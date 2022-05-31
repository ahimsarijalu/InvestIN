export interface User {
  userId?: string;
  email?: string;
  name?: string;
  createdAt?: string;
  bio?: string;
  city?: string;
  province?: string;
  investorRole?: boolean;
  organization?: string;
  category?: string;
  imageUrl?: string[];
  postsLiked?: string[];
}
