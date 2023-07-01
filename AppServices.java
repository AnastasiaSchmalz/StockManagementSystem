package stockmanagementsystem;

import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

//used for storing methods which are used in StockGui and its dialogs
public class AppServices {
	
	//custom exception in case stock would be negative after changes
	//static so it can be used without instantiating a new AppServices-object
	private static class NegativeStockException extends Exception {
		//constructor
		protected NegativeStockException() {
			//message when exception is thrown
			super("New stock can not be negative");	
		}
	}
	
	
	//private constructor, so class can not be instantiated elsewhere
	private AppServices() {
		throw new IllegalStateException("Utility class");
	}
	
	//model for filling table with drinks
	static DefaultTableModel tableModel = AppServices.defineTableModelWithDrinks(); 

	//creating table-model for showing column-headers and later drinks
	static DefaultTableModel defineTableModelWithDrinks() {
		//defining table-model
		String[] columnHeaders = {"Id", "Name", "Preis", "Menge", "Neuer Bestand"};
		DefaultTableModel model = new DefaultTableModel(columnHeaders, 0);
		
		for(Drink drink : Stock.readStockData(Constants.fileDrinksList)) {
			String[] drinkForTable = new String[]{String.valueOf(drink.getId()), drink.getName(), String.valueOf(drink.getPrice()), String.valueOf(drink.getStock()), " "};
			model.addRow(drinkForTable);
		}
		return model;
	}
	
	static void createAddNewDrinkDialog() {
		//instantiating dialog for adding a new drink
		AddNewDrinkDialog addDrinkDialog = new AddNewDrinkDialog();
		addDrinkDialog.setVisible(true);
		AppServices.updateTable();
	}
	
	static void addNewStockToDrink () {
		//getting value of new entry in stock column and corresponding id
		int idOfChangedDrink = StockGui.drinksTable.getSelectedRow();
		//getting corresponding cell editor from table, so the value in fourth column can be accessed
		TableCellEditor cellEditor = StockGui.drinksTable.getCellEditor(idOfChangedDrink,3);
		//holds value of user input in table cell 
		Object cellValue = cellEditor.getCellEditorValue();
		//converting value into string	
	    String newStockString = (String) cellValue;	
	    //removing whitespace
	    newStockString = newStockString.replaceAll("\\s", "");	
	    
	    //checking if there is input in cell   
		if(!newStockString.equals("")) { 
			int newStockValue = Integer.parseInt(newStockString);
			if(newStockValue >= 0) {
				Stock.setNewStockInFile(Constants.fileDrinksList, idOfChangedDrink, newStockValue);
				//updates table in gui
				AppServices.updateTable();
			}
			else {	//shows dialog with warning-message if new stock would be negative
				JOptionPane.showMessageDialog(StockGui.frame,
					    "Neue Menge kann nicht negativ sein",
					    "Warnung",
					    JOptionPane.WARNING_MESSAGE);
			}
		}

		else {	//shows dialog with warning-message if cell is empty
			JOptionPane.showMessageDialog(StockGui.frame,
				    "Neue Menge kann nicht leer sein",
				    "Warnung",
				    JOptionPane.WARNING_MESSAGE);
		}
	}
	
	//updating table to show changes which were made in the background, especially after reading Done.csv
	public static void updateTable() {
		AppServices.tableModel.setRowCount(0);
		try {
			for(Drink drink : Stock.readStockData(Constants.fileDrinksList)) {
				String[] drinkForTable = new String[]{String.valueOf(drink.getId()), drink.getName(), String.valueOf(drink.getPrice()), String.valueOf(drink.getStock()), " "};
				AppServices.tableModel.addRow(drinkForTable);
				StockGui.totalStockLabel.setText(String.valueOf(Stock.getTotalStock(Constants.fileDrinksList)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		StockGui.drinksTable.repaint();
	}
	
	//adding new drink-object to table and file based on entries in textfields
	public static void addNewDrinkToList(JTextField name, JTextField price, JTextField stock, Container parentComponent) {
		int idNewDrink = 0; //initializing id which will be adjusted later
		String nameNewDrink = name.getText();
		double priceNewDrink = Double.parseDouble(price.getText());
		int stockNewDrink = Integer.parseInt(stock.getText());
		
		//instantiating new drink-object
		Drink newDrink = new Drink(idNewDrink, nameNewDrink, priceNewDrink, stockNewDrink); 
		ArrayList<Drink> currentDrinks = Stock.readStockData(Constants.fileDrinksList);
		
		//used to return true if new drink-object already exists
		boolean isInFile = false; 
		for(Drink drink : currentDrinks) {
			//checking if drink already exists in file based on name
		if(drink.getName().equals(newDrink.getName())) { 
			isInFile = true;
			//opens dialog with warning-message
			JOptionPane.showMessageDialog(parentComponent, "Dieses Getränk gibt es schon im Bestand", "Warnung", JOptionPane.WARNING_MESSAGE);	
			break;
			}
		}
		if(!isInFile) {
			//new drink is appended to list, therefore it gets the next available id (will be changed later when drinks are sorted again)
			newDrink.setId(currentDrinks.size()); 
			Stock.bookInDrink(Constants.fileDrinksList, newDrink);
			JOptionPane.showMessageDialog(parentComponent, "Neues Getränk eingetragen. Bitte Tabelle aktualisieren, um neues Getränk zu sehen", "Bestätigung", JOptionPane.INFORMATION_MESSAGE);	//notifys user if new drink was added successfully
			//clearing textfields in dialog
			name.setText("");
			price.setText("");
			stock.setText("");
		}
	}
	
	//checking if Done.csv has any data and if so changes stock of the drinks Done.csv contains
	public static void changeStockFromDone(ArrayList<DrinkDoneFile> drinksDone, ArrayList<Drink> drinks) throws NegativeStockException {
		for(int j=0; j< drinksDone.size(); j++) {
			for(int k=0; k< drinks.size(); k++) {
				//checking if drink exists in table
				if(drinksDone.get(j).getName().equals(drinks.get(k).getName())) { 
					//changing stock
					int newStock = drinks.get(k).getStock()-drinksDone.get(j).getChangeInStock(); 
					if(newStock < 0) {
						//new stock can not be negative
						throw new NegativeStockException();	
					}
					//gets id of drink which stock changes
					int drinkId = drinks.get(k).getId(); 
					//sets new stock for drink in file
					Stock.setNewStockInFile(Constants.fileDrinksList, drinkId, newStock); 
					break;
				}
			}
		}
	}
}
