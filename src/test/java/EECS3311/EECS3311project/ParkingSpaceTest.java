package EECS3311.EECS3311project;

import junit.framework.TestCase;

public class ParkingSpaceTest extends TestCase {
	public void testParkingSpace() {
		ParkingSpace space = new ParkingSpace("1");
		assertEquals("1", space.getId());
		String account = "testaccount";
		space.setAccount(account);
		assertEquals(account, space.getAccount());
		String bookingID = "0";
		space.setBookID(bookingID);
		assertEquals(bookingID, space.getBookID());
		assertFalse(space.isOccupied());
		String expected = "1,0";
		assertEquals(expected, space.toString());
		space.setOccupied(true);
		assertTrue(space.isOccupied());
		expected = "1,1,0,testaccount";
		assertEquals(expected, space.toString());
	}
}
