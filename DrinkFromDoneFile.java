package stockmanagementsystem;

public class DrinkFromDoneFile {
	
	String name;
	int changeInStock;
	
	public DrinkFromDoneFile(String name, int changeInStock) {
		this.name = name;
		this.changeInStock = changeInStock;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getChangeInStock() {
		return this.changeInStock;
	}

}
