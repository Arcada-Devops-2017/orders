# orders

## Api documentation

## How to get data

### Get all data for the user that the auth token represents

http://orders.arcada.nitor.zone/api/FetchAll?AuthToken=””

### Get data with specific parameters:

http://orders.arcada.nitor.zone/api/FetchData

Ex. If you wanted to get all orders for current user containing product id 1

http://orders.arcada.nitor.zone/api/FetchData?AuthToken=””&product-id=”1”
```
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
    "shippingInfo":
        {
            "firstName",
            "lastName",
            "phoneNumber",
            "address":"",
            "postalCode":"",
            "country":"",
	    "eta":""
        }
}

```
### If auth token fails
```
{
   “status”: “401”
   "message": "Invalid authToken"
}
```

### If failed to get data
```
{
    "status": "404",
    "response": "Failed to get data because of {errorMsg}"
}
```

## What kind of format we want order data in

http://orders.arcada.nitor.zone/api/SendData?JsonUrl=""

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
"shippingInfo":
    {
        “firstName”,
        “lastName”,
        “phoneNumber”,
        "address":"",
        "postalCode":"",
        "country":"",
        "eta":""
    }
}
```

### Error that can be returned
```
{
   “status”: “401”
   "message": "Invalid (error message)"
}

```
Where error message is the field(s) that are invalid.
