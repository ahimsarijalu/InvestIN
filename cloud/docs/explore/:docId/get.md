# Show An Explore

Only show one specific explore post for Authenticated Users.

**URL** : `/explore/:docId`

**Method** : `GET`

**Auth** : Bearer Token

## Success Response

**Code** : `200 OK`

**Content examples**

Data will be null and only a message will be returned back as a response.

```json
"data": [
    {
        "avatar": "https://gravatar.com/avatar/ef6a35db480ffd35f77408f8d00c07df?s=400&d=robohash&r=x",
        "author": "Bambang",
        "text": "foo bar",
        "likeUsers": [
            "user1",
            "user2",
            "user3"
        ],
        "imageUrl": [
            "https://firebasestorage.googleapis.com/v0/b/investin-350603.appspot.com/o/explore%2FRaiden-Shogun-Talents.jpeg?alt=media"
        ],
        "createdAt": "2022-05-30T08:48:14.599Z",
        "category": "Sport"
    }
]
```