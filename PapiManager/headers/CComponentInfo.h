/*
 * CComponentInfo.h
 *
 *  Created on: Jul 18, 2015
 *      Author: root
 */

#ifndef CCOMPONENTINFO_H_
#define CCOMPONENTINFO_H_

#include <string>
#include <vector>

#include "../headers/CEventInfo.h"

class CComponentInfo
{
	private:
		std::string _name;
		std::vector<CEventInfo*> _events;
	public:
		CComponentInfo(std::string name, std::vector<CEventInfo*> _events);
		~CComponentInfo();
		std::string GetName();
		std::vector<CEventInfo*> GetEvents();
};

#endif /* CCOMPONENTINFO_H_ */
