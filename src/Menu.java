/*
 * Menu() class is used to execute function by sending parameters to it:
 * <ul>
 * 	<li>@param int option - sends choosen case from comboBox
 * 	<li>@param double number - sends value entered by user
 * 	<li>@param chosenConverter - sends which converter has been selected
 * 	<li>@param whichWay - sends in which way converter would work (e.g. it would convert PLN to EURO or EURO to PLN)
 * </ul>
 * <p>@return Menu return string result of converting
 */

public class Menu {
	Menu(){}
	
	String menu (int option, double number, int chosenConverter, int whichWay, double[] currency) {
		Surface sur = new Surface(currency);
		String result = "";
		//Area Switch
		if(chosenConverter == 0) {
			switch(option){
				case 0: 
					result = sur.sqMeters2sqFeets(number, whichWay);
					break;
				case 1:
					result = sur.sqMeters2sqYd(number, whichWay);
					break;
				case 2:
					result = sur.sqYd2sqFeets(number, whichWay);
					break;
				default:
					break;
			}
		}
		//weight Switch
		else if(chosenConverter== 1) {
			switch(option){
				case 0:
					result = sur.kg2lb(number, whichWay);
					break;
				case 1:
					result = sur.kg2oz(number, whichWay);
					break;
				case 2:
					result = sur.oz2lb(number, whichWay);
					break;
				default:
					break;
			}
		}
		//currency Switch
		else if(chosenConverter == 2) {
			switch(option){
			case 0:
				result = sur.pln2euro(number, whichWay);
				break;
			case 1:
				result = sur.pln2usd(number, whichWay);
				break;
			case 2:
				result = sur.euro2usd(number, whichWay);
				break;
			default:
				break;
		}
		}
	return result;
	}
}

	
