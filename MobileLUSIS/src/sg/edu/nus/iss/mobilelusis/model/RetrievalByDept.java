/*
 * @author - Sanny
 * @Latest Updated Date -  10 Sep 2014
 * @filename - RetrievalByDept.java
 */
package sg.edu.nus.iss.mobilelusis.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RetrievalByDept implements Parcelable{
	
	private String RetrievalID;
	private String ItemCode;
	private String DeptCode;
	private String DeptDesc;
	private String RequestedQty;
	private String RetrievedQty;
	
	public RetrievalByDept() {
		// TODO Auto-generated constructor stub
	}

	public String getRetrievalID() {
		return RetrievalID;
	}

	public void setRetrievalID(String retrievalID) {
		RetrievalID = retrievalID;
	}

	public String getItemCode() {
		return ItemCode;
	}

	public void setItemCode(String itemCode) {
		ItemCode = itemCode;
	}

	public String getDeptCode() {
		return DeptCode;
	}

	public void setDeptCode(String deptCode) {
		DeptCode = deptCode;
	}

	public String getDeptDesc() {
		return DeptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		DeptDesc = deptDesc;
	}

	public String getRequestedQty() {
		return RequestedQty;
	}

	public void setRequestedQty(String requestedQty) {
		RequestedQty = requestedQty;
	}

	public String getRetrievedQty() {
		return RetrievedQty;
	}

	public void setRetrievedQty(String retrievedQty) {
		RetrievedQty = retrievedQty;
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(RetrievalID);
		dest.writeString(DeptCode);
		dest.writeString(DeptDesc);
		dest.writeString(ItemCode);
		dest.writeString(RequestedQty);
		dest.writeString(RetrievedQty);
	}
	
	public static final Parcelable.Creator<RetrievalByDept> CREATOR = new Parcelable.Creator<RetrievalByDept>() {

		public RetrievalByDept createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			RetrievalByDept obj = new RetrievalByDept();
			
			obj.RetrievalID = source.readString();
			obj.DeptCode = source.readString();
			obj.DeptDesc = source.readString();
			obj.ItemCode = source.readString();
			obj.RequestedQty = source.readString();
			obj.RetrievedQty = source.readString();
			
			return obj;
		}

		public RetrievalByDept[] newArray(int size) {
			// TODO Auto-generated method stub
			return new RetrievalByDept[size];
		}
		 
	};
	
	
	public String toString() {
		
		return "[RequestedQty :"+RequestedQty+" , RetrievedQty : "+RetrievedQty+"]";
		
	};
	
	
	
	
	

}
