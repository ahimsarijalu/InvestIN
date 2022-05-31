interface CommentContent {
  text: string;
  exploreId?: string;
}

export interface Comment {
  comment: CommentContent[];
}
