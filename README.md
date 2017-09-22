# orders

## Api documentation

### How to get data

If auth token fails
```
{
   “status”: “401”
   "message": "Invalid authToken"
}
```
Get all data for the user that the auth token represents

http://orders.arcada.nitor.zone/api//FetchAll?AuthTok=””

Get data with specific parameters:

http://orders.arcada.nitor.zone/api/FetchData?AuthToken=””&orderId=””&orderDate=””&product-storeId=””&product-amount=””&product-price=””shippingInfo-firstName&shippingInfo-lastName&shippingInfo-phoneNumber&shippingInfo-address=””&shippingInfo-postalcode=””&shippingInfo-country=””&shippingInfo-eta=””

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

If failed to get data
```
{
    "status": "404",
    "response": "Failed to get data because of {errorMsg}"
}
```
### What kind of format we want order data in

http://orders.arcada.nitor.zone/api/SendData?AuthToken=””&orderId=””&product-storeId=””&product-amount=””&product-price=””shippingInfo-firstName&shippingInfo-lastName&shippingInfo-phoneNumber&shippingInfo-address=””&shippingInfo-postalcode=””&shippingInfo-country=””&shippingInfo-eta=””

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
Error that can be returned
```
{
   “status”: “401”
   "message": "Invalid (error message)"
}

```
Where error message is the field(s) that are invalid.
