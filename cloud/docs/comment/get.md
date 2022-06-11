# Show All Comments

Get the details of all comments on an explore post for Authenticated Users.

**URL** : `/comment/:exploreId`

**Method** : `GET`

**Auth** : Bearer Token

## Success Response

**Code** : `200 OK`

**Content examples**

```json
"data": [
    {
        "comment": [
            {
                "createdAt": "2022-05-31T19:47:36.671Z",
                "photoURL": "https://firebasestorage.googleapis.com...",
                "userId": "7777777777777",
                "exploreId": "2KtQl2s9HfMh59pyjzkB",
                "displayName": "Investout ahaha",
                "text": "Nice comment PagMan Zulu"
            }
        ]
    }
]
```

## Notes

* Planned pagination is still an ongoing work, for the time being the comment section is not utilized further.