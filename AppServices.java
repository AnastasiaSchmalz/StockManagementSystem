package stockmanagementsystem;

import java.awt.Container;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

//this class is used for storing methods which are used in StockGui and its dialogs
public class AppServices {
	
	//private constructor, so class can not be instantiated elsewhere
	private AppServices() {
		throw new IllegalStateException("Utility class");
	}
	
	static DefaultTableModel tableModel = AppServices.defineTableModelWithDrinks(); //used model for filling table with drinks

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
	
	static void addNewDrink() {
		//instantiating dialog for adding a new drink
		AddNewDrinkDialog addDrinkDialog = new AddNewDrinkDialog();
		addDrinkDialog.setVisible(true);
		AppServices.updateTable();
	}
	
	static void addNewStockToDrink () {
		//getting value of new entry in stock column and corresponding id
		int idOfChangedDrink = StockGui.drinksTable.getSelectedRow();
		TableCellEditor cellEditor = StockGui.drinksTable.getCellEditor(idOfChangedDrink,3); //getting corresponding cell editor from table, so the value in fourth column can be accessed
		Object cellValue = cellEditor.getCellEditorValue();	//holds value of user input in table cell
	    String newStockString = (String) cellValue;	//converts value into string
	    newStockString = newStockString.replaceAll("\\s", "");	//removes whitespace
	        
		if(!newStockString.equals("")) { //checks if there is input in cell
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
		int idNewDrink = 0; //instantiating id which will be adjusted later
		String nameNewDrink = name.getText();
		double priceNewDrink = Double.parseDouble(price.getText());
		int stockNewDrink = Integer.parseInt(stock.getText());
		Drink newDrink = new Drink(idNewDrink, nameNewDrink, priceNewDrink, stockNewDrink); //instantiating new drink-object
		ArrayList<Drink> currentDrinks = Stock.readStockData(Constants.fileDrinksList);
		boolean isInFile = false; //used to return true if new drink-object already exists
		for(Drink drink : currentDrinks) {
		if(drink.getName().equals(newDrink.getName())) { //checking if drink already exists in file based on name
			isInFile = true;
			JOptionPane.showMessageDialog(parentComponent, "Dieses Getränk gibt es schon im Bestand", "Warnung", JOptionPane.WARNING_MESSAGE);	//opens dialog with warning-message
			break;
			}
		}
		if(!isInFile) {
			newDrink.setId(currentDrinks.size()); //new drink is appended to list, therefore it gets the next available id (will be changed later when drinks are sorted again)
			Stock.bookInDrink(Constants.fileDrinksList, newDrink);
			JOptionPane.showMessageDialog(parentComponent, "Neues Getränk eingetragen", "Bestätigung", JOptionPane.INFORMATION_MESSAGE);	//notifys user if new drink was added successfully
			//clearing textfields in dialog
			name.setText("");
			price.setText("");
			stock.setText("");
		}
	}
	
	//checking if Done.csv has any data and if so changes stock of the drinks Done.csv contains
	public static void changeStockFromDone(ArrayList<DrinkDoneFile> drinksDone, ArrayList<Drink> drinks) throws Exception {
		for(int j=0; j< drinksDone.size(); j++) {
			for(int k=0; k< drinks.size(); k++) {
				if(drinksDone.get(j).getName().equals(drinks.get(k).getName())) { //checks if drink exists in table
					int newStock = drinks.get(k).getStock()-drinksDone.get(j).getChangeInStock(); //changes stock
					if(newStock < 0) {
						throw new Exception ("Neuer Bestand kann nicht negativ sein");	//new stock can not be negative
					}
					int drinkId = drinks.get(k).getId(); //gets id of drink which stock changes
					Stock.setNewStockInFile(Constants.fileDrinksList, drinkId, newStock); //sets new stock for drink in file
					break;
				}
			}
		}
	}
}
