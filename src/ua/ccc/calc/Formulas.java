package ua.ccc.calc;

import java.util.Calendar;

import ua.ccc.Config;
import ua.ccc.calc.enums.EngineType;

public class Formulas
{
	/**
	 * Ввозная пошлина (в евро).
	 * @param carPrice - цена авто (в евро)
	 * @return евро
	 */
	public static double getImportDuty(int carPrice, EngineType type)
	{
		// Ввозная пошлина не действует на электромобили
		if (type == EngineType.ELECTRIC)
			return 0;
		
		return Math.round(carPrice * 0.07);
	}
	
	/**
	 * Акцизный сбор (в евро).
	 * @param engineVolume - объем двигателя (см³)
	 * @param yearOfIssue - год выпуска
	 * @param type - тип двигателя
	 * @return евро
	 */
	public static double getExcise(double engineVolume, int yearOfIssue, EngineType type)
	{
		// До 2022 года полностью электрические автомобили освобождены от акцизного сбора
		if (type == EngineType.ELECTRIC)
			return 0;
		
		// Возраст считается из следующего года от выпуска авто
		int carAge = (Calendar.getInstance().getWeekYear() - yearOfIssue-1);
		
		// Автомобили старше 15 лет считаются за 15-ти летние
		if (carAge > 15)
			carAge = 15;
		
		int baseRate;
		
		if (type == EngineType.GAS)
		{
			if (engineVolume <= 3000)
				baseRate = 50;
			else
				baseRate = 100;
		}
		else
		{
			if (engineVolume <= 3500)
				baseRate = 75;
			else
				baseRate = 150;
		}
		
		return (engineVolume / 1000) * baseRate * carAge;
	}
	
	/**
	 * Налог на добавленую стоимость (НДС) (в евро).<BR><BR>
	 * (акциз + пошлина + стоимость авто) * 0.2=>(20%)
	 * @param carPrice - цена авто (в евро)
	 * @param importDuty - ввозная пошлина
	 * @param excise - акцизный сбор
	 * @return евро
	 */
	public static double getVAT(int carPrice, double importDuty, double excise)
	{
		return (carPrice + importDuty + excise) * 0.2;
	}
	
	/**
	 * Сбор за первую регистрацию транспортного средства (в гривнах).<BR><BR>
	 * @param engineVolume - объем двигателя (см³)
	 * @param yearOfIssue - год выпуска
	 * @return гривны
	 */
	public static double getFirstRegistrationFee(double engineVolume, int yearOfIssue)
	{
		// Возраст считается из следующего года от выпуска авто
		final int carAge = (Calendar.getInstance().getWeekYear() - yearOfIssue-1);
		
		int val = 1;
		
		// Коефициент для ТС, которые использовались свыше 8 лет
		if (carAge > 8)
			val = 40;
		
		return (engineVolume * 0.089) * val;
	}
	
	
	/**
	 * Сбор в пенсионный фонд. от 3% до 5%, в зависимости от стоимости растаможенного авто.<BR><BR>
	 * @param fullCarPrice - полная цена автомобиля (в гривнах)
	 * @return гривны
	 */
	public static double getPensionFundFee(double fullCarPrice)
	{
		double val = 0.03; //3%
		
		if (fullCarPrice > 510980)
			val = 0.05; //5%
		else if (fullCarPrice > 290730)
			val = 0.04; //4%;
		
		return fullCarPrice * val;
	}
	
	/**
	 * Конвертация суммы из евро в доллар.<BR><BR>
	 * @param eur - евро
	 * @return доллар
	 */
	public static int EURToUSD(double eur)
	{
		return Math.round((float) ((Config.EUR / Config.USD) * eur));
	}
	
	/**
	 * Конвертация суммы из доллара в евро.<BR><BR>
	 * @param usd - доллар
	 * @return евро
	 */
	public static int USDToEUR(double usd)
	{
		return Math.round((float) (usd / (Config.EUR / Config.USD)));
	}
	
	/**
	 * Конвертация суммы из евро в гривны.<BR><BR>
	 * @param eur - евро
	 * @return гривны
	 */
	public static int EURToUAH(double eur)
	{
		return Math.round((float) (Config.EUR * eur));
	}
	
	/**
	 * Конвертация суммы из гривны в евро.<BR><BR>
	 * @param uah - гривна
	 * @return евро
	 */
	public static int UAHToEUR(double uah)
	{
		return Math.round((float) (uah / Config.EUR));
	}
	
	/**
	 * Конвертация суммы из гривны в доллар.<BR><BR>
	 * @param uah - гривна
	 * @return доллар
	 */
	public static int UAHToUSD(double uah)
	{
		return Math.round((float) (uah / Config.USD));
	}
	
	/**
	 * Конвертация суммы из доллара в гривны.<BR><BR>
	 * @param usd - доллар
	 * @return гривны
	 */
	public static int USDToUAH(double usd)
	{
		return Math.round((float) (usd * Config.USD));
	}
}