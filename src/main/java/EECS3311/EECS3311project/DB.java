package EECS3311.EECS3311project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DB {
	public static String CUSTOMER_FILE = "src/main/db/customer.txt";
	public static String AUTHORITY_FILE = "src/main/db/authority.txt";
	public static String ADMIN_FILE = "src/main/db/administrator.txt";
	public static String PARKING_SPACE_FILE = "src/main/db/parkingspace.txt";
	public static String BOOK_FILE = "src/main/db/book.txt";
	
	public DB(String CUSTOMER_FILE, String AUTHORITY_FILE, String ADMIN_FILE, String PARKING_SPACE_FILE, String BOOK_FILE) {
		DB.CUSTOMER_FILE = CUSTOMER_FILE;
		DB.AUTHORITY_FILE = AUTHORITY_FILE;
		DB.ADMIN_FILE = ADMIN_FILE;
		DB.PARKING_SPACE_FILE = PARKING_SPACE_FILE;
		DB.BOOK_FILE = BOOK_FILE;
	}
	
	public DB() {}
	
	public boolean login(String account, String password, int userType) throws FileNotFoundException {
		Scanner reader = new Scanner(new File(CUSTOMER_FILE));
		if (userType == 1) {
			reader = new Scanner(new File(ADMIN_FILE));
		} else if (userType == 2) {
			reader = new Scanner(new File(AUTHORITY_FILE));
		}
		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			String[] info = line.split(",");
			if (info[1].equals(account)) {
				if (info[2].equals(password)) {
					reader.close();
					return true;
				}
				reader.close();
				return false;
			}
		}
		reader.close();
		return false;
	}
	
	public boolean addNewParkingSpace(String id) throws IOException {
		Scanner reader = new Scanner(new File(PARKING_SPACE_FILE));
		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			if (!line.equals("")) {
				String[] info = line.split(",");
				if (info[0].equals(id)) {
					reader.close();
					return false;
				}
			}
		}
		reader.close();
		PrintWriter writer = new PrintWriter(new FileWriter(PARKING_SPACE_FILE, true));
		writer.write(id + "\n");
		writer.flush();
		writer.close();
		return true;
	}
	
	public boolean deleteParkingSpace(String id) throws IOException {
		File file = new File(PARKING_SPACE_FILE);
		Scanner reader = new Scanner(file);
		StringBuilder builder = new StringBuilder();
		boolean deleted = false;
		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			String[] info = line.split(",");
			if (info[0].equals(id)) {
				deleted = true;
				continue;
			} else {
				builder.append(line + "\n");
			}
		}
		reader.close();
		PrintWriter writer = new PrintWriter(new FileWriter(file));
		writer.write(builder.toString());
		writer.flush();
		writer.close();
		return deleted;
	}
	
	public List<ParkingSpace> getAllParkingSpace() throws ParseException, IOException {
		List<ParkingSpace> result = new ArrayList<ParkingSpace>();
		Scanner reader = new Scanner(new File(PARKING_SPACE_FILE));
		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			if (!line.equals("")) {
				String[] info = line.split(",");
				ParkingSpace parkingSpace = new ParkingSpace(info[0]);
//				parkingSpace.setOccupied(info[1].equals("1"));
////				parkingSpace.setPaid(info[2].equals("1"));
//				if (info.length > 2) {
//					parkingSpace.setBookID(info[2]);
//					parkingSpace.setAccount(info[3]);
//				}
				result.add(parkingSpace);
			}
		}
		reader.close();
		reader = new Scanner(new File(BOOK_FILE));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date(System.currentTimeMillis());
		while (reader.hasNextLine()) {
			String[] info = reader.nextLine().split(",");
			for (int i = 0; i < result.size(); i++) {
				if (result.get(i).getId().equals(info[2])) {
					if (format.parse(info[4]).compareTo(date) < 0 && format.parse(info[5]).compareTo(date) > 0) {
						result.get(i).setOccupied(true);
						result.get(i).setBookID(info[0]);
						result.get(i).setAccount(info[1]);
					}
				}
			}
		}
		reader.close();
		return result;
	}
	
	public boolean register(String account, String password, String firstname, String lastname, int userType) throws IOException {
		int maxID = 0;
		File file = new File(CUSTOMER_FILE);
		if (userType == 1) {
			file = new File(ADMIN_FILE);
		} else if (userType == 2) {
			file = new File(AUTHORITY_FILE);
		}
		Scanner reader = new Scanner(file);
		while (reader.hasNextLine()) {
			String[] info = reader.nextLine().split(",");
			maxID = Math.max(maxID, Integer.parseInt(info[0]));
			if (info[1].equals(account)) {
				reader.close();
				return false;
			}
		}
		reader.close();
		PrintWriter writer = new PrintWriter(new FileWriter(file, true));
		writer.write((maxID + 1) + "," + account + "," + password + "," + firstname + "," + lastname + "\n");
		writer.flush();
		writer.close();
		return true;
	}
	
	public boolean delete(String account, int userType) throws IOException {
		File file = new File(CUSTOMER_FILE);
		if (userType == 1) {
			file = new File(ADMIN_FILE);
		} else if (userType == 2) {
			file = new File(AUTHORITY_FILE);
		}
		Scanner reader = new Scanner(file);
		StringBuilder builder = new StringBuilder();
		boolean deleted = false;
		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			String[] info = line.split(",");
			if (info[1].equals(account)) {
				deleted = true;
				continue;
			} else {
				builder.append(line + "\n");
			}
		}
		reader.close();
		PrintWriter writer = new PrintWriter(new FileWriter(file));
		writer.write(builder.toString());
		writer.flush();
		writer.close();
		return deleted;
	}
	
	public boolean book(String account, String parkingSpace, String licensePlateNumber, Date startDate, Date endDate) throws IOException, ParseException {
		List<ParkingSpace> parkingSpaces = getAllParkingSpace();
		for (int i = 0; i < parkingSpaces.size(); i++) {
			if (parkingSpace.equals(parkingSpaces.get(i).getId())) {
				if (parkingSpaces.get(i).isOccupied()) {
					return false;
				}
			}
		}
		int maxID = -1;
		File file = new File(BOOK_FILE);
		Scanner reader = new Scanner(file);
		while (reader.hasNextLine()) {
			String[] info = reader.nextLine().split(",");
			maxID = Math.max(maxID, Integer.parseInt(info[0]));
		}
		reader.close();
		PrintWriter writer = new PrintWriter(new FileWriter(file, true));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		writer.write((maxID + 1) + "," + account + "," + parkingSpace + "," + licensePlateNumber + "," + format.format(startDate) + "," + format.format(endDate) + ",0,0\n");
		writer.flush();
		writer.close();
		return true;
	}
	
	public String cancel(String account, String bookingID) throws IOException, ParseException {
		File file = new File(BOOK_FILE);
		Scanner reader = new Scanner(file);
		StringBuilder builder = new StringBuilder();
		String deleted = null;
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			String[] info = line.split(",");
			if (info[0].equals(bookingID) && (info[1].equals(account) || account.equals(""))) {
				if (format.parse(info[4]).compareTo(date) > 0) {
					deleted = info[2];
					continue;
				} else {
					builder.append(line + "\n");
				}
			} else {
				builder.append(line + "\n");
			}
		}
		reader.close();
		PrintWriter writer = new PrintWriter(new FileWriter(file));
		writer.write(builder.toString());
		writer.flush();
		writer.close();
		return deleted;
	}
	
	public List<Booking> getCustomerBookings(String account) throws FileNotFoundException, ParseException {
		List<Booking> result = new ArrayList<>();
		File file = new File(BOOK_FILE);
		Scanner reader = new Scanner(file);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			String[] info = line.split(",");
			if (info[1].equals(account) || account.equals("")) {
				Booking booking = new Booking(info[0], info[1], info[2], info[3], format.parse(info[4]), format.parse(info[5]), info[7].equals("1"), info[6].equals("1"));
				if (booking.isPaid()) {
					booking.setPayment(info[8]);
				}
				result.add(booking);
			}
		}
		reader.close();
		return result;
	}
	
	public boolean updatePaymentState(String bookingID, String paymentDetail) throws IOException {
		StringBuilder builder = new StringBuilder();
		Scanner reader = new Scanner(new File(BOOK_FILE));
		boolean result = false;
		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			if (!line.equals("")) {
				String[] info = line.split(",");
				if (bookingID.equals(info[0]) && info[7].equals("0")) {
					builder.append(line.substring(0, line.length() - 1) + "1," + paymentDetail + "\n");
					result = true;
				} else {
					builder.append(line + "\n");
				}
			}
		}
		reader.close();
		PrintWriter writer = new PrintWriter(new FileWriter(BOOK_FILE));
		writer.write(builder.toString());
		writer.flush();
		writer.close();
		return result;
	}
	
	public boolean grantRequest(String bookingID) throws ParseException, IOException {
		Scanner reader = new Scanner(new File(BOOK_FILE));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		StringBuilder builder = new StringBuilder();
		String[] booking = null;
		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			String[] info = line.split(",");
			if (info[0].equals(bookingID)) {
				booking = line.split(",");
			}
		}
		reader.close();
		if (booking != null && booking[6].equals("0")) {
			reader = new Scanner(new File(BOOK_FILE));
			boolean granted = true;
			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				String[] info = line.split(",");
				if (!info[0].equals(bookingID)) {
					if (info[6].equals("1")) {
						if (!(format.parse(info[5]).compareTo(format.parse(booking[4])) < 0 || format.parse(info[4]).compareTo(format.parse(booking[5])) > 0)) {
							granted = false;
						}
					}
					builder.append(line +"\n");
				}
			}
			if (granted) {
				builder.append(booking[0]);
				for (int i = 1; i < booking.length; i++) {
					if (i == 6) {
						builder.append(",1");
					} else {
						builder.append("," + booking[i]);
					}
				}
			}
			builder.append("\n");
			PrintWriter writer = new PrintWriter(new FileWriter(BOOK_FILE));
			writer.write(builder.toString());
			writer.flush();
			writer.close();
			return granted;
		}
		return false;
	}
	
}
