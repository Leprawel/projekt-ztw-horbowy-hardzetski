@echo off

for /f "tokens=5" %%a in ('netstat -ano ^| find /i ":8081"') do taskkill /F /pid %%a