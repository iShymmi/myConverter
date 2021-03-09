import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/*
 * ConversionFromFile() is a class that read given File and calls method to operate on them
 */
class ConversionFromFile {
	private TableOfConversion tableOfConversion;
	private ArrayList<String> inputArray = new ArrayList<>();
	private ArrayList<String> resultArray = new ArrayList<>();
	private ArrayList<String> inConvertingLables = new ArrayList<>();
	void convert(File textFile, int choosenConverter, int whichWay, int optionID, String convertingLables, double[] currency, Locale l) {
		FileReader fileReader = null;
				BufferedReader bufferedReader = null;
				Menu menu = new Menu();
				inputArray = new ArrayList<>();
				resultArray = new ArrayList<>();
				double parsedInput;
				ResourceBundle r = ResourceBundle.getBundle("messages", l);
				String result;
				try {
					fileReader = new FileReader(textFile);
					bufferedReader = new BufferedReader(fileReader);
					String line;				
		            while ((line = bufferedReader.readLine()) != null) {
		            	String[] tokens = line.split(",|\\s");
			            	for (String input: tokens) {
			            		if(!input.isEmpty()) {
				            		try {
				            			parsedInput = Double.parseDouble(input);
				            	        result = menu.menu(optionID, parsedInput, choosenConverter, whichWay, currency);
				            	        resultArray.add(result.replaceAll(",","."));
				            	        inputArray.add(input.replaceAll(",","."));
				            	        inConvertingLables.add(convertingLables);
				            	    } catch (NumberFormatException nfe) {
										nfe.printStackTrace();
									}
			            		}
		            	}
		            }
				} catch (IOException e1) {
					e1.printStackTrace();
				}finally {
		            try {	            	
		                if (bufferedReader != null)	bufferedReader.close();

		                if (fileReader != null) fileReader.close();
		               
		                TableOfConversion tableOfConversion = new TableOfConversion(inputArray, resultArray, inConvertingLables, r.getLocale());
		                tableOfConversion.showGUI();
		            } catch (IOException ex) {
		                System.err.format("IOException: %s%n", ex);
		            }
		        }
			}
	
	TableOfConversion getConversionTable() {
		return tableOfConversion;
	}

	ArrayList<String> getInputArray() {
		return inputArray;
	}
	ArrayList<String> getResultArrayArray() {
		return resultArray;
	}
	ArrayList<String> getInConvertingLables() {
		return inConvertingLables;
	}
}
