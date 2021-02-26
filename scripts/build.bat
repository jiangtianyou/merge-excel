@echo off
cd /d %~dp0
cd ..
mvn install  & mvn package