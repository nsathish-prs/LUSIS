/*
 * @author - Sanny
 * @Latest Updated Date -  10 Sep 2014
 * @filename - Disbursement.java
 */
package sg.edu.nus.iss.mobilelusis.model;

import java.util.ArrayList;


import android.os.Parcel;
import android.os.Parcelable;

public class Disbursement implements Parcelable{
	private String DeptCode;
	private String DeptName;
	private String DisbursementID;
	private String EmpID;
	private String RepresentativeName;
	private String status;
	private String CollectionPoint;
	private ArrayList<StationeryItem> StationeryItems;
	private String Date;
	private String Location;
	
	
	
	public String getDisbursementID() {
		return DisbursementID;
	}
	public void setDisbursementID(String disbursementID) {
		DisbursementID = disbursementID;
	}
	public String getRepresentativeName() {
		return RepresentativeName;
	}
	public void setRepresentativeName(String representativeID) {
		RepresentativeName = representativeID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCollectionPoint() {
		return CollectionPoint;
	}
	public void setCollectionPoint(String collectionPoint) {
		CollectionPoint = collectionPoint;
	}
	
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	
	public Disbursement() {
		// TODO Auto-generated constructor stub
		StationeryItems = new ArrayList<StationeryItem>();
	}
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[ Disbursement Collection Point : "+CollectionPoint+" ]";
	}
	
	
	

	public String getDeptCode() {
		return DeptCode;
	}
	public void setDeptCode(String deptCode) {
		DeptCode = deptCode;
	}



	public ArrayList<StationeryItem> getStationeryItems() {
		return StationeryItems;
	}
	public void setStationeryItems(ArrayList<StationeryItem> stationeryItems) {
		StationeryItems = stationeryItems;
	}



	public String getDeptName() {
		return DeptName;
	}
	public void setDeptName(String deptName) {
		DeptName = deptName;
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(DeptCode);
		dest.writeString(DeptName);
		dest.writeString(DisbursementID);
		dest.writeString(RepresentativeName);
		dest.writeString(CollectionPoint);
		dest.writeString(status);
		dest.writeString(Date);
		dest.writeString(Location);
		dest.writeString(EmpID);
		dest.writeTypedList(StationeryItems);
	}

	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}

	public String getEmpID() {
		return EmpID;
	}
	public void setEmpID(String empID) {
		EmpID = empID;
	}

	public static final Parcelable.Creator<Disbursement> CREATOR = new Parcelable.Creator<Disbursement>() {

		public Disbursement createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			Disbursement r = new Disbursement();
			
			r.DeptCode = source.readString();
			r.DeptName = source.readString();
			r.DisbursementID = source.readString();
			r.RepresentativeName = source.readString();
			r.CollectionPoint = source.readString();
			r.status = source.readString();
			r.Date = source.readString();
			r.Location = source.readString();
			r.EmpID = source.readString();
			source.readTypedList(r.StationeryItems, StationeryItem.CREATOR);
			return r;
		}

		public Disbursement[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Disbursement[size];
		}
		 
	};
	
}
