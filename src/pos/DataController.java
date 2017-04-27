package pos;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import db.DBManager;
import dto.Product;

public class DataController{
	
	DBManager manager;
	Connection con;
	Vector<Vector> data = new Vector<Vector>();
	Vector<String> columnName = new Vector<String>();
	InsertFrame insertFrame;
	InsertCoupon insertCoupon;
	//InsertMenu insertMenu;
	MyPanel myPanel;
	ArrayList<String> menu= new ArrayList<String>();
	ArrayList<ArrayList> coupon= new ArrayList<ArrayList>();
	
	public DataController(MyPanel mypanel) {
		manager = DBManager.getInstance();
		this.con =manager.getConnection();
		this.myPanel =mypanel;
		getMenu();
	}
	public AbstractTableModel getDataModel(){
		DataModel  model = new DataModel(data, columnName);
		return model;
	}
	
	public void getMenu(){
		String sql ="select product_name from product";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt =con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				menu.add(rs.getString(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void getList(String table_Name){
		data.removeAll(data);
		String sql = "select * from "+table_Name;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try {
			pstmt =  con.prepareStatement(sql);
			rs =pstmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			for(int i=1;i<=meta.getColumnCount();i++){
				columnName.add(meta.getColumnName(i));
			}
			while(rs.next()){
				Vector vec = new Vector();
				for(int i=0;i<meta.getColumnCount();i++){
					vec.add(rs.getString(i+1));
				}
				data.add(vec);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void productList(){
		String sql = "select p.product_id,p.product_name ,p.product_price,r.milk, r.coffee,r.honeybread,r.muffin,r.cake,r.apple,r.orange ,r.caramel,r.chocopowder,r.whitechocopowder,r.mango,r.grape,r.blueberry,r.tomato,r.hanrabong,r.bagle,r.scone,r.roll,r.danish,r.twist,r.triple_bean  from product p INNER JOIN recipe r on p.product_id = r.product_id  group by p.product_id";
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try {
			pstmt =  con.prepareStatement(sql);
			rs =pstmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			for(int i=1;i<=meta.getColumnCount();i++){
				columnName.add(meta.getColumnName(i));
			}
			while(rs.next()){
				Vector vec = new Vector();
				for(int i=0;i<meta.getColumnCount();i++){
					vec.add(rs.getString(i+1));
				}
				data.add(vec);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void addEmp(InsertFrame insertFrame){
		this.insertFrame = insertFrame;
		data.removeAll(data);
		StringBuffer sql = new StringBuffer();
		sql.append("insert into emp(emp_login_id,emp_login_pw,emp_name,emp_phone,emp_job,emp_sal)");
		sql.append("values(?,?,?,?,?,?)");
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		String phone=insertFrame.choice.getSelectedItem()+"-"+insertFrame.t_phone1.getText()+"-"+insertFrame.t_phone2.getText();
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			int row =data.size();
			System.out.println(row);
			pstmt.setString(1,insertFrame.t_id.getText() );
			pstmt.setString(2,insertFrame.t_pw.getText() );
			pstmt.setString(3,insertFrame.t_name.getText() );
			pstmt.setString(4,phone);
			pstmt.setString(5,insertFrame.choice_job.getSelectedItem() );
			pstmt.setString(6,insertFrame.t_sal.getText() );
			rs = pstmt.executeQuery();
			System.out.println("여기까지오니?");
			getList("emp");
			myPanel.table.updateUI();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/*public void addMenu(InsertMenu insertMenu){
		this.insertMenu = insertMenu;
		data.removeAll(data);
		StringBuffer sql = new StringBuffer();
		sql.append("insert into product(emp_login_id,emp_login_pw,emp_name,emp_phone,emp_job,emp_sal)");
		sql.append("values(?,?,?,?,?,?)");
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		String phone=insertFrame.choice.getSelectedItem()+"-"+insertFrame.t_phone1.getText()+"-"+insertFrame.t_phone2.getText();
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			int row =data.size();
			System.out.println(row);
			pstmt.setString(1,insertFrame.t_id.getText() );
			pstmt.setString(2,insertFrame.t_pw.getText() );
			pstmt.setString(3,insertFrame.t_name.getText() );
			pstmt.setString(4,phone);
			pstmt.setString(5,insertFrame.choice_job.getSelectedItem() );
			pstmt.setString(6,insertFrame.t_sal.getText() );
			rs = pstmt.executeQuery();
			System.out.println("여기까지오니?");
			getList("recipe");
			myPanel.table.updateUI();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	*/
	
	public void SearchEmp(){
		String str ="";
		System.out.println(myPanel.choice.getSelectedItem());
		if(myPanel.choice.getSelectedItem().equals("이름")){
			str = "emp_name";
		}else{
			str = "emp_login_id";
		}
		
		String sql="select * from emp where "+str+" = ?";
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		System.out.println(sql);
		System.out.println(myPanel.t_search.getText());
		data.removeAll(data);
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, myPanel.t_search.getText());
			rs =pstmt.executeQuery();
			while(rs.next()){
				Vector vec = new Vector();
				vec.add(rs.getString(1));
				vec.add(rs.getString(2));
				vec.add(rs.getString(3));
				vec.add(rs.getString(4));
				vec.add(rs.getString(5));
				vec.add(rs.getString(6));
				vec.add(rs.getString(7));
				data.add(vec);
			}
			myPanel.table.setModel(getDataModel());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void SearchMember(){
		String str ="";
		System.out.println(myPanel.choice.getSelectedItem());
		if(myPanel.choice.getSelectedItem().equals("이름")){
			str = "member_name";
		}else{
			str = "member_login_id";
		}
		String sql="select * from member where "+str+" = ?";
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		System.out.println(sql);
		System.out.println(myPanel.t_search.getText());
		data.removeAll(data);
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, myPanel.t_search.getText());
			rs =pstmt.executeQuery();
			while(rs.next()){
				Vector vec = new Vector();
				vec.add(rs.getString(1));
				vec.add(rs.getString(2));
				vec.add(rs.getString(3));
				vec.add(rs.getString(4));
				vec.add(rs.getString(5));
				vec.add(rs.getString(6));
				vec.add(rs.getString(7));
				vec.add(rs.getString(8));
				vec.add(rs.getString(9));
				vec.add(rs.getString(10));
				data.add(vec);
			}
			myPanel.table.setModel(getDataModel());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void editTable(DataModel model,TableModelEvent e,String type){
		
		System.out.println("바뀜");
		PreparedStatement pstmt =null;
		int row = e.getFirstRow();
		int col = e.getColumn();
		String data =(String) model.getValueAt(row, col);
		String pk = (String) model.getValueAt(row, 0);
		String column=(String) model.getColumnName(col);
		String sql=null;
		if(type.equals("product")){
			 sql ="update recipe set "+column+" ='"+data+"' where product_id ="+pk;
		}else{
			 sql ="update " +type+" set "+column+" ='"+data+"' where "+type+"_id ="+pk;
		}
		System.out.println(sql);
		try {
			pstmt = con.prepareStatement(sql);
			int result = pstmt.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}finally{
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}	
	}
	
	
	public void addDB(Vector<Vector> data){
		this.data= data;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		String sql2 = "select goods_id from goods";
		String sql= "insert into goods(goods_name,goods_quantity,goods_company) values(?,?,?)";
		String up_sql = "update goods set goods_quantity = ?  where goods_id= ?";
		try {
			pstmt = con.prepareStatement(sql2);
			rs = pstmt.executeQuery();
			int count=0;
			while(rs.next()){
				count++;
			}
			int n =data.size()-count;
			for(int i=0;i<count;i++){
				pstmt = con.prepareStatement(up_sql);
				pstmt.setString(1, (String) data.get(i).get(2));
				pstmt.setString(2, (String) data.get(i).get(0));
				int result = pstmt.executeUpdate();
				if(result==1){
					System.out.println("디비수정성공");
				}
			}
			if(n>0){
				for(int i=count;i<data.size();i++){
					  System.out.println(data.get(i).get(1));	
					  pstmt =con.prepareStatement(sql);
					  pstmt.setString(1, (String) data.get(i).get(1));
					  pstmt.setString(2, (String) data.get(i).get(2));
					  pstmt.setString(3, (String) data.get(i).get(3));
					  pstmt.execute();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public ChartPanel makeChat(String str){
		JFreeChart chart=null;
		StringBuffer sb= new StringBuffer();
		String sql=null;
		String title = "";
		if(str.equals("일별매출액")){
			sql ="select  sum(p.product_price),DATE_FORMAT(s.sales_date,'%Y-%m-%d')  from sales s INNER JOIN product p on s.product_id=p.product_id group by DATE_FORMAT(s.sales_date,'%Y-%m-%d')";
			title="일별 매출액";
		}else if(str.equals("상품판매량")){
			sql = "select count(s.product_id),p.product_name from sales s INNER JOIN product p on s.product_id=p.product_id GROUP BY p.product_name";
			title="상품총판매량";
		}
		PreparedStatement pstmt= null;
		DefaultCategoryDataset dataSet= new DefaultCategoryDataset();
		ResultSet rs =null;
		try {
			pstmt =con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				dataSet.addValue(rs.getInt(1),title, rs.getString(2));
			}
			chart = ChartFactory.createBarChart(title,null, null, dataSet,  PlotOrientation.VERTICAL, true, true, false);
			Font oldtitle = chart.getTitle().getFont();
			chart.getTitle().setFont(new Font("굴림",oldtitle.getStyle(), oldtitle.getSize()));
			Font oldlegend = chart.getLegend().getItemFont();
			chart.getLegend().setItemFont(new Font("굴림",oldlegend.getStyle(), oldlegend.getSize()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}
	
	
	public void createCoupon(InsertCoupon insertCoupon){
		data.removeAll(data);
		coupon.removeAll(coupon);
		this.insertCoupon=insertCoupon;
		String sql = "insert into coupon(product_id,coupon_number,coupon_status) values(?,?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt =  con.prepareStatement(sql);
			pstmt.setString(1,Integer.toString(insertCoupon.choice.getSelectedIndex()+1));
			pstmt.setString(2, insertCoupon.t_num.getText());
			pstmt.setString(3, "create");
			System.out.println("여기서부터");
			System.out.println(insertCoupon.choice.getSelectedIndex());
			System.out.println(insertCoupon.t_num.getText());
			System.out.println("여기");
			pstmt.executeUpdate();
			getList("coupon");
			myPanel.table.updateUI();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void getCoupon(){
		StringBuffer sb = new StringBuffer();
		sb.append("select p.product_name, c.coupon_number ");
		sb.append(" from product p inner join coupon c on c.product_id=p.product_id");
		sb.append(" and  c.coupon_status='create'");
	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt =con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				ArrayList list = new ArrayList();
				list.add(rs.getString(1));
				list.add(rs.getString(2));
				coupon.add(list);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void authorizeCoupon(String id,String coupon){
		data.removeAll(data);
		String sql ="update member set member_coupon = ? where member_login_id=?";
		String sql2 ="update coupon set coupon_status =? where coupon_number=?";
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		ArrayList<String> list2 =new ArrayList<String>();
		try {
			pstmt =con.prepareStatement(sql);
			pstmt.setString(1, coupon);
			pstmt.setString(2, id);
			int result =pstmt.executeUpdate();
			pstmt =con.prepareStatement(sql2);
			pstmt.setString(1, "issued");
			pstmt.setString(2, coupon);
			int result2 =pstmt.executeUpdate();
			if(result==1&&result2==1){
				System.out.println("쿠폰발행완료,수정완료");
			}
			getList("member");
			myPanel.table.updateUI();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addMenu(Product dto){
		String sql = "insert into Product(product_category_id,product_name,product_price,product_image) values(?,?,?,?)";
		String sql2 = "select Product_id from product where product_name='"+dto.getProduct_name()+"'";
		String sql3 = "insert into Recipe(product_id) values(?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String name=null;
		try {
			pstmt =con.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(dto.getProduct_category_id()));
			pstmt.setString(2, dto.getProduct_name());
			pstmt.setString(3, Integer.toString(dto.getProduct_price()));
			pstmt.setString(4, dto.getProduct_img());
			int result = pstmt.executeUpdate();
			//////////////////////////////
			pstmt= con.prepareStatement(sql2);
			rs =  pstmt.executeQuery();
			while(rs.next()){
				name=rs.getString(1);
			}
			/////////////////////////////////
			pstmt=con.prepareStatement(sql3);
			pstmt.setString(1, name);
			int result2= pstmt.executeUpdate();
			//////////////////////////////
			if(result==1&&result2==1){
				System.out.println("등록완료");
			}
			data.removeAll(data);
			productList();
			myPanel.table.updateUI();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
}
