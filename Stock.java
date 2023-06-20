package stockmanagementsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Stock extends Observable{
	
	static ArrayList<Drink> drinks;
	int totalStock;
	double totalPrice;
	
	public Stock() {
//		this.drinks = drinks;
//		this.totalStock = totalStock;
//		this.totalPrice = totalPrice;
	}
	
	public ArrayList<Drink> getDrinks() {
		for(Drink drink : drinks) {
			System.out.println(drink.getId());
			System.out.println(drink.getName());
			System.out.println(drink.getPrice());
			System.out.println(drink.getStock());
		}
		return drinks;
	}
	
/*	public int getTotalStock() {
		return totalStock;
	}
	
	public double totalPrice() {
		return totalPrice;
	} */
	
	public ArrayList<Drink> readStockData(String file) throws Exception {
		ArrayList<Drink> drinksList = new ArrayList<>();
		int idAttribute = 0;
		String nameAttribute = null;
		double priceAttribute = 0;
		int stockAttribute = 0;
		
		//array for converting each line from csv-data into its own array
		String[] drinksPerLine = new String[1];
		
		File fileToRead = new File(file);
		Scanner scanner;
		try {
			scanner = new Scanner(fileToRead);
			while(scanner.hasNext()) {
			String drinkToBeSeparated = scanner.nextLine();
			for(int i = 0; i < 1; i++) {
				drinksPerLine[i] = drinkToBeSeparated;
//				String drinkAttribute = Arrays.toString(drinksPerLine);				
//				System.out.println(drinkAttribute);
				String[] drinkAttributeArray = drinkToBeSeparated.split(";");
//				System.out.println(Arrays.toString(drinkAttributeArray));
				Drink drinkToAdd = null;
				for (int j = 0; j < 4; j++) {
					if(j == 0) {
						idAttribute = Integer.parseInt(drinkAttributeArray[j]);
					}
					else if(j == 1) {
						nameAttribute = drinkAttributeArray[j];
					}
					else if(j == 2) {
						priceAttribute = Double.parseDouble(drinkAttributeArray[j]);
					}
					else if(j == 3) {
						stockAttribute = Integer.parseInt(drinkAttributeArray[j]);
					}
					else {
						throw new Exception("Error while converting data into attributes for drinks");
					}
				}
				drinkToAdd = new Drink(idAttribute, nameAttribute, priceAttribute, stockAttribute);
/*				System.out.println(drinkToAdd.getName());
				System.out.println(drinkToAdd.getPrice());
				System.out.println(drinkToAdd.getStock()); */
				drinksList.add(drinkToAdd);
				
				//comparing and sorting by stock and by name
				class DrinkComparator implements Comparator<Drink> {
					@Override
					public int compare(Drink drinkOne, Drink drinkTwo) {
						int compareValue = Integer.compare(drinkOne.getStock(), drinkTwo.getStock());
						if(compareValue == 0) {
							compareValue = drinkOne.getName().compareTo(drinkTwo.getName());
						}
						return compareValue;
					}
				}
				Collections.sort(drinksList, new DrinkComparator());
				}
			} scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		drinks = drinksList;
		return drinksList;
	}
	
	public void setNewStockInFile(String file, int index, int newStock) throws Exception {
		//reads file and converts into arraylist
		this.readStockData(file);
		//looks for drink with corresponding index to change its stock
		for (Drink drink: drinks) {
			if(index == drink.getId()) {
				drink.setStock(newStock);
			}
		}
		//transforms arraylist into csv-file
		File fileAfterTransformation = new File(file);
		FileWriter filewriter = new FileWriter(fileAfterTransformation, false);
		for(Drink drink: drinks) {
			String idString = String.valueOf(drink.getId()).concat(";");
			String nameString = drink.getName().concat(";");
			String priceString = String.valueOf(drink.getPrice()).concat(";");
			String stockString = String.valueOf(drink.getStock());
			String drinkString = idString+nameString+priceString+stockString;
			filewriter.write(drinkString);
			filewriter.write("\n");
		}
		filewriter.close();
	}
	
	private void testTransformListToStrings( ) {
		Drink drinkOne = new Drink(1, "Pepsi", 0.99, 368);
		Drink drinkTwo = new Drink(2, "Coca Cola", 0.99, 299);
		Drink drinkThree = new Drink(3, "Fanta", 0.99, 459);
		Drink drinkFour = new Drink(4, "7Up", 0.99, 342);
		Drink drinkFive = new Drink(5, "Dr. Pepper", 0.99, 256);
		Drink drinkSix = new Drink(6, "La Croix", 0.99, 412);
		
		ArrayList<Drink> drinksTest = new ArrayList<>();
		drinksTest.add(drinkOne);
		drinksTest.add(drinkTwo);
		drinksTest.add(drinkThree);
		drinksTest.add(drinkFour);
		drinksTest.add(drinkFive);
		drinksTest.add(drinkSix);
		
		for (Drink drink: drinksTest) {
			if(drink.getId() == 1) {
				drink.setStock(700);
			}
		}
		
		for(Drink drink: drinksTest) {
			String idString = String.valueOf(drink.getId()).concat(";");
			String nameString = drink.getName().concat(";");
			String priceString = String.valueOf(drink.getPrice()).concat(";");
			String stockString = String.valueOf(drink.getStock());
			String drinkString = idString+nameString+priceString+stockString;
			System.out.println(drinkString);
		}
	}
	
	protected void bookInDrink(Drink newDrink) {
		//not implemented yet
	}
	
	protected void bookOutDrink(Drink drinkToRemove) {
		//not implemented yet
	}
	
	//Observable Methods
	@Override
	public void addObserver(Observer o) {
		
	}
	
	@Override
	public void deleteObserver(Observer o) {
		
	}
	
	public static void main(String[] args) throws Exception {
/*		Drink drinkOne = new Drink("Pepsi", 0.99, 368);
		Drink drinkTwo = new Drink("Fanta", 0.99, 289);
		Drink[] drinks ={drinkOne, drinkTwo};*/
		Stock stock = new Stock(); 
		
		stock.setNewStockInFile("C:\\Users\\Anastasia\\OneDrive\\Dokumente\\GetränkeTest1.CSV\\", 1, 521);
		stock.readStockData("C:\\Users\\Anastasia\\OneDrive\\Dokumente\\GetränkeTest1.CSV\\");
		System.out.println(stock.getDrinks());
		
//		stock.testTransformListToStrings();
	}

}
