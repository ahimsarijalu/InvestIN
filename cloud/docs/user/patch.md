# Update Current User

Allow the Authenticated User to update their details.

**URL** : `/user/:userId`

**Method** : `PATCH`

**Auth** : Bearer Token

**Body**

```json
{
    "name": " ",
    "bio": " ",
    "city": " ",
    "province": " ",
    "investorRole": boolean,
    "organization": " ",
    "category": " ",
    "imageUrl": [" ", " "],
    "postsLiked": [" ", " ", " "]
}
```

Note that `id`, `userId`, `createdAt` and `email` are currently read only fields.

## Success Responses

**Condition** : Data provided is valid and User is Authenticated.

**Code** : `200 OK`

**Content example** : Response will reflect back all the updated information.

```json
"data": {
        "userId": "1mHDkpVaJVVWp3JlhFrJNHZDuO12",
        "createdAt": "2022-05-31T03:23:52.003Z",
        "investorRole": false,
        "bio": "Foo bar foo bar foo bar",
        "email": "investor@dev.com",
        "postsLiked": [
            "post1id",
            "post2id",
            "post3id"
        ],
        "displayName": "Bambang",
        "city": "Foo",
        "category": "Sport",
        "province": "Bar",
        "organization": "FooBar",
        "avatar": "https://firebasestorage.googleapis.com/v0/b/investin-350603.appspot.com/o/avatar%2Favatar_placeholder.png?alt=media&token=7057cc7a-194f-4420-9f81-c906d681e21d",
        "id": "1mHDkpVaJVVWp3JlhFrJNHZDuO12"
    }
```

## Error Response

**Condition** : If provided data is invalid, e.g. missing field.

**Code** : `400 BAD REQUEST`

**Content example** :

```json
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<title>Error</title>
</head>

<body>
	<pre>Bad Request</pre>
</body>

</html>
```

## Notes

* Endpoint will ignore irrelevant and read-only data such as parameters that
  don't exist, or fields that are not editable like `id` or `email`.
* Partial data update is not allowed/doable as of this version.