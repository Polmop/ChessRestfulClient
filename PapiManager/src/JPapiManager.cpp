/*
 * JPapiManager.cpp
 *
 *  Created on: Aug 2, 2015
 *      Author: root
 */

#include <string.h>
#include <vector>
#include <string>
#include <memory.h>
#include <stdlib.h>
#include <iostream>

#include "../headers/JPapiManager.h"
#include "../headers/CPapiManager.h"
#include "../headers/CEventInfo.h"

using namespace std;
using namespace JPapiManager;

typedef struct EventInfoStruct
{
	char* Name;
	char* Unit;
	int Code;
	int ReturnDataType;
	int EventType;
} EventInfoStruct;

typedef struct EventInfoArrayStruct
{
	int ArraySize;
	EventInfoStruct* EventInfoArray;
} EventInfoArrayStruct;

typedef struct EventResultStruct
{
	char* Name;
	char* Unit;
	long long Result;
} EventResultStruct;

typedef struct EventResultArrayStruct
{
	int ArraySize;
	EventResultStruct* EventResultArray;
} EventResultArrayStruct;

typedef struct ComponentInfoStruct
{
	char* Name;
	EventInfoArrayStruct Events;
} ComponentInfoStruct;

typedef struct ComponentInfoArrayStruct
{
	int ArraySize;
	ComponentInfoStruct* ComponentInfoArray;
} ComponentInfoArrayStruct;

extern "C" bool Init()
{
	return CPapiManager::GetInstance()->Init();
}

extern "C" bool Close()
{
	return CPapiManager::GetInstance()->Close();
}

extern "C" bool StartMeasure(EventInfoArrayStruct* eventsArray)
{
	vector<CEventInfo*>* events = new vector<CEventInfo*>();

	for(int i = 0; i < eventsArray->ArraySize; i++)
	{
		events->push_back(new CEventInfo(
				eventsArray->EventInfoArray[i].Name,
				eventsArray->EventInfoArray[i].Unit,
				eventsArray->EventInfoArray[i].Code,
				eventsArray->EventInfoArray[i].ReturnDataType,
				eventsArray->EventInfoArray[i].EventType
				));
	}

	return CPapiManager::GetInstance()->StartMeasure(events);
}

extern "C" EventResultArrayStruct StopMeasure()
{
	vector<CEventResult*>* results = CPapiManager::GetInstance()->StopMeasure();

	EventResultArrayStruct resultsStruct;
	resultsStruct.ArraySize = results->size();
	resultsStruct.EventResultArray = (EventResultStruct*)malloc(sizeof(EventResultStruct) * resultsStruct.ArraySize);
	memset(resultsStruct.EventResultArray, 0, sizeof(EventResultStruct) * resultsStruct.ArraySize);

	for(unsigned int i = 0; i < results->size(); i++)
	{
		resultsStruct.EventResultArray[i].Name = (char*)malloc(sizeof(char) * ((*results)[i]->GetName().size() + 1));
		memset(resultsStruct.EventResultArray[i].Name, 0, sizeof(char) * ((*results)[i]->GetName().size() + 1));
		strcpy(resultsStruct.EventResultArray[i].Name, (*results)[i]->GetName().c_str());

		resultsStruct.EventResultArray[i].Unit = (char*)malloc(sizeof(char) * ((*results)[i]->GetUnit().size() + 1));
		memset(resultsStruct.EventResultArray[i].Unit, 0, sizeof(char) * ((*results)[i]->GetUnit().size() + 1));
		strcpy(resultsStruct.EventResultArray[i].Unit, (*results)[i]->GetUnit().c_str());

		resultsStruct.EventResultArray[i].Result = (*results)[i]->GetResult();
	}

	return resultsStruct;
}

