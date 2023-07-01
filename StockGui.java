package stockmanagementsystem;

import java.awt.EventQueue;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class StockGui {

	static JFrame frame;
	static JTable drinksTable;
	static JLabel totalStockLabel;
	/**
	 * Launch the application.
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	CheckDoneFile checkDoneFile = new CheckDoneFile(); //thread for checking Done.csv and making changes to the stock
		    	checkDoneFile.start();
		    }
		});
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockGui window = new StockGui();
					StockGui.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public StockGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 946, 620);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//defining table
		JLabel labelTable = new JLabel("Aktueller Bestand");
		drinksTable = new JTable(AppServices.tableModel);		
		
		JLabel labelSum = new JLabel("Gesamtbestand:");
		
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
		btnCloseApp.addActionListener(e -> {
				int option = JOptionPane.showConfirmDialog(frame, "Anwendung schließen?", "Anwendung schließen?", JOptionPane.OK_CANCEL_OPTION);	//dialog asks for confirmation if close-button was pushed, prevents closing application by accident
				if(option == JOptionPane.OK_OPTION) {
					frame.dispose(); //closes dialog only when ok-button is pushed
				}
			}
		);
		springLayout.putConstraint(SpringLayout.EAST, btnCloseApp, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnCloseApp);
		
		JButton btnAddDrink = new JButton("Neues Getränk hinzufügen");
		btnAddDrink.setToolTipText("Neues Getränk hinzufügen");
		springLayout.putConstraint(SpringLayout.WEST, btnAddDrink, 560, SpringLayout.EAST, tableContainer);
		springLayout.putConstraint(SpringLayout.EAST, btnAddDrink, 760, SpringLayout.EAST, tableContainer);
		btnAddDrink.addActionListener(e -> AppServices.createAddNewDrinkDialog());
		frame.getContentPane().add(btnAddDrink);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);			//formatter for european date format
		JLabel date = new JLabel(String.valueOf((LocalDate.now()).format(formatter)));					//shows today's date in european format
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
		
		JButton btnRefresh = new JButton("Aktualisieren"); //updates table to show changes which were made in the background
		btnRefresh.addActionListener(e -> AppServices.updateTable());
		springLayout.putConstraint(SpringLayout.NORTH, btnRefresh, 10, SpringLayout.NORTH, tableContainer);
		springLayout.putConstraint(SpringLayout.WEST, btnRefresh, 10, SpringLayout.WEST, tableContainer);
		frame.getContentPane().add(btnRefresh);
		frame.getContentPane().add(totalStockLabel);
		totalStockLabel.setText(String.valueOf(Stock.getTotalStock(Constants.fileDrinksList)));
		
		//changing stock of corresponding drink on enter
		drinksTable.addKeyListener (new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					AppServices.addNewStockToDrink();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				//not used
			}

			@Override
			public void keyPressed(KeyEvent e) {
				//not used
			}});
	}
}
