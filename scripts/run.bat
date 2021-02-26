@echo off
cd /d %~dp0
echo %~dp0 %*  > debug.txt


java -jar %~dp0\\excel-0.0.1-SNAPSHOT.jar %*

set /p input= Success, Press Eenter to exit!
