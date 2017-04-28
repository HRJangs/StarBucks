package pos;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class CouponPanel extends MyPanel implements ActionListener, TableModelListener{
	JButton bt_create;
	
	public CouponPanel() {
		//bt_edit = new JButton("직원 정보 수정");
		dataController = new DataController(this);
		bt_create= new JButton("쿠폰 발행");
		dataController.getList("coupon");
		
		p_south.setPreferredSize(new Dimension(800, 70));
		setLayout(new BorderLayout());
		
		model =(DataModel) dataController.getDataModel();
		model.addTableModelListener(this);
		
		bt_create.addActionListener(this);
		
		//테이블 초기 설정
		table.setModel(model);
		p_north.add(bt_create);
		add(p_north,BorderLayout.NORTH);
		add(scroll);
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		addCoupon();
		
	}
	public void addCoupon(){
		new InsertCoupon(dataController);
	}

	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int col = e.getColumn();
		dataController.editTable(model,e,"coupon");
	}
}
