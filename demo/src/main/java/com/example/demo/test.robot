*** Settings ***
Library     Collections
Library     RequestsLibrary

*** Variables ***
${page}      http://api.github.com

*** Test Cases ***
Get page and test if string exists
    Create Session      authApi     ${page}
    ${resp}=        Get Request     authApi     /users/bulkan
    Should Be Equal As Strings      ${resp.status_code}     200
    Dictionary Should Contain Key     ${resp.json()}        orderId
    Dictionary Should Contain Key     ${resp.json()}        orderDate
    Dictionary Should Contain Key     ${resp.json()}        product
    Dictionary Should Contain Key     ${resp.json()}        id
    Dictionary Should Contain Key     ${resp.json()}        storeId
    Dictionary Should Contain Key     ${resp.json()}        amount
    Dictionary Should Contain Key     ${resp.json()}        price
