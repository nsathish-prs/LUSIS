package sg.edu.nus.iss.mobilelusis;

import sg.edu.nus.iss.mobilelusis.model.Employee;

public class Temp{

	private static Employee employee;
	
	public static Employee getEmployee(){
		return employee;
	}
	public static void putEmployee(Employee e){
		employee = e;
	}
	
}
