package order.list;

import javax.swing.JFrame;

public class LoginFinish extends JFrame{
	
	public LoginFinish() {
		setSize(500, 500);
		setVisible(true); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		CallMain callMain = new CallMain();
		OrdersListMain orderlistMain = new OrdersListMain();
		orderlistMain.setCallMain(callMain);
	}

	public static void main(String[] args) {
		new LoginFinish();

	}

}
