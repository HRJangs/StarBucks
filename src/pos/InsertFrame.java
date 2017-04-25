package pos;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InsertFrame extends JFrame implements ActionListener{
	JButton bt_add,bt_exit;
	JLabel la_id,la_pw,la_phone,la_name,la_job,la_sal;
	JTextField t_id,t_pw,t_phone1,t_phone2,t_name,t_sal;
	Choice choice,choice_job;
	JPanel p_phone,p_11,p_12,p_21,p_22,p_31,p_32,p_41,p_42,p_51,p_52,p_61,p_62,p_71,p_72;
	DataController dataController;
	public InsertFrame(DataController dataController) {
		this.dataController= dataController;
		bt_add=  new JButton("등록");
		bt_exit= new JButton("나가기");
		la_id= new JLabel("직원 ID");
		la_pw= new JLabel("직원 PassWord");
		la_name= new JLabel("직원 이름");
		la_phone= new JLabel("직원 휴대전화 번호");
		la_job= new JLabel("직원 직급");
		la_sal = new JLabel("직원 급여");
		
		p_phone = new JPanel();
		p_11 = new JPanel();
		p_12 = new JPanel();
		p_21 = new JPanel();
		p_22 = new JPanel();
		p_31 = new JPanel();
		p_32 = new JPanel();
		p_41 = new JPanel();
		p_42 = new JPanel();
		p_51 = new JPanel();
		p_52 = new JPanel();
		p_61 = new JPanel();
		p_62 = new JPanel();
		p_71 =new JPanel();
		p_72 = new JPanel();
		
		t_id = new JTextField(15);
		t_pw = new JTextField(15);
		t_name = new JTextField(15);
		t_phone1 = new JTextField(5);
		t_phone2 = new JTextField(5);
		t_sal = new JTextField(15);
		choice_job =new Choice();
		choice_job.setPreferredSize(new Dimension(170, 20));
		choice_job.add("manager");
		choice_job.add("clerk");
		
		p_11.add(la_id);
		p_12.add(t_id);
		p_21.add(la_pw);
		p_22.add(t_pw);
		p_31.add(la_name);
		p_32.add(t_name);
		p_41.add(la_phone);
		p_51.add(la_job);
		p_52.add(choice_job);
		p_61.add(la_sal);
		p_62.add(t_sal);
		p_71.add(bt_add);
		p_72.add(bt_exit);
		
		choice = new Choice();
		choice.add("010");
		choice.add("011");
		choice.add("019");
		choice.add("016");
		p_phone.add(choice);
		p_phone.add(t_phone1);
		p_phone.add(t_phone2);
		
		setLayout(new GridLayout(7,2));
		add(p_11);
		add(p_12);
		add(p_21);
		add(p_22);
		add(p_31);
		add(p_32);
		add(p_41);
		add(p_phone);
		add(p_51);
		add(p_52);
		add(p_61);
		add(p_62);
		add(p_71);
		add(p_72);
		
		bt_add.addActionListener(this);
		bt_exit.addActionListener(this);
		setVisible(true);
		setSize(400,400);
		setLocationRelativeTo(null);
	}
	public void getOutPage(){
		this.dispose();
	}
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==bt_add){
			dataController.addEmp(this);
			JOptionPane.showMessageDialog(this, "등록완료");
			this.dispose();
		}else if(obj==bt_exit){
			getOutPage();
		}	
	}
}
