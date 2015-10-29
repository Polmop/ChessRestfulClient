/*
 * CPapiManager.cpp
 *
 *  Created on: Jul 18, 2015
 *      Author: root
 */

#include <stdio.h>
#include <string.h>
#include <sstream>
#include <vector>
#include <papi.h>

#include "../headers/CPapiManager.h"
#include "../headers/CPapiMeasure.h"
#include "../headers/CComponentInfo.h"
#include "../headers/CLogger.h"

using namespace std;

CPapiManager* CPapiManager::_instance = NULL;

CPapiManager::CPapiManager()
{
	_isInitialized = false;
	_isCounting = false;
}

CPapiManager* CPapiManager::GetInstance()
{
	const string LOG_COMPONENT_NAME = "CPapiManager::GetInstance";

	if(_instance == NULL)
	{
		CLogger::Log(LOG_COMPONENT_NAME, "Creating new instance for PapiManager", CLogger::Information);
		_instance = new CPapiManager();
	}

	return _instance;
}

CPapiManager::~CPapiManager()
{
	// TODO Auto-generated destructor stub
}

bool CPapiManager::Init()
{
	const string LOG_COMPONENT_NAME = "CPapiManager::Init";

	if ( PAPI_library_init( PAPI_VER_CURRENT ) == PAPI_VER_CURRENT )
	{
		CLogger::Log(LOG_COMPONENT_NAME, "PAPI_library_init() success", CLogger::Information);
		_isInitialized = true;
	}
	else
	{
		CLogger::Log(LOG_COMPONENT_NAME, "PAPI_library_init() failed", CLogger::Error);//TODO: Some error code or something... -.-
		_isInitialized = false;
	}

	return _isInitialized;
}

bool CPapiManager::Close()
{
	const string LOG_COMPONENT_NAME = "CPapiManager::Clear";

	if(!_isInitialized)
	{
		CLogger::Log(LOG_COMPONENT_NAME, "PAPI not even initialized. Nothing to do.", CLogger::Information);
		return true;
	}
	else
	{
		if(_isCounting)
		{
			CLogger::Log(LOG_COMPONENT_NAME, "PAPI measurement in progress. Aborting measure and closing PAPI_library.", CLogger::Information);
			//TODO: StopMeasure();
		}

		PAPI_shutdown();
		_isInitialized = false;
		CLogger::Log(LOG_COMPONENT_NAME, "PAPI_shutdown() called. PAPI library closed.", CLogger::Information);
	}

	return !_isInitialized;
}

bool CPapiManager::StartMeasure(vector<CEventInfo*>* events)
{
	return CPapiMeasure::Start(events);
}

vector<CEventResult*>* CPapiManager::StopMeasure()
{
	return CPapiMeasure::Stop();
}

vector<CComponentInfo*>* CPapiManager::GetComponentsInfo()
{
	const string LOG_COMPONENT_NAME = "CPapiManager::GetComponentsInfo";

	if(!_isInitialized)
	{
		CLogger::Log(LOG_COMPONENT_NAME, "PAPI not initialized. Aborting.", CLogger::Error);
		return NULL;
	}

	int componentsCount = 0;
	const PAPI_component_info_t* componentInfo = NULL;
	string componentName;
	vector<CComponentInfo*>* componentsInfo = new vector<CComponentInfo*>();
	stringstream message;

	componentsCount = PAPI_num_components();

	for(int componentId = 0; componentId < componentsCount; componentId++)
	{
		componentInfo = PAPI_get_component_info(componentId);

		if (componentInfo == NULL)
		{
			message << "PAPI_get_component_info failed for ComponentId " << componentId;
			CLogger::Log(LOG_COMPONENT_NAME, message.str(), CLogger::Warning);
			message.str("");
			continue;
		}

		message << "Found " << componentInfo->name << " component under ComponentId " << componentId;
		CLogger::Log(LOG_COMPONENT_NAME, message.str(), CLogger::Information);
		message.str("");

		if (componentInfo->disabled)
		{
			message << "Component " << componentInfo->name << " is disabled due to: " << componentInfo->disabled_reason;
			CLogger::Log(LOG_COMPONENT_NAME, message.str(), CLogger::Warning);
			message.str("");
			continue;

			//TODO: Add check for known fixes (like unable to read msr files) and perform then if found and try again.
		}

		message << "Creating " << componentInfo->name << " component entry and adding it to components list";
		CLogger::Log(LOG_COMPONENT_NAME, message.str(), CLogger::Information);
		message.str("");

		componentsInfo->push_back(new CComponentInfo(componentInfo->name, GetComponentEventsInfo(componentId)));
	}

	return componentsInfo;
}

vector<CEventInfo*> CPapiManager::GetComponentEventsInfo(int componentId)
{
	const string LOG_COMPONENT_NAME = "CPapiManager::GetComponentEventsInfo";

	vector<CEventInfo*> componentEventInfo;

	GetComponentNativeEventsInfo(componentId, &componentEventInfo);
	GetComponentPresetEventsInfo(componentId, &componentEventInfo);

	return componentEventInfo;
}

