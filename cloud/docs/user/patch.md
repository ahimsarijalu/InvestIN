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
    "organization": " ",
    "category": " ",
    "imageUrl": [" ", " "],
    "postsLiked": [" ", " ", " "]
}
```

Note that `id`, `userId`, `createdAt`, `investorRole` and `email` are currently read only fields.

## Success Responses

**Condition** : Data provided is valid and User is Authenticated.

**Code** : `200 OK`

**Content example** : Response will reflect back all the updated information. A
User with `name` of 'Tester' sets their name, passing `investorRole` as 'false' and `organization` as 'FoobBar'.

```json
"data": {
        "name": "Tester",
        "bio": "Foo bar foo bar foo bar",
        "city": "Foo",
        "province": "Bar",
        "organization": "FooBar",
        "category": "Internet Publishing",
        "imageUrl": [
            "https://firebasestorage.googleapis.com/v0/...",
            "https://firebasestorage.googleapis.com/v0/..."
        ],
        "postsLiked": [
            "post1id",
            "post2id",
            "post3id"
        ]
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
* Partial data update is not allowed as of this version.