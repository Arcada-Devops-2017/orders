*** Settings ***
Library     Collections
Library     RequestsLibrary
Library     HttpLibrary.HTTP
Library     String

*** Variables ***
${pageOrder}   http://orders.arcada.nitor.zone

*** Test Cases ***
Get page and test if string exists
    Create Session      authApi     ${pageOrder}
    ${resp}=      Get Request     FetchAllApi    /api/FetchAll?authToken="orderRobotTestAuth"
    Set Global Variable     ${resp}
    Should Be Equal As Strings      ${resp.status_code}     200

Orders resp FetchAll contains keys
    log to console      \nStarting JSON\n${resp.json()}

    ${jsonstring}=     catenate
    ...  ${resp.json()}

    ${json_string}=     Replace String      ${jsonstring}       u'       "
    ${json_string}=     Replace String      ${jsonstring}       '       "


    log to console       \nConverted JSON:\n${json_string}
    ${json}=             evaluate        json.loads('''${json_string}''')    json
    set to dictionary    ${json["orderData"][0]}    id=the new value
    ${json_string}=      evaluate        json.dumps(${json})                 json
    Dictionary Should Contain Key     ${json_string}        orderId
    Dictionary Should Contain Key     ${json_string}        orderDate
    set to dictionary    ${json["product"]}    products=the new value
    log to console      \nFound key${json["product"]}\n
    Dictionary Should Contain Key     ${json_string}        id
    Dictionary Should Contain Key     ${json_string}        storeId
    Dictionary Should Contain Key     ${json_string}        amount
    Dictionary Should Contain Key     ${json_string}        price


Post Orders

 [Tags]  post
    Create Session  httpbin  ${pageOrder}
    ${file_data}=  Get Binary File  ${CURDIR}${/}data.json
    &{files}=  Create Dictionary  file=${file_data}
    ${resp}=  Post Request  httpbin  /api/PostData  files=${files}
    ${file}=  To Json  ${resp.json()['files']['file']}
    Dictionary Should Contain Key  ${file}  200
    Dictionary Should Contain Key  ${file}  OK

*** comments ***

Testing
working copy

