package EECS3311.EECS3311project;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class CustomerGUI {
	private Controller controller;
	
	private JFrame mainFrame;
	private JPanel parkingPanel;
	private JPanel controlPanel;
	private String account;
	private List<ParkingSpaceView> parkingSpaceViews;
	
	private JButton book;
	private JButton cancel;
	private JButton pay;
	private JButton view;
	
	public CustomerGUI(String account, Controller controller) {
		this.account = account;
		this.controller = controller;
		initControlPanel();
		initParkingPanel();
		initListener();
		mainFrame = new JFrame("Toronto Parking Meter System");
		mainFrame.add(controlPanel, BorderLayout.NORTH);
		mainFrame.add(parkingPanel, BorderLayout.CENTER);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void initControlPanel() {
		JLabel welcomeLabel = new JLabel("Welcome, " + account + "!");
		book = new JButton("BOOK SPACE");
		cancel = new JButton("CANCELLATIONS");
		pay = new JButton("PAY");
		view = new JButton("VIEW BOOKINGS");
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		controlPanel.add(welcomeLabel);
		controlPanel.add(book);
		controlPanel.add(cancel);
		controlPanel.add(pay);
		controlPanel.add(view);
	}
	
	public void initParkingPanel() {
		parkingPanel = new JPanel();
		parkingPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		List<ParkingSpace> parkingSpaces = controller.getAllParkingSpace();
		parkingSpaceViews = new ArrayList<ParkingSpaceView>();
		if (parkingSpaces != null) {
			for (ParkingSpace parkingSpace : parkingSpaces) {
				parkingSpaceViews.add(new ParkingSpaceView(controller, parkingSpace, 100));
			}
			for (ParkingSpaceView view : parkingSpaceViews) {
				parkingPanel.add(view);
			}
		}
	}
	
	public void initListener() {
		book.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parkingSpaceViews != null) {
					int no = 0;
					for (ParkingSpaceView view : parkingSpaceViews) {
						if (view.isSelected()) {
							no += 1;
						}
					}
					if (no > 3) {
						JOptionPane.showMessageDialog(mainFrame, "Customer is allowed to book up to three parking spaces");
					} else if (no == 0) {
						JOptionPane.showMessageDialog(mainFrame, "Please choose the parking spaces you want to book");
					} else {
						String startdate = JOptionPane.showInputDialog(mainFrame, "Enter the start date(YYYY-mm-dd HH:MM): ", "Enter the booking start date", JOptionPane.INFORMATION_MESSAGE);
						String enddate = JOptionPane.showInputDialog(mainFrame, "Enter the end date(YYYY-mm-dd HH:MM): ", "Enter the booking end date", JOptionPane.INFORMATION_MESSAGE);
						String licensePlateNumber = JOptionPane.showInputDialog(mainFrame, "Enter the license plate number: ", "Enter the booking license plate number", JOptionPane.INFORMATION_MESSAGE);
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						if (startdate == null || enddate == null || licensePlateNumber == null) {
							JOptionPane.showMessageDialog(mainFrame, "Invalid input value");
						} else {
							for (ParkingSpaceView view : parkingSpaceViews) {
								if (view.isSelected()) {
									try {
										view.book(account, licensePlateNumber, format.parse(startdate), format.parse(enddate));
									} catch (ParseException e) {
										JOptionPane.showMessageDialog(mainFrame, "Invalid input value");
									}
								}
							}
						}
					}
				}
			}
		});
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String bookingID = JOptionPane.showInputDialog(mainFrame, "Enter the booking id you want to cancel: ", JOptionPane.INFORMATION_MESSAGE);
				if (bookingID == null || bookingID.trim().equals("")) {
					JOptionPane.showMessageDialog(mainFrame, "Invalid input value");
				} else {
					String spaceID = controller.cancel(account, bookingID);
					if (spaceID != null) {
						JOptionPane.showMessageDialog(mainFrame, "Booking canceled");
					} else {
						JOptionPane.showMessageDialog(mainFrame, "Unable to cancel the booking");
					}
				}
			}
		});
		pay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String bookingID = JOptionPane.showInputDialog(mainFrame, "Enter the booking id you want to pay: ", JOptionPane.INFORMATION_MESSAGE);
				if (bookingID == null || bookingID.trim().equals("")) {
					JOptionPane.showMessageDialog(mainFrame, "Invalid input value");
				} else {
					JPanel payPanel = new JPanel();
					String[] forms = new String[] {"Paypal", "credit" , "debit"};
					JComboBox<String> paymentForm = new JComboBox<>(forms);
					payPanel.add(paymentForm);
					int option = JOptionPane.showConfirmDialog(null,
		                        payPanel,
		                        "Choose the payment form: ",
		                        JOptionPane.OK_CANCEL_OPTION,
		                        JOptionPane.PLAIN_MESSAGE);
					if (option == JOptionPane.OK_OPTION) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
						Date date = new Date(System.currentTimeMillis());
						String paymentDetail = "Pay at " + format.format(date) + " by " + paymentForm.getSelectedItem();
						boolean paid = controller.updatePaymentState(bookingID, paymentDetail);
						if (!paid) {
							JOptionPane.showMessageDialog(mainFrame, "Payment for " + bookingID + " is failed!");
						} else {
							JOptionPane.showMessageDialog(mainFrame, "Payment for " + bookingID + " is succeed!");
						}
					}
				}
			}
		});
		view.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				final List<Booking> bookings = controller.getCustomerBookings(account);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String[] columnNames = new String[] {"BookingID", "parkingSpaceID", "licensePlateNumber", "Start Date", "End Date", "Granted?"};
				String[][] values = new String[bookings.size()][6];
				for (int i = 0; i < bookings.size(); i++) {
					values[i][0] = bookings.get(i).getBookingID();
					values[i][1] = bookings.get(i).getParkingSpace();
					values[i][2] = bookings.get(i).getLicensePlateNumber();
					values[i][3] = format.format(bookings.get(i).getStartDate());
					values[i][4] = format.format(bookings.get(i).getEndDate());
					values[i][5] = bookings.get(i).isGranted() ? "Granted" : "Not Granted";
				}
				final JTable table = new JTable(values, columnNames);
				table.setEnabled(false);
				JScrollPane scrollPane = new JScrollPane(table);
				final JFrame bookingFrame = new JFrame("Booking History");
				bookingFrame.setBounds((mainFrame.getWidth() - 1000) / 2, (mainFrame.getHeight() - 400) / 2, 1000, 400);
				bookingFrame.add(scrollPane);
				bookingFrame.setVisible(true);
				mainFrame.setEnabled(false);
				bookingFrame.addWindowListener(new WindowAdapter() {

					@Override
					public void windowClosing(WindowEvent e) {
						mainFrame.setEnabled(true);
					}
					
				});
				table.addMouseListener(new MouseAdapter() {
		            public void mouseClicked(MouseEvent event) {
		            	int row = table.rowAtPoint(event.getPoint());
		            	if (bookings.get(row).isPaid()) {
		            		JOptionPane.showMessageDialog(bookingFrame, bookings.get(row).isPaid() ? bookings.get(row).getPayment() : "Not paid yet!");
		            	}
		            }
		        });
			}
		});
	}
}
