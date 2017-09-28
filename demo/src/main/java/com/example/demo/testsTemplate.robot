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
    Set Global Variable     ${counter}
    log to console      \nStarting JSON\n${resp.json()}
    
    ${jsonstring}=     catenate
    ...  ${resp.json()}
    
    ${json_string}=     Replace String      ${jsonstring}       '       "
  

    log to console       \nConverted JSON:\n${json_string}
    ${json}=             evaluate        json.loads('''${json_string}''')    json
   
    :FOR   ${item}   in  @${json["data"]}
    \   log to console      \n${counter}\n  
    \   log to console     ${json["data"][${counter}]}
    \   set to dictionary    ${json["data"][${counter}]}    id=the new value
    \   Dictionary Should Contain Key     ${json_string}        precision
    \   ${counter}=     ${counter+1}
    \   log to console      \n${counter}\n  


*** comments ***
    :FOR   ${item}   in  @${json["data"]}
      \   log to console      \n${counter}\n
    \   set to dictionary    ${json["data"][0]}    id=the new value
    \   ${json_string}=      evaluate        json.dumps(${json})                 json
    \    Dictionary Should Contain Key     ${json_string}        precision
    \   ${counter++}

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

        \ set to dictionary    ${json["main"]}    id=the new value
    \ ${json_string}=      evaluate        json.dumps(${json})
    \ Dictionary Should Contain Key     ${json_string}        temp
