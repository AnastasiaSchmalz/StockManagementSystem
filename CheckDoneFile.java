package stockmanagementsystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CheckDoneFile extends Thread{
	
	File file = new File (Constants.fileDone);
	Scanner scanner;
	String[] nameAndChangeInStockArray;
	ArrayList<DrinkFromDoneFile> drinksDone = new ArrayList<>();

	@Override
	public void run() {
		while(true) {
		try {
			scanner = new Scanner(file);
			while(scanner.hasNext()) {
				String nameAndChangeInStock = scanner.nextLine();
				nameAndChangeInStockArray = nameAndChangeInStock.split(";");
				String name ="";
				int changeInStock = 0;
				for(int i=0; i<2; i++) {
					if(i == 0) {
						name = nameAndChangeInStockArray[i];
					}
					else if(i == 1) {
						changeInStock = Integer.parseInt(nameAndChangeInStockArray[i]);
					}
					else {
						throw new Exception("Exception while converting data into attributes for drinks");
					}
				}
				DrinkFromDoneFile drinkToAdd = new DrinkFromDoneFile(name, changeInStock);
				drinksDone.add(drinkToAdd);
				}
			ArrayList<Drink> drinks = Stock.readStockData(Constants.fileDrinksList);	
			
			for(int j=0; j< drinksDone.size(); j++) {
					for(int k=0; k< drinks.size(); k++) {
						if(drinksDone.get(j).getName().equals(drinks.get(k).getName())) {
							int newStock = drinks.get(k).getStock()-drinksDone.get(j).getChangeInStock();
							if(newStock < 0) {
								throw new Exception("Neuer Bestand kann nicht negativ sein");
							}
							int drinkId = drinks.get(k).getId();
							drinks.get(k).setStock(newStock);
							Stock.setNewStockInFile(Constants.fileDrinksList, drinkId, newStock);
							break;
						}
					}
				}
			scanner.close();
			Thread.sleep(60000);
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		} finally {
			file.delete();
		}
		}
	}
	public static void main(String[] args) {
		CheckDoneFile test = new CheckDoneFile();
		test.start();
	}
}
