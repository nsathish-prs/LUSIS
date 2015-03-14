/*
 * @author - Sanny
 * @Latest Updated Date -  10 Sep 2014
 * @filename - Department.java
 */
package sg.edu.nus.iss.mobilelusis.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Department implements Parcelable{

	private String DeptCode;
	private String Name;
	private String ContactPerson;
	private String PhoneNumber;
	private String FaxNumber;
	private String DeptHead;
	private String CollectionPoint;
	private String RepresentativeName;
	private String DisbDate;
	private String DisbStatus;
	
	public String getDeptCode() {
		return DeptCode;
	}
	public void setDeptCode(String deptCode) {
		DeptCode = deptCode;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getContactPerson() {
		return ContactPerson;
	}
	public void setContactPerson(String contactPerson) {
		ContactPerson = contactPerson;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getFaxNumber() {
		return FaxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		FaxNumber = faxNumber;
	}
	public String getDeptHead() {
		return DeptHead;
	}
	public void setDeptHead(String deptHead) {
		DeptHead = deptHead;
	}
	public String getCollectionPoint() {
		return CollectionPoint;
	}
	public void setCollectionPoint(String collectionPoint) {
		CollectionPoint = collectionPoint;
	}
	public String getRepresentativeName() {
		return RepresentativeName;
	}
	public void setRepresentativeName(String representativeName) {
		RepresentativeName = representativeName;
	}
	
	public String getDisbDate() {
		return DisbDate;
	}
	public void setDisbDate(String disbDate) {
		DisbDate = disbDate;
	}

	public String getDisbStatus() {
		return DisbStatus;
	}
	public void setDisbStatus(String disbStatus) {
		DisbStatus = disbStatus;
	}
	
	public Department() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[ DeptCode : "+DeptCode+" ]";
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
		dest.writeString(Name);
		dest.writeString(CollectionPoint);
		dest.writeString(RepresentativeName);
		dest.writeString(DisbDate);
		dest.writeString(DisbStatus);
	}
	
	

	public static final Parcelable.Creator<Department> CREATOR = new Parcelable.Creator<Department>() {

		public Department createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			Department d = new Department();
			d.DeptCode = source.readString();
			d.Name = source.readString();
			d.CollectionPoint = source.readString();
			d.RepresentativeName = source.readString();
			d.DisbDate = source.readString();
			d.DisbStatus = source.readString();
			return d;
		}

		public Department[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Department[size];
		}
		 
	};
}
