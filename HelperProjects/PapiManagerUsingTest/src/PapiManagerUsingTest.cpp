//============================================================================
// Name        : PapiManagerUsingTest.cpp
// Author      : Bulczi
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include <vector>
#include <unistd.h>
#include <cstdlib>
#include <CComponentInfo.h>
#include <CPapiManager.h>

using namespace std;

int main() {

	CPapiManager* papi = CPapiManager::GetInstance();
	//string choosenEventName = "PAPI_TOT_INS";
	string choosenEventName = "rapl:::PP0_ENERGY:PACKAGE0";
	CEventInfo* choosenEventInfo;

	papi->Init();

	vector<CComponentInfo*>* components = papi->GetComponentsInfo();

	for(unsigned int i = 0; i < components->size(); i++)
	{
		cout<<(*components)[i]->GetName()<<"\n";

		vector<CEventInfo*> events = (*components)[i]->GetEvents();

		for(unsigned int j = 0; j < events.size(); j++)
		{
			cout<<"\t"<<events[j]->GetName()<<"\n";

			if(events[j]->GetName().compare(choosenEventName) == 0)
			{
				choosenEventInfo = events[j];
			}
		}
	}

	//Idle 1

	cout<<"Creating vectors to measure vector"<<endl;

	vector<CEventInfo*>* eventsToMeasure = new vector<CEventInfo*>();
	eventsToMeasure->push_back(choosenEventInfo);

	cout<<"Starting measure - Idle 1"<<endl;
	if(!papi->StartMeasure(eventsToMeasure))
	{
		cout<<"Measure start error. Aborting..."<<endl;
		return 0;
	}

	sleep(5);

	cout<<"Stopping measure"<<endl;
	vector<CEventResult*> results = *(papi->StopMeasure());

	cout<<"Printing results"<<endl;
	for(unsigned int e = 0; e < results.size(); e++)
	{
		cout<<results[e]->GetName()<<" "<<results[e]->GetResult()<<results[e]->GetUnit()<<endl;
	}

	//Idle 2

	eventsToMeasure = new vector<CEventInfo*>();
	eventsToMeasure->push_back(choosenEventInfo);

	cout<<"Starting measure - Idle 2"<<endl;
	if(!papi->StartMeasure(eventsToMeasure))
	{
		cout<<"Measure start error. Aborting..."<<endl;
		return 0;
	}

	sleep(5);

	cout<<"Stopping measure"<<endl;
	results = *(papi->StopMeasure());

	cout<<"Printing results"<<endl;
	for(unsigned int e = 0; e < results.size(); e++)
	{
		cout<<results[e]->GetName()<<" "<<results[e]->GetResult()<<results[e]->GetUnit()<<endl;
	}

	//BubbleSort

	eventsToMeasure = new vector<CEventInfo*>();
	eventsToMeasure->push_back(choosenEventInfo);

	cout<<"Starting measure - BubbleSort"<<endl;
	if(!papi->StartMeasure(eventsToMeasure))
	{
		cout<<"Measure start error. Aborting..."<<endl;
		return 0;
	}

	cout<<"BubbleSort testing"<<endl;
	system("./SortingApp b 100");

	cout<<"Stopping measure"<<endl;
	results = *(papi->StopMeasure());

	cout<<"Printing results"<<endl;
	for(unsigned int e = 0; e < results.size(); e++)
	{
		cout<<results[e]->GetName()<<" "<<results[e]->GetResult()<<results[e]->GetUnit()<<endl;
	}

	//QuickSort

	eventsToMeasure = new vector<CEventInfo*>();
	eventsToMeasure->push_back(choosenEventInfo);

	cout<<"Starting measure - QuickSort"<<endl;
	if(!papi->StartMeasure(eventsToMeasure))
	{
		cout<<"Measure start error. Aborting..."<<endl;
		return 0;
	}

	cout<<"QuickSort testing"<<endl;
	system("./SortingApp q 100");

	cout<<"Stopping measure"<<endl;
	results = *(papi->StopMeasure());

	cout<<"Printing results"<<endl;
	for(unsigned int e = 0; e < results.size(); e++)
	{
		cout<<results[e]->GetName()<<" "<<results[e]->GetResult()<<results[e]->GetUnit()<<endl;
	}

	cout<<"Return"<<endl;
	return 0;
}
