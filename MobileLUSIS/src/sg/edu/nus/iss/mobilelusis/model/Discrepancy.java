/*
 * @author - Sanny
 * @Latest Updated Date -  10 Sep 2014
 * @filename - Discrepancy.java
 */
package sg.edu.nus.iss.mobilelusis.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Discrepancy implements Parcelable {

	private String DescripancyID;
	private String VoucherID;
	private String ItemCode;
	private String Quantity;
	private String Reason;
	private Date Date;
	private String Status;
	private String ApprovedBy;
	private String RaisedBy;
	
	
	public Discrepancy() {
		super();

	}
	
	public String getDescripancyID() {
		return DescripancyID;
	}
	public void setDescripancyID(String descripancyID) {
		DescripancyID = descripancyID;
	}
	public String getVoucherID() {
		return VoucherID;
	}
	public void setVoucherID(String voucherID) {
		VoucherID = voucherID;
	}
	public String getItemCode() {
		return ItemCode;
	}
	public void setItemCode(String itemCode) {
		ItemCode = itemCode;
	}
	public String getQuantity() {
		return Quantity;
	}
	public void setQuantity(String quantity) {
		Quantity = quantity;
	}
	public String getReason() {
		return Reason;
	}
	public void setReason(String reason) {
		Reason = reason;
	}
	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		Date = date;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getApprovedBy() {
		return ApprovedBy;
	}
	public void setApprovedBy(String approvedBy) {
		ApprovedBy = approvedBy;
	}
	
	public String getRaisedBy() {
		return this.RaisedBy;
	}

	public void setRaisedBy(String raisedBy) {
		RaisedBy = raisedBy;
	}

	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[ Item Code : "+ItemCode+" Adj Quantity : "+Quantity+" Reason : "+Reason+"]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(DescripancyID);
		dest.writeString(VoucherID);
		dest.writeString(ItemCode);
		dest.writeString(Quantity);
		dest.writeString(Reason);
		dest.writeLong(Date.getTime());
		dest.writeString(Status);
		if (ApprovedBy == null) {
			dest.writeString("");
		} else {
			dest.writeString(ApprovedBy);
		}
		if (RaisedBy == null) {
			dest.writeString("");
		} else {
			dest.writeString(RaisedBy);			
		}


	}
	
	
	public static final Parcelable.Creator<Discrepancy> CREATOR = new Creator<Discrepancy>() {  
		 public Discrepancy createFromParcel(Parcel source) {  
			 Discrepancy discrepancy = new Discrepancy();
			 
			 discrepancy.setDescripancyID(source.readString());
			 discrepancy.setVoucherID(source.readString());
			 discrepancy.setItemCode(source.readString());
			 discrepancy.setQuantity(source.readString());
			 discrepancy.setReason(source.readString());
			 
			 long time = source.readLong();
			 Date date = new Date(time);			 
			 discrepancy.setDate(date);
			 
			 discrepancy.setStatus(source.readString());
			 discrepancy.setApprovedBy(source.readString());
			 discrepancy.setRaisedBy(source.readString());
					 
		     return discrepancy;  
		 }  
		 
		 public Discrepancy[] newArray(int size) {  
		     return new Discrepancy[size];  
		 }  
	};
	
	
	
}
