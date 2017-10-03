*** Settings ***
Library     Collections
Library     RequestsLibrary
Library     HttpLibrary.HTTP
Library     String

*** Variables ***
${page}      http://samples.openweathermap.org

*** Test Cases ***
Get page and test if string exists
    Create Session      authApi     ${page}
    ${resp}=        Get Request     authApi     /data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1
    Set Global Variable     ${resp}
    Should Be Equal As Strings      ${resp.status_code}     200

Dictionary contains key
    log to console      \nStarting JSON\n${resp.json()}

    ${jsonstring}=     catenate
    ...  ${resp.json()}

    ${json_string}=     Replace String      ${jsonstring}       '       "


    log to console       \nConverted JSON:\n${json_string}
    ${json}=             evaluate        json.loads('''${json_string}''')    json
    set to dictionary    ${json["orderData"][0]}    id=the new value
    ${json_string}=      evaluate        json.dumps(${json})                 json
    Dictionary Should Contain Key     ${json_string}        orderId
    Dictionary Should Contain Key     ${json_string}        orderDate

    log to console      \nFound key\n

*** comments ***

Testing
working copy

Get page and test if string exists
    Create Session      authApi     ${page}
    ${resp}=        Get Request     authApi     /data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1
    Set Global Variable     ${resp}
    Should Be Equal As Strings      ${resp.status_code}     200

Dictionary contains key
    log to console      \nStarting JSON\n${resp.json()}

    ${jsonstring}=     catenate
    ...  ${resp.json()}

    ${json_string}=     Replace String      ${jsonstring}       '       "


    log to console       \nConverted JSON:\n${json_string}
    ${json}=             evaluate        json.loads('''${json_string}''')    json
    set to dictionary    ${json["weather"][0]}    id=the new value
    log to console       \weather JSON:\n${json["weather"][0]}
    ${json_string}=      evaluate        json.dumps(${json})                 json

    Dictionary Should Contain Key     ${json_string}        main
    log to console      \nFound key\n
