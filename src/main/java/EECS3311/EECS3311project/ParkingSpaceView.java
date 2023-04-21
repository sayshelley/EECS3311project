package EECS3311.EECS3311project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ParkingSpaceView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private ParkingSpace parkingSpace;
	private ImageIcon image;
	private JCheckBox select;
	private JLabel idLabel;

	public ParkingSpaceView(Controller controller, ParkingSpace parkingSpace, int width) {
		this.controller = controller;
		this.parkingSpace = parkingSpace;
		setPreferredSize(new Dimension(width, (int) (width * 2)));
		if (parkingSpace.isOccupied()) {
//			if (parkingSpace.isPaid()) {
				image = new ImageIcon("src/main/images/occupied.png");
//			} else {
//				image = new ImageIcon("src/main/images/unpaid.png");
//			}
		} else {
			image = new ImageIcon("src/main/images/available.png");
		}
		select = new JCheckBox(parkingSpace.getId() + "");
		idLabel = new JLabel("<html>" + parkingSpace.getId() + "<br />BookID: " + parkingSpace.getBookID() + "</html>");
		double scale = (double) image.getIconHeight() / image.getIconWidth();
		image = new ImageIcon(image.getImage().getScaledInstance(width - 20, (int) ((width - 20) * scale), Image.SCALE_SMOOTH));
		initialize();
	}
	
	public void initialize() {
		JLabel imageLabel = new JLabel();
		imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		imageLabel.setPreferredSize(new Dimension(image.getIconWidth() + 20, image.getIconHeight() + 20));
		imageLabel.setIcon(image);
		imageLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!parkingSpace.isOccupied()) {
					select.setSelected(!select.isSelected());
					update();
				} else {
					JOptionPane.showMessageDialog(ParkingSpaceView.this, "The parking space is occupied!", "Error", JOptionPane.ERROR_MESSAGE, null);
				}
			}
			
		});
		add(imageLabel, BorderLayout.CENTER);
		JPanel infoPanel = new JPanel();
		infoPanel.add(select, BorderLayout.WEST);
		infoPanel.add(idLabel, BorderLayout.EAST);
		add(infoPanel, BorderLayout.SOUTH);
		select.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				update();
			}
		});
		if (parkingSpace.isOccupied()) {
			select.setVisible(false);
			idLabel.setVisible(true);
		} else {
			idLabel.setVisible(false);
			select.setVisible(true);
		}
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
	}
	
	public void update() {
		if (parkingSpace.isOccupied()) {
			select.setVisible(false);
			idLabel.setVisible(true);
//			if (parkingSpace.isPaid()) {
				ImageIcon tmp = new ImageIcon("src/main/images/occupied.png");
				double scale = (double) tmp.getIconHeight() / tmp.getIconWidth();
				image.setImage(tmp.getImage().getScaledInstance(image.getIconWidth(), (int) (image.getIconWidth() * scale), Image.SCALE_SMOOTH));
//			} else {
//				ImageIcon tmp = new ImageIcon("src/main/images/unpaid.png");
//				double scale = (double) tmp.getIconHeight() / tmp.getIconWidth();
//				image.setImage(tmp.getImage().getScaledInstance(image.getIconWidth(), (int) (image.getIconWidth() * scale), Image.SCALE_SMOOTH));
//			}
		} else {
			idLabel.setVisible(false);
			select.setVisible(true);
			if (select.isSelected()) {
				ImageIcon tmp = new ImageIcon("src/main/images/selected.png");
				double scale = (double) tmp.getIconHeight() / tmp.getIconWidth();
				image.setImage(tmp.getImage().getScaledInstance(image.getIconWidth(), (int) (image.getIconWidth() * scale), Image.SCALE_SMOOTH));
			} else {
				ImageIcon tmp = new ImageIcon("src/main/images/available.png");
				double scale = (double) tmp.getIconHeight() / tmp.getIconWidth();
				image.setImage(tmp.getImage().getScaledInstance(image.getIconWidth(), (int) (image.getIconWidth() * scale), Image.SCALE_SMOOTH));
			}
		}
		repaint();
	}
	
	public boolean isSelected() {
		return select.isVisible() & select.isSelected();
	}
	
	public void book(String account, String licensePlateNumber, Date startdate, Date enddate) {
		controller.book(account, parkingSpace.getId(), licensePlateNumber, startdate, enddate);
		update();
	}
	
	public ParkingSpace getParkingSpace() {
		return parkingSpace;
	}
}
