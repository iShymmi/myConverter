import java.util.Locale;

/**
*Converter gives the opportunity to convert:
*<ol> 
	<li>currency:
	    <ul> 
	        <li>PLN <==> EURO,
	        <li>PLN <==> USD,
	        <li>EURO <==> USD
	    </ul>
	<li>weight:
	    <ul> 
	        <li>kg <==> lb,
	        <li>kg <==> oz,
	        <li>lb <==> oz
	    </ul>
	<li>area:
	    <ul> 
	        <li>M^2 <==> FT^2,
	        <li>M^2 <==> yd^2,
	        <li>yd^2 <==> FT^2
	    </ul>
</ol>
@author Rafal Szymanski - Informatyka Niestacjonarne semestr 4.
@version java version "1.8.0_241"
*/

public class Main {
	public static void main(String[] args) {
		GUI gui = new GUI(Locale.getDefault(), null ,null ,null);
		try{
			new ConverterSplashScreen().paint();
		}catch(Exception e) {
			System.out.println("SplashScreen Error");
		}
		gui.showGUI();
	}
}
