package sg.edu.nus.iss.mobilelusis.model;

import android.os.Parcel;
import android.os.Parcelable;

public class StationeryItem implements Parcelable {
	
	private String itemCode = null;
	private String itemName = null;
	private String itemUnit = null;
	private String category = null;
	private int quantity = 0;
	private String status = null;
	private String itemRequested = null;
	private String itemRetrieved = null;
	private String desbID = null;
	private String reason = null;
	
	public StationeryItem() {
		super();
	}
	
	public StationeryItem(String itemCode, String itemName) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.setReason("");
	}

	public String getItemCode() {
		return itemCode;
	}


	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int string) {
		this.quantity = string;
	}

	public void setCategory(String category) {

		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[Item Request : "+this.itemRequested+" Item Retireved : "+this.itemRetrieved+" ]";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
	public String getItemRequested() {
		return itemRequested;
	}

	public void setItemRequested(String itemRequested) {
		this.itemRequested = itemRequested;
	}

	public String getItemRetrieved() {
		return itemRetrieved;
	}

	public void setItemRetrieved(String itemRetrieved) {
		this.itemRetrieved = itemRetrieved;
	}

	public String getDesbID() {
		return desbID;
	}

	public void setDesbID(String desbID) {
		this.desbID = desbID;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(itemCode);  
		dest.writeString(itemName);  
		dest.writeString(itemUnit);
		dest.writeInt(quantity);
		dest.writeString(status);
		dest.writeString(itemRequested);
		dest.writeString(itemRetrieved);
		dest.writeString(desbID);
		dest.writeString(category);
		
	}

	public static final Parcelable.Creator<StationeryItem> CREATOR = new Creator<StationeryItem>() {  
		 public StationeryItem createFromParcel(Parcel source) {  
		     StationeryItem stationeryItem = new StationeryItem();  
		     stationeryItem.itemCode = source.readString();  
		     stationeryItem.itemName = source.readString();
		     stationeryItem.itemUnit = source.readString();
		     stationeryItem.quantity = source.readInt();
		     stationeryItem.status = source.readString();
		     stationeryItem.itemRequested = source.readString();
		     stationeryItem.itemRetrieved = source.readString();
		     stationeryItem.desbID = source.readString();
		     stationeryItem.category = source.readString();
		     return stationeryItem;  
		 }  
		 
		 public StationeryItem[] newArray(int size) {  
		     return new StationeryItem[size];
		 }  
	};

}
