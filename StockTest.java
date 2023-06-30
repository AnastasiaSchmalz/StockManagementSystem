package stockmanagementsystem;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StockTest {
	
	//initializing empty list and drinks for following tests
	ArrayList<Drink> testDrinks = new ArrayList<>();
	Drink drinkOne = new Drink(1, "Pepsi", 0.99, 368);
	Drink drinkTwo = new Drink(2, "Coca Cola", 0.99, 299);
	Drink drinkThree = new Drink(3, "Fanta", 0.99, 459);
	Drink drinkFour = new Drink(4, "7Up", 0.99, 342);
	Drink drinkFive = new Drink(5, "Dr. Pepper", 0.99, 299);
	Drink drinkSix = new Drink(6, "La Croix", 0.99, 412);
	
	@BeforeEach
	void init() {
		testDrinks.add(drinkOne);
		testDrinks.add(drinkTwo);
		testDrinks.add(drinkThree);
		testDrinks.add(drinkFour);
		testDrinks.add(drinkFive);
		testDrinks.add(drinkSix);
	}
	
	@Test
	void testGetDrinks() {		
		Stock.drinks = testDrinks;
		assertEquals(testDrinks, Stock.getDrinks());
	}
	
	@Test
	void testGetTotalStock() {
		
		//transforming arraylist into csv-file
		File testFile = new File(Constants.testFile);
		try (FileWriter filewriter = new FileWriter(testFile, false)) { //try-with-ressources: closes filewriter automatically after execution
			//building one string with comma-seperated values for id, name, price and stock and writes each string into a new line in the file
			for(Drink drink: testDrinks) {
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
		
		assertEquals(2179, Stock.getTotalStock(Constants.testFile));
	}
	
	@Test
	void TestCompareStockData() {
		
		//Creating list of manually sorted drinks, tests both sorting conditions
		ArrayList<Drink> sortedTestDrinks = new ArrayList<>();
		sortedTestDrinks.add(drinkTwo);
		sortedTestDrinks.add(drinkFive);
		sortedTestDrinks.add(drinkFour);
		sortedTestDrinks.add(drinkOne);
		sortedTestDrinks.add(drinkSix);
		sortedTestDrinks.add(drinkThree);
		
		assertEquals(sortedTestDrinks,Stock.compareStockData(testDrinks));
	}
	
	@Test
	void testReadStockData() throws Exception {

		//transforming arraylist into csv-file
		File testFile = new File(Constants.testFile);
		try (FileWriter filewriter = new FileWriter(testFile, false)) { //try-with-ressources: closes filewriter automatically after execution
			//building one string with comma-seperated values for id, name, price and stock and writes each string into a new line in the file
			for(Drink drink: testDrinks) {
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
		ArrayList<Drink> testList = Stock.readStockData(Constants.testFile);
		Stock.compareStockData(testDrinks); //sorts drinks by stock, in case it is the same, then sorts by name
		for(int i=0; i<6; i++) {
			assertEquals(testDrinks.get(i).getName(),testList.get(i).getName());
		}
		
	}
	
	@Test
	void TestSetNewStockInFile() {
		
		//tests method based on the arraylist which is read from the file after setNewStockInFile() was processed
		Stock.setNewStockInFile(Constants.testFile, 5, 500);
		ArrayList<Drink> listWithChangedStock = Stock.readStockData(Constants.testFile);
		
		assertEquals(500, listWithChangedStock.get(5).getStock());
	}
	
	@Test
	void TestBookInDrink() {
		
		Drink testDrink = new Drink(testDrinks.size(), "Spezi", 0.99, 600);
		Stock.bookInDrink(Constants.testFile, testDrink);
		assertEquals(testDrink.getName(), Stock.readStockData(Constants.testFile).get(Stock.drinks.size()-1).getName()); //compares names of new drink and new element in list of drinks
		
	}

}
