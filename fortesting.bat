cd C:\Users\Amitha\git\Calendar-Management-App 
decide.exe
ping 127.0.0.1 -n 1 -w 70> nul
del msgbox.vbs
@echo off
echo x=msgbox("2018/06/28 11:16:00 test" ,64, "" )  >>  msgbox.vbs
start msgbox.vbs