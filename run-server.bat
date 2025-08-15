@echo off
echo Starting Chat Server...
echo.
echo Make sure MySQL is running and database.properties is configured correctly.
echo.
mvn exec:java -Dexec.mainClass="com.chatapp.server.ChatServer"
pause
