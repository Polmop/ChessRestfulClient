/*
 * CPapiMeasure.cpp
 *
 *  Created on: Jul 20, 2015
 *      Author: root
 */

#include "../headers/CPapiMeasure.h"
#include "../headers/CLogger.h"

#include <string>
#include <sstream>
#include <papi.h>

using namespace std;

bool CPapiMeasure::_isCounting = false;
int CPapiMeasure::_eventSet = 0;
vector<CEventResult*>* CPapiMeasure::_eventsVector = NULL;

// Dodac oblusge class CEventResults (zamiast CEvent) oraz nowej fukncji CEventInfo::GetEventCode()
bool CPapiMeasure::Start(vector<CEventInfo*>* events)
{
	const string LOG_COMPONENT_NAME = "CPapiMeasure::Start";

	int errorCode;
	stringstream message;

	if(_isCounting)
	{
		CLogger::Log(LOG_COMPONENT_NAME, "Performance counting already in progress. Stop current measurement to start a new one.", CLogger::Error);
		return false;
	}

	int eventSet = PAPI_NULL;

	errorCode = PAPI_create_eventset(&eventSet);
	if (errorCode != PAPI_OK)
	{
		message << "Error when creating event set (Error: " << hex << errorCode << ")";
		CLogger::Log(LOG_COMPONENT_NAME, message.str(), CLogger::Error);
		message.str("");

		return false;
	}

	for(unsigned int i=0; i<events->size(); i++)
	{
		errorCode = PAPI_add_event(eventSet, (*events)[i]->GetCode());
		if (errorCode != PAPI_OK)
		{
			message << "Error when trying to add " << (*events)[i]->GetName() << " event to event set (Error: " << hex << errorCode << ")";
			CLogger::Log(LOG_COMPONENT_NAME, message.str(), CLogger::Error);
			message.str("");

			return false;
		}
	}

	errorCode = PAPI_start(eventSet);
	if (errorCode != PAPI_OK)
	{
		message << "Start measurement error (PAPI_Start) (Error: " << hex << errorCode << ")";
		CLogger::Log(LOG_COMPONENT_NAME, message.str(), CLogger::Error);
		message.str("");

		return false;
	}

	_isCounting = true;
	_eventSet = eventSet;
	_eventsVector = new vector<CEventResult*>();

	for(unsigned int i = 0; i < events->size(); i++)
	{
		_eventsVector->push_back((*events)[i]->GetResultClass());
	}

	return true;
}

vector<CEventResult*>* CPapiMeasure::Stop()
{
	const string LOG_COMPONENT_NAME = "CPapiMeasure::Stop";

	long_long results[_eventsVector->size()];
	int errorCode;
	stringstream message;

	errorCode = PAPI_stop(_eventSet, results);
	if (errorCode != PAPI_OK)
	{
		message << "Stop measurement error (PAPI_Stop) (Error: " << hex << errorCode << ")";
		CLogger::Log(LOG_COMPONENT_NAME, message.str(), CLogger::Error);
		message.str("");

		return NULL;
	}

	for(unsigned int i=0; i<_eventsVector->size(); i++)
	{
		(*_eventsVector)[i]->SetResult(results[i]);
	}

	_isCounting = false;

	return _eventsVector;
}
