package stockmanagementsystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CheckDoneFile extends Thread{
	
	File file = new File (Constants.fileDone);
	String[] nameAndChangeInStockArray;
	ArrayList<DrinkDoneFile> drinksDone = new ArrayList<>();

	@Override
	public void run() {
		while(true) {
			try {
				Scanner scanner = new Scanner(file);
				while(scanner.hasNext()) {
				String nameAndChangeInStock = scanner.nextLine(); //building string for each line in Done.csv
				nameAndChangeInStockArray = nameAndChangeInStock.split(";"); //seperating name from change in stock in string
				String name =""; //instantiating name
				int changeInStock = 0; //instantiating change in stock
				
				for(int i=0; i<2; i++) { //loop to convert name and change in stock into corresponding data types
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
			AppServices.changeStockFromDone(drinksDone, drinks);	//method to change stock of a drink based on data in Done.csv; throws exception if value of new stock will be negative
			scanner.close(); //closes scanner manually, so file can be deleted afterwards
			file.delete();
			Thread.sleep(60000); //sleeps for 1 minute
			} catch (Exception e) {
				e.printStackTrace();
				if(e instanceof InterruptedException) {
					Thread.currentThread().interrupt(); //sets interrupt status to true
				}
			}
		}
	}
}
