package card;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import dto.Card;

public class CardMouseAdapter  extends MouseAdapter {
	Card card;CardListMain main;
	
	public CardMouseAdapter(Card card, CardListMain main) {
		this.card = card;
		this.main = main;
	}
	
	public void mouseClicked(MouseEvent e) {
		int result = JOptionPane.showConfirmDialog(main, card.getCard_companyname() + "를 삭제하시겠습니까?", "카드 삭제", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		/*JOptionPane pane = new JOptionPane(card.getCard_companyname() + "를 삭제하시겠습니까?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
		int result = pane.getOptionType();*/
		
		if(result == JOptionPane.OK_OPTION) {
			//삭제하기
			CardThread thread = new CardThread(card, main, "delete");
			thread.start();
		}
	}
}
