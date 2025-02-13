@echo off

:: Path to the JAR file
set JAR_FILE=target\sftp-client-1.0-SNAPSHOT-shaded.jar

:: Function to display usage
:usage
echo Usage: %0 [upload^|download] [host] [user] [password] [remoteDir] [localFilePath^|downloadPath]
echo If any parameter is not provided, the default from src\main\resources\config.properties will be used.
goto :eof

:: Check if the correct number of arguments is provided
if "%~1"=="" (
  call :usage
  exit /B 1
)

:: Run the operation
java -jar %JAR_FILE% %*