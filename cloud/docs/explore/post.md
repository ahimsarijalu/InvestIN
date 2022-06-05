# Create Explore

Add an explore post for Authenticated Users.

**URL** : `/explore`

**Method** : `POST`

**Auth** : Bearer Token

**Body**

```json
{
    "text": " ",
    "imageUrl": [" "],
}
```

## Success Response

**Code** : `200 OK`

**Content examples**

This will create a new explore post.

```json
"data": {
        "text": "This is a text of string for the user to input whatever they want to express or vent with investin.",
        "imageUrl": [
            "https://firebasestorage.googleapis.com/v0...",
        ],
        "createdAt": "2022-06-05T18:56:46.540Z"
    }
```

## Notes

* Integration between Auth User collection in order to obtain the `author` has not been implemented yet