package ua.ccc.calc.enums;

public enum Currency
{
	USD("$"),
	EUR("€"),
	UAH("₴");
	
	private final String _emblem;
	
	Currency(String emblem)
	{
		_emblem = emblem;
	}
	
	public String getEmblem()
	{
		return _emblem;
	}
}