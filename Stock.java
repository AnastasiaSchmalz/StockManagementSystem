package stockmanagementsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Stock{
	
	//arraylist contains drinks that were read from file
	public static ArrayList<Drink> drinks; 
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
		ArrayList<Drink> drinksList = Stock.readStockData(file);
		//returning a stream of the list with drinks
		totalStock = drinksList.stream()
				//returning a new stream for only the stock of each drink 
				.mapToInt(Drink::getStock)
				//returning the sum of stock of each drink 
				.sum();	
		return totalStock;
	}
	
	//using standard-methods for comparing and sorting arraylist of drinks
	//parameter is list that results from reading the file
	public static ArrayList<Drink> compareStockData(ArrayList<Drink> listOfDrinks) { 
		class DrinkComparator implements Comparator<Drink> {
			@Override
			public int compare(Drink drinkOne, Drink drinkTwo) {
				//first comparing stock of botch drink
				int compareValue = Integer.compare(drinkOne.getStock(), drinkTwo.getStock());
				//if stock is the same, comparing the names of both drinks 
				if(compareValue == 0) {
					compareValue = drinkOne.getName().compareTo(drinkTwo.getName()); 
				}
				return compareValue;
			}
		}
		Collections.sort(listOfDrinks, new DrinkComparator());
		for(Drink drink : listOfDrinks) {
			//changing id to reflect each drink's new spot in sorted table
			drink.setId(listOfDrinks.indexOf(drink)); 
		}
		return listOfDrinks;
	}

	
	public static ArrayList<Drink> readStockData(String file){
		//initializes list for storing drinks that were read from file
		ArrayList<Drink> drinksList = new ArrayList<>(); 
		int idAttribute = 0;
		String nameAttribute = null;
		double priceAttribute = 0;
		int stockAttribute = 0;
		
		File fileToRead = new File(file);
		//try-with-resources: closes scanner automatically after execution
		try (Scanner scanner = new Scanner(fileToRead)) { 
			//iterating through data in file
			while(scanner.hasNext()) { 
				//making for each line in csv-file its own string
				String drinkToBeSeparated = scanner.nextLine(); 
				//separating string into id, name, price and stock
				String[] drinkAttributeArray = drinkToBeSeparated.split(";");
				//instantiating drink-object for each drink in file to add it to an arraylist which will be shown in the table
				Drink drinkToAdd = null; 
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
			//dialog with warning for user
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
		//try-with-resources: closes filewriter automatically after execution
		try (FileWriter filewriter = new FileWriter(fileAfterTransformation, false)) { 
			//building one string with comma-separated values for id, name, price and stock and writes each string into a new line in the file
			for(Drink drink: drinks) {
				//building one string per drink with its id, name, price and stock
				String idString = String.valueOf(drink.getId()).concat(";");
				String nameString = drink.getName().concat(";");
				String priceString = String.valueOf(drink.getPrice()).concat(";");
				String stockString = String.valueOf(drink.getStock());
				String drinkString = idString+nameString+priceString+stockString;
				filewriter.write(drinkString);
				//writing every drinks into its own line in file
				filewriter.write("\n"); 
			}
		//exception if named file is not a file at all, can not be created or opened
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

	
	protected static void bookInDrink(String file, Drink newDrink) {
		//transforming arraylist into csv-file
		File fileWithNewDrink = new File(file);
		//try-with-resources: closes scanner automatically after execution
		try (FileWriter filewriter = new FileWriter(fileWithNewDrink, true)) { 
			//building a string with comma-separated values for id, name, price and stock and writes it into a new line in the file
			String idString = String.valueOf(newDrink.getId()).concat(";");
			String nameString = newDrink.getName().concat(";");
			String priceString = String.valueOf(newDrink.getPrice()).concat(";");
			String stockString = String.valueOf(newDrink.getStock());
			String drinkString = idString+nameString+priceString+stockString;
			
			filewriter.write(drinkString);
			filewriter.write("\n");
		//exception if named file is not a file at all, can not be created or opened
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

}
