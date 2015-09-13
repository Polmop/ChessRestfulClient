/*
 * CEvent.cpp
 *
 *  Created on: Jul 18, 2015
 *      Author: root
 */

#include "../headers/CEventResult.h"

#include <papi.h>

using namespace std;

CEventResult::CEventResult(string name, string unit)
{
	_name = name;
	_unit = unit;
	_result = 0;
}

CEventResult::~CEventResult()
{
	_name.clear();
	_unit.clear();
}


string CEventResult::GetName()
{
	return _name;
}

string CEventResult::GetUnit()
{
	return _unit;
}

long_long CEventResult::GetResult()
{
	return _result;
}

void CEventResult::SetResult(long_long result)
{
	_result = result;
}
