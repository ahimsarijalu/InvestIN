# Upload User Avatar

Add a user avatar for Authenticated Users.

**URL** : `/user/upload/:userId`

**Method** : `POST`

**Auth** : Bearer Token

**Body**

```json
multipart/form-data

image avatar.jpeg
```

## Success Response

**Code** : `200 OK`

**Content examples**

An `id` will need to be passed in to the url parameters. The body is in `multipart/form-data` as blob image object.

## Notes

* Only one single image can be uploaded and referenced to an Authenticated User. This image/avatar is the same as setting up your profile picture.