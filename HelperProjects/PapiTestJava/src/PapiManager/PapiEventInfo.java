package PapiManager;

public class PapiEventInfo
{
	private String _name;
	private String _unit;
	private int _code;
	private int _eventType;
	private int _returnDataType;
	
	public PapiEventInfo(String name, String unit, int code, int eventType, int returnDataType)
	{
		_name = name;
		_unit = unit;
		_code = code;
		_eventType = eventType;
		_returnDataType = returnDataType;
	}
	
	public String GetName()
	{
		return _name;
	}
	
	public String GetUnit()
	{
		return _unit;
	}
	
	public int GetCode()
	{
		return _code;
	}
	
	public int GetEventType()
	{
		return _eventType;
	}
	
	public int GetReturnDataType()
	{
		return _returnDataType;
	}
	
}
