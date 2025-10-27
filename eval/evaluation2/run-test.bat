@echo off
echo ====================================
echo  Running Test Program
echo ====================================
echo.

mvn exec:java -Dexec.mainClass="ma.projet.test.TestProjet"

pause
