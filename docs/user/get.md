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
        "email": "investin@dev.com",
        "userId": "1mHDkpVaJVVWp3JlhFrJNHZDuO12",
        "createdAt": "2022-05-31T03:23:52.003Z",
        "id": "1mHDkpVaJVVWp3JlhFrJNHZDuO12",
        "investorRole": false,
        "category": "Fashion",
        "name": "InvestinTest",
    },
    {...}
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
        "email": "investin@dev.com",
        "userId": "1mHDkpVaJVVWp3JlhFrJNHZDuO12",
        "createdAt": "2022-05-31T03:23:52.003Z",
        "id": "1mHDkpVaJVVWp3JlhFrJNHZDuO12",
        "investorRole": false,
        "category": "Fashion",
        "name": "InvestinTest",
    }
]
```

## Notes

* Email duplications will not appear and Firebase will automatically detect it upon signing up.