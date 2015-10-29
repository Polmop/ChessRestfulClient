package Model;

public class TotalEventResult
{
	private int _totalRuns;
	private double _averageResult;
	
	public TotalEventResult()
	{
		_totalRuns = 0;
		_averageResult = 0;
	}
	
	public void AddResult(long result)
	{
		_totalRuns++;
		
		if(_totalRuns == 1)
		{
			_averageResult = (double)result;
		}
		else
		{
			double a = _averageResult * (((double)_totalRuns - 1)/(double)_totalRuns);
			double b = (double)result * (1/(double)_totalRuns);
			
			_averageResult = a + b;
		}
	}
	
	public int GetTotalRuns()
	{
		return _totalRuns;
	}
	
	public double GetAverageResult()
	{
		return _averageResult;
	}
}
