package PapiManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;

public class JPapiManager
{
	private interface PapiManagerLibrary extends Library
	{	
		PapiManagerLibrary INSTANCE = (PapiManagerLibrary)Native.loadLibrary("PapiManager", PapiManagerLibrary.class);
		
		public static class EventInfoStruct extends Structure
		{
			//public static class ByValue extends EventInfoStruct implements Structure.ByValue {}
			public static class ByReference extends EventInfoStruct implements Structure.ByReference {}
			
			public String Name;
			public String Unit;
			public int Code;
			public int ReturnDataType;
			public int EventType;
			
			@Override
			protected List<String> getFieldOrder()
			{
				return Arrays.asList(new String[] {"Name", "Unit", "Code", "ReturnDataType", "EventType"});
			}
		}
	
		/*private*/public class EventInfoArrayStruct extends Structure
		{
			public static class ByValue extends EventInfoArrayStruct implements Structure.ByValue {}
			public static class ByReference extends EventInfoArrayStruct implements Structure.ByReference {}
	
			public int ArraySize;
			public EventInfoStruct.ByReference EventInfoArray;
			
			@Override
			protected List<String> getFieldOrder()
			{
				return Arrays.asList(new String[] {"ArraySize", "EventInfoArray"});
			}
		}
		
		public class EventResultStruct extends Structure
		{
			//public static class ByValue extends EventResultStruct implements Structure.ByValue {}
			public static class ByReference extends EventResultStruct implements Structure.ByReference {}
			
			public String Name;
			public String Unit;
			public long Result;
			
			@Override
			protected List<String> getFieldOrder()
			{
				return Arrays.asList(new String[] {"Name", "Unit", "Result"});
			}
		}
		
		/*private*/public class EventResultArrayStruct extends Structure
		{
			public static class ByValue extends EventResultArrayStruct implements Structure.ByValue {}
			//public static class ByReference extends EventResultArrayStruct implements Structure.ByReference {}
	
			public int ArraySize;
			public EventResultStruct.ByReference EventResultArray = null;
			
			@Override
			protected List<String> getFieldOrder()
			{
				return Arrays.asList(new String[] {"ArraySize", "EventResultArray"});
			}
		}
		
		public class ComponentInfoStruct extends Structure
		{
			//public static class ByValue extends ComponentInfoStruct implements Structure.ByValue {}
			public static class ByReference extends ComponentInfoStruct implements Structure.ByReference {}
			
		    public String Name;
			public EventInfoArrayStruct.ByValue Events;
			
			@Override
			protected List<String> getFieldOrder()
			{
				return Arrays.asList(new String[] {"Name", "Events"});
			}
		}
		
		/*private*/public class ComponentInfoArrayStruct extends Structure
		{
			public static class ByValue extends ComponentInfoArrayStruct implements Structure.ByValue {}
			//public static class ByReference extends ComponentInfoArrayStruct implements Structure.ByReference {}
	
			public int ArraySize;
			public ComponentInfoStruct.ByReference ComponentInfoArray;
			
			@Override
			protected List<String> getFieldOrder()
			{
				return Arrays.asList(new String[] {"ArraySize", "ComponentInfoArray"});
			}
		}
		 
		boolean Init();
		boolean Close();
		boolean StartMeasure(EventInfoArrayStruct.ByReference eventsArray);
		EventResultArrayStruct.ByValue StopMeasure();
		ComponentInfoArrayStruct.ByValue GetComponentsInfo();
		String GetPapiNativeAvailStringCmdResult();
		String GetPapiComponentAvailCmdResult();
		String GetPapiAvailCmdResult();
	}

	public static boolean Init()
	{
		return PapiManagerLibrary.INSTANCE.Init();
	}
	
	public static boolean Close()
	{
		return PapiManagerLibrary.INSTANCE.Close();
	}
	
