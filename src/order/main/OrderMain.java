package order.main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import db.DBManager;
import dto.Emp;
import dto.Orders;
import dto.Product;
import dto.Product_category;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import order.payment.Payment;
import pos.login.PosWindow;

<<<<<<< HEAD

public class OrderMain extends JPanel implements ActionListener,Runnable, ItemListener{
=======
public class OrderMain extends JPanel implements ActionListener, Runnable, ItemListener {
>>>>>>> b9bfcbd8db5201280628f0730795a35d13cc7e20

	Connection con;
	DBManager manager;
	Thread thread;
	MusicThread music;
	// p_east ��üȭ�� ����, p_west ��üȭ�� ���� p_product �ֹ��ѰŶߴ� ��,p_topMenu�޴� ���� ��ư�� �ִ°�
	// p_subMenu�޴� ������ư�� p_pay �����ϱ��ư �ִ� ��

<<<<<<< HEAD

	JPanel p_pos,p_product,p_component,p_topMenu,p_subMenu, p_sum,p_pay , p_east, p_west ,p_date,p_music ,p_etc ,p_con ,p_list;
	JButton bt_pay, bt_allDelete,bt_stop,bt_play, bt_prev, bt_next,bt_reservation, bt_reservation_show ,bt_income , bt_stock;
	
	CheckboxGroup group = new CheckboxGroup();
	Checkbox cb_repeat_one = new Checkbox("�Ѱ�ݺ�", false, group);
	Checkbox cb_repeat_all = new Checkbox("����ݺ�", true, group);
=======
	JPanel p_pos, p_product, p_component, p_topMenu, p_subMenu, p_sum, p_pay, p_east, p_west, p_date, p_music, p_etc,
			p_con, p_list;
	JButton bt_pay, bt_allDelete, bt_stop, bt_play, bt_prev, bt_next, bt_reservation, bt_reservation_show, bt_income,
			bt_stock;

	CheckboxGroup group = new CheckboxGroup();
	Checkbox cb_repeat_one = new Checkbox("�Ѱ�ݺ�", false, group);
	Checkbox cb_repeat_all = new Checkbox("����ݺ�", true, group);
<<<<<<< HEAD
	
	
	JScrollPane scroll_menu, scroll_bt;
	
=======
>>>>>>> b9bfcbd8db5201280628f0730795a35d13cc7e20

	JScrollPane scroll;

>>>>>>> 6d9bebe73a979adab01cc7386414db34e358ea76
	Canvas can;
	BufferedImage image = null;

	JLabel la_sum_name, la_sum, la_info, la_date;
	Vector<Product_category> bigMenu = new Vector<Product_category>();// ���� �޴���
																		// �������(Ŀ��,�꽺.��)
	Vector<Product> product_list = new Vector<Product>(); // product ���� �޴����� ������
															// �������
	Vector<ProductPanel> menu_list = new Vector<ProductPanel>();
	Vector<Orders> orders_list = new Vector<Orders>();
	PosWindow posWindow;
<<<<<<< HEAD
	/*String[] coffee = {"http://localhost:9090/data/1.jpg","http://localhost:9090/data/2.jpg","http://localhost:9090/data/8.jpg","http://localhost:9090/data/9.jpg","http://localhost:9090/data/10.jpg",
			"http://localhost:9090/data/11.jpg","http://localhost:9090/data/12.jpg","http://localhost:9090/data/13.jpg","http://localhost:9090/data/14.jpg","http://localhost:9090/data/15.jpg"};
	String[] drink = {"http://localhost:9090/data/3.jpg","http://localhost:9090/data/4.jpg","http://localhost:9090/data/16.jpg","http://localhost:9090/data/17.jpg","http://localhost:9090/data/18.jpg"
			,"http://localhost:9090/data/19.jpg","http://localhost:9090/data/20.jpg","http://localhost:9090/data/21.jpg","http://localhost:9090/data/22.jpg"};
	String[] bread = {"http://localhost:9090/data/5.jpg","http://localhost:9090/data/6.jpg","http://localhost:9090/data/7.jpg","http://localhost:9090/data/23.jpg","http://localhost:9090/data/24.jpg",
			"http://localhost:9090/data/25.jpg","http://localhost:9090/data/26.jpg","http://localhost:9090/data/27.jpg","http://localhost:9090/data/28.jpg"};
	*/
	ArrayList<String> coffee=new ArrayList<String>();
	ArrayList<String> drink=new ArrayList<String>();
	ArrayList<String> bread=new ArrayList<String>();
=======

