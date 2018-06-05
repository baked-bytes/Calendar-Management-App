cd C:\Users\morris\Desktop\testing\OOAD\ 
decide.exe
ping 127.0.0.1 -n 1 -w 70> nul
del msgbox.vbs
@echo off
echo x=msgbox("2018/06/05 17:14:00 testing edited 5" ,64, "" )  >>  msgbox.vbs
start msgbox.vbs
