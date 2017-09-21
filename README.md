# orders
The component keeping track of orders

## Api documentation

### How we send data to frontEnd

```
{
        "response":"OK or Failed to get data because of ",
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
            "address":"",
            "postalCode":"",
            "country":""
        }
}
```
### END How we send data to frontEnd 


### How to get data from us
```
{
    "response":"authToken OK or Failed",
    "authToken":""
}

```
### END how to get data from us


### How to save order data

```
{
    "response":"OK or wrong value on field",
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
        "address":"",
        "postalCode":"",
        "country":"",
        "eta":""
    }
}


```

### END How to save order data