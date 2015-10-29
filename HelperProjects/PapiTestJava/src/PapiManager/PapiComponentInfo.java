package PapiManager;

import java.util.ArrayList;

public class PapiComponentInfo
{
	private String _name;
	private ArrayList<PapiEventInfo> _events;
	
	public PapiComponentInfo(String name, ArrayList<PapiEventInfo> events)
	{
		_name = name;
		_events = events;
	}
	
	public String GetName()
	{
		return _name;
	}
	
	public ArrayList<PapiEventInfo> GetEvents()
	{
		return _events;
	}
	
}
