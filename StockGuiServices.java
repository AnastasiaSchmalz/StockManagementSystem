package stockmanagementsystem;

import java.awt.Container;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class StockGuiServices {
	
	private StockGuiServices() throws Exception {
		throw new Exception();
	}
	
	static DefaultTableModel tableModel = StockGuiServices.defineTableModelWithDrinks();

	static DefaultTableModel defineTableModelWithDrinks() {
		//Defining table-model
		String[] columnHeaders = {"Id", "Name", "Preis", "Menge", "Neuer Bestand"};
		DefaultTableModel model = new DefaultTableModel(columnHeaders, 0);
		
		//Adding drinks to table
		try {
			for(Drink drink : Stock.readStockData(Constants.fileDrinksList)) {
				String[] drinkForTable = new String[]{String.valueOf(drink.getId()), drink.getName(), String.valueOf(drink.getPrice()), String.valueOf(drink.getStock()), " "};
				model.addRow(drinkForTable);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	static void addNewDrink() throws Exception {
		//Instantiating dialog for adding a new drink
		AddNewDrinkDialog addDrinkDialog = new AddNewDrinkDialog();
		addDrinkDialog.setVisible(true);
		StockGuiServices.updateTable();
	}
	
	static void addNewStockToDrink () throws Exception {
		//Instantianting new gui
		//Get value of new entry in stock column and corresponding id
		int idOfChangedDrink = StockGui.drinksTable.getSelectedRow();
		TableCellEditor cellEditor = StockGui.drinksTable.getCellEditor(idOfChangedDrink,3);	
		cellEditor.stopCellEditing();
		Object cellValue = cellEditor.getCellEditorValue();
	    String textFieldString = (String) cellValue;
	    //Remove whitespace
	    textFieldString = textFieldString.replaceAll("\\s", "");
	        
		if(!textFieldString.equals("")) {
			int newStockValue = Integer.parseInt(textFieldString);
			if(newStockValue >= 0) {
				//Uses setNewStockInFile from Stock.java
				try {
					Stock.setNewStockInFile(Constants.fileDrinksList, idOfChangedDrink, newStockValue);
					//Updates table in gui
					StockGuiServices.updateTable();
					
				} catch (Exception e1) {
					e1.printStackTrace();
					}

			}
			else {
				JOptionPane.showMessageDialog(StockGui.frame,
					    "Neue Menge kann nicht negativ sein",
					    "Warnung",
					    JOptionPane.WARNING_MESSAGE);
			}
		}

		else {
			JOptionPane.showMessageDialog(StockGui.frame,
				    "Neue Menge kann nicht leer sein",
				    "Warnung",
				    JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public static void updateTable() {
		StockGuiServices.tableModel.setRowCount(0);
		try {
			for(Drink drink : Stock.readStockData(Constants.fileDrinksList)) {
				String[] drinkForTable = new String[]{String.valueOf(drink.getId()), drink.getName(), String.valueOf(drink.getPrice()), String.valueOf(drink.getStock()), " "};
				StockGuiServices.tableModel.addRow(drinkForTable);
				StockGui.totalStockLabel.setText(String.valueOf(Stock.getTotalStock(Constants.fileDrinksList)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		StockGui.drinksTable.repaint();
	}
	
	public static void addNewDrinkToList(JTextField name, JTextField price, JTextField stock, Container parentComponent) {
		int idNewDrink = 0;
		String nameNewDrink = name.getText();
		double priceNewDrink = Double.parseDouble(price.getText());
		int stockNewDrink = Integer.parseInt(stock.getText());
		Drink newDrink = new Drink(idNewDrink, nameNewDrink, priceNewDrink, stockNewDrink);
		try {
			ArrayList<Drink> currentDrinks = Stock.readStockData(Constants.fileDrinksList);
			boolean isInFile = false;
			for(Drink drink : currentDrinks) {
			if(drink.getName().equals(newDrink.getName())) {
				isInFile = true;
				JOptionPane.showMessageDialog(parentComponent, "Dieses Getränk gibt es schon im Bestand", "Warnung", JOptionPane.WARNING_MESSAGE);
				break;
			}
			}
			if(!isInFile) {
				try {
					newDrink.setId(currentDrinks.size());
					Stock.bookInDrink(Constants.fileDrinksList, newDrink);
					JOptionPane.showMessageDialog(parentComponent, "Neues Getränk eingetragen", "Bestätigung", JOptionPane.INFORMATION_MESSAGE);
					name.setText("");
					price.setText("");
					price.setText("");
				} catch (IOException e1) {
					e1.printStackTrace();
			}
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}
