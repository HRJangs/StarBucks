package pos;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import dto.Member;
import pos.login.PosWindow;

public class AdminPage extends JPanel{
	
	JPanel p_home,p_south;
	JTabbedPane tabbedPane;
	PosWindow posWindow;
	BufferedImage bufferedImage;
	JLabel la_south;
	Canvas can;
	public AdminPage(PosWindow posWindow) {
		getImage();
		this.posWindow = posWindow;
		can = new Canvas(){
			public void paint(Graphics g) {
				g.drawImage(bufferedImage, 0, 0, this);
			}
		};
		la_south = new JLabel(posWindow.id+"님 안녕하세요? 관리페이지입니다");
		la_south.setFont(new Font("돋움", Font.BOLD, 15));
		tabbedPane = new JTabbedPane();
		
		p_home = new JPanel();
		p_south = new JPanel();
		p_home.setLayout(new BorderLayout());
		p_home.setBackground(Color.white);
		p_home.add(can);
		p_south.add(la_south);
		p_south.setBackground(Color.black);
		la_south.setForeground(Color.white);
		p_home.add(p_south,BorderLayout.SOUTH);
		tabbedPane.addTab("홈",p_home);
		tabbedPane.setBackground(new Color(0, 115, 67));
		tabbedPane.setForeground(new Color(255, 255, 255));
		tabbedPane.setBorder(null);
		UIManager.put("TabbedPane.selected", Color.white);
		this.setBackground(Color.white);
		tabbedPane.addTab("직원관리",new EmpPanel() );
		tabbedPane.addTab("회원관리",new MemberPanel());
		tabbedPane.addTab("매출관리",new SalesPanel());
		tabbedPane.addTab("재고관리",new GoodsPanel());
		tabbedPane.addTab("레시피관리",new RecipePanel());
		tabbedPane.addTab("공지사항관리",new BoardPanel(posWindow));
		//tabbedPane.add("예약관리",new ReservationPanel(new Member()));
		tabbedPane.addTab("쿠폰관리", new CouponPanel());
		add(tabbedPane);
		
		can.setPreferredSize(new Dimension(800, 600));
		setBackground(Color.white);
		setVisible(true);
		setSize(800,800);
	}
	public void getImage(){
		URL url;
		try {
			url = new URL("http://localhost:9090/data/home_img.jpg");
			bufferedImage = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
}