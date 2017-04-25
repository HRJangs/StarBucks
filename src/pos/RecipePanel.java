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
	JButton bt_search,bt_reg,bt_edit;

	public RecipePanel() {
		la_name =new JLabel("이름");
		choice = new Choice();
		dataController = new DataController(this);
		dataController.getList("recipe");
		choice.add("--------");
		choice.add("커피류");
		choice.add("빵류");
		
		setLayout(new BorderLayout());
		//p_north.add(choice);
	
	
		//테이블 초기 설정
		model =(DataModel) dataController.getDataModel();
		model.addTableModelListener(this);
		
		
		table.setModel(model);
		
		add(p_north,BorderLayout.NORTH);
		add(scroll);
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
	
	}
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int col = e.getColumn();
		dataController.editTable(model,e,"product");
	}
	
}
