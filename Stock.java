package stockmanagementsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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
/*		for(Drink drink : drinks) {
			System.out.println(drink.getName());
			System.out.println(drink.getPrice());
			System.out.println(drink.getStock());
		}*/
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
		String nameAttribute = null;
		double priceAttribute = 0;
		int stockAttribute = 0;
		
		//to convert each line from csv-data into its own array
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
				for (int j = 0; j < 3; j++) {
					if(j == 0) {
						nameAttribute = drinkAttributeArray[j];
					}
					else if(j == 1) {
						priceAttribute = Double.parseDouble(drinkAttributeArray[j]);
					}
					else if(j == 2) {
						stockAttribute = Integer.parseInt(drinkAttributeArray[j]);
					}
					else {
						throw new Exception("There was an error while converting data into attributes for drinks");
					}
				}
				drinkToAdd = new Drink(nameAttribute, priceAttribute, stockAttribute);
/*				System.out.println(drinkToAdd.getName());
				System.out.println(drinkToAdd.getPrice());
				System.out.println(drinkToAdd.getStock()); */
				drinksList.add(drinkToAdd);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		drinks = drinksList;
		return drinksList;
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
		
		stock.readStockData("C:\\Users\\Anastasia\\OneDrive\\Dokumente\\GetränkeTest1.CSV\\");
		System.out.println(stock.getDrinks());
	}

}
