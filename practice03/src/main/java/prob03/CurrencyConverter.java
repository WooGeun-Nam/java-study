package prob03;

public class CurrencyConverter {
	private static double rate;

	public static void setRate(double rate) {
		CurrencyConverter.rate = rate;
	}
	// 스태틱 메소드

	public static double toDollar(double d) {
		double result = d/rate;
		return result;
	}

	public static double toKRW(double d) {
		double result = d*rate;
		return result;
	}
}
