package EECS3311.EECS3311project;

public class Main {

	
	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		ParkingSpace parkingSpace = new ParkingSpace("0");
////		parkingSpace.setOccupied(true);
//		frame.add(new ParkingSpaceView(parkingSpace, 200));
//		frame.pack();
//		frame.setVisible(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Controller controller = new Controller();
//		CustomerGUI customerGUI = new CustomerGUI("test", controller);
//		new OfficerGUI("test", controller);
		new LoginGUI(controller);
//		AdminGUI adminGUI = new AdminGUI(controller);
	}
}
