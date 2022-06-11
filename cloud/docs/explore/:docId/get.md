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
        "createdAt": "2022-05-30T08:48:14.599Z",
            "id": "2KtQl2s9HfMh59pyjzkB",
            "text": "foo bar",
            "commentCounter": 1,
            "category": "Sport",
            "comment": {
                "EExpG8rCJEMuwwQf8q5Q": {
                    "avatar": "https://firebasestorage.googleapis.com/v0/b/investin-350603.appspot.com/o/avatar%2FRaiden-Shogun-Talents.jpeg?alt=media&token=36defcfc-a095-438f-8575-73e710189cec",
                    "author": "Forsen",
                    "exploreId": "2KtQl2s9HfMh59pyjzkB",
                    "text": "This is a real comment ayo..!?",
                    "id": "EExpG8rCJEMuwwQf8q5Q",
                    "userId": "tPUYSi1OgvhpZCvtFz7rYDfIHF03",
                    "createdAt": "2022-06-09T13:23:01.960Z"
                }
            },
            "authorId": "1mHDkpVaJVVWp3JlhFrJNHZDuO12",
            "author": "Bambang",
            "imageUrl": [
                "https://firebasestorage.googleapis.com/v0/b/investin-350603.appspot.com/o/explore%2Fwindows_2021_visual2_day_hr-1920x1200.jpg?alt=media&token=542d93bb-f265-45d4-9445-dd4ab40505f9"
            ],
            "avatar": "https://gravatar.com/avatar/ef6a35db480ffd35f77408f8d00c07df?s=400&d=robohash&r=x"
    }
]
```