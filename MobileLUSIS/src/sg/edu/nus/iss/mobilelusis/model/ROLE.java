package sg.edu.nus.iss.mobilelusis.model;

public enum ROLE {	
	DEPT_HEAD("DeptHead"),
	EMPLOYEE("Employee"),
	REPRESENTATIVE("Representative"),
	CLERK("Clerk"),
	SUPERVISOR("Supervisor"),
	MANAGER("Manager"), 
	DELEGATE("Delegate"), NONE("No role");
	
	private final String name;

	private ROLE(String s) {
	    name = s;
	}
	
	public boolean equalsName(String otherName){
	    return (otherName == null)? false:name.equals(otherName);
	}

	public String toString(){
	   return name;
	}
	
}




