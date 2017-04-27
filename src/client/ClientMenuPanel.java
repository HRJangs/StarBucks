package client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dto.Product;

public class ClientMenuPanel extends JPanel implements ActionListener {
	ClientOrders clientOrders;
	BufferedImage image;
	Canvas can;
	JButton bt;
	JLabel la_name, la_price;
	Product product;

	JLabel la_order_name, la_order_count, la_order_price;
	String name;
	String price;
	int price_sum;
	int p_count;

	public ClientMenuPanel(ClientOrders clientOrders, Product product) {
		this.clientOrders = clientOrders;
		this.product = product;

		// 주문용 메뉴 추후 아예 패널로 줘야 할듯?
		name = product.getProduct_name();
		price = Integer.toString(product.getProduct_price());
		p_count = 1;

		la_order_name = new JLabel(name);
		la_order_count = new JLabel(Integer.toString(p_count));
		la_order_price = new JLabel(price);

		setPreferredSize(new Dimension(580, 80));

		int id = product.getProduct_id();
		try {
			// 제품 이미지 가져오기
			URL url = new URL("http://211.238.142.120:9090/data/" + id + ".jpg");
			image = ImageIO.read(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		can = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, 70, 70, this);
			}
		};

		can.setPreferredSize(new Dimension(70, 70));

		la_name = new JLabel(product.getProduct_name());
		la_price = new JLabel("price : " + Integer.toString(product.getProduct_price()));

		bt = new JButton("담기");
		bt.addActionListener(this);

		add(can);
		add(la_name);
		add(la_price);
		add(bt);

		// 위치
		setLayout(null);
		can.setBounds(20, 5, 70, 70);
		la_name.setBounds(180, 10, 300, 30);
		la_price.setBounds(180, 40, 300, 30);
		bt.setBounds(420, 20, 100, 40);

		// 색
		la_price.setForeground(new Color(157, 151, 123));
		bt.setForeground(new Color(123, 109, 100));
		bt.setBackground(new Color(215, 210, 204));
		bt.setBorderPainted(false);

		bt.setFocusPainted(false); // 버튼선택시 생기는 외곽선 사용 안함

	}

	public void actionPerformed(ActionEvent e) {
		JPanel p_order = new JPanel();
		p_order.setBackground(new Color(244, 244, 242));
		p_order.setPreferredSize(new Dimension(580, 30));
		p_order.setLayout(null);
		la_order_name.setBounds(20, 0, 300, 30);
		la_order_count.setBounds(320, 0, 50, 30);
		la_order_price.setBounds(450, 0, 150, 30);

		clientOrders.la_null.setText("");
		price_sum = Integer.parseInt(price) * p_count;
		if (p_count == 1) {
			la_order_name.setText(name);
			la_order_count.setText(p_count + " 개");
			la_order_price.setText(Integer.toString(price_sum) + " 원");

			Font font = new Font("돋움", Font.BOLD, 15);
			la_order_name.setFont(font);
			la_order_count.setFont(font);
			la_order_price.setFont(font);

			p_order.add(la_order_name);
			p_order.add(la_order_count);
			p_order.add(la_order_price);

			clientOrders.p_center.add(p_order);
		} else if (p_count > 1) {
			la_order_name.setText(name);
			la_order_count.setText(p_count + " 개");
			la_order_price.setText(Integer.toString(price_sum) + " 원");
		}
		System.out.println("p_count : " + p_count);

		p_count++;

		clientOrders.orders_list.add(product);
		System.out.println("제품 주문에 추가됨");

		// 총 갯수
		int count = 0;

		// 합계금액 체크
		int sum = 0;
		for (int i = 0; i < clientOrders.orders_list.size(); i++) {
			sum += clientOrders.orders_list.get(i).getProduct_price();
			count = i + 1;
		}
		System.out.println(count);
		clientOrders.la_count.setText("총 " + count + "개			");
		clientOrders.la_sum.setText(sum + " 원");

		clientOrders.p_center.updateUI();

	}

}
