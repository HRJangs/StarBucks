package pos;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InsertCoupon extends JFrame implements ActionListener{
	Choice choice;
	JTextField t_num;
	DataController dataController;
	JButton bt_reg,bt_back;
	JPanel p_center,p_south,p_up,p_down;	
	public InsertCoupon(DataController dataController) {
		this.dataController = dataController;
		p_center = new JPanel();
		p_south = new JPanel();
		p_up = new JPanel();
		p_down = new JPanel();
		choice = new Choice();
		t_num = new JTextField(15);
		bt_back = new JButton("뒤로");
		bt_reg = new JButton("발행");
		for(int i=0;i<dataController.menu.size();i++){
			choice.add(dataController.menu.get(i));
		}
	
		choice.setPreferredSize(new Dimension(150, 50));
		t_num.setPreferredSize(new Dimension(50,50));
		p_center.setLayout(new GridLayout(2, 1));
		p_up.add(choice);
		p_down.add(t_num);
		p_center.add(p_up);
		p_center.add(p_down);
		p_south.add(bt_reg);
		p_south.add(bt_back);
		
		bt_reg.addActionListener(this);
		bt_back.addActionListener(this);
		add(p_center);
		add(p_south,BorderLayout.SOUTH);
		setVisible(true);
		setSize(200, 300);
		setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==bt_reg){
			dataController.createCoupon(this);
			JOptionPane.showMessageDialog(this, "쿠폰이 발행되었습니다");
			
		}else{
			this.dispose();
		}
	}
}
