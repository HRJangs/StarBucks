package pos;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class MemberPanel extends MyPanel implements ActionListener,TableModelListener{
	JLabel la_name;
	Choice choice;
	JButton bt_search,bt_edit,bt_coupon,bt_origin;
	int row,col;
	public MemberPanel() {
		la_name =new JLabel("이름");
		choice = new Choice();
		bt_search = new JButton("검색");
		bt_origin = new JButton("전체보기");
		//bt_edit = new JButton("회원 정보 수정");
		bt_coupon = new JButton("쿠폰");
		dataController=new DataController(this);
		dataController.getList("member");
		p_south.setPreferredSize(new Dimension(800, 70));
		setLayout(new BorderLayout());
		
		choice.add("이름");
		choice.add("ID");
	
		//p_north.add(bt_edit);
		p_north.add(bt_coupon);
		p_south.add(choice);
		p_south.add(t_search);
		p_south.add(bt_search);
		//p_south.add(bt_origin);
		
		//버튼에 리스너 연결
		//bt_edit.addActionListener(this);
		bt_search.addActionListener(this);
		bt_origin.addActionListener(this);
		bt_coupon.addActionListener(this);
		model =(DataModel) dataController.getDataModel();
		model.addTableModelListener(this);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				row= table.getSelectedRow();
				col=table.getSelectedColumn();
			}
		});
		
		table.setModel(model);
		add(p_north,BorderLayout.NORTH);
		add(scroll);
		add(p_south,BorderLayout.SOUTH);
	}
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == bt_search){
			search();
		}else if(obj == bt_coupon){
			sendCoupon();
		}else if(obj==bt_origin){
			System.out.println("눌럿니?");
			dataController.data.removeAll(dataController.data);
			dataController.getList("member");
			model =(DataModel) dataController.getDataModel();
			model.addTableModelListener(this);
			table.setModel(model);
		}
	}

	public void search(){
		JOptionPane.showMessageDialog(this, "검색할게요");
		dataController.SearchMember();
	}
	public void sendCoupon(){
		String str =(String) dataController.data.get(row).get(1);
		System.out.println(str);
		dataController.coupon.removeAll(dataController.coupon);
		dataController.getCoupon();
		new IssueCoupon(dataController,str);
	}
	
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int col = e.getColumn();
		dataController.editTable(model,e,"member");
	}
	
	
}