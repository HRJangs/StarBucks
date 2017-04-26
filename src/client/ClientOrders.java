package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dto.Product;

public class ClientOrders extends JPanel implements ActionListener {

	ClientMain main;
	JPanel p_north, p_center, p_south, p_sum;
	JButton bt_orders_delete, bt_orders_send;
	Vector<Product> orders_list = new Vector<Product>();
	JScrollPane scroll, scroll2;
	JLabel la_count, la_sum, la_null;
	ArrayList<ClientMenuPanel> panel_list = new ArrayList<ClientMenuPanel>();

	public ClientOrders(ClientMain main) {
		this.main = main;

		p_north = new JPanel();
		p_center = new JPanel();
		p_sum = new JPanel();
		p_south = new JPanel();
		scroll = new JScrollPane(p_north);
		scroll2 = new JScrollPane(p_center);

		scroll.setPreferredSize(new Dimension(300 * 2, 400));
		scroll2.setPreferredSize(new Dimension(300 * 2, 200));
		p_center.setPreferredSize(new Dimension(580, main.product_list.size() * 30));
		p_south.setPreferredSize(new Dimension(300 * 2, 50 * 2));
		p_sum.setPreferredSize(new Dimension(300 * 2, 50));
		p_north.setPreferredSize(new Dimension(580, main.product_list.size() * 90));

		bt_orders_send = new JButton("�ֹ��ϱ�");
		bt_orders_delete = new JButton("���");

		la_count = new JLabel();
		la_sum = new JLabel();
		la_null = new JLabel("��� �޴��� �����ϴ�.");

		p_center.add(la_null);

		p_sum.add(la_count);
		p_sum.add(la_sum);
		p_south.add(p_sum);
		p_south.add(bt_orders_send);
		p_south.add(bt_orders_delete);

		// ��ǰ ��� �÷��α�
		init();

		add(scroll, BorderLayout.NORTH);
		add(scroll2, BorderLayout.CENTER);
		add(p_south, BorderLayout.SOUTH);

		// ��
		this.setBackground(Color.WHITE);
		p_north.setBackground(Color.WHITE);
		p_center.setBackground(Color.WHITE);
		p_south.setBackground(Color.WHITE);
		p_sum.setBackground(Color.WHITE);
		bt_orders_send.setForeground(Color.WHITE);
		bt_orders_send.setBackground(new Color(123, 109, 100));
		bt_orders_send.setBorderPainted(false);
		bt_orders_delete.setForeground(Color.WHITE);
		bt_orders_delete.setBackground(new Color(123, 109, 100));
		bt_orders_delete.setBorderPainted(false);
		bt_orders_send.setFocusPainted(false); // ��ư���ý� ����� �ܰ��� ��� ����
		bt_orders_delete.setFocusPainted(false);
		la_sum.setForeground(new Color(151, 138, 92));

		Font font = new Font("����", Font.BOLD, 20);
		la_sum.setFont(font);
		la_count.setFont(font);
		la_null.setFont(font);

		// ��ư�� ������ ����
		bt_orders_delete.addActionListener(this);
		bt_orders_send.addActionListener(this);

		setPreferredSize(new Dimension(300 * 2, 400 * 2));
		setVisible(true);
	}

	// ���Ӱ� ���ÿ� ������ ��ǰ ������ ��η� �� ����
	public void init() {
		for (int i = 0; i < main.product_list.size(); i++) {
			ClientMenuPanel cmp = new ClientMenuPanel(this, main.product_list.get(i));
			p_north.add(cmp);

			if (i % 2 == 0) {
				cmp.setBackground(Color.WHITE);
			} else {
				cmp.setBackground(new Color(244, 244, 242));
			}

			panel_list.add(cmp);

		}
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("������ ĳġ");
		Object obj = e.getSource();

		// �ֹ��ϱ⸦ ���������,
		if (obj == bt_orders_send) {
			/*JOptionPane.showConfirmDialog(null, "�ֹ��Ͻðڽ��ϱ�?", null, JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);*/

			OrdersPay pay = new OrdersPay(main, orders_list);
			// �󺧿� ������ �͵��� product�� �����;��Ѵ�.
			/*ClientThread thread = new ClientThread(main, orders_list);
			thread.start();*/

		} else if (obj == bt_orders_delete) {

			System.out.println("����");
			orders_list.removeAllElements();
			la_sum.setText("");
			la_count.setText("");
			p_center.removeAll();
			p_center.updateUI();
			la_null = new JLabel("��� �޴��� �����ϴ�.");
			p_center.add(la_null);

			for (int i = 0; i < panel_list.size(); i++) {
				panel_list.get(i).p_count = 1;
			}
		}
	}
}
