/*
 * CEvent.h
 *
 *  Created on: Jul 18, 2015
 *      Author: root
 */

#ifndef CEVENTRESULT_H_
#define CEVENTRESULT_H_

#include <string>
#include <papi.h>

class CEventResult
{
	private:
		std::string _name;
		std::string _unit;
		long_long _result;
	public:
		CEventResult(std::string name, std::string unit);
		~CEventResult();
		std::string GetName();
		std::string GetUnit();
		long_long GetResult();
		void SetResult(long_long result);
};

#endif /* CEVENTRESULT_H_ */