	public static boolean StartMeasure(ArrayList<PapiEventInfo> jEventsInfoArray)
	{
		PapiManagerLibrary.EventInfoArrayStruct.ByReference cEventsInfoArray = new PapiManagerLibrary.EventInfoArrayStruct.ByReference();
    	cEventsInfoArray.ArraySize = jEventsInfoArray.size();
    	cEventsInfoArray.EventInfoArray = new PapiManagerLibrary.EventInfoStruct.ByReference();
    	/* 
    	 * !!! NOTE:
    	 * toArray() function takes care of memory allocation for whole array.
    	 * Programmer don't have to allocate the memory manually for PapiManagerLibrary.EventInfoStruct.ByReference array elements. 
    	 */
    	PapiManagerLibrary.EventInfoStruct[] cEventsInfo = (PapiManagerLibrary.EventInfoStruct[])cEventsInfoArray.EventInfoArray.toArray(cEventsInfoArray.ArraySize);
    	for (int i = 0; i < cEventsInfoArray.ArraySize; i++)
    	{
    		cEventsInfo[i].Name = jEventsInfoArray.get(i).GetName();
    		cEventsInfo[i].Unit = jEventsInfoArray.get(i).GetUnit();
    		cEventsInfo[i].Code = jEventsInfoArray.get(i).GetCode();
    		cEventsInfo[i].ReturnDataType = jEventsInfoArray.get(i).GetReturnDataType();
    		cEventsInfo[i].EventType = jEventsInfoArray.get(i).GetEventType();
    	}

		return PapiManagerLibrary.INSTANCE.StartMeasure(cEventsInfoArray);
	}
	
	public static ArrayList<PapiEventResult> StopMeasure()
	{
		ArrayList<PapiEventResult> jEventsResultArray = new ArrayList<PapiEventResult>();
		
    	PapiManagerLibrary.EventResultArrayStruct.ByValue cEventsResultArray = PapiManagerLibrary.INSTANCE.StopMeasure();
		
    	PapiManagerLibrary.EventResultStruct[] cEventsResult = (PapiManagerLibrary.EventResultStruct[])cEventsResultArray.EventResultArray.toArray(cEventsResultArray.ArraySize);
    	for (int i = 0; i < cEventsResultArray.ArraySize; i++)
    	{
    		jEventsResultArray.add(new PapiEventResult(
    				cEventsResult[i].Name,
    				cEventsResult[i].Unit,
    				cEventsResult[i].Result
    				));
    	}
    	
		return jEventsResultArray;
	}
	
	public static ArrayList<PapiComponentInfo> GetComponentsInfo()
	{
		ArrayList<PapiComponentInfo> jComponentsInfoArray = new ArrayList<PapiComponentInfo>();
		
		PapiManagerLibrary.ComponentInfoArrayStruct.ByValue cComponentsInfoArray = PapiManagerLibrary.INSTANCE.GetComponentsInfo();
		
    	PapiManagerLibrary.ComponentInfoStruct[] cComponentsInfo = (PapiManagerLibrary.ComponentInfoStruct[])cComponentsInfoArray.ComponentInfoArray.toArray(cComponentsInfoArray.ArraySize);
    	for (int i = 0; i < cComponentsInfoArray.ArraySize; i++)
    	{
    		ArrayList<PapiEventInfo> jEventsInfo = new ArrayList<PapiEventInfo>();
    		
    		PapiManagerLibrary.EventInfoStruct[] cEventsInfo = (PapiManagerLibrary.EventInfoStruct[])cComponentsInfo[i].Events.EventInfoArray.toArray(cComponentsInfo[i].Events.ArraySize);	
    		for (int j = 0; j < cComponentsInfo[i].Events.ArraySize; j++)
    		{
    			jEventsInfo.add(new PapiEventInfo(
    					cEventsInfo[j].Name,
    					cEventsInfo[j].Unit,
    					cEventsInfo[j].Code,
    					cEventsInfo[j].EventType,
    					cEventsInfo[j].ReturnDataType
    					));
    		}
    		jComponentsInfoArray.add(new PapiComponentInfo(cComponentsInfo[i].Name, jEventsInfo));
    	}
    	
    	return jComponentsInfoArray;
	}

	public static String GetPapiNativeAvailStringCmdResult()
	{
		return PapiManagerLibrary.INSTANCE.GetPapiNativeAvailStringCmdResult();
	}
	
	public static String GetPapiComponentAvailCmdResult()
	{
		return PapiManagerLibrary.INSTANCE.GetPapiComponentAvailCmdResult();
	}
	
	public static String GetPapiAvailCmdResult()
	{
		return PapiManagerLibrary.INSTANCE.GetPapiAvailCmdResult();
	}
}