/*
 * CLogger.cpp
 *
 *  Created on: Jul 18, 2015
 *      Author: root
 */

#include <iostream>
#include <fstream>
#include <string>

#include "../headers/CLogger.h"

using namespace std;

void CLogger::StartLog()
{
	Log("CLogger::StartLog", ">> Starting log:", Information);
}

void CLogger::EndLog()
{
	Log("CLogger::EndLog", "<< Ending log: ", Information);
}

void CLogger::Log(std::string componentName, std::string message, int type)
{
	  time_t currentTime;
	  struct tm *localTime;
	  string typePrefix;

	  ofstream out( "PapiManagerLib.log", ios::out | ios::app);
	  if( !out )
	  {
		  cout << "Couldn't open file."  << endl;
		  return;
	  }

	  switch(type)
	  {
	  case Error:
		  	  typePrefix = "[Error]";
		  	  break;
	  case Information:
			  typePrefix = "[Info] ";
			  break;
	  case Warning:
			  typePrefix = "[Warn] ";
			  break;
	  default:
		  typePrefix = "[ ??? ]";
		  break;
	  }

	  time( &currentTime );                   // Get the current time
	  localTime = localtime( &currentTime );  // Convert the current time to the local time

	  int Day    = localTime->tm_mday;
	  int Month  = localTime->tm_mon + 1;
	  int Year   = localTime->tm_year + 1900;
	  int Hour   = localTime->tm_hour;
	  int Min    = localTime->tm_min;
	  int Sec    = localTime->tm_sec;

	  out << typePrefix << "\t" << Day << "/" << Month << "/" << Year << "  " << Hour << ":" << Min << ":" << Sec << "\t" << componentName << "\t" << message << std::endl;
#if debug
	  cout << componentName << "\t" << message << std::endl;
#endif

	  out.close();
}

