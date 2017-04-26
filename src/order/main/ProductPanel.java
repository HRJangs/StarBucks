package order.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dto.Product;

public class ProductPanel extends JPanel{
	Product info;
	JLabel la_name;
	JLabel la_number;
	JLabel la_total;
	OrderMain orderMain;
	int id;
	
	
	public ProductPanel(Product info,OrderMain orderMain) {
		this.id=info.getProduct_id();
		this.info=info;
		this.orderMain=orderMain;
		this.setLayout(new BorderLayout());
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				deleteMenu();
			}
			
		});
		 
		//라벨 두개로 나눠서 합계부분라벨 따로 두기 
		la_name=new JLabel(info.getProduct_name());
		la_name.setPreferredSize(new Dimension(200, 50));
		la_name.setFont(new Font("돋움", Font.BOLD , 20 ));
		//la_name.setBackground(Color.BLACK);
		
		la_number=new JLabel("1");
		la_number.setPreferredSize(new Dimension(100, 50));
		la_number.setFont(new Font("돋움", Font.BOLD , 20 ));
		
		
		la_total=new JLabel(Integer.toString(info.getProduct_price()));
		la_total.setPreferredSize(new Dimension(100, 50));
		la_total.setFont(new Font("돋움", Font.BOLD , 20 ));
		
		add(la_name,BorderLayout.WEST);
		add(la_number,BorderLayout.CENTER);
		add(la_total,BorderLayout.EAST);

		//setPreferredSize(new Dimension(400,350));		
	}

	
	public void deleteMenu(){
		int ans=JOptionPane.showConfirmDialog(orderMain.p_component,"삭제?");
		
		if(ans==JOptionPane.OK_OPTION){
			orderMain.menu_list.remove(this); //메모리 상의 명단에서 지우기
			orderMain.p_component.remove(this);//디자인 존재 지우기
			orderMain.p_component.updateUI();
			orderMain.Sum();
			
			/*			
			int index=0;
			int length=orderMain.product_list.size();
			
			System.out.println("현재 갯수는 "+length);
			
			for(int i=0; i <length;i++){
				if(orderMain.menu_list.get(i)==this){
					index=i;					
				}
			}
			System.out.println(index+" 를 지울께요~~");
			try{
				orderMain.menu_list.remove(index);
			}catch(Exception e){
				System.out.println("에러발생 후 지우려는 index는 "+index);	
			}
 			*/			
		}
		
		
		System.out.println(orderMain.menu_list.size());
		
	
		
	}
}
