package dto;

import java.sql.Timestamp;

public class Orders {
	private int orders_id=1;
	private int product_id=3;
	private Timestamp orders_date=new Timestamp(1492040411581L);
	private int orders_emp_id=2;
	private int orders_client_id=1;
	private String orders_status="ready"; //0이 준비중
	private String orders_payment_type;
	private String orders_type="offline";
	private String product_name;
	private int price;
	
	public int getOrders_id() {
		return orders_id;
	}
	public void setOrders_id(int orders_id) {
		this.orders_id = orders_id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public Timestamp getOrders_date() {
		return orders_date;
	}
	public void setOrders_date(Timestamp orders_date) {
		this.orders_date = orders_date;
	}
	public int getOrders_emp_id() {
		return orders_emp_id;
	}
	public void setOrders_emp_id(int orders_emp_id) {
		this.orders_emp_id = orders_emp_id;
	}
	public int getOrders_client_id() {
		return orders_client_id;
	}
	public void setOrders_client_id(int orders_client_id) {
		this.orders_client_id = orders_client_id;
	}
	public String getOrders_status() {
		return orders_status;
	}
	public void setOrders_status(String orders_status) {
		this.orders_status = orders_status;
	}
	public String getOrders_payment_type() {
		return orders_payment_type;
	}
	public void setOrders_payment_type(String orders_payment_type) {
		this.orders_payment_type = orders_payment_type;
	}
	public String getOrders_type() {
		return orders_type;
	}
	public void setOrders_type(String orders_type) {
		this.orders_type = orders_type;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
	

}
