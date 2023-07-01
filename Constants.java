package stockmanagementsystem;

//class for holding values of constants
public class Constants {
	
	//private constructor, so class can not be instantiated elsewhere
	private Constants() {
		throw new IllegalStateException("Utility class");
	}

	public static final String fileDrinksList = "C:\\Users\\Anastasia\\StockManagementSystem\\Lager.CSV\\";
	public static final String fileDone = "C:\\Users\\Anastasia\\StockManagementSystem\\Done.CSV\\";
	public static final String testFile = "C:\\Users\\Anastasia\\StockManagementSystem\\TestDrinksList.CSV\\";
}
