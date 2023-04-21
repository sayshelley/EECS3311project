package EECS3311.EECS3311project;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class OfficerGUI {
	private Controller controller;
	
	private JFrame mainFrame;
	private JPanel parkingPanel;
	private JPanel controlPanel;
	private String account;
	private List<ParkingSpaceView> parkingSpaceViews;
	
	private JButton view;
	private JButton add;
	private JButton remove;
	
	public OfficerGUI(String account, Controller controller) {
		this.account = account;
		this.controller = controller;
		parkingPanel = new JPanel();
		controlPanel = new JPanel();
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
		JLabel welcomeLabel = new JLabel("Welcome, Manager " + account + "!");
		view = new JButton("VIEW BOOKINGS");
		add = new JButton("ADD SPACE");
		remove = new JButton("REMOVE SPACE");
		controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		controlPanel.add(welcomeLabel);
		controlPanel.add(view);
		controlPanel.add(add);
		controlPanel.add(remove);
	}
	
	public void initParkingPanel() {
		parkingPanel.removeAll();
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
		view.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showBookings();
			}
		});

		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = JOptionPane.showInputDialog(mainFrame, "Enter the new parking space id: ", JOptionPane.INFORMATION_MESSAGE);
				if (id == null || id.trim().equals("")) {
					JOptionPane.showMessageDialog(mainFrame, "Invalid input value");
				} else {
					boolean result = controller.addNewParkingSpace(id);
					if (result) {
						JOptionPane.showMessageDialog(mainFrame, "New parking space with id " + id + " is added.");
						initParkingPanel();
						mainFrame.repaint();
						mainFrame.revalidate();
					} else {
						JOptionPane.showMessageDialog(mainFrame, "Unable to add new parking space with id " + id + ".");
					}
				}
			}
		});
		remove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = JOptionPane.showInputDialog(mainFrame, "Enter the parking space id you want to remove: ", JOptionPane.INFORMATION_MESSAGE);
				if (id == null || id.trim().equals("")) {
					JOptionPane.showMessageDialog(mainFrame, "Invalid input value");
				} else {
					boolean result = controller.deleteParkingSpace(id);
					if (result) {
						JOptionPane.showMessageDialog(mainFrame, "The parking space with id " + id + " is deleted.");
						initParkingPanel();
						mainFrame.repaint();
						mainFrame.revalidate();
					} else {
						JOptionPane.showMessageDialog(mainFrame, "Unable to delete parking space with id " + id + ".");
					}
				}
			}
		});
	}
	
	public void showBookings() {
		final List<Booking> bookings = controller.getCustomerBookings("");
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
		final JFrame bookingFrame = new JFrame("Bookings");
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
            	if (!bookings.get(row).isGranted()) {
            		Object[] options ={ "GRANT REQUEST", "CANCEL REQUEST", "CANCEL"};
            		int option = JOptionPane.showOptionDialog(mainFrame, "Choose the option to this request: ", "Request", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            		if (option == JOptionPane.YES_OPTION) {
            			boolean result = controller.grantRequest(bookings.get(row).getBookingID());
            			if (!result) {
    						JOptionPane.showMessageDialog(mainFrame, "Unable to grant the request.");
            			} else {
    						JOptionPane.showMessageDialog(mainFrame, "Request granted.");
            			}
            		} else if (option == JOptionPane.NO_OPTION) {
            			String result = controller.cancel("", bookings.get(row).getBookingID());
            			if (result == null) {
    						JOptionPane.showMessageDialog(mainFrame, "Unable to cancel the request.");
            			} else {
    						JOptionPane.showMessageDialog(mainFrame, "Request canceled.");
            			}
            		}
            	}
            }
        });
	}
}
