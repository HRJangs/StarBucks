package pos;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class SalesPanel extends MyPanel implements TableModelListener,ItemListener{
	
	JLabel la_name;
	JPanel p_content,p_down,p_up;
	Choice ch1;
	public SalesPanel() {
		la_name =new JLabel("이름");
		ch1 = new Choice();
		dataController = new DataController(this);
		p_content =new JPanel();
		p_down = new JPanel();
		p_up = new JPanel();
		dataController.getList("sales");
		ch1.add("일별매출액");
		ch1.add("상품판매량");
		
		ch1.addItemListener(this);
		
		p_north.add(ch1);
		p_south.setPreferredSize(new Dimension(800, 70));
		p_content.setPreferredSize(new Dimension(800, 530));
		setLayout(new BorderLayout());
		p_content.setLayout(new GridLayout(2, 1));
		//버튼에 리스너 연결
		
		//테이블 초기 설정
		model =(DataModel) dataController.getDataModel();
		model.addTableModelListener(this);
		
		p_up.setLayout(new BorderLayout());
		p_down.setLayout(new BorderLayout());
		table.setModel(model);
		p_up.add(scroll);
		p_content.add(p_up);
		p_content.add(p_down);
		p_down.add(dataController.makeChat("일별매출액"));
		add(p_north,BorderLayout.NORTH);
		add(p_content);
	}
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int col = e.getColumn();
		dataController.editTable(model,e,"sales");
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		String str = ch1.getSelectedItem();
		p_down.removeAll();
		p_down.add(dataController.makeChat(str));
		p_down.updateUI();
	}
	
}
