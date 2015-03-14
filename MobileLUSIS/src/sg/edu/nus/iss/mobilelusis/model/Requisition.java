package sg.edu.nus.iss.mobilelusis.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.mobilelusis.utils.IConstants;


import android.os.Parcel;
import android.os.Parcelable;

public class Requisition implements Parcelable, Comparable<Requisition> {
	
	private String reqId = null;
	private Employee employee = null;

	private List<StationeryItem> items = null;
	private String status;
	private String empComments = null;

	private Calendar date = null;
	
	private String rejectionReason = null;
	
	public Requisition(String reqId) {
		this.reqId = reqId;
		items = new ArrayList<StationeryItem>();
		date = Calendar.getInstance();
		date.set(Calendar.HOUR, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
	}

	public Requisition(String reqId, String dateString) {
		this.reqId = reqId;
		items = new ArrayList<StationeryItem>();
		
		try {
			Date d = IConstants.sdf.parse(dateString);
			date = Calendar.getInstance();
			date.setTime(d);
			date.set(Calendar.HOUR, 0);
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.SECOND, 0);
			date.set(Calendar.MILLISECOND, 0);
		} catch (ParseException pe) {
			pe.printStackTrace();
			date = Calendar.getInstance();
			date.set(Calendar.HOUR, 0);
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.SECOND, 0);
			date.set(Calendar.MILLISECOND, 0);
		}
		
	}

	private Calendar getDate() {
		return this.date;
	}
	
	public void addItem(StationeryItem item) {
		items.add(item);
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
		
	}
	
	public String getRequisitionId() {
		return reqId;
	}
	
	public StationeryItem [] getStationeryItems() {
		return items.toArray(new StationeryItem[items.size()]);
	}
	
	public String getEmpComments() {
		return empComments;
	}

	public void setEmpComments(String empComments) {
		this.empComments = empComments;
	}

	

	
	public String getSubmitDate() {
		return IConstants.sdf.format(this.date.getTime()); 
	}

	public void setSubmitDate(String submitDate) {
		try {
			Date d = IConstants.sdf.parse(submitDate);
			date.setTime(d);
			date.set(Calendar.HOUR, 0);
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.SECOND, 0);
			date.set(Calendar.MILLISECOND, 0);			
		} catch (ParseException pe) {
			pe.printStackTrace();
			this.date = Calendar.getInstance();
			date.set(Calendar.HOUR, 0);
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.SECOND, 0);
			date.set(Calendar.MILLISECOND, 0);

		}
//		this.submitDate = submitDate;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	@Override
	public String toString() {
		return this.reqId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(reqId);
		dest.writeParcelable(employee, flags);
		dest.writeArray(items.toArray(new StationeryItem[items.size()]));
		dest.writeString(status);
		String d = IConstants.sdf.format(this.date.getTime());
		dest.writeString(d);
	}
	
	public void clearItems() {
		items.clear();
	}
	
	
	public static final Parcelable.Creator<Requisition> CREATOR = new Creator<Requisition>() {  
		 public Requisition createFromParcel(Parcel source) {  
			 String reqId = source.readString();
			 Requisition requisition = new Requisition(reqId);
			 Employee employee = source.readParcelable(Employee.class.getClassLoader());			 
			 requisition.setEmployee(employee);
			 List<StationeryItem> stationeryItems = source.readArrayList(StationeryItem.class.getClassLoader());
			 requisition.clearItems();
			 for (StationeryItem item : stationeryItems) {
				 requisition.addItem(item);
			 }
			 String status = source.readString();
			 requisition.setStatus(status);
			 
			 String dateStr = source.readString();
			 requisition.setSubmitDate(dateStr);
			 
		     return requisition;  
		 }  
		 
		 public Requisition[] newArray(int size) {  
		     return new Requisition[size];  
		 }  
	};

	@Override
	public int compareTo(Requisition another) {		
		return this.date.compareTo(another.getDate());
	}


}
