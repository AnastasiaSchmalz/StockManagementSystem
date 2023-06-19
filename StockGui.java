package stockmanagementsystem;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class StockGui {

	private JFrame frame;
	private JTable drinksTable;
	private JTextField newStock;

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
	}

	/**
	 * Create the application.
	 */
	public StockGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 946, 620);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		String[] columnHeaders = {"Name", "Preis", "Menge"};
		DefaultTableModel model = new DefaultTableModel(columnHeaders, 0);
		ArrayList<Drink> drinksForTable = Stock.drinks;
		for(Drink drink : drinksForTable) {
			String[] drinks = new String[]{drink.getName(), String.valueOf(drink.getPrice()), String.valueOf(drink.getStock())};
			model.addRow(drinks);
		}
		drinksTable = new JTable(model);
		
		JLabel lblNewLabel = new JLabel("New label");
		
		newStock = new JTextField();
		newStock.setColumns(10);
		
		JButton btnBookIn = new JButton("New button");
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.EAST, btnBookIn, -197, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, newStock, 6, SpringLayout.EAST, drinksTable);
		springLayout.putConstraint(SpringLayout.EAST, newStock, -272, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnBookIn, -3, SpringLayout.NORTH, drinksTable);
		springLayout.putConstraint(SpringLayout.WEST, btnBookIn, 6, SpringLayout.EAST, newStock);
		springLayout.putConstraint(SpringLayout.NORTH, newStock, -2, SpringLayout.NORTH, drinksTable);
		springLayout.putConstraint(SpringLayout.EAST, drinksTable, -313, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, drinksTable, 109, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, drinksTable, 73, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, drinksTable, -6, SpringLayout.NORTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 503, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_1, 164, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 503, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 109, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().setLayout(springLayout);
		frame.getContentPane().add(drinksTable);
		frame.getContentPane().add(lblNewLabel);
		frame.getContentPane().add(newStock);
		frame.getContentPane().add(btnBookIn);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnCloseApp = new JButton("New button");
		springLayout.putConstraint(SpringLayout.EAST, btnCloseApp, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnCloseApp);
		
		JButton btnAddDrink = new JButton("New button");
		springLayout.putConstraint(SpringLayout.SOUTH, btnAddDrink, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnCloseApp, 0, SpringLayout.NORTH, btnAddDrink);
		springLayout.putConstraint(SpringLayout.WEST, btnAddDrink, 109, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(btnAddDrink);
		
		JButton btnRemoveDrink = new JButton("New button");
		springLayout.putConstraint(SpringLayout.WEST, btnRemoveDrink, 6, SpringLayout.EAST, btnAddDrink);
		springLayout.putConstraint(SpringLayout.SOUTH, btnRemoveDrink, -10, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(btnRemoveDrink);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_2, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_2, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btnBookOut = new JButton("New button");
		springLayout.putConstraint(SpringLayout.NORTH, btnBookOut, -3, SpringLayout.NORTH, drinksTable);
		springLayout.putConstraint(SpringLayout.WEST, btnBookOut, 13, SpringLayout.EAST, btnBookIn);
		springLayout.putConstraint(SpringLayout.EAST, btnBookOut, 82, SpringLayout.EAST, btnBookIn);
		frame.getContentPane().add(btnBookOut);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
	}
}
