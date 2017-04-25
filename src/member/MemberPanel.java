package member;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

public class MemberPanel extends JPanel {
	MemberWindow member;

	public MemberPanel(MemberWindow member) {
		this.member = member;
		setLayout(new BorderLayout());

		setVisible(false); // 최초에 등장안함
		// setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(900, 700));

	}

}
