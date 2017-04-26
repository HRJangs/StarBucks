package pos;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MyPanel extends JPanel{
	JTable table;
	Choice choice;
	JTextField t_search;
	JScrollPane scroll;
	JPanel p_south,p_north;
	DataModel model;
	DataController dataController;
	public MyPanel() {
		table = new JTable();
		choice = new Choice();
		t_search =new JTextField(20);
		scroll =new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		p_south = new JPanel();
		p_north = new JPanel();
		choice.add("¿Ã∏ß");
		choice.add("ID");
		table.setBackground(Color.white);
		table.setPreferredScrollableViewportSize(new Dimension(800, 400));
		setBackground(Color.white);
		p_south.setBackground(new Color(0,114, 67));
		p_north.setBackground(Color.white);
	}
}
