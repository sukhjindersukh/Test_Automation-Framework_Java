<<<<<<< HEAD
@echo off
cls
echo "Test suite is starting"
call mvn clean
call mvn package
echo Test Suite run has completed... 
echo Please Press anything to exit :D
Pause>Null
=======
@echo off
cls
echo "Test suite is starting"
call mvn test
echo Test Suite run has completed 
echo Please Press anything to exit 
Pause>Nul
>>>>>>> refs/heads/master
