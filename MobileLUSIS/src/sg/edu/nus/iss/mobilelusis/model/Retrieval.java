/*
 * @author - Sanny
 * @Latest Updated Date -  10 Sep 2014
 * @filename - Retrieval.java
 */
package sg.edu.nus.iss.mobilelusis.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Retrieval implements Parcelable{
	
	
	private String RetrievalID = null;
	private String ItemCode = null;
	private String ItemDesc = null;
	private String ItemBinID = null;
	private String Avaliability = null;
	private String RequestedQty = null;
	private String RetrievedQty = null;
	private String Unit = null;
	private String status = null;
	private ArrayList<RetrievalByDept> retrievalByDeptList;
	private ArrayList<Disbursement> DisbusementList;
	
	

	public Retrieval() {
		// TODO Auto-generated constructor stub
		retrievalByDeptList = new ArrayList<RetrievalByDept>();
		DisbusementList = new ArrayList<Disbursement>();
	}
	
	public Retrieval(String retrievalID,String itemCode) {
		// TODO Auto-generated constructor stub
		this.RetrievalID = retrievalID;
		this.ItemCode = itemCode;
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
	
	
	public String getItemDesc() {
		return ItemDesc;
	}

	public void setItemDesc(String itemDesc) {
		ItemDesc = itemDesc;
	}

	public String getItemBinID() {
		return ItemBinID;
	}

	public void setItemBinID(String itemBinID) {
		this.ItemBinID = itemBinID;
	}

	public String getAvaliability() {
		return Avaliability;
	}

	public void setAvaliability(String avaliability) {
		Avaliability = avaliability;
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

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

	public ArrayList<RetrievalByDept> getRetrievalByDeptList() {
		return retrievalByDeptList;
	}

	public void setRetrievalByDeptList(ArrayList<RetrievalByDept> retrievalByDeptList) {
		this.retrievalByDeptList = retrievalByDeptList;
	}
	
	public ArrayList<Disbursement> getDisbusementList() {
		return DisbusementList;
	}

	public void setDisbusementList(ArrayList<Disbursement> disbusementList) {
		DisbusementList = disbusementList;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return "[ Retreival ID : "+ RetrievalID + " Retreiavl by Dept : "+retrievalByDeptList+" Disbursement List : "+DisbusementList+" ]";
	}
	
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
//		dest.writeString(this.RetrievalID);
		dest.writeString(this.ItemBinID);
		dest.writeString(this.ItemCode);
		dest.writeString(this.ItemDesc);
		dest.writeString(this.Avaliability);
		dest.writeString(this.RequestedQty);
		dest.writeString(this.RetrievedQty);
		dest.writeString(this.Unit);
		dest.writeString(this.status);
		dest.writeTypedList(this.retrievalByDeptList);
		dest.writeTypedList(this.DisbusementList);
		
	}
	
	public static final Parcelable.Creator<Retrieval> CREATOR = new Parcelable.Creator<Retrieval>() {

		public Retrieval createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			Retrieval r = new Retrieval();
			
//			r.RetrievalID = source.readString();
			r.ItemBinID = source.readString();
			r.ItemCode = source.readString();
			r.ItemDesc = source.readString();
			r.Avaliability = source.readString();
			r.RequestedQty = source.readString();
			r.RetrievedQty = source.readString();
			r.Unit = source.readString();
			r.status = source.readString();
			source.readTypedList(r.retrievalByDeptList, RetrievalByDept.CREATOR);
			source.readTypedList(r.DisbusementList,Disbursement.CREATOR);
		    
			
			return r;
		}

		public Retrieval[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Retrieval[size];
		}
		 
	};
	
	
	
}
