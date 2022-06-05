# Upload Image

Add an image to an existing explore post for Authenticated Users.

**URL** : `/explore/upload/:docId`

**Method** : `POST`

**Auth** : Bearer Token

**Body**

```json
multipart/form-data

image.jpeg
```

## Success Response

**Code** : `200 OK`

**Content examples**

An `id` will need to be passed in to the url parameters. The body is in `multipart/form-data` as blob image object.

![](../upload%20image%20postman.png)

## Notes

* For the time being, only one single image can be uploaded and referenced to an explore post.