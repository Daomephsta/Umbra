package daomephsta.umbra;

public enum TimeOfDay
{
	SUNRISE(0),
	NOON(6000),
	SUNSET(12000),
	MIDNIGHT(18000);
	
	private final int ticks;
	
	private TimeOfDay(int ticks)
	{
		this.ticks = ticks;
	}
	
	public int getTicks()
	{
		return ticks;
	}
}
