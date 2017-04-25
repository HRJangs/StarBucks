package goods;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import db.DBManager;
import dto.Goods;
import dto.Orders;
import dto.Recipe;

public class GoodsMain /*extends JFrame implements ActionListener*/{
	JButton bt_complete;
	DBManager manager;
	Connection con;
	Orders dto;
	Recipe recipe;//해당 상품의 레시피 겟

	//기존 재고량
	int milk_ori;
	int coffee_ori;
	int honeybread_ori;
	int muffin_ori;
	int cake_ori;
	int apple_ori;
	int orange_ori;
	
	//재고 사용량
	int milk_use;
	int coffee_use;
	int honeybread_use;
	int muffin_use;
	int cake_use;
	int apple_use;
	int orange_use;
	
	//업데이트할 재고량
	

	public GoodsMain(Orders dto) {
		this.dto=dto;
		//bt_complete = new JButton("전달완료");
		
		//리스너 부착
		//bt_complete.addActionListener(this);
		
		//add(bt_complete);
		
		
		//setLocationRelativeTo(null);
		//setSize(100,100);
		//setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		//접속
		init();
		
		//해당제품 레시피 알기
		getRecipe();
				
		//재고량 확인
		getGoods();
	
	}
	public void init(){
		manager = DBManager.getInstance();
		con = manager.getConnection();
	}
	
	//제품명 알아내서 레시피 겟하기!
	public void getRecipe(){
			
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		int product_id=dto.getProduct_id();
		
		String sql = "select * from recipe where product_id="+product_id;
	
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			rs.next();
			recipe = new Recipe();
			recipe.setProduct_id(rs.getInt("product_id"));
			recipe.setMilk(rs.getInt("milk"));
			recipe.setCoffee(rs.getInt("coffee"));
			recipe.setHoneybread(rs.getInt("honeybread"));
			recipe.setMuffin(rs.getInt("muffin"));
			recipe.setCake(rs.getInt("cake"));
			recipe.setApple(rs.getInt("apple"));
			recipe.setOrange(rs.getInt("orange"));
			
			//System.out.println(recipe.getOrange());
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void getGoods(){
		milk_use = recipe.getMilk();
		coffee_use = recipe.getCoffee();
		honeybread_use = recipe.getHoneybread();
		muffin_use = recipe.getMuffin();
		cake_use = recipe.getCake();
		apple_use = recipe.getApple();
		orange_use = recipe.getOrange();
		
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		String sql="select * from goods";
		
		try {
			pstmt= con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				Goods goods = new Goods();
				goods.setGoods_id(rs.getInt("goods_id"));
				goods.setGoods_name(rs.getString("goods_name"));
				goods.setGoods_quantity(rs.getInt("goods_quantity"));
				goods.setGoods_company(rs.getString("goods_company"));
//				System.out.println(goods.getGoods_name());
				
				if(goods.getGoods_name().equals("milk")){
					milk_ori=goods.getGoods_quantity();
				}else if(goods.getGoods_name().equals("coffee")){
					coffee_ori=goods.getGoods_quantity();
				}else if(goods.getGoods_name().equals("honeybread")){
					honeybread_ori=goods.getGoods_quantity();
				}else if(goods.getGoods_name().equals("muffin")){
					muffin_ori=goods.getGoods_quantity();
				}else if(goods.getGoods_name().equals("cake")){
					cake_ori=goods.getGoods_quantity();
				}else if(goods.getGoods_name().equals("apple")){
					apple_ori=goods.getGoods_quantity();
				}else if(goods.getGoods_name().equals("orange")){
					orange_ori=goods.getGoods_quantity();
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void updateGoods(){
		//계산하기
		int milk_result=milk_ori-milk_use;
		int coffee_result=coffee_ori-coffee_use;
		int honeybread_result=honeybread_ori-honeybread_use;
		int muffin_result=muffin_ori-muffin_use;
		int cake_result=cake_ori-cake_use;
		int apple_result=apple_ori-apple_use;
		int orange_result=orange_ori-orange_use;
		
		PreparedStatement pstmt= null;
		ResultSet rs = null;	
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("update goods set goods_quantity= case when goods_name='milk' then "+milk_result);
		sb.append(" when goods_name='coffee' then "+coffee_result);
		sb.append(" when goods_name='honeybread' then "+honeybread_result);
		sb.append(" when goods_name='muffin' then "+muffin_result);
		sb.append(" when goods_name='cake' then "+cake_result);
		sb.append(" when goods_name='apple' then "+apple_result);
		sb.append(" when goods_name='orange' then "+orange_result);
		sb.append(" end where goods_name in ('milk','coffee','honeybread','muffin','cake','apple','orange')");
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			int result=pstmt.executeUpdate();
			System.out.println(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	/*
	//레시피 읽어서 재고 털기
	public void complete(){
		//해당제품 레시피 알기
		getRecipe();
		
		//재고량 확인
		getGoods();
		
		
		int result = JOptionPane.showConfirmDialog(this, "재고처리완료");
		
		if(result == JOptionPane.OK_OPTION){
			updateGoods();
			//System.exit(0);
		}
		
	}
	//클릭시
	public void actionPerformed(ActionEvent e) {
		complete();
		
	}*/
	

}
