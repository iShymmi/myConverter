import java.text.DecimalFormat;
/*
 * class Surface() contains methods that calculate the appropriate values
 * @return Returns a result in decimal format
 */

class Surface {
	private double pln2euroConstant;
	private double pln2usdConstant;
	private double euro2usdConstant;
	
	Surface(double[] currency){
		pln2euroConstant = currency[0];
		pln2usdConstant = currency[2];
		euro2usdConstant = currency[1];
	};
	
	private DecimalFormat decimalFormat = new DecimalFormat("#0.00");
	private double  result = 0;
	private final double sqMeters2sqFeetsConstant = 10.764;
	private final double sqMeters2sqYdConstant = 1.1960;
	private final double sqYd2sqFeetsConstant = 0.11111;
	private final double kg2lbConstant = 2.20462262;
	private final double kg2ozConstant = 35.2739619;
	private final double oz2lbConstant = 0.0625;
	
	//AREA
	String sqMeters2sqFeets (double sf, int whichWay) {
		if (whichWay == 1) {
			result = sf*sqMeters2sqFeetsConstant;
		}
		else if (whichWay == 2) {
			result = sf/sqMeters2sqFeetsConstant;
		}
		return decimalFormat.format(result);
	}
	String sqMeters2sqYd (double sf, int whichWay) {
		if (whichWay == 1) {
			result = sf*sqMeters2sqYdConstant;
		}
		else if (whichWay == 2) {
			result = sf/sqMeters2sqYdConstant;
		}
		return decimalFormat.format(result);
	}
	String sqYd2sqFeets (double sf, int whichWay) {
		if (whichWay == 1) {
			result = sf/sqYd2sqFeetsConstant;
		}
		else if (whichWay == 2) {
			result = sf*sqYd2sqFeetsConstant;
		}
		return decimalFormat.format(result);
	}
	//WEIGHT
	String kg2lb (double sf, int whichWay) {
		if (whichWay == 1) {
			result = sf*kg2lbConstant;
		}
		else if (whichWay == 2) {
			result = sf/kg2lbConstant;
		}
		return decimalFormat.format(result);
	}
	String kg2oz (double sf, int whichWay) {
		if (whichWay == 1) {
			result = sf*kg2ozConstant;
		}
		else if (whichWay == 2) {
			result = sf/kg2ozConstant;
		}
		return decimalFormat.format(result);
	}
	String oz2lb (double sf, int whichWay) {
		if (whichWay == 1) {
			result = sf/oz2lbConstant ;
		}
		else if (whichWay == 2) {
			result = sf*oz2lbConstant ;
		}
		return decimalFormat.format(result);
	}
	//CURRENCY
	String pln2euro (double sf, int whichWay) {
		if (whichWay == 1) {
			result = sf/pln2euroConstant;
		}
		else if (whichWay == 2) {
			result = sf*pln2euroConstant;
		}
		return decimalFormat.format(result);
	}
	String pln2usd (double sf, int whichWay) {
		if (whichWay == 1) {
			result = sf/pln2usdConstant;
		}
		else if (whichWay == 2) {
			result = sf*pln2usdConstant;
		}
		return decimalFormat.format(result);
	}
	String euro2usd (double sf, int whichWay) {
		if (whichWay == 1) {
			result = sf*euro2usdConstant ;
		}
		else if (whichWay == 2) {
			result = sf/euro2usdConstant ;
		}
		return decimalFormat.format(result);
	}
}
