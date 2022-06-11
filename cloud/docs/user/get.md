# Show All Users

Get the details of all Authenticated User.

**URL** : `/user`

**Method** : `GET`

**Auth** : Bearer Token

## Success Response

**Code** : `200 OK`

**Content examples**

Upon getting a user, the `userId` is the same as `id`. This `id` is the modified document id whereas `userId` is generated from Firebase' Authentication. The `{...}` indicates a continuation of users.

```json
"data": [
    {
        "email": "investor@dev.com",
            "createdAt": "2022-05-31T03:23:52.003Z",
            "organization": "FooBar",
            "bio": "Foo bar foo bar foo bar",
            "postsLiked": [
                "post1id",
                "post2id",
                "post3id"
            ],
            "displayName": "Bambang",
            "city": "Foo",
            "category": "Sport",
            "avatar": "https://firebasestorage.googleapis.com/v0/b/investin-350603.appspot.com/o/avatar%2F11-Jun-20222400166427091768582.jpg?alt=media&token=35bfa239-d1ec-4a2f-ae96-4406f43e3571",
            "userId": "1mHDkpVaJVVWp3JlhFrJNHZDuO12",
            "province": "Bar",
            "investorRole": true,
            "id": "1mHDkpVaJVVWp3JlhFrJNHZDuO12"
    },
    {...} //continuing
]
```

----
# Show Current User

Get the details of the currently Authenticated User.

**URL** : `/user/:userId`

**Method** : `GET`

**Auth** : Bearer Token

## Success Response

**Code** : `200 OK`

**Content examples**

Upon getting a user, the `userId` is the same as `id`. This `id` is the modified document id whereas `userId` is generated from Firebase' Authentication.

```json
"data": [
    {
        "email": "investor@dev.com",
            "createdAt": "2022-05-31T03:23:52.003Z",
            "organization": "FooBar",
            "bio": "Foo bar foo bar foo bar",
            "postsLiked": [
                "post1id",
                "post2id",
                "post3id"
            ],
            "displayName": "Bambang",
            "city": "Foo",
            "category": "Sport",
            "avatar": "https://firebasestorage.googleapis.com/v0/b/investin-350603.appspot.com/o/avatar%2F11-Jun-20222400166427091768582.jpg?alt=media&token=35bfa239-d1ec-4a2f-ae96-4406f43e3571",
            "userId": "1mHDkpVaJVVWp3JlhFrJNHZDuO12",
            "province": "Bar",
            "investorRole": true,
            "id": "1mHDkpVaJVVWp3JlhFrJNHZDuO12"
    }
]
```

## Notes

* Email duplications will not appear and Firebase will automatically detect it upon signing up.