	String[] coffee = {"http://211.238.142.120:9090/data/1.jpg","http://211.238.142.120:9090/data/2.jpg",
			"http://211.238.142.120:9090/data/8.jpg","http://211.238.142.120:9090/data/9.jpg","http://211.238.142.120:9090/data/10.jpg",
			"http://211.238.142.120:9090/data/11.jpg","http://211.238.142.120:9090/data/12.jpg",
			"http://211.238.142.120:9090/data/13.jpg","http://211.238.142.120:9090/data/14.jpg",
			"http://211.238.142.120:9090/data/15.jpg"};
	String[] drink = {"http://211.238.142.120:9090/data/3.jpg","http://211.238.142.120:9090/data/4.jpg",
			"http://211.238.142.120:9090/data/16.jpg","http://211.238.142.120:9090/data/17.jpg","http://211.238.142.120:9090/data/18.jpg"
			,"http://211.238.142.120:9090/data/19.jpg","http://211.238.142.120:9090/data/20.jpg",
			"http://211.238.142.120:9090/data/21.jpg","http://211.238.142.120:9090/data/22.jpg"};
	String[] bread = {"http://211.238.142.120:9090/data/5.jpg","http://211.238.142.120:9090/data/6.jpg",
			"http://211.238.142.120:9090/data/7.jpg","http://211.238.142.120:9090/data/23.jpg",
			"http://211.238.142.120:9090/data/24.jpg",
			"http://211.238.142.120:9090/data/25.jpg","http://211.238.142.120:9090/data/26.jpg",
			"http://211.238.142.120:9090/data/27.jpg","http://211.238.142.120:9090/data/28.jpg"};
	
>>>>>>> b9bfcbd8db5201280628f0730795a35d13cc7e20
	int total;
	int order_number = 1;
	JButton obj;

	Emp emp;

	// ���ǰ���
	URL[] url = new URL[4];
	int num;
	boolean repeat_one_flag = false;
	boolean repeat_all_flag = true;

	// Label timeLabel=new Label("Time: ");

	Slider timeSlider;
	Slider volumnSlider;
	HBox mediaBar;

	int count = 0;

	// ���� �̹����� �޾ƿ������ؼ� �̹����� ���
	String[] img_array = {};

