package EECS3311.EECS3311project;

public class ParkingSpace {
	private String id;
	private boolean isOccupied;
	private String bookID;
	private String account;
	
	public ParkingSpace(String id) {
		this.id = id;
	}
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public void setBookID(String bookID) {
		this.bookID = bookID;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public boolean isOccupied() {
		return isOccupied;
	}
	
	public String getId() {
		return id;
	}
	
	public String getBookID() {
		return bookID;
	}
	
	public String getAccount() {
		return account;
	}
	
	public String toString() {
		String s = getId() + "," + (isOccupied ? 1 : 0);
		if (isOccupied) {
			s += "," + bookID + "," + account;
		}
		return s;
	}
}
