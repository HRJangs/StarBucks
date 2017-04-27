package pos;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dto.Product;

public class InsertMenu extends JFrame implements ActionListener{
	JTextField t_name,t_img,t_price;
	JButton bt_img,bt_reg;
	JFileChooser chooser;
	DataController dataController;
	Choice choice; 
	Product dto = new Product();
	JPanel p_center,p_south;
	File file;
	String fileName;
	boolean flag;
	public InsertMenu(DataController dataController) {
		this.dataController=dataController;
		p_south= new JPanel();
		p_center = new JPanel();
		bt_img = new JButton("이미지 등록");
		bt_reg = new JButton("제품 등록");
		t_name = new JTextField(15);
		t_img = new JTextField(15);
		t_price = new JTextField(15);
		chooser = new JFileChooser();
		choice=new Choice();
		choice.add("제품 분류를 선택하세요");
		choice.add("Coffe");
		choice.add("Drink");
		choice.add("Bread");
	
		p_center.add(t_name);
		p_center.add(t_img);
		p_center.add(t_price);
		p_center.add(choice);
		p_south.add(bt_img);
		p_south.add(bt_reg);
		add(p_south,BorderLayout.SOUTH);
		add(p_center);
		bt_reg.addActionListener(this);
		bt_img.addActionListener(this);
		setVisible(true);
		setSize(300,400);
		setLocationRelativeTo(null);
	}

	public void imageUpload(){
		int result=chooser.showOpenDialog(this);
		if(result == JFileChooser.APPROVE_OPTION){
			file=chooser.getSelectedFile();
			fileName = file.getName();
			t_img.setText(fileName);
		}
		flag=true;
	}	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==bt_img){
			imageUpload();
		}else if(obj==bt_reg){
				if(flag){
				dto.setProduct_category_id(choice.getSelectedIndex());
				dto.setProduct_name(t_name.getText());
				dto.setProduct_img(t_img.getText());
				dto.setProduct_price(Integer.parseInt(t_price.getText()));
				System.out.println(dto.getProduct_img());
				System.out.println(dto.getProduct_category_id());
				System.out.println(dto.getProduct_name());
				System.out.println(dto.getProduct_price());
				dataController.addMenu(dto);
				
				try {
					FileInputStream fis = new FileInputStream(file);
					PosThread posThread = new PosThread(fis, fileName);
					posThread.start();
					JOptionPane.showMessageDialog(this, "성공");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				this.dispose();
			}else if(flag==false){
				JOptionPane.showMessageDialog(this, "이미지를 먼저 등록해주세요");
			}
		}
	}
}