void CPapiManager::GetComponentNativeEventsInfo(int componentId, std::vector<CEventInfo*>* componentEventsInfo)
{
	const string LOG_COMPONENT_NAME = "GetComponentNativeEventsInfo";

	CLogger::Log(LOG_COMPONENT_NAME, "Looking for native components...", CLogger::Information);
	GetComponentEventsInfoGeneric(componentId, CEventInfo::NATIVE, componentEventsInfo);
	CLogger::Log(LOG_COMPONENT_NAME, "No more native events found", CLogger::Information);
}

void CPapiManager::GetComponentPresetEventsInfo(int componentId, std::vector<CEventInfo*>* componentEventsInfo)
{
	const string LOG_COMPONENT_NAME = "CPapiManager::GetComponentPresetEventsInfo";

	CLogger::Log(LOG_COMPONENT_NAME, "Looking for preset components...", CLogger::Information);
	GetComponentEventsInfoGeneric(componentId, CEventInfo::PRESET, componentEventsInfo);
	CLogger::Log(LOG_COMPONENT_NAME, "No more preset events found", CLogger::Information);
}

void CPapiManager::GetComponentEventsInfoGeneric(int componentId, int eventType, std::vector<CEventInfo*>* componentEventsInfo)
{
	const string LOG_COMPONENT_NAME = "CPapiManager::GetComponentEventsInfoGeneric";

	PAPI_event_info_t eventInfo;
	int eventReturnDataType;
	int eventCode;
	int errorCode;
	stringstream message;

	eventCode = CEventInfo::GetEventMaskFromType(eventType);

	int r = PAPI_enum_cmp_event(&eventCode, PAPI_ENUM_FIRST, componentId);

	while ( r == PAPI_OK ) {

		char* eventName = new char[PAPI_MAX_STR_LEN];
		char* eventUnit = new char[PAPI_MAX_STR_LEN];

		errorCode = PAPI_event_code_to_name(eventCode, eventName);
		if (errorCode != PAPI_OK)
		{
			message << "Error translating " << hex << eventCode << " event onto literal name (Error: " << hex << errorCode << ")";
			CLogger::Log(LOG_COMPONENT_NAME, message.str(), CLogger::Error);
			message.str("");
			continue;
		}

		message <<  eventName << " event found (hex code: " << hex << eventCode << ")";
		CLogger::Log(LOG_COMPONENT_NAME, message.str(), CLogger::Information);
		message.str("");

		errorCode = PAPI_get_event_info(eventCode, &eventInfo);
		if (errorCode != PAPI_OK)
		{
			message << "Error getting event info for " << eventName << " (Error: " << hex << errorCode << ")";
			CLogger::Log(LOG_COMPONENT_NAME, message.str(), CLogger::Error);
			message.str("");
			continue;
		}

		strncpy(eventUnit, eventInfo.units, sizeof(eventInfo.units)-1);
		// buffer must be null terminated to safely use strstr operation on it below
		//eventUnit[sizeof(eventUnit)-1] = '\0';
		eventReturnDataType = eventInfo.data_type;

		message << eventName << " unit is " << eventUnit << " and return value type is " << CEventInfo::ReturnValueTypeToString(eventReturnDataType) << " Adding to Component Events Info vector.";
		CLogger::Log(LOG_COMPONENT_NAME, message.str(), CLogger::Information);
		message.str("");

		componentEventsInfo->push_back(new CEventInfo(eventName, eventUnit, eventCode, eventReturnDataType, CEventInfo::NATIVE));

		r = PAPI_enum_cmp_event( &eventCode, PAPI_ENUM_EVENTS, componentId );
	}
}

string CPapiManager::GetPapiNativeAvailStringCmdResult()
{
	return ExecuteCmd("papi_native_avail");
}

string CPapiManager::GetPapiComponentAvailCmdResult()
{
	return ExecuteCmd("papi_component_avail");
}

string CPapiManager::GetPapiAvailCmdResult()
{
	return ExecuteCmd("papi_avail");
}

string CPapiManager::ExecuteCmd(string cmd)
{
	const string LOG_COMPONENT_NAME = "CPapiManager::ExecuteCmd";

	stringstream message;
	char buffer[128];
	string cmdResult;
	FILE* cmdLine;

	message << "Executing '" << cmd << "' shell command";
	CLogger::Log(LOG_COMPONENT_NAME, message.str(), CLogger::Information);
	message.str("");

	cmdResult = "";
	cmdLine = popen(cmd.c_str(), "r");

	if (!cmdLine)
	{
		CLogger::Log(LOG_COMPONENT_NAME, "Error occurred when executing the command. Abort.", CLogger::Error);
		return cmdResult;
	}

	while(!feof(cmdLine))
	{
		if(fgets(buffer, 128, cmdLine) != NULL)
		{
			cmdResult.append(buffer);
		}
	}

	CLogger::Log(LOG_COMPONENT_NAME, "Command results collected", CLogger::Information);

	pclose(cmdLine);
	return cmdResult;
}
