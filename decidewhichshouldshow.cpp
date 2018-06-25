#include <iostream>
#include <fstream>
#include <fstream>
#include <ctime>
#include <string>
#include <unistd.h>
#include   <stdio.h> 
#include   <stdlib.h>
#define   MAXPATH   260 
using namespace std;

int main () {
char   buffer[MAXPATH];   
      getcwd(buffer,   MAXPATH); 
ifstream fileInput;
ofstream myfile ("fortesting.bat");
int offset;
string line;
string month;
string day;
string hour;
string minute;
time_t t = time(0);   
tm* now = localtime(&t);
if((now->tm_mon + 1) < 10)
	month = "0" + to_string(now->tm_mon + 1);
else 
    month = to_string(now->tm_mon + 1);
if((now->tm_mday) < 10)
    day = "0" + to_string(now->tm_mday);
else 
    day = to_string(now->tm_mday);
if((now->tm_hour) < 10)
    hour = "0" + to_string(now->tm_hour);
else 
    hour = to_string(now->tm_hour);
if((now->tm_min) < 10)
    minute = "0" + to_string(now->tm_min);
else 
    minute = to_string(now->tm_min);
string search = to_string(now->tm_year + 1900) + "/" + month +"/" + day + " " + hour + ":" + minute +":"+"00" ;
cout << "search: " << search<< endl;
cout << "cd " + string(buffer)  + " \n";
fileInput.open("reminderrecord.txt");
if(fileInput.is_open()) {
    while(!fileInput.eof()) {
        getline(fileInput, line);
        if ((offset = line.find(search, 0)) != string::npos) {
            if (myfile.is_open())
            {
              myfile << "cd " + string(buffer)  + " \n";
              myfile << "decide.exe\n";
              myfile << "ping 127.0.0.1 -n 1 -w 70> nul\n";
              myfile << "del msgbox.vbs\n";
              myfile << "@echo off\n";
              myfile << "echo x=msgbox(\""+ line +"\" ,64, \"\" )  >>  msgbox.vbs\n";
              myfile << "start msgbox.vbs\n";
              myfile.close();
            }
        }
    }
    fileInput.close();
}
else cout << "Unable to open file.";
}
