/*
 * CPapiManager.h
 *
 *  Created on: Jul 18, 2015
 *      Author: root
 */

#ifndef CPAPIMANAGER_H_
#define CPAPIMANAGER_H_

#include "../headers/CComponentInfo.h"
#include "../headers/CEventInfo.h"
#include "../headers/CEventResult.h"

#include <vector>

class CPapiManager
{
	private:
		bool _isInitialized;
		bool _isCounting;
		static CPapiManager* _instance;
		CPapiManager();
		std::string ExecuteCmd(std::string cmd);
		std::vector<CEventInfo*> GetComponentEventsInfo(int componentId);
		void GetComponentNativeEventsInfo(int componentId, std::vector<CEventInfo*>* componentEventsInfo);
		void GetComponentPresetEventsInfo(int componentId, std::vector<CEventInfo*>* componentEventsInfo);
		void GetComponentEventsInfoGeneric(int componentId, int eventType, std::vector<CEventInfo*>* componentEventsInfo);
	public:
		static CPapiManager* GetInstance();
		virtual ~CPapiManager();
		bool Init();
		bool Close();
		bool StartMeasure(std::vector<CEventInfo*>* events);
		std::vector<CEventResult*>* StopMeasure();
		std::vector<CComponentInfo*>* GetComponentsInfo();
		std::string GetPapiNativeAvailStringCmdResult();
		std::string GetPapiComponentAvailCmdResult();
		std::string GetPapiAvailCmdResult();
};

#endif /* CPAPIMANAGER_H_ */
