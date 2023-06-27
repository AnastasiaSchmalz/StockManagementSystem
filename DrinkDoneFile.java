package stockmanagementsystem;

/*objects of this class are drinks which are found in Done.csv;
their only instance-variables are their name and change in stock*/
public class DrinkDoneFile {
	
	String name;
	int changeInStock;
	
	public DrinkDoneFile(String name, int changeInStock) {
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
