package stockmanagementsystem;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.SwingConstants;

public class StockGui {

	JFrame frame;
	JTable drinksTable;
	JLabel totalStockLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockGui window = new StockGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		CheckDoneFile checkDoneFile = new CheckDoneFile();
		checkDoneFile.start();
		try {
			checkDoneFile.join();
		} catch (InterruptedException e) {
			
		}
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public StockGui() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		frame = new JFrame();
		frame.setBounds(100, 100, 946, 620);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Defining table
		JLabel labelTable = new JLabel("Aktueller Bestand");
		String[] columnHeaders = {"Id", "Name", "Preis", "Menge", "Neuer Bestand"};
		DefaultTableModel model = new DefaultTableModel(columnHeaders, 0);
		
		
		//Adding drinks to table
		Stock stock = new Stock();
		for(Drink drink : stock.readStockData("C:\\Users\\Anastasia\\OneDrive\\Dokumente\\GetränkeTest1.CSV\\")) {
			String[] drinkForTable = new String[]{String.valueOf(drink.getId()), drink.getName(), String.valueOf(drink.getPrice()), String.valueOf(drink.getStock()), " "};
			model.addRow(drinkForTable);
		}
		drinksTable = new JTable(model);		
		
		JLabel labelSum = new JLabel("Gesamtsumme:");
		
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, drinksTable, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, drinksTable, -10, SpringLayout.NORTH, labelTable);
		springLayout.putConstraint(SpringLayout.WEST, labelTable, 0, SpringLayout.WEST, labelSum);
		springLayout.putConstraint(SpringLayout.EAST, drinksTable, -313, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, drinksTable, 109, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().setLayout(springLayout);
		frame.getContentPane().add(drinksTable);
		frame.getContentPane().add(labelSum);
		frame.getContentPane().add(labelTable);
		
		JPanel tableContainer = new JPanel(springLayout);
		frame.getContentPane().add(tableContainer);
		
		JButton btnCloseApp = new JButton("Anwendung schließen");
		springLayout.putConstraint(SpringLayout.SOUTH, btnCloseApp, -10, SpringLayout.SOUTH, frame.getContentPane());
		btnCloseApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(frame, "Anwendung schließen?", "Anwendung schließen?", JOptionPane.OK_CANCEL_OPTION);
				if(option == JOptionPane.OK_OPTION) {
					frame.dispose();
				}
			}
		});
		springLayout.putConstraint(SpringLayout.EAST, btnCloseApp, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnCloseApp);
		
		JButton btnAddDrink = new JButton("Neues Getränk hinzufügen");
		btnAddDrink.setToolTipText("Neues Getränk hinzufügen");
		springLayout.putConstraint(SpringLayout.WEST, btnAddDrink, 560, SpringLayout.EAST, tableContainer);
		springLayout.putConstraint(SpringLayout.EAST, btnAddDrink, 760, SpringLayout.EAST, tableContainer);
		btnAddDrink.setHorizontalAlignment(SwingConstants.RIGHT);
		btnAddDrink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddNewDrinkDialog addDrinkDialog = new AddNewDrinkDialog();
				addDrinkDialog.setVisible(true);
				model.setRowCount(0);
				try {
					for(Drink drink : stock.readStockData("C:\\Users\\Anastasia\\OneDrive\\Dokumente\\GetränkeTest1.CSV\\")) {
						String[] drinkForTable = new String[]{String.valueOf(drink.getId()), drink.getName(), String.valueOf(drink.getPrice()), String.valueOf(drink.getStock()), " "};
						model.addRow(drinkForTable);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				drinksTable.repaint();
			}
		});
		frame.getContentPane().add(btnAddDrink);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
		JLabel date = new JLabel(String.valueOf((LocalDate.now()).format(formatter)));
		springLayout.putConstraint(SpringLayout.NORTH, labelTable, 0, SpringLayout.NORTH, date);
		springLayout.putConstraint(SpringLayout.NORTH, date, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, date, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(date);
		
		JScrollPane scrollPane = new JScrollPane(drinksTable);
		springLayout.putConstraint(SpringLayout.NORTH, labelSum, 6, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -134, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnAddDrink, 2, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, labelSum, 0, SpringLayout.WEST, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 170, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -172, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(scrollPane);
		
		totalStockLabel = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, totalStockLabel, 0, SpringLayout.NORTH, labelSum);
		springLayout.putConstraint(SpringLayout.WEST, totalStockLabel, 6, SpringLayout.EAST, labelSum);
		frame.getContentPane().add(totalStockLabel);
		totalStockLabel.setText(String.valueOf(stock.getTotalStock("C:\\Users\\Anastasia\\OneDrive\\Dokumente\\GetränkeTest1.CSV\\")));
		
		//change stock of corresponding drink on enter
		drinksTable.addKeyListener (new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
				//get value of new entry in stock column and corresponding id
				int idOfChangedDrink = drinksTable.getSelectedRow();
				TableCellEditor cellEditor = drinksTable.getCellEditor(idOfChangedDrink,3);	
				cellEditor.stopCellEditing();
				Object cellValue = cellEditor.getCellEditorValue();
			    String textFieldString = (String) cellValue;
			    //remove whitespace
			    textFieldString = textFieldString.replaceAll("\\s", "");
			        
				if(!textFieldString.equals("")) {
					int newStockValue = Integer.parseInt(textFieldString);
					if(newStockValue >= 0) {
						//uses setNewStockInFile from Stock.java
						try {
							stock.setNewStockInFile("C:\\Users\\Anastasia\\OneDrive\\Dokumente\\GetränkeTest1.CSV\\", idOfChangedDrink, newStockValue);
							
							model.setRowCount(0);
							for(Drink drink : stock.readStockData("C:\\Users\\Anastasia\\OneDrive\\Dokumente\\GetränkeTest1.CSV\\")) {
								String[] drinkForTable = new String[]{String.valueOf(drink.getId()), drink.getName(), String.valueOf(drink.getPrice()), String.valueOf(drink.getStock()), " "};
								model.addRow(drinkForTable);
								totalStockLabel.setText(String.valueOf(stock.getTotalStock("C:\\Users\\Anastasia\\OneDrive\\Dokumente\\GetränkeTest1.CSV\\")));
							}
							drinksTable.repaint();
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							}

					}
					else {
						//opens dialog with message
						JOptionPane.showMessageDialog(frame,
							    "Neue Menge kann nicht negativ sein",
							    "Warnung",
							    JOptionPane.WARNING_MESSAGE);
					}
				}

				else {
					JOptionPane.showMessageDialog(frame,
						    "Neue Menge kann nicht leer sein",
						    "Warnung",
						    JOptionPane.WARNING_MESSAGE);
				}
			}
		}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}});
	}
}
