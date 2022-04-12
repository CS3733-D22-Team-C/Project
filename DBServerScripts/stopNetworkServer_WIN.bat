ECHO OFF
ECHO Stopping Network Server...

cd %DERBY_HOME%\lib\
java -jar derbyrun.jar server shutdown

ECHO Stopped Network Server