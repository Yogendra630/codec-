@echo off
echo Chat Application - Java Direct Run
echo =================================
echo.

echo Checking Java installation...
java -version
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    pause
    exit /b 1
)

echo.
echo Building and running the application...
echo.

echo Step 1: Compiling Java files...
javac -cp "lib/*" -d target/classes src/main/java/com/chatapp/*.java src/main/java/com/chatapp/*/*.java
if %errorlevel% neq 0 (
    echo ERROR: Compilation failed
    pause
    exit /b 1
)

echo Step 2: Running the application...
java -cp "target/classes;lib/*" com.chatapp.Main

pause