/*malloc version*/
extern "C" ComponentInfoArrayStruct GetComponentsInfo()
{
	vector<CComponentInfo*>* results = CPapiManager::GetInstance()->GetComponentsInfo();

	ComponentInfoArrayStruct componentsStruct;
	componentsStruct.ArraySize = results->size();
	componentsStruct.ComponentInfoArray = (ComponentInfoStruct*)malloc(sizeof(ComponentInfoStruct) * componentsStruct.ArraySize);
	memset(componentsStruct.ComponentInfoArray, 0, sizeof(ComponentInfoStruct) * componentsStruct.ArraySize);

	for(int i = 0; i < componentsStruct.ArraySize; i++)
	{
		/* NOTE:
		 * Allocating +1 byte for all strings, because strcpy() DON'T copy /0 sign.
		 * If string is not NULL TERMINATED, JNA throws segmentation error (SIGSEGV).
		 */
		componentsStruct.ComponentInfoArray[i].Name = (char*)malloc(sizeof(char) * ((*results)[i]->GetName().size() + 1));
		memset(componentsStruct.ComponentInfoArray[i].Name, 0, sizeof(char) * ((*results)[i]->GetName().size() + 1));
		strcpy(componentsStruct.ComponentInfoArray[i].Name, (*results)[i]->GetName().c_str());

		componentsStruct.ComponentInfoArray[i].Events.ArraySize = (*results)[i]->GetEvents().size();
		componentsStruct.ComponentInfoArray[i].Events.EventInfoArray = (EventInfoStruct*)malloc(sizeof(EventInfoStruct) * (*results)[i]->GetEvents().size());
		memset(componentsStruct.ComponentInfoArray[i].Events.EventInfoArray, 0, sizeof(EventInfoStruct) * (*results)[i]->GetEvents().size());

		for(unsigned int j = 0; j < (*results)[i]->GetEvents().size(); j++)
		{
			componentsStruct.ComponentInfoArray[i].Events.EventInfoArray[j].Name = (char*)malloc(sizeof(char) * ((*results)[i]->GetEvents()[j]->GetName().size() + 1));
			memset(componentsStruct.ComponentInfoArray[i].Events.EventInfoArray[j].Name, 0, sizeof(char) * ((*results)[i]->GetEvents()[j]->GetName().size() + 1));
			strcpy(componentsStruct.ComponentInfoArray[i].Events.EventInfoArray[j].Name, (*results)[i]->GetEvents()[j]->GetName().c_str());

			componentsStruct.ComponentInfoArray[i].Events.EventInfoArray[j].Unit = (char*)malloc(sizeof(char) * ((*results)[i]->GetEvents()[j]->GetUnit().size() + 1));
			memset(componentsStruct.ComponentInfoArray[i].Events.EventInfoArray[j].Unit, 0, sizeof(char) * ((*results)[i]->GetEvents()[j]->GetUnit().size() + 1));
			strcpy(componentsStruct.ComponentInfoArray[i].Events.EventInfoArray[j].Unit, (*results)[i]->GetEvents()[j]->GetUnit().c_str());

			componentsStruct.ComponentInfoArray[i].Events.EventInfoArray[j].Code = (*results)[i]->GetEvents()[j]->GetCode();
			componentsStruct.ComponentInfoArray[i].Events.EventInfoArray[j].EventType = (*results)[i]->GetEvents()[j]->GetEventType();
			componentsStruct.ComponentInfoArray[i].Events.EventInfoArray[j].ReturnDataType = (*results)[i]->GetEvents()[j]->GetReturnDataType();
		}
	}

	return componentsStruct;
}

extern "C" char* GetPapiNativeAvailStringCmdResult()
{
	string resultStr = CPapiManager::GetInstance()->GetPapiNativeAvailStringCmdResult();
	char* resultC =  (char*)malloc(sizeof(char) * (resultStr.size() + 1));
	memset(resultC, 0, sizeof(char) * (resultStr.size() + 1));
	strcpy(resultC, resultStr.c_str());

	return resultC;
}

extern "C" char* GetPapiComponentAvailCmdResult()
{
	string resultStr = CPapiManager::GetInstance()->GetPapiComponentAvailCmdResult();

	char* resultC =  (char*)malloc(sizeof(char) * (resultStr.size() + 1));
	memset(resultC, 0, sizeof(char) * (resultStr.size() + 1));
	strcpy(resultC, resultStr.c_str());

	return resultC;
}

extern "C" char* GetPapiAvailCmdResult()
{
	string resultStr = CPapiManager::GetInstance()->GetPapiAvailCmdResult();
	char* resultC =  (char*)malloc(sizeof(char) * (resultStr.size() + 1));
	memset(resultC, 0, sizeof(char) * (resultStr.size() + 1));
	strcpy(resultC, resultStr.c_str());

	return resultC;
}


