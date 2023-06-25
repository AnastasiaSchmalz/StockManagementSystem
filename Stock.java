package stockmanagementsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Stock{
	
	ArrayList<Drink> drinks;
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
	
	public int getTotalStock(String file) throws Exception {
		List<Drink> drinks = this.readStockData(file);
		totalStock = drinks.stream()
				.mapToInt(drink -> drink.getStock())
				.sum();
		return totalStock;
	}

	
	public ArrayList<Drink> readStockData(String file) throws Exception {
		ArrayList<Drink> drinksList = new ArrayList<>();
		int idAttribute = 0;
		String nameAttribute = null;
		double priceAttribute = 0;
		int stockAttribute = 0;
		
		File fileToRead = new File(file);
		Scanner scanner;
		try {
			scanner = new Scanner(fileToRead);
			while(scanner.hasNext()) {
				String drinkToBeSeparated = scanner.nextLine();
				String[] drinkAttributeArray = drinkToBeSeparated.split(";");
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
					else {
						throw new Exception("Exception while converting data into attributes for drinks");
					}
				}
				drinkToAdd = new Drink(idAttribute, nameAttribute, priceAttribute, stockAttribute);
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
				for(Drink drink : drinksList) {
					drink.setId(drinksList.indexOf(drink));
				}
			}
		scanner.close();
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

	
	protected void bookInDrink(String file, Drink newDrink) throws IOException {
		File fileWithNewDrink = new File(file);
		FileWriter filewriter = new FileWriter(fileWithNewDrink, true);
		
		String idString = String.valueOf(newDrink.getId()).concat(";");
		String nameString = newDrink.getName().concat(";");
		String priceString = String.valueOf(newDrink.getPrice()).concat(";");
		String stockString = String.valueOf(newDrink.getStock());
		String drinkString = idString+nameString+priceString+stockString;
		
		filewriter.write(drinkString);
		filewriter.write("\n");
		filewriter.close();
	}
	
	
	public static void main(String[] args) throws Exception {
	}

}
