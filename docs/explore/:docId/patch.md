# Update Explore

Edit one specific explore post for Authenticated Users.

**URL** : `/explore/:docId`

**Method** : `PATCH`

**Auth** : Bearer Token

**Body**

```json
{
    "text": " ",
    "imageUrl": [" "],
    "likeUsers": [" "],
}
```

## Success Response

**Code** : `200 OK`

**Content examples**

Data will be null and only a message will be returned back as a response.

```json
"data": {
    "text": "foo bar",
    "imageUrl": "https://firebasestorage.googleapis.com/v0...",
    "likeUsers": [
        "user1",
        "user2",
        "user3"
    ]
}
```