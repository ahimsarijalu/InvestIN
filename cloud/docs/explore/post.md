# Create Explore

Add an explore post for Authenticated Users.

**URL** : `/explore`

**Method** : `POST`

**Auth** : Bearer Token

**Body**

```json
{
    "text": " ",
}
```

## Success Response

**Code** : `200 OK`

**Content examples**

This will create a new explore post.

```json
"data": {
        "id": "gTENpCvzVCY9w4KDvZJE",
        "text": "This is a text of string for the user to input whatever they want to express or vent with investin.",
        "commentCounter": 0,
        "createdAt": "2022-06-11T03:44:04.538Z"
    }
```

## Notes

* Integration between Auth User collection in order to obtain the `author` has not been implemented yet