	public OrderMain(PosWindow posWindow) {

		for (int i = 0; i < url.length; i++) {
			try {
				url[i] = new URL("http://211.238.142.120:9090/data/jazz" + (i + 1) + ".mp3");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
<<<<<<< HEAD

=======
<<<<<<< HEAD
>>>>>>> b9bfcbd8db5201280628f0730795a35d13cc7e20
		//timeLabel.setMinWidth(Control.USE_PREF_SIZE);
		//mediaBar.getChildren().add(timeLabel);
		
		this.posWindow =posWindow;
		p_date=new JPanel();

		p_east=new JPanel();
		p_west=new JPanel();
		p_pos=new JPanel();
		p_product=new JPanel();
		p_component=new JPanel();
		p_sum=new JPanel();
		p_pay=new JPanel();

		p_topMenu=new JPanel();
		p_subMenu=new JPanel(); 
		p_etc=new JPanel();
		p_con=new JPanel();
		p_date=new JPanel();
		p_music=new JPanel();
	
		scroll_menu=new JScrollPane(p_component,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll_bt=new JScrollPane(p_subMenu);
		
		bt_allDelete=new JButton("��ü����");
		bt_pay=new JButton("�����ϱ�");
=======

		// timeLabel.setMinWidth(Control.USE_PREF_SIZE);
		// mediaBar.getChildren().add(timeLabel);

		this.posWindow = posWindow;
		p_date = new JPanel();

		p_east = new JPanel();
		p_west = new JPanel();
		p_pos = new JPanel();
		p_product = new JPanel();
		p_component = new JPanel();
		p_sum = new JPanel();
		p_pay = new JPanel();

		p_topMenu = new JPanel();
		p_subMenu = new JPanel();
		p_etc = new JPanel();
		p_con = new JPanel();
		p_date = new JPanel();
		p_music = new JPanel();

		scroll = new JScrollPane(p_component);

		bt_allDelete = new JButton("��ü����");
		bt_pay = new JButton("�����ϱ�");

>>>>>>> 6d9bebe73a979adab01cc7386414db34e358ea76
		bt_allDelete.setBackground(Color.WHITE);
		bt_pay.setBackground(Color.WHITE);
		bt_allDelete.setPreferredSize(new Dimension(150, 50));
		bt_pay.setPreferredSize(new Dimension(150, 50));

<<<<<<< HEAD
		
		
		bt_next=new JButton("����");
		bt_prev=new JButton("����");
		bt_play=new JButton("��");
		bt_stop=new JButton("||");
		
=======
		bt_next = new JButton("����");
		bt_prev = new JButton("����");
		bt_play = new JButton("��");
		bt_stop = new JButton("||");
>>>>>>> b9bfcbd8db5201280628f0730795a35d13cc7e20

		bt_next.setBackground(Color.WHITE);
		bt_next.setPreferredSize(new Dimension(70, 50));
		bt_prev.setBackground(Color.WHITE);
		bt_prev.setPreferredSize(new Dimension(70, 50));
		bt_play.setBackground(Color.WHITE);
		bt_play.setPreferredSize(new Dimension(70, 50));
		bt_stop.setBackground(Color.WHITE);
		bt_stop.setPreferredSize(new Dimension(70, 50));

		la_date = new JLabel("�ð�");
		la_sum_name = new JLabel("�հ�:");
		la_sum = new JLabel("0");
		la_sum_name.setFont(new Font("����", Font.BOLD, 30));
		la_sum.setFont(new Font("����", Font.BOLD, 30));

		la_info = new JLabel("��ǰ��  ����   �ݾ�");
		la_info.setFont(new Font("����", Font.BOLD, 30));

		Product dto = new Product();

		// �г� ũ�� ����
		p_east.setPreferredSize(new Dimension(800, 800));
		p_west.setPreferredSize(new Dimension(400, 800));
		p_date.setPreferredSize(new Dimension(400, 200));
		p_pos.setPreferredSize(new Dimension(400, 150));
		p_product.setPreferredSize(new Dimension(400, 100)); // �ֹ��Ѱ� �ߴ°�
		p_sum.setPreferredSize(new Dimension(400, 100));
		p_component.setPreferredSize(new Dimension(400, 350));
		p_topMenu.setPreferredSize(new Dimension(800, 70));// (����)Ŀ��. ����. �� ������
															// ��ư
		p_subMenu.setPreferredSize(new Dimension(800, 500)); // (����)�ֹ���ư��
		p_music.setPreferredSize(new Dimension(400, 200));
		p_pay.setPreferredSize(new Dimension(400, 150)); // �����ϱ� ��ư �ִ� �г�
		p_etc.setPreferredSize(new Dimension(800, 80));
		p_con.setPreferredSize(new Dimension(800, 200));
<<<<<<< HEAD
		scroll_menu.setPreferredSize(new Dimension(400, 350));
		scroll_bt.setPreferredSize(new Dimension(800, coffee.length * 200));
		
		
=======

>>>>>>> 6d9bebe73a979adab01cc7386414db34e358ea76
		try {
			URL image_url = new URL("http://211.238.142.120:9090/data/sb_join.png");
			image = ImageIO.read(image_url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		can = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage((Image) image, 30, 0, 350, 150, this);
			}
		};

		can.setPreferredSize(new Dimension(400, 150));
		p_pos.add(can);

		// p_west �гο� p_pos, p_product , p_component,p_sum, p_pay
		p_pos.setBackground(Color.LIGHT_GRAY);
		p_product.setBackground(Color.LIGHT_GRAY);
		p_component.setBackground(Color.LIGHT_GRAY);
		p_sum.setBackground(Color.LIGHT_GRAY);
		p_pay.setBackground(Color.LIGHT_GRAY);

		// p_east �гο� p_topMenu,p_subMenu,p_etc,p_date , p_music

		// ���� ��ư ������ p_component �� ������
		p_west.setLayout(new FlowLayout());
		p_west.add(p_pos);
		p_west.add(p_product);
		p_product.add(la_info);
		p_west.add(p_component);
		p_west.add(p_sum);
		p_sum.add(la_sum_name);
		p_sum.add(la_sum);
		// p_sum.add(bt_allDelete,BorderLayout.WEST);
		p_west.add(p_pay);
		p_pay.add(bt_allDelete);
		p_pay.add(bt_pay);

		add(p_west, BorderLayout.CENTER);
		// p_west.add(scroll);
		// scroll.setPreferredSize(new Dimension(750,600));
		// ���� �޴���ư�� , �ð��̶� �����÷��̹�ư
		p_east.setLayout(new FlowLayout());
		p_east.add(p_topMenu);
		p_east.add(p_subMenu);
		p_east.add(p_etc);

		// p_etc.add(mediaBar);

		p_east.add(p_con);

		p_con.setLayout(new GridLayout(1, 2));
		p_con.add(p_music);
		p_con.add(p_date);

		add(p_east, BorderLayout.EAST);
		p_date.add(la_date);

		p_music.add(bt_prev);

		p_music.add(bt_stop);
		p_music.add(bt_play);
		p_music.add(bt_next);

<<<<<<< HEAD
		
=======
>>>>>>> b9bfcbd8db5201280628f0730795a35d13cc7e20
		p_music.add(cb_repeat_one);
		p_music.add(cb_repeat_all);

		cb_repeat_one.addItemListener(this);
		cb_repeat_all.addItemListener(this);

		bt_allDelete.addActionListener(this);
		bt_pay.addActionListener(this);
		bt_play.addActionListener(this);
		bt_stop.addActionListener(this);

		bt_next.addActionListener(this);
		bt_prev.addActionListener(this);

		setSize(1200, 850);
		setVisible(true);

		thread = new Thread(this);
		thread.start();

		init();
		getEmp();
		getMenu();
		getSubMenu();

	}

	// �����ͺ��̽� Ŀ�ؼ� ������
	public void init() {
		manager = DBManager.getInstance();
		con = manager.getConnection();
		System.out.println(con);
	}

	public void getEmp() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from emp where emp_login_id = ?";

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, posWindow.id);
			rs = pstmt.executeQuery();

			rs.next();

			emp = new Emp();

			emp.setEmp_id(rs.getInt("emp_id"));
			emp.setEmp_login_id(rs.getString("emp_login_id"));
			emp.setEmp_login_pw(rs.getString("emp_login_id"));
			emp.setEmp_name(rs.getString("emp_name"));
			emp.setEmp_phone(rs.getString("emp_phone"));
			emp.setEmp_job(rs.getString("emp_job"));
			emp.setEmp_id(rs.getInt("emp_sal"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ���� ��ư�� ��������
	// DB���� ������ ��ư���� �� ����� ������ DB���̸�ŭ ��ư�� ���������
	public void getMenu() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from product_category order by product_category_id asc";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Product_category vo = new Product_category();
				vo.setProduct_category_id(rs.getInt("product_category_id"));
				vo.setProduct_category_name(rs.getString("product_category_name"));

				bigMenu.add(vo);
				// prduct_category_name ��ŭ ��ư ����
				JButton bt = new JButton(vo.getProduct_category_name());
				bt.setBackground(Color.WHITE);
				bt.setPreferredSize(new Dimension(70, 50));
				bt.addActionListener(this);
				System.out.println(bt.getText());
				p_topMenu.add(bt);
				p_topMenu.updateUI();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// �����޴��鿡 �ִ� ��ư�� ��������
	public void getSubMenu() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from product order by product_id asc";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Product dto = new Product();
				dto.setProduct_category_id(rs.getInt("product_category_id"));
				dto.setProduct_id(rs.getInt("product_id"));
				dto.setProduct_name(rs.getString("product_name"));
				dto.setProduct_price(rs.getInt("product_price"));
				
				product_list.add(dto);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// �����޴��� ��ư�� ������ p_component �� �޴��� �߰��ǰ�
	// ��ư ������ �迭�� �ö󰡱�!!! �����ϱ� �����ϱ�
	public void actionPerformed(ActionEvent e) {
		// p_subMenu.removeAll();
		obj = (JButton) e.getSource();
		// p_subMenu.g
		if (obj == bt_pay) { // ��ü��ư
			pay();

		} else if (obj == bt_allDelete) {// ��ü����
			allDelete();

		} else if (obj.getText().equals("coffee") || obj.getText().equals("drink") || obj.getText().equals("bread")) {
			ShowMenu(obj);

		} else if (obj == bt_play) {// �������
			musicStart();

		} else if (obj == bt_stop) {// ���Ǹ��߱�
			musicStop();

		} else if (obj == bt_next) {
			nextMusic();

		} else if (obj == bt_prev) {// ���� ��
			prevMusic();

		} else { // �޴� �ֱ�
			for (int i = 0; i < product_list.size(); i++) {
				if (obj.getText().equals(product_list.get(i).getProduct_name())) {
					InsertMenu(product_list.get(i));
					break;

				}
			}
		}
<<<<<<< HEAD

=======
	}
>>>>>>> b9bfcbd8db5201280628f0730795a35d13cc7e20

	// �迭�� �Ƹ޸�ī�� �ø����!!!!!!!!!!!!!!!!!!
	private void InsertMenu(Product product) {

		System.out.println(product.getProduct_name());

		// dto.setProduct_category_id(product_category_id);

		// product_list.add(product); ?

		ProductPanel productPanel = new ProductPanel(product, this);
		menu_list.add(productPanel);

		p_component.add(productPanel);
		p_component.updateUI();
		Sum();

	}

	public void ShowMenu(JButton obj) {
		p_subMenu.removeAll();
<<<<<<< HEAD
		ArrayList<String> list  =new ArrayList<String>();
		int cnt = 0;
		for(int i=0;i<bigMenu.size();i++){
			if(obj.getText().equals(bigMenu.get(i).getProduct_category_name())){
				int id=bigMenu.get(i).getProduct_category_id();
				for(int a=0;a<product_list.size();a++){
					if(id==product_list.get(a).getProduct_category_id()){
						JButton bt=null;
=======

		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < bigMenu.size(); i++) {
			if (obj.getText().equals(bigMenu.get(i).getProduct_category_name())) {
				int id = bigMenu.get(i).getProduct_category_id();
				for (int a = 0; a < product_list.size(); a++) {
					if (id == product_list.get(a).getProduct_category_id()) {
						JButton bt = null;
>>>>>>> 6d9bebe73a979adab01cc7386414db34e358ea76
						try {
							if (obj.getText().equals("coffee")) {
								System.out.println("Ŀ��");
<<<<<<< HEAD
								ImageIcon icon= new ImageIcon(new URL(coffee[cnt]));
								bt= new JButton(product_list.get(a).getProduct_name(), icon);
						}else if(obj.getText().equals("drink")){
							System.out.println("e");
							ImageIcon icon= new ImageIcon(new URL(drink[cnt]));
							 bt = new JButton(product_list.get(a).getProduct_name(), icon);
						}else if(obj.getText().equals("bread")){
							System.out.println("Q");
							ImageIcon icon= new ImageIcon(new URL(bread[cnt]));
							 bt = new JButton(product_list.get(a).getProduct_name(), icon);
						} }catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
<<<<<<< HEAD

=======
						
						cnt++;
						
=======
								ImageIcon icon = new ImageIcon(new URL(coffee[a]));
								bt = new JButton(icon);
							} else if (obj.getText().equals("drink")) {
								System.out.println("e");
								ImageIcon icon = new ImageIcon(new URL(drink[a]));
								bt = new JButton(icon);
							} else if (obj.getText().equals("bread")) {
								System.out.println("Q");
								ImageIcon icon = new ImageIcon(new URL(bread[a]));
								bt = new JButton(icon);
							}
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

>>>>>>> 6d9bebe73a979adab01cc7386414db34e358ea76
>>>>>>> b9bfcbd8db5201280628f0730795a35d13cc7e20
						System.out.println("�̰Ŵ����� �� ������");

						bt.setBackground(Color.WHITE);
<<<<<<< HEAD
						//bt.setPreferredSize(new Dimension(200,50));
						bt.setPreferredSize(new Dimension(200,200));
						bt.addActionListener(this);
						
=======

						bt.setPreferredSize(new Dimension(200, 50));

>>>>>>> 6d9bebe73a979adab01cc7386414db34e358ea76
						p_subMenu.add(bt);
						p_subMenu.updateUI();
<<<<<<< HEAD
						
						
						
/*						try {
							URL url = new URL("http://localhost:9090/data/" + id + ".jpg");
							image=ImageIO.read(url);
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						can=new Canvas() {
							public void paint(Graphics g) {
								g.drawImage(image, 0, 0, 70,70,this);
							}
							
						};
						can.setPreferredSize(new Dimension(70, 70));
						p_subMenu.add(can);
						p_subMenu.updateUI();*/
						

=======

						/*
						 * try { URL url = new URL("http://localhost:9090/data/"
						 * + id + ".jpg"); image=ImageIO.read(url); } catch
						 * (MalformedURLException e) { // TODO Auto-generated
						 * catch block e.printStackTrace(); } catch (IOException
						 * e) { // TODO Auto-generated catch block
						 * e.printStackTrace(); } can=new Canvas() { public void
						 * paint(Graphics g) { g.drawImage(image, 0, 0,
						 * 70,70,this); }
						 * 
						 * }; can.setPreferredSize(new Dimension(70, 70));
						 * p_subMenu.add(can); p_subMenu.updateUI();
						 */

>>>>>>> b9bfcbd8db5201280628f0730795a35d13cc7e20
					}
				}
			}
		}
	}

<<<<<<< HEAD
	
	//��ü���� ��ư ������ �޴� ��ü����
	public void allDelete(){
		int ans=JOptionPane.showConfirmDialog(this, "��ü����?");
		if(ans==JOptionPane.OK_OPTION){
			total=0;
			
=======
	// ��ü���� ��ư ������ �޴� ��ü����
	public void allDelete() {
		int ans = JOptionPane.showConfirmDialog(this, "��ü����?");
		if (ans == JOptionPane.OK_OPTION) {
			total = 0;
>>>>>>> b9bfcbd8db5201280628f0730795a35d13cc7e20

			menu_list.removeAll(menu_list);
			p_component.removeAll();
			p_component.updateUI();
			la_sum.setText("0");

		}
	}

	public void Sum() {

		int s = 0;
		System.out.println(s);
		for (int i = 0; i < menu_list.size(); i++) {
			String sum = menu_list.get(i).la_total.getText();
			s += Integer.parseInt(sum);

		}
		la_sum.setText(Integer.toString(s));
		System.out.println(s);
		total = s;
	}

	// �����ϱ� ��ư ������ ����DTO �� ����â�� �ѱ��;�.��......
	public void pay() {
		System.out.println("����â����");
		for (int i = 0; i < menu_list.size(); i++) {
			int id = menu_list.get(i).id;
			Orders orders = new Orders();

			orders.setProduct_id(id);
			orders.setOrders_emp_id(emp.getEmp_id());
			orders.setOrders_client_id(order_number);
			orders.setProduct_name(menu_list.get(i).info.getProduct_name());
			orders.setPrice(menu_list.get(i).info.getProduct_price());
			orders_list.add(orders);

		}
		new Payment(orders_list, total, emp);
		order_number++;

		menu_list.removeAll(menu_list);
		p_component.removeAll();
		p_component.updateUI();
		la_sum.setText("0");
		p_sum.updateUI();
		orders_list.removeAll(orders_list);

	}

	public void date() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		String today = (new SimpleDateFormat("yyyy-MM-dd HH�� mm�� ss��").format(date));

		la_date.setText(today);
		la_date.setFont(new Font("����", Font.BOLD, 25));
		p_date.updateUI();

	}

	// �ð�
	public void run() {
		while (true) {
			date();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

<<<<<<< HEAD
	
=======
>>>>>>> b9bfcbd8db5201280628f0730795a35d13cc7e20
	public void itemStateChanged(ItemEvent e) {
		if (cb_repeat_one.getState()) {
			repeat_all_flag = false;
			repeat_one_flag = true;
			System.out.println("��");
		} else if (cb_repeat_all.getState()) {
			repeat_all_flag = true;
			repeat_one_flag = false;
			System.out.println("��");
		}
	}

	/*---------------------------
				���Ǻκ�
	----------------------------*/

	// üũ�ڽ� �ݺ����ο� ���� �ٸ��� �ൿ�ϰ� �غ���~!
	public void repeat_one() {
		music.mediaPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				music.mediaPlayer.stop();
				System.out.println("������? 1?");
				musicStart();
				System.out.println("1!");
				return;
			}
		});
	}

	public void repeat_all() {
		music.mediaPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				System.out.println("��?");
				nextMusic();
				System.out.println("��!");
				return;
			}
		});
	}

	// ����
	public void musicStart() {

		System.out.println("���");
		if (music == null) {
			music = new MusicThread(url);
			music.run();
		} else {
			music.mediaPlayer.setStartTime(music.mediaPlayer.getStartTime());
			System.out.println("�����ִ�?");
			music.mediaPlayer.play();
			count++;
		}
		if (repeat_all_flag && !repeat_one_flag) {// ����ݺ��̶��,
			repeat_all();
		} else {// �Ѱ�ݺ��̶��,
			repeat_one();
		}
		System.out.println(count);

	}

	public void musicStop() {
		System.out.println("�����");
		Duration current = music.mediaPlayer.getCurrentTime();
		music.mediaPlayer.pause();
		music.mediaPlayer.setStartTime(current);
	}

	public void nextMusic() {
		music.mediaPlayer.stop();
		int num = music.i;
		if (num == url.length - 1) {
			num = -1;
		}
		num++;
		music.next(num);

<<<<<<< HEAD
		
		if(repeat_all_flag&&!repeat_one_flag){//����ݺ��̶��,
=======
		if (repeat_all_flag && !repeat_one_flag) {// ����ݺ��̶��,
>>>>>>> b9bfcbd8db5201280628f0730795a35d13cc7e20
			repeat_all();
		} else {// �Ѱ�ݺ��̶��,
			repeat_one();
		}

<<<<<<< HEAD
		

=======
>>>>>>> b9bfcbd8db5201280628f0730795a35d13cc7e20
	}

	public void prevMusic() {
		music.mediaPlayer.stop();
		num = music.i;
		if (num == 0) {
			num = url.length;
		}
		num--;
		music.next(num);

		if (repeat_all_flag && !repeat_one_flag) {// ����ݺ��̶��,
			repeat_all();
		} else {// �Ѱ�ݺ��̶��,
			repeat_one();
		}
	}

<<<<<<< HEAD

	//��ϰ�������
	public void getList(){
		FileChooser chooser=new FileChooser();
=======
	// ��ϰ�������
	public void getList() {
		FileChooser chooser = new FileChooser();
>>>>>>> b9bfcbd8db5201280628f0730795a35d13cc7e20
		chooser.getExtensionFilters().add(new ExtensionFilter("*.mp3"));
		File file = chooser.showOpenDialog(null);
		String path = file.getAbsolutePath();
		path = path.replace("\\", "/");
		// media

	}

}