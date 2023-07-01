package stockmanagementsystem;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DrinkTest {

	Drink drink = new Drink(1, "Pepsi", 0.99, 368);
	
	@Test
	void testGetId() {
		assertEquals(1, drink.getId());
	}
	
	@Test
	void testGetName() {
		assertEquals("Pepsi", drink.getName());
	}
	
	@Test
	void testGetPrice() {
		assertEquals(0.99, drink.getPrice());
	}
	
	@Test
	void testGetStock() {
		assertEquals(368, drink.getStock());
	}

}
