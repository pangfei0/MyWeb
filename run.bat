@echo off

set MVN=mvn
set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m
set ENVIRONMENT=dev
set COMMAND=%1
set PORT=8080
SHIFT

:loop
IF NOT "%1"=="" (
    IF "%1"=="-env" (
        SET ENVIRONMENT=%2
        SHIFT
    )
    IF "%1"=="-port" (
        SET PORT=%2
        SHIFT
    )
    SHIFT
    GOTO :loop
)

if "%COMMAND%" == "" (
    call:Usage
)

if "%COMMAND%" == "build" (
    %MVN% compile
    %MVN% clean install
)

if "%COMMAND%" == "test" (
    %MVN% test
)

if "%COMMAND%" == "web" (
    call:ShowVariables
    %MVN% spring-boot:run
)

goto:eof 

:ShowVariables
echo ******************************************************
echo              Environment: %ENVIRONMENT%
echo              PORT: %PORT%
if "%DEBUG%" == "true" (
echo              Debug Port: %DEBUG_PORT%
)
echo ******************************************************
goto:eof

:Usage
echo.
echo Available Commands:
echo.
echo * build    Build whole projects
echo * web      Run Website
echo        -env [set the environment, available value: prod/dev/test]
echo * netease  Run API service for Netease
echo        -env [set the environment, available value: prod/dev/test]
goto:eof
