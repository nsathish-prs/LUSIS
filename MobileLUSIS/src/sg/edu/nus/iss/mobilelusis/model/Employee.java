package sg.edu.nus.iss.mobilelusis.model;

import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.IJSONConstants;
import android.os.Parcel;
import android.os.Parcelable;

/*
 * @author
 */
public class Employee implements Parcelable {


	private String dept = null;
	private String deptCode = null;
	private String id = null;
	private String name = null;
	private ROLE role;
	private ROLE additionalRole;
	private boolean responseStatus;
	private String password;
	
	public Employee() {
		super();
	}
	

	public Employee(String dept, String deptCode, String id, String name,
			String role, String additionalRole, boolean responseStatus,
			String password) {
		super();
		this.dept = dept;
		this.deptCode = deptCode;
		this.id = id;
		this.name = name;
		setRoleByString(role);
		setAdditionalRole(additionalRole);
		this.responseStatus = responseStatus;
		this.setPassword(password);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ROLE getRole() {
		return role;
	}

	public void setRole(ROLE role) {
		this.role = role;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ROLE getAdditionalRole() {
		return additionalRole;
	}

	public void setAdditionalRole(ROLE additionalRole) {
		this.additionalRole = additionalRole;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.dept);
		
		if (this.additionalRole != null) {
			dest.writeString(this.additionalRole.toString());			
		} else {
			dest.writeString("");
		}

		dest.writeString(this.role.toString());
		dest.writeString(this.name);
		dest.writeString(this.deptCode);
		dest.writeString(this.password);
	}
	
	@Override
	public String toString() {
		return "[Name : "+this.name+" EmpID : "+id+"]";
	}
	
	public static final Parcelable.Creator<Employee> CREATOR = new Creator<Employee>() {  
		 public Employee createFromParcel(Parcel source) {  
			 Employee employee = new Employee();  
			 employee.id = source.readString();
			 employee.setDept(source.readString());
			 String additionalRole = source.readString();
			 employee.setAdditionalRole(additionalRole);			 
			 String role = source.readString();
			 employee.setRoleByString(role);
			 employee.setName(source.readString());			 
			 employee.setDeptCode(source.readString());
			 employee.setPassword(source.readString());
		     return employee;  
		 }  
		 
		 public Employee[] newArray(int size) {  
		     return new Employee[size];  
		 }  
	};

	public void setAdditionalRole(String additionalRole) {
		if (additionalRole == null || additionalRole.equals("") || additionalRole.equalsIgnoreCase("null")) {
			this.additionalRole = ROLE.NONE;
		}
		else if (additionalRole.equalsIgnoreCase(IConstants.DELEGATE)) {
			this.additionalRole = ROLE.DELEGATE;			
		} else if (additionalRole.equalsIgnoreCase(IConstants.REPRESENTATIVE)) {
			this.additionalRole = ROLE.REPRESENTATIVE;
		}
		
	}

	public void setRoleByString(String role) {
		 if (role.equalsIgnoreCase("employee")) {
			 this.role = ROLE.EMPLOYEE;				 
		 } else if (role.equalsIgnoreCase("DeptHead")) {
			 this.role = ROLE.DEPT_HEAD;
		 } else if (role.equalsIgnoreCase("Representative")) {
			 this.role = ROLE.REPRESENTATIVE;
		 } else if (role.equalsIgnoreCase("Clerk")) {
			 this.role = ROLE.CLERK;
		 } else if (role.equalsIgnoreCase("Supervisor")) {
			 this.role = ROLE.SUPERVISOR;
		 } else if (role.equalsIgnoreCase("Manager")) {
			 this.role = ROLE.MANAGER;
		 } else {
			 this.role = ROLE.NONE;
		 }

	}


	public String getAdditionalRoleAsString() {
		return this.additionalRole.toString();
	}


	public String getPassword() {
		return this.password;
	}


	public boolean getReponseStatus() {
		return this.responseStatus;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	

}