package order.main;

public class OrderPay {
	private int product_id;  // 아메리카논지 라뗀지 가져오기
	private int orders_emp_id=2; // 2번이나 3번으로 고정
	private int orders_client_id;//0부터 순서대로 증가시키기 
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
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


	
}
