/*
 * CEventInfo.h
 *
 *  Created on: Jul 18, 2015
 *      Author: root
 */

#ifndef CEVENTINFO_H_
#define CEVENTINFO_H_

#include <string>

#include "CEventResult.h"

class CEventInfo
{
	private:
		std::string _name;
		std::string _unit;
		int _code;
		int _returnDataType;
		int _eventType;
		void SetReturnDataType(int returnDataType);
	public:
		enum EventType { NATIVE, PRESET };
		enum ReturnValueType { INT64, UINT64, FP64, BIT64 };
		static std::string ReturnValueTypeToString(int eventReturnDataType);
		static int GetEventMaskFromType(int eventType);
		CEventInfo(std::string name, std::string unit, int code, int returnDataType, int eventMask);
		~CEventInfo();
		std::string GetName();
		std::string GetUnit();
		int GetCode();
		int GetReturnDataType();
		int GetEventType();
		CEventResult* GetResultClass();
};

#endif /* CEVENTINFO_H_ */
