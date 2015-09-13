/*
 * CLogger.h
 *
 *  Created on: Jul 18, 2015
 *      Author: root
 */

#ifndef CLOGGER_H_
#define CLOGGER_H_

#include <string>

class CLogger
{
	public:
		enum LogType { Information, Warning, Error };
		static void StartLog();
		static void EndLog();
		static void Log(std::string componentName, std::string message, int type);
};

#endif /* CLOGGER_H_ */
