package EECS3311.EECS3311project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class Controller {
	private DB db;
	
	public Controller(String CUSTOMER_FILE, String AUTHORITY_FILE, String ADMIN_FILE, String PARKING_SPACE_FILE, String BOOK_FILE) {
		db = new DB(CUSTOMER_FILE, AUTHORITY_FILE, ADMIN_FILE, PARKING_SPACE_FILE, BOOK_FILE);
	}
	
	public Controller() {
		db = new DB();
	}
	
	public List<ParkingSpace> getAllParkingSpace() {
		try {
			return db.getAllParkingSpace();
		} catch (IOException | ParseException e) {
			return null;
		}
	}
	
	public boolean login(String account, String password, int userType) {
		try {
			return db.login(account, password, userType);
		} catch (FileNotFoundException e) {
			return false;
		}
	}
	
	public boolean register(String account, String password, String fname, String lname, int userType) {
		try {
			return db.register(account, password, fname, lname, userType);
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean delete(String account, int userType) {
		try {
			return db.delete(account, userType);
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean book(String account, String parkingSpace, String licensePlateNumber, Date startDate, Date endDate) {
		try {
			db.book(account, parkingSpace, licensePlateNumber, startDate, endDate);
			return true;
		} catch (IOException | ParseException e) {
			return false;
		}
	}
	
	public String cancel(String account, String bookingID) {
		try {
			return db.cancel(account, bookingID);
		} catch (IOException | ParseException e) {
			return null;
		}
	}
	
	public List<Booking> getCustomerBookings(String account) {
		try {
			return db.getCustomerBookings(account);
		} catch (FileNotFoundException | ParseException e) {
			return null;
		}
	}
	
	public boolean updatePaymentState(String bookingID, String paymentDetail) {
		try {
			return db.updatePaymentState(bookingID, paymentDetail);
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean addNewParkingSpace(String id) {
		try {
			return db.addNewParkingSpace(id);
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean deleteParkingSpace(String id) {
		try {
			return db.deleteParkingSpace(id);
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean grantRequest(String bookingID) {
		try {
			return db.grantRequest(bookingID);
		} catch (ParseException | IOException e) {
			return false;
		}
	}
}
