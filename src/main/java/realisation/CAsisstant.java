package main.java.realisation;

public class CAsisstant {
	/**
	 * Method check String on belong numeric the value 
	 * @param isString
	 * @return If String is numeric return true or false if String is't numeric
	 */
	public static boolean isNumeric(String isString) {
		boolean isNumeric = false;
		isString.trim();
		
		if (isString == "" || isString == null) {
			isNumeric = false;
		}
				
		try {
			Integer.parseInt(isString);
			isNumeric = true;
		} catch (Exception e) {
			isNumeric = false;
		}
		
		try {
			Double.parseDouble(isString);
			isNumeric = true;
		} catch (Exception e) {
			isNumeric = false;
		}
		
		return isNumeric;
		
	}
}
