/*
 * JPapiManager.h
 *
 *  Created on: Aug 2, 2015
 *      Author: root
 */

#ifndef JPAPIMANAGER_H_
#define JPAPIMANAGER_H_

namespace JPapiManager
{
	//Find way to pass to pass them to Java (via String in the worst case :S)
	enum EventType { NATIVE, PRESET };
	enum ReturnValueType { INT64, UINT64, FP64, BIT64 };
}

#endif /* JPAPIMANAGER_H_ */
