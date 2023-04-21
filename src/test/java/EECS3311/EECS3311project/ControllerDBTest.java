package EECS3311.EECS3311project;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

public class ControllerDBTest extends TestCase {
	public void testControllerDB() throws IOException {
		File cfile = new File("src/main/db/test_customer.txt");
		File adminfile = new File("src/main/db/test_admin.txt");
		File afile = new File("src/main/db/test_authority.txt");
		File bfile = new File("src/main/db/test_booking.txt");
		File pfile = new File("src/main/db/test_pfile.txt");
		if (!cfile.exists()) {
			cfile.createNewFile();
		} else {
			cfile.delete();
			cfile.createNewFile();
		}
		if (!adminfile.exists()) {
			adminfile.createNewFile();
		} else {
			adminfile.delete();
			adminfile.createNewFile();
		}
		if (!afile.exists()) {
			afile.createNewFile();
		} else {
			afile.delete();
			afile.createNewFile();
		}
		if (!bfile.exists()) {
			bfile.createNewFile();
		} else {
			bfile.delete();
			bfile.createNewFile();
		}
		if (!pfile.exists()) {
			pfile.createNewFile();
		} else {
			pfile.delete();
			pfile.createNewFile();
		}
		Controller controller = new Controller("src/main/db/test_customer.txt", 
				"src/main/db/test_authority.txt", 
				"src/main/db/test_admin.txt", 
				"src/main/db/test_pfile.txt", 
				"src/main/db/test_booking.txt");
		assertFalse(controller.login("AccountNotExist", "testpassword", 2));
		controller.register("AccountNotExist", "testpassword", "", "", 2);
		controller.register("AccountNotExist", "testpassword", "", "", 2);
		controller.register("AccountExist", "testpassword", "", "", 1);
		controller.register("AccountExist2", "testpassword", "", "", 1);
		controller.delete("AccountExist2", 1);
		assertTrue(controller.login("AccountNotExist", "testpassword", 2));
		List<ParkingSpace> parkingSpaces = controller.getAllParkingSpace();
		assertEquals(0, parkingSpaces.size());
		assertTrue(controller.addNewParkingSpace("0"));
		assertTrue(controller.addNewParkingSpace("1"));
		assertTrue(controller.addNewParkingSpace("2"));
		assertFalse(controller.addNewParkingSpace("0"));
		parkingSpaces = controller.getAllParkingSpace();
		assertEquals(3, parkingSpaces.size());
		assertTrue(controller.deleteParkingSpace("1"));
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			Date startDate = format.parse("2021-04-22 12:00");
			Date endDate = format.parse("2021-04-23 12:00");
			controller.book("testaccount", "0", "testnumber", startDate, endDate);
			parkingSpaces = controller.getAllParkingSpace();
			controller.book("testaccount", "1", "testnumber", startDate, endDate);
			assertEquals(controller.getCustomerBookings("").size(), controller.getCustomerBookings("testaccount").size());
			controller.updatePaymentState("0", "Pay by apple pay at 2021-04-21 22:00");
			parkingSpaces = controller.getAllParkingSpace();
			controller.cancel("testaccount", "0");
			controller.grantRequest("1");
		} catch (ParseException e) {
		}
		String notexistcfile = "src/main/db/not_exist_customer.txt";
		String notexistadminfile = "src/main/db/not_exist_admin.txt";
		String notexistafile = "src/main/db/not_exist_authority.txt";
		String notexistbfile = "src/main/db/not_exist_booking.txt";
		String notexistpfile = "src/main/db/not_exist_pfile.txt";
		controller = new Controller(notexistcfile, notexistafile, notexistadminfile, notexistpfile, notexistbfile);
		assertFalse(controller.login("AccountNotExist", "testpassword", 2));
		controller.register("AccountNotExist", "testpassword", "", "", 2);
		controller.register("AccountNotExist", "testpassword", "", "", 2);
		controller.register("AccountExist", "testpassword", "", "", 1);
		controller.register("AccountExist2", "testpassword", "", "", 1);
		controller.delete("AccountExist2", 1);
		parkingSpaces = controller.getAllParkingSpace();
		controller.addNewParkingSpace("0");
		controller.deleteParkingSpace("1");
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			Date startDate = format.parse("2021-04-22 12:00");
			Date endDate = format.parse("2021-04-23 12:00");
			controller.book("testaccount", "0", "testnumber", startDate, endDate);
			parkingSpaces = controller.getAllParkingSpace();
			controller.book("testaccount", "1", "testnumber", startDate, endDate);
			controller.getCustomerBookings("");
			controller.getCustomerBookings("testaccount");
			controller.updatePaymentState("0", "Pay by apple pay at 2021-04-21 22:00");
			parkingSpaces = controller.getAllParkingSpace();
			controller.cancel("testaccount", "0");
			controller.grantRequest("1");
		} catch (ParseException e) {
		}
	}
}
