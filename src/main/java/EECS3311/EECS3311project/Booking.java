package EECS3311.EECS3311project;

import java.util.Date;

public class Booking {
	private String bookingID;
	private String account;
	private String parkingSpace;
	private String licensePlateNumber;
	private Date startDate;
	private Date endDate;
	private boolean isPaid;
	private String payment;
	private boolean granted;
	
	public Booking(String bookingID, String account, String parkingSpace, String licensePlateNumber, Date startDate, Date endDate, boolean isPaid, boolean granted) {
		this.bookingID = bookingID;
		this.account = account;
		this.parkingSpace = parkingSpace;
		this.licensePlateNumber = licensePlateNumber;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isPaid = isPaid;
		this.granted = granted;
		payment = "";
	}
	
	public void setPayment(String payment) {
		this.payment = payment;
	}
	
	public String getBookingID() {
		return bookingID;
	}
	
	public String getAccount() {
		return account;
	}
	
	public String getParkingSpace() {
		return parkingSpace;
	}
	
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public boolean isPaid() {
		return isPaid;
	}
	
	public String getPayment() {
		return payment;
	}
	
	public boolean isGranted() {
		return granted;
	}
}
