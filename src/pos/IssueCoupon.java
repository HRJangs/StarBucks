package pos;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
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
		t_id = new JTextField(14);
		p_center = new JPanel();
		p_south =new JPanel();
		bt_reg = new JButton("발행");
		bt_back = new JButton("뒤로");
		t_id.setText(str);
		t_id.setEnabled(false);
		choice.add("--쿠폰을 선택해주세요--");
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
		
		t_id.setPreferredSize(new Dimension(20, 30));
		
		setVisible(true);
		setSize(250, 150);
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
			JOptionPane.showMessageDialog(this, str+"고객님에게 쿠폰 발행완료");
			this.dispose();
		}else if(obj==bt_back){
			this.dispose();
		}
		
	}
}
