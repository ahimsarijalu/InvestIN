# Create A Comment

Add a comment on an explore post for Authenticated Users.

**URL** : `/comment/:exploreId`

**Method** : `POST`

**Auth** : Bearer Token

**Body** : 
```json
{
    "userId": " ",
    "displayName": " ",
    "photoURL": " ",
    "text": " "
}
```
## Success Response

**Code** : `201 CREATED`

**Content examples**

```json
"data": {
    "userId": "O6qCCFvCsdVdyVGytDX1KIMrQvS2",
    "displayName": "Investin",
    "photoURL": "https://firebasestorage.googleapis.com...",
    "text": "Some nice comments yo",
    "exploreId": "2KtQl2s9HfMh59pyjzkB",
    "createdAt": "2022-06-05T21:14:55.038Z"
}
```

## Notes

* This creates another collection called `comment` in the Firestore DB and further addition of posts will be under that collection.