package stockmanagementsystem.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DrinkTest {

	@Test
	void test() {
		Drink drink = new Drink("Pepsi", 0.99, 368);
		
		assertEquals("Pepsi", drink.getName());
		assertEquals(0.99, drink.getPrice());
		assertEquals(368, drink.getStock());
	}

}
