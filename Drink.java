package stockmanagementsystem;

public class Drink {

	protected int id;
	protected String name;
	protected double price;
	protected int stock;
	
	public Drink (int id, String name, double price, int stock) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
	}
	
	public int getId()
	{
		return id;
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
	
	protected void setId(int newId) {
		id = newId;
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
