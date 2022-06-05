# Delete Explore

Delete an explore post for Authenticated Users.

**URL** : `/explore/:docId`

**Method** : `DELETE`

**Auth** : Bearer Token

## Success Response

**Code** : `200 OK`

**Content examples**

Data will be null and only a message will be returned back as a response.

```json
"success": true,
"message": "Explore deleted successfully",
"data": null
```