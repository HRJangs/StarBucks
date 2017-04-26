package pos;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IssueCoupon extends JFrame implements ActionListener{
	
	DataController dataController;
	String str;
	JButton bt_reg,bt_back;
	Choice choice;
	JTextField t_id;
	JPanel p_center,p_south;
	String number;
	public IssueCoupon(DataController dataController,String str) {
		this.str=str;
		this.dataController = dataController;
		choice = new Choice();
		t_id = new JTextField(10);
		p_center = new JPanel();
		p_south =new JPanel();
		bt_reg = new JButton("¹ßÇà");
		bt_back = new JButton("µÚ·Î");
		t_id.setText(str);
		t_id.setEnabled(false);
		for(int i=0;i<dataController.coupon.size();i++){
			choice.add((String) dataController.coupon.get(i).get(0));
		}
		p_center.add(t_id);
		p_center.add(choice);
		p_south.add(bt_reg);
		p_south.add(bt_back);
		
		bt_back.addActionListener(this);
		bt_reg.addActionListener(this);
		add(p_center);
		add(p_south,BorderLayout.SOUTH);
		setVisible(true);
		setSize(300, 300);
		setLocationRelativeTo(null);
		
		
		
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==bt_reg){
			for(int i=0;i<dataController.coupon.size();i++){
				if(choice.getSelectedItem().equals(dataController.coupon.get(i).get(0))){
					number=(String) dataController.coupon.get(i).get(1);
				}	
			}
			System.out.println(str+"  "+number);
			dataController.authorizeCoupon(str,number);
			JOptionPane.showMessageDialog(this, str+"°í°´´Ô¿¡°Ô ÄíÆù ¹ßÇà¿Ï·á");
			this.dispose();
		}else if(obj==bt_back){
			this.dispose();
		}
		
	}
}
