package EECS3311.EECS3311project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

public class BookingTest extends TestCase {
	public void testBooking() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date startDate;
		try {
			startDate = format.parse("2021-04-22 12:00");
			Date endDate = format.parse("2021-04-23 12:00");
			Booking booking = new Booking("0", "testaccount", "1", "testnumber", startDate, endDate, false, false);
			assertEquals(booking.getBookingID(), "0");
			assertEquals(booking.getAccount(), "testaccount");
			assertEquals(booking.getLicensePlateNumber(), "testnumber");
			assertEquals(booking.getParkingSpace(), "1");
			assertEquals(booking.getPayment(), "");
			assertEquals(booking.getStartDate(), startDate);
			assertEquals(booking.getEndDate(), endDate);
			assertEquals(booking.isPaid(), false);
			assertEquals(booking.isGranted(), false);
			String payment = "Pay by apple pay at 2021-04-21 22:00";
			booking.setPayment(payment);
			assertEquals(booking.getPayment(), payment);
		} catch (ParseException e) {
		}
	}
}
