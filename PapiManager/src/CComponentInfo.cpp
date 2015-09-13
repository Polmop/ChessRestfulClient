/*
 * CComponentInfo.cpp
 *
 *  Created on: Jul 18, 2015
 *      Author: root
 */

#include "../headers/CComponentInfo.h"

using namespace std;

CComponentInfo::CComponentInfo(string name, vector<CEventInfo*> events)
{
	_name = name;
	_events = events;
}

CComponentInfo::~CComponentInfo()
{
	_name.clear();
	_events.clear();
}

string CComponentInfo::GetName()
{
	return _name;
}

vector<CEventInfo*> CComponentInfo::GetEvents()
{
	return _events;
}

