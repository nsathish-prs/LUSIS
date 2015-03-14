package sg.edu.nus.iss.mobilelusis.model;

public class PurchaseOrderDetail {
	
	private String description = null;
	private String item = null;
	private int minQty = 0;
	private int ordQty = 0;
	private String orderDate = null;
	private String supplier = null;
	

	public PurchaseOrderDetail(String description, String item, int minQty,
			int ordQty, String orderDate, String supplier) {
		super();
		this.description = description;
		this.item = item;
		this.minQty = minQty;
		this.ordQty = ordQty;
		this.orderDate = orderDate;
		this.supplier = supplier;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getItem() {
		return item;
	}


	public void setItem(String item) {
		this.item = item;
	}


	public int getMinQty() {
		return minQty;
	}


	public void setMinQty(int minQty) {
		this.minQty = minQty;
	}


	public int getOrdQty() {
		return ordQty;
	}


	public void setOrdQty(int ordQty) {
		this.ordQty = ordQty;
	}


	public String getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}


	public String getSupplier() {
		return supplier;
	}


	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	

	

}
