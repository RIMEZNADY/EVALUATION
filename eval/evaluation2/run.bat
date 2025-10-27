@echo off
echo ====================================
echo  Projet Management - Installation
echo ====================================
echo.

echo Step 1: Downloading Maven dependencies...
call mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo.
    echo ERROR: Failed to build project. Please check your Maven and MySQL configuration.
    pause
    exit /b 1
)

echo.
echo ====================================
echo  Installation complete!
echo ====================================
echo.
echo Before running tests, make sure:
echo  1. MySQL is running
echo  2. Database 'projet_management' exists
echo  3. Update hibernate.cfg.xml with your MySQL credentials
echo.
echo To run the test program:
echo   mvn exec:java -Dexec.mainClass="ma.projet.test.TestProjet"
echo.
pause
