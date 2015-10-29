package PapiManager;

public class PapiEventResult
{
	private String _name;
	private String _unit;
	private long _result;
	
	public PapiEventResult(String name, String unit, long result)
	{
		_name = name;
		_unit = unit;
		_result = result;
	}
	
	public String GetName()
	{
		return _name;
	}
	
	public String GetUnit()
	{
		return _unit;
	}
	
	public long GetResult()
	{
		return _result;
	}
}
