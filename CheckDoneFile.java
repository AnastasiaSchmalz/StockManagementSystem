package stockmanagementsystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CheckDoneFile extends Thread{
	
	File file = new File (Constants.fileDone);
	String[] nameAndChangeInStockArray;
	ArrayList<DrinkDoneFile> drinksDone = new ArrayList<>();

	@Override
	public void run() { //calls Runnable's run()-methode
		while(true) {
			try {
				Scanner scanner = new Scanner(file);
				while(scanner.hasNext()) {
				//building string for each line in Done.csv
				String nameAndChangeInStock = scanner.nextLine(); 
				//separating name from change in stock in string
				nameAndChangeInStockArray = nameAndChangeInStock.split(";"); 
				//initializing name
				String name =""; 
				//initializing change in stock
				int changeInStock = 0; 
				
				//loop for converting name and change in stock into corresponding data types
				for(int i=0; i<2; i++) { 
					if(i == 0) {
						name = nameAndChangeInStockArray[i];
					}
					else if(i == 1) {
						changeInStock = Integer.parseInt(nameAndChangeInStockArray[i]);
					}
				}
				DrinkDoneFile drinkToAdd = new DrinkDoneFile(name, changeInStock);
				drinksDone.add(drinkToAdd);
				}
				ArrayList<Drink> drinks = Stock.readStockData(Constants.fileDrinksList);
				
				//using method to change stock of a drink based on data in Done.csv; throws exception if value of new stock will be negative
				AppServices.changeStockFromDone(drinksDone, drinks);
				//closing scanner manually, so file can be deleted afterwards	
				scanner.close(); 
				//deleting file after executing previous methods
				file.delete(); 
				//sleeping for 1 minute
				Thread.sleep(60000); 
			} catch (Exception e) {
				e.printStackTrace();
				if(e instanceof InterruptedException) {
					//setting interrupt status to true
					Thread.currentThread().interrupt(); 
				}
			}
		}
	}
}
