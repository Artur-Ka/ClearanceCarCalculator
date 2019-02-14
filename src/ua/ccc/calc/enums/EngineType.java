package ua.ccc.calc.enums;

public enum EngineType
{
	/** Бензин */
	GAS("Бензин"),
	/** Дизель */
	DIESEL("Дизель"),
	/** Электро */
	ELECTRIC("Электро");
	
	private final String _name;
	
	EngineType(String name)
	{
		_name = name;
	}
	
	@Override
	public String toString()
	{
		return _name;
	}
}