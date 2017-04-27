package dto;

public class Product {
	private int product_id;
	private int product_category_id;
	private String product_name;
	private int product_price;
	private String product_img;

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getProduct_category_id() {
		return product_category_id;
	}

	public void setProduct_category_id(int product_category_id) {
		this.product_category_id = product_category_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getProduct_price() {
		return product_price;
	}

	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}
	
	public String getProduct_img() {
		return product_img;
	}

	public void setProduct_img(String product_img) {
		this.product_img = product_img;
	}
}
