@echo off
cls
echo "Test suite is starting"
call mvn clean
call mvn test
echo Test Suite run has completed 
echo Please Press anything to exit 
Pause>Nul