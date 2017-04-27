package pos.login;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

//이 윈도우는 크기가 결정되어있지 않아야 한다.
//(로그인 폼을 지나고 지나면 그대로커지는 형태) 
//왜? 윈도우 안에 들어오게 될 패널이 그 크기를 결정하게되므로 
//로그인기능일때 작게, 게임 본화면에서는 크게!

public class PosWindow extends JFrame {
	LoginForm loginForm;
	public String id;
	
	JPanel[] page = new JPanel[3];

	public PosWindow() {
		setLayout(new FlowLayout());

		page[0] = new LoginForm(this);
		page[1] = new JPanel();
		page[2] = new JPanel();
		
		add(page[0]);
		add(page[1]);
		add(page[2]);

		setPage(0);

		setVisible(true);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// 윈도우 안에 어떤 패널이 올지를 결정해주는 메서드를 정의해보자
	public void setPage(int index) { // 페이지를 다르게 넘겨받을수있으니 매개변수 재사용가능성이있엉어ㅓ어
										// //넘겨진 index 는 받고 나머지껀 아니야
		for (int i = 0; i < page.length; i++) {
			if (i == index) {
				page[i].setVisible(true);
			} else {
				page[i].setVisible(false);
			}
		}
		pack();// 내용물의 크기만큼 윈도우크기를 설정 (Loginform의 사이즈를 여기서 지정하지않았는데 가져오게끔해보자
				// 내용물을 조정하는 것)
		setLocationRelativeTo(null);// 화면중앙
	}

	public static void main(String[] args) {
		new PosWindow();
	}

}
