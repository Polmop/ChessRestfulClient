/*
 * CPapiMeasure.h
 *
 *  Created on: Jul 20, 2015
 *      Author: root
 */

#ifndef CPAPIMEASURE_H_
#define CPAPIMEASURE_H_

#include <vector>

#include "CEventInfo.h"

class CPapiMeasure
{
	private:
		static bool _isCounting;
		static int _eventSet;
		static std::vector<CEventResult*>* _eventsVector;
	public:
		static bool Start(std::vector<CEventInfo*>* events);
		static std::vector<CEventResult*>* Stop();
};

#endif /* CPAPIMEASURE_H_ */
