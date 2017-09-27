# orders

## Api documentation

## How to get data

### Get all data for the user that the auth token represents

http://orders.arcada.nitor.zone/api/FetchAll?authToken=""


### Response

### If auth token fails
```
{
   “status”: “401”
   "message": "Invalid authToken"
}
```
### if all ok

```
{
    "authToken":"",
    "orderData":[
    {
    "orderId":"",
    "orderDate":"",
    "product":
        {
            "id": "",
            "storeId":"",
            "amount":"",
            "price":""
        },
    "orderId":"",
    "orderDate":"",
    "product":
        {
            "id": "",
            "storeId":"",
            "amount":"",
            "price":""
        }
    }],
}

```


### Get data with specific parameters:

http://orders.arcada.nitor.zone/api/FetchData?authToken=""&orderId=""

Ex. If you wanted to get a users specific order


### Response

### if authToken invalid
```
{
   “status”: “401”
   "message": "Invalid authToken"
}
```
### if id invalid

```
{
   “status”: “401”
   "message": "Invalid orderId"
}
```
### Success

```
{
    "authToken":"",
    "orderId":"",
    "orderDate":"",
    "product":
        {
            "id": "",
            "storeId":"",
            "amount":"",
            "price":""
        },
}

```


## What kind of format we want order data in

http://orders.arcada.nitor.zone/api/PostData

```
{
"authToken": "",
"product":
    {
        "id": "",
        "storeId":"",
        "amount":"",
        "price":""
    },

}
```

### Error that if authToken failed
```
{
   “status”: “401”
   "message": "Invalid authToken"
}

```
### error in (object) {errorMessage}

```
{
   “status”: “401”
   "message": "error in object"
}

```

