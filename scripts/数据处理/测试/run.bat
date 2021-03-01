@echo off
cd /d %~dp0

java -jar %~dp0\\excel-0.0.1-SNAPSHOT.jar %~dp0 b1b2d6-d15

set /p input= Success, Press Eenter to exit!
