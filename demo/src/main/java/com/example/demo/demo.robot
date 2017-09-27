*** Settings ***
Library     Selenium2Library
Suite Setup     Go to homepage
Suite Teardown  Close All Browsers

*** Variables ***
${HOMEPAGE}      http://www.google.fi
${BROWSER}  chrome

*** Test Cases ***
Google github and find github
    Google and check results        github      https://github.com/

*** Keywords ***
Google and check results
    [Arguments]     ${searchkey}    ${result}
    Input text      id=lst-ib   ${searchkey}
    Press Key    name=q    \\13
    Wait Until Page Contains      ${result}
Go to homepage
    Open Browser   ${HOMEPAGE}      ${BROWSER}