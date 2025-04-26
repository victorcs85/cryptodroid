@echo off

set hookDir=.git\hooks

if not exist %hookDir% (
    echo "Git hooks directory not found."
    exit /b 1
)

copy /Y pre-push %hookDir%\pre-push

echo "pre-push hook installed successfully."
