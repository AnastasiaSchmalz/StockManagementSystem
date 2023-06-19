package stockmanagementsystem.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import stockmanagementsystem.Drink;
import stockmanagementsystem.Stock;

class StockTest {
	
	@Test
	void testReadStockData() throws Exception {
		Drink drinkOne = new Drink("Pepsi", 0.99, 368);
		Drink drinkTwo = new Drink("Coca Cola", 0.99, 299);
		Drink drinkThree = new Drink("Fanta", 0.99, 459);
		Drink drinkFour = new Drink("7Up", 0.99, 342);
		Drink drinkFive = new Drink("Dr. Pepper", 0.99, 256);
		Drink drinkSix = new Drink("La Croix", 0.99, 412);
		
		ArrayList<Drink> testDrinks = new ArrayList<>();
		testDrinks.add(drinkOne);
		testDrinks.add(drinkTwo);
		testDrinks.add(drinkThree);
		testDrinks.add(drinkFour);
		testDrinks.add(drinkFive);
		testDrinks.add(drinkSix);

		Stock stock = new Stock();
		//assertTrue(testDrinks.equals(stock.readStockData("C:\\Users\\Anastasia\\OneDrive\\Dokumente\\GetränkeTest1.CSV\\")));
		//assertEquals(testDrinks.get(0).getName(),getName(stock.readStockData("C:\\Users\\Anastasia\\OneDrive\\Dokumente\\GetränkeTest1.CSV\\").get(0)));
	}

}
