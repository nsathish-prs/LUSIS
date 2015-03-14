package sg.edu.nus.iss.mobilelusis.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VoucherItem implements Parcelable {

	private String description = null;
	private String item = null;
	private int qty = 0;
	private String reason = null;
	private String status = null;
	private int discrepancyId = -1;

	
	public VoucherItem(String description, String item, int qty, String reason,
			String status, int discrepancyId) {
		super();
		this.description = description;
		this.item = item;
		this.qty = qty;
		this.reason = reason;
		this.status = status;
		this.discrepancyId = discrepancyId;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getDescription() {
		return description;
	}



	public String getItem() {
		return item;
	}



	public int getQty() {
		return qty;
	}



	public String getReason() {
		return reason;
	}

	
	public int getDiscrepancyId() {
		return this.discrepancyId;
	}


	@Override
	public int describeContents() {
		return 0;
	}



	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(description);
		dest.writeString(item);
		dest.writeInt(qty);
		dest.writeString(reason);
		dest.writeString(status);		
		dest.writeInt(discrepancyId);
	}
	
	public static final Parcelable.Creator<VoucherItem> CREATOR = new Creator<VoucherItem>() {  
		 public VoucherItem createFromParcel(Parcel source) {  
			 
			 String description = source.readString();
			 String item = source.readString();
			 int qty = source.readInt();
			 String reason = source.readString();
			 String status = source.readString();
			 int discrepancyId = source.readInt();
			 VoucherItem voucherItem = new VoucherItem(description, item, qty, reason, status, discrepancyId);
		     return voucherItem;  
		 }  
		 
		 public VoucherItem[] newArray(int size) {  
		     return new VoucherItem[size];  
		 }  
	};
	

}
