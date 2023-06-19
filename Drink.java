package stockmanagementsystem;

public class Drink {

	protected String name;
	protected double price;
	protected int stock;
	
	public Drink (String name, double price, int stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
	}
	
	public String getName() {
		return name;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getStock() {
		return stock;
	}
	
	protected void setName(String newName) {
		name = newName;
	}
	
	protected void setPrice(double newPrice) {
		price = newPrice;
	}
	
	protected void setStock(int newStock) {
		stock = newStock;
	}
}
