package sg.edu.nus.iss.mobilelusis.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PurchaseOrder implements Parcelable {
	
	private int id = 0;
	private String supplier = null;
	private String expDate = null;
	private String status = "Pending";
//	private int quantity = 0;
	
	
	public PurchaseOrder() {
		super();
		supplier = "";
		expDate = "11-Sep-2014";
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getSupplier() {
		return supplier;
	}


	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getExpDate() {
		return expDate;
	}


	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(supplier);
		dest.writeString(expDate);
		dest.writeString(status);

		
	}
	
	public static final Parcelable.Creator<PurchaseOrder> CREATOR = new Creator<PurchaseOrder>() {  
		 public PurchaseOrder createFromParcel(Parcel source) {  
			 PurchaseOrder po = new PurchaseOrder();  
			 po.id = source.readInt();
			 po.supplier = source.readString();
			 po.expDate = source.readString();
			 po.status = source.readString();
		     return po;  
		 }  
		 
		 public PurchaseOrder [] newArray(int size) {  
		     return new PurchaseOrder[size];  
		 }  
	};
	
}
