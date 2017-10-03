*** Settings ***
Library     Collections
Library     RequestsLibrary
Library     HttpLibrary.HTTP
Library     String

*** Variables ***
${page}      http://samples.openweathermap.org
${counter}=     0

*** Test Cases ***


Get page and test if string exists
    Create Session      authApi     ${page}
    ${resp}=        Get Request     authApi     /pollution/v1/co/0.0,10.0/2016-12-25T01:04:08Z.json?appid=b1b15e88fa797225412429c1c50c122a1
    Set Global Variable     ${resp}
    Should Be Equal As Strings      ${resp.status_code}     200

Dictionary contains key
    ${counter}      Set Variable        ${0}
    log to console      \nStarting JSON\n${resp.json()}

    ${jsonstring}=     catenate
    ...  ${resp.json()}

    ${json_string}=     Replace String      ${jsonstring}       u'       "
    ${json_string}=     Replace String      ${jsonstring}       '       "

    log to console       \nConverted JSON:\n${json_string}
    ${json}=             evaluate        json.loads('''${json_string}''')    json


    log to console      \nStarting loop\n
    :FOR   ${item}   in  @${json["data"]}
        \   log to console      \nLoop count: ${counter}\n
        \   log to console     ${json["data"][${counter}]}
        \   set to dictionary    ${json["data"][${counter}]}    id=the new value
        \   ${json_string}=      evaluate        json.dumps(${json["data"][${counter}]})        json
        \   log to console      \nDumperinoe ${json_string}\n
        \   Dictionary Should Contain Key     ${json_string}        precision
        \   ${counter}=          evaluate       ${counter}+1
        \   log to console      \n${counter}\n


*** comments ***
    :FOR   ${item}   in  @${json["data"]}
      \   log to console      \n${counter}\n
    \   set to dictionary    ${json["data"][0]}    id=the new value
    \   ${json_string}=      evaluate        json.dumps(${json})                 json
    \    Dictionary Should Contain Key     ${json_string}        precision
    \   ${counter++}

Testing
Old Stuff

