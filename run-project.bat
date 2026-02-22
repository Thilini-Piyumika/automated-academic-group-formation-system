@echo off
echo ==========================================
echo Automated Academic Group Formation System
echo ==========================================

echo.
echo Starting Backend...
start "Backend" cmd /k "cd /d %~dp0academic-group-formation-tool && mvnw.cmd spring-boot:run"

echo.
echo Waiting for backend to fully start...
timeout /t 25

echo.
echo Starting Frontend...
start "Frontend" cmd /k "cd /d %~dp0grouping-extention && npm start"

echo.
echo System Launched Successfully!
pause