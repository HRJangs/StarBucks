package pos;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class RecipePanel extends MyPanel implements ActionListener,TableModelListener{
	JTextField t_search;
	JLabel la_name;
	Choice choice;
	JButton bt_add;
	JButton bt_excel_download, bt_excel_upload;
	
	//�̹��� ���ε�
	JButton bt_image_upload;
	JFileChooser chooser;
	
	public RecipePanel() {
		la_name =new JLabel("�̸�");
		choice = new Choice();
		//bt_add= new JButton("�޴��߰�");
		dataController = new DataController(this);
		dataController.productList();
		//table.getColumn("product_name").setMaxWidth(20);
		bt_excel_download = new JButton("�������Ϸ� �ޱ�");
		bt_excel_upload = new JButton("�������� �ø���");
		
		//�̹��� �ø���
		bt_image_upload = new JButton("�̹��� �ø���");
		chooser = new JFileChooser(""); //��򰡷� ���ؾ��ϴµ� �� ��
		
		
		setLayout(new BorderLayout());
		//p_north.add(choice);
		p_south.setPreferredSize(new Dimension(800, 70));
	
		//���̺� �ʱ� ����
		model =(DataModel) dataController.getDataModel();
		model.addTableModelListener(this);
		//p_north.add(bt_add);
		p_south.add(bt_excel_download);
		p_south.add(bt_excel_upload);
		
		
		p_south.add(bt_image_upload);
		
		bt_image_upload.addActionListener(this);
		
		
		table.setModel(model);
		
		//add(p_north,BorderLayout.NORTH);
		add(scroll);
		add(p_south,BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==bt_image_upload){
			imageUpload();
		}
	}
	
	public void imageUpload(){
		int result=chooser.showOpenDialog(this);
		if(result == JFileChooser.APPROVE_OPTION){
			File file=chooser.getSelectedFile();
			String fileName = file.getName();
			
			try {
				FileInputStream fis = new FileInputStream(file);
				PosThread posThread = new PosThread(fis, fileName);
				posThread.start();
				JOptionPane.showMessageDialog(this, "����");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int col = e.getColumn();
		dataController.editTable(model,e,"product");
	}
	
}
