package sg.edu.nus.iss.mobilelusis.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class DisbursementApproval implements Parcelable {
	
	private String DeptCode = null;
	private Employee EmployeeDetail = null;
	private ArrayList<Disbursement> DisbursementList = null;
	

	public String getDeptCode() {
		return DeptCode;
	}

	public void setDeptCode(String deptCode) {
		DeptCode = deptCode;
	}

	public Employee getEmployeeDetail() {
		return EmployeeDetail;
	}

	public void setEmployeeDetail(Employee employeeDetail) {
		EmployeeDetail = employeeDetail;
	}

	public ArrayList<Disbursement> getDisbursementList() {
		return DisbursementList;
	}

	public void setDisbursementList(ArrayList<Disbursement> disbursementList) {
		DisbursementList = disbursementList;
	}
	
	public DisbursementApproval() {
		// TODO Auto-generated constructor stub
		EmployeeDetail  = new Employee();
		DisbursementList = new ArrayList<Disbursement>();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(this.DeptCode);
		dest.writeParcelable(EmployeeDetail,flags);
		dest.writeTypedList(this.DisbursementList);
	}
	
	public static final Parcelable.Creator<DisbursementApproval> CREATOR = new Parcelable.Creator<DisbursementApproval>() {

		public DisbursementApproval createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			DisbursementApproval da = new DisbursementApproval();
			da.DeptCode = source.readString();
			source.readParcelable(Employee.class.getClassLoader());
			source.readTypedList(da.DisbursementList,Disbursement.CREATOR);
			return da;
		}

		public DisbursementApproval[] newArray(int size) {
			// TODO Auto-generated method stub
			return new DisbursementApproval[size];
		}
		 
	};

}
