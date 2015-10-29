/*
 * CEventInfo.cpp
 *
 *  Created on: Jul 18, 2015
 *      Author: root
 */

#include <string>
#include <papi.h>

#include "../headers/CEventInfo.h"
#include "../headers/CEventResult.h"

using namespace std;

CEventInfo::CEventInfo(string name, string unit, int code, int returnDataType, int eventType)
{
	_name = name;
	_unit = unit;
	_code = code;
	_eventType = eventType;
	SetReturnDataType(returnDataType);
}

CEventInfo::~CEventInfo()
{
	_name.clear();
	_unit.clear();
}

void CEventInfo::SetReturnDataType(int returnDataType)
{
	switch(returnDataType)
	{
		case PAPI_DATATYPE_INT64:
			_returnDataType = INT64;
			break;
		case PAPI_DATATYPE_UINT64:
			_returnDataType = UINT64;
			break;
		case PAPI_DATATYPE_FP64:
			_returnDataType = FP64;
			break;
		case PAPI_DATATYPE_BIT64:
			_returnDataType = BIT64;
			break;
		default:
			_returnDataType = -1;
			break;
	}
}

string CEventInfo::ReturnValueTypeToString(int eventReturnDataType)
{
	string returnValueString;

	switch(eventReturnDataType)
	{
		case PAPI_DATATYPE_INT64:
			returnValueString = "INT64";
			break;
		case PAPI_DATATYPE_UINT64:
			returnValueString = "UINT64";
			break;
		case PAPI_DATATYPE_FP64:
			returnValueString = "FP64";
			break;
		case PAPI_DATATYPE_BIT64:
			returnValueString = "BIT64";
			break;
		default:
			returnValueString = "Unknown";
			break;
	}

	return returnValueString;
}

/* TODO Zmienic na GetEventCode */
int CEventInfo::GetEventMaskFromType(int eventType)
{
	int mask;

	switch(eventType)
	{
		case NATIVE:
			mask = PAPI_NATIVE_MASK | 0;
			break;
		case PRESET:
			mask = PAPI_PRESET_MASK | 0;
			break;
		default:
			mask = 0;
			break;
	}

	return mask;
}

int CEventInfo::GetCode()
{
	return _code;
}

string CEventInfo::GetName()
{
	return _name;
}

string CEventInfo::GetUnit()
{
	return _unit;
}

int CEventInfo::GetReturnDataType()
{
	return _returnDataType;
}


int CEventInfo::GetEventType()
{
	return _eventType;
}

CEventResult* CEventInfo::GetResultClass()
{
	return new CEventResult(_name, _unit);
}
