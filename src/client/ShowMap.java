package client;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShowMap extends JPanel implements ActionListener{  
	BufferedImage img,img2;
	JPanel p_north,p_center,p_down,p_grid_up,p_down_top;
	Canvas can,can2;
	JButton bt_road,bt_1,bt_2,bt_3,bt_4,bt_5,bt_6,bt_7;
	JButton[] bt_array = {bt_1,bt_2,bt_3,bt_4,bt_5,bt_6,bt_7};
	String[] img_array={"http://localhost:9090/data/location_bt.png","http://localhost:9090/data/sogang1_bt.jpg","http://localhost:9090/data/sogang2_bt.jpg","http://localhost:9090/data/sogang3_bt.jpg","http://localhost:9090/data/sogang4_bt.jpg","http://localhost:9090/data/sogang5_bt.jpg","http://localhost:9090/data/sogang6_bt.jpg"};
	String[] img_array2={"http://localhost:9090/data/location.png","http://localhost:9090/data/sogang1.jpg","http://localhost:9090/data/sogang2.jpg","http://localhost:9090/data/sogang3.jpg","http://localhost:9090/data/sogang4.jpg","http://localhost:9090/data/sogang5.jpg","http://localhost:9090/data/sogang6.jpg"};
	JLabel la_info,la_top;
    public ShowMap() {
    	p_down_top= new JPanel();
    	p_north =new JPanel();
    	p_center =new JPanel();
    	p_down =new JPanel();
    	p_grid_up= new JPanel();
    	for(int i =0;i<bt_array.length;i++){
    		try {
    			ImageIcon icon= new ImageIcon(new URL(img_array[i]));
				bt_array[i] = new JButton(icon);
				bt_array[i].setPreferredSize(new Dimension(80,60));
				bt_array[i].addActionListener(this);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
    	}
    	
    	
    	la_top  = new JLabel("매장 안내(이미지를 클릭하시면 길찾기 페이지로 연결됩니다.)");
    	try {
			img = ImageIO.read(new URL("http://localhost:9090/data/location.jpg"));
			img2 = ImageIO.read(new URL("http://localhost:9090/data/mart_info.PNG"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	can = new Canvas(){
    		public void paint(Graphics g) {
    			g.drawImage(img, 0, 0, this);
    		}
    	};
    	can2 =  new Canvas(){
    		public void paint(Graphics g) {
    			g.drawImage(img2, 0, 0, this);
    		}
    	};
    	
    	can.addMouseListener(new MouseAdapter() {
    		
    		public void mouseClicked(MouseEvent e) {
    			try {
    				Desktop.getDesktop().browse(new URI("https://www.google.co.kr/maps/dir//%EC%8A%A4%ED%83%80%EB%B2%85%EC%8A%A4+%EC%84%9C%EA%B0%95%EB%8C%80%EC%A0%90+%EC%84%9C%EC%9A%B8%ED%8A%B9%EB%B3%84%EC%8B%9C+%EB%A7%88%ED%8F%AC%EA%B5%AC+%EC%8B%A0%EC%88%98%EB%8F%99+63-14/@37.5518244,126.9353454,17z/data=!4m8!4m7!1m0!1m5!1m1!1s0x357c98be6f268835:0x841626bec371de2!2m2!1d126.9377752!2d37.5523762"));
    			} catch (IOException e1) {
    				e1.printStackTrace();
    			} catch (URISyntaxException e1) {
    				e1.printStackTrace();
    			}
    		
    		}
		});
    	p_grid_up.add(can);
    	p_north.add(p_grid_up);
    	p_center.setLayout(new GridLayout(2, 1));
    	p_down.setLayout(new BorderLayout());
    	p_center.setBackground(Color.white);
    	p_grid_up.setBackground(Color.white);
    	can.setPreferredSize(new Dimension(600, 350));
    	can2.setPreferredSize(new Dimension(600, 300));
    	la_top.setForeground(Color.black);
    	la_top.setFont(new Font("굴림", Font.PLAIN, 13));
    	this.setLayout(new BorderLayout());
    	setBackground(Color.white);
    	p_center.add(p_north);
    	for(int i=0;i<bt_array.length;i++){
    		p_down_top.add(bt_array[i]);
    	}
    	p_down.setBackground(Color.white);
    	
    	p_down.add(p_down_top,BorderLayout.NORTH);
    	p_down.add(can2);
    	p_center.add(p_down);
    	add(la_top,BorderLayout.NORTH);
    	add(p_center);
    	
		setPreferredSize(new Dimension(300*2, 400*2-50));	
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		try {
		if(obj==bt_array[0]){
			img = ImageIO.read(new URL(img_array2[0]));
			can.repaint();
		}else if(obj==bt_array[1]){
			img = ImageIO.read(new URL(img_array2[1]));
			can.repaint();
		}else if(obj==bt_array[2]){
			img = ImageIO.read(new URL(img_array2[2]));
			can.repaint();
		}else if(obj==bt_array[3]){
			img = ImageIO.read(new URL(img_array2[3]));
			can.repaint();
		}else if(obj==bt_array[4]){
			img = ImageIO.read(new URL(img_array2[4]));
			can.repaint();
		}else if(obj==bt_array[5]){
			img = ImageIO.read(new URL(img_array2[5]));
			can.repaint();
		}else if(obj==bt_array[6]){
			img = ImageIO.read(new URL(img_array2[6]));
			can.repaint();
		}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	
}