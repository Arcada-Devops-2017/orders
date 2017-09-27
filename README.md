# orders

## Api documentation

## How to get data

### Get all data for the user that the auth token represents

http://orders.arcada.nitor.zone/api/FetchAll

```
{
   "authToken": “”
}
```

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
    "orderId":"",
    "orderDate":"",
    "product":[
        {
            "id": "",
            "storeId":"",
            "amount":"",
            "price":""
        },
          {
            "id": "",
            "storeId":"",
            "amount":"",
            "price":""
        }],
}

```


### Get data with specific parameters:

http://orders.arcada.nitor.zone/api/FetchData

Ex. If you wanted to get specific users order and a specific product of that order.

```
{
   “authToken”: “”,
   "orderId": "",
   "productId":""
}
```

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
   "message": "Invalid id on object"
}
```
### Success

```
{
    "orderId":"",
    "orderDate":"",
    "product":
        {
            "id": "1",
            "storeId":"1",
            "amount":"5",
            "price":"10€"
        },
}

```


## What kind of format we want order data in

http://orders.arcada.nitor.zone/api/PostData

```
{
"authToken": "",
"product":[
    {
        "id": "",
        "storeId":"",
        "amount":"",
        "price":""
    }, 
    {
        "id": "",
        "storeId":"",
        "amount":"",
        "price":""
    }],

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

