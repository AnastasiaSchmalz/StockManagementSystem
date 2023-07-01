package stockmanagementsystem;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DrinkDoneFileTest {

	DrinkDoneFile testDrink = new DrinkDoneFile("Sprite", 200);
	
	@Test
	void testGetName() {
		assertEquals("Sprite", testDrink.getName());
	}
	
	@Test
	void testGetChangeInStock() {
		assertEquals(200, testDrink.getChangeInStock());
	}

}
