package stockmanagementsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Stock{
	
	static ArrayList<Drink> drinks;
	static int totalStock;
	static double totalPrice;
	
	//private constructor, so class can not be instantiated elsewhere
	private Stock() {
		throw new IllegalStateException("Utility class");
	}
	
	public static ArrayList<Drink> getDrinks() {
		return drinks;
	}
	
	public static int getTotalStock(String file) {
		List<Drink> drinksList = Stock.readStockData(file);
		totalStock = drinksList.stream()
				.mapToInt(Drink::getStock)
				.sum();
		return totalStock;
	}
	
	//standard-methods for comparing and sorting arraylist of drinks
	public static void compareStockData(ArrayList<Drink> listOfDrinks) { //new list for sorted drinks
		class DrinkComparator implements Comparator<Drink> {
			@Override
			public int compare(Drink drinkOne, Drink drinkTwo) {
				int compareValue = Integer.compare(drinkOne.getStock(), drinkTwo.getStock()); //first compares stock of botch drink
				if(compareValue == 0) {
					compareValue = drinkOne.getName().compareTo(drinkTwo.getName()); //if stock is the same, it compares the names of both drinks
				}
				return compareValue;
			}
		}
		Collections.sort(listOfDrinks, new DrinkComparator());
		for(Drink drink : listOfDrinks) {
			drink.setId(listOfDrinks.indexOf(drink)); //changing id to reflect each drink's new spot in sorted table
		}
	}

	
	public static ArrayList<Drink> readStockData(String file){
		ArrayList<Drink> drinksList = new ArrayList<>();
		int idAttribute = 0;
		String nameAttribute = null;
		double priceAttribute = 0;
		int stockAttribute = 0;
		
		File fileToRead = new File(file);
		try (Scanner scanner = new Scanner(fileToRead)) { //try-with-ressources: closes scanner automatically after execution
			while(scanner.hasNext()) { //iterates through data in file
				String drinkToBeSeparated = scanner.nextLine(); //makes for each line in csv-file its own string
				String[] drinkAttributeArray = drinkToBeSeparated.split(";"); //seperates String into id, name, price and stock
				Drink drinkToAdd = null; //instantiating drink-object for each drink in file to add it to an arraylist which will be shown in the table
				for (int i = 0; i < 4; i++) {
					if(i == 0) {
						idAttribute = Integer.parseInt(drinkAttributeArray[i]);
					}
					else if(i == 1) {
						nameAttribute = drinkAttributeArray[i];
					}
					else if(i == 2) {
						priceAttribute = Double.parseDouble(drinkAttributeArray[i]);
					}
					else if(i == 3) {
						stockAttribute = Integer.parseInt(drinkAttributeArray[i]);
					}
				}
				drinkToAdd = new Drink(idAttribute, nameAttribute, priceAttribute, stockAttribute);
				drinksList.add(drinkToAdd);
				
				//comparing and sorting by stock and by name
				Stock.compareStockData(drinksList);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Datei existiert nicht oder kann nicht geöffnet werden", "Warnung", JOptionPane.WARNING_MESSAGE);
		}
		drinks = drinksList;
		return drinksList;
	}
	
	public static void setNewStockInFile(String file, int index, int newStock)  {
		//reads file and converts into arraylist
		Stock.readStockData(file);
		//looks for drink with corresponding index to change its stock
		for (Drink drink: drinks) {
			if(index == drink.getId()) {
				drink.setStock(newStock);
			}
		}
		//transforming arraylist into csv-file
		File fileAfterTransformation = new File(file);
		try (FileWriter filewriter = new FileWriter(fileAfterTransformation, false)) { //try-with-ressources: closes filewriter automatically after execution
			//building one string with comma-seperated values for id, name, price and stock and writes each string into a new line in the file
			for(Drink drink: drinks) {
				//building one string per drink with its id, name, price and stock
				String idString = String.valueOf(drink.getId()).concat(";");
				String nameString = drink.getName().concat(";");
				String priceString = String.valueOf(drink.getPrice()).concat(";");
				String stockString = String.valueOf(drink.getStock());
				String drinkString = idString+nameString+priceString+stockString;
				filewriter.write(drinkString);
				filewriter.write("\n"); //makes sure every drink is written into its own line in file
			}
		} catch (IOException e) { //exception if named file is not a file at all, can not be created or opened
			e.printStackTrace();
		}
	}

	
	protected static void bookInDrink(String file, Drink newDrink) {
		//transforming arraylist into csv-file
		File fileWithNewDrink = new File(file);
		try (FileWriter filewriter = new FileWriter(fileWithNewDrink, true)) { //try-with-ressources: closes scanner automatically after execution
			//building a string with comma-seperated values for id, name, price and stock and writes it into a new line in the file
			String idString = String.valueOf(newDrink.getId()).concat(";");
			String nameString = newDrink.getName().concat(";");
			String priceString = String.valueOf(newDrink.getPrice()).concat(";");
			String stockString = String.valueOf(newDrink.getStock());
			String drinkString = idString+nameString+priceString+stockString;
			
			filewriter.write(drinkString);
			filewriter.write("\n");
		} catch (IOException e) { //exception if named file is not a file at all, can not be created or opened
			e.printStackTrace();
		}
	}

}
