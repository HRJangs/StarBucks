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

public class RecipePanel extends MyPanel implements ActionListener,TableModelListener{
	JTextField t_search;
	JLabel la_name;
	Choice choice;
	JButton bt_add;
	JButton bt_excel_download, bt_excel_upload;
	public RecipePanel() {
		la_name =new JLabel("이름");
		choice = new Choice();
		//bt_add= new JButton("메뉴추가");
		dataController = new DataController(this);
		dataController.productList();
		//table.getColumn("product_name").setMaxWidth(20);
		bt_excel_download = new JButton("엑셀파일로 받기");
		bt_excel_upload = new JButton("엑셀파일 올리기");
		setLayout(new BorderLayout());
		//p_north.add(choice);
		p_south.setPreferredSize(new Dimension(800, 70));
	
		//테이블 초기 설정
		model =(DataModel) dataController.getDataModel();
		model.addTableModelListener(this);
		//p_north.add(bt_add);
		p_south.add(bt_excel_download);
		p_south.add(bt_excel_upload);
		//bt_add.addActionListener(this);
		table.setModel(model);
		
		//add(p_north,BorderLayout.NORTH);
		add(scroll);
		add(p_south,BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==bt_add){
			//new InsertMenu(dataController);
		}
	}
	
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int col = e.getColumn();
		dataController.editTable(model,e,"product");
	}
	
}
