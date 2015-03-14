package sg.edu.nus.iss.mobilelusis.dept.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sg.edu.nus.iss.mobilelusis.dept.activity.SetDelegateActivity;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.IJSONConstants;
import sg.edu.nus.iss.mobilelusis.utils.JSONHandler;
import sg.edu.nus.iss.mobilelusis.utils.JSONParser;
import android.util.Log;

public class SetDelegateController {

	
	private Employee deptHead = null;
	private SetDelegateActivity activity;
	
	// Note that these employees CAN be null
	private Employee delegate = null;
	
	private List<Employee> employeeList = null;
	
	private DatePickerController dpController = null;
	
	public SetDelegateController(SetDelegateActivity activity, Employee deptHead) {
		super();		
		this.activity = activity;
		this.deptHead = deptHead;
		dpController = new DatePickerController(activity);
		employeeList = new ArrayList<Employee>();
	}
	
	
	public boolean getInitialDelegates() {
		boolean result = false;
		
		String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_GET_DELEGATE;
		Log.i("URI ChangeDelegateDetails", uri);
		String url = addGetDeptDetailsParamsToUrl(uri, deptHead.getDeptCode());
		Log.i("URLChangeDelegateDetails", url);
		JSONObject returnObject = JSONParser.getJSONFromUrl(url);		

		if (returnObject != null) {
			
			try {

				String startDateString = IConstants.EMPTY; 
				if (!returnObject.isNull(IConstants.DELEGATE_START_DATE)) {
					startDateString = returnObject.getString(IConstants.DELEGATE_START_DATE);
					dpController.setDate(startDateString, true);
				} 
				
				String endDateString = IConstants.EMPTY; 
				if (!returnObject.isNull(IConstants.DELEGATE_START_DATE)) {
					endDateString = returnObject.getString(IConstants.DELEGATE_END_DATE);
					dpController.setDate(endDateString, false);
				}
				
				if (!returnObject.isNull(IConstants.DELEGATE_PREV_DELEGATE)) {
					if (!returnObject.isNull(IConstants.DELEGATE_PREV_DELEGATE)) {
						JSONObject emp = returnObject.getJSONObject(IConstants.DELEGATE_PREV_DELEGATE);
						delegate = JSONHandler.unparseEmployee(deptHead, emp);						
					} else {
						delegate = null;
					}
				}
				
				employeeList.clear();
				if (!returnObject.isNull(IConstants.DELEGATE_EMP_LIST)) {
					JSONArray array = returnObject.getJSONArray(IConstants.DELEGATE_EMP_LIST);
					if (array != null) {
						for (int i = 0; i < array.length(); i++) {
							JSONObject emp = array.getJSONObject(i);
							Employee employee = JSONHandler.unparseEmployee(deptHead, emp);
		        			//Employee employee = getEmployeeFromJSON(emp);
		        			employeeList.add(employee);							
						}
					}
				}				
				result = true;
			} catch (JSONException jsone) {
				jsone.printStackTrace();
				result = false;
			}
		} 
		return result;
	}
	
//	private Employee getEmployeeFromJSON(JSONObject emp) throws JSONException {
//		String addRole = emp.getString(IConstants.ADD_ROLE);
//		String dept = emp.getString(IConstants.DEPT);
//		String departmentCode = emp.getString(IConstants.DEPT_CODE);
//		String empId = emp.getString(IConstants.EMPID);
//		String name = emp.getString(IConstants.EMPLOYEE_NAME);
//		String pwd = emp.getString(IConstants.PASSWORD);
//		boolean responseStatus = emp.getBoolean(IConstants.RESPONSE_STATUS);
//		String role = emp.getString(IConstants.ROLE);        			
//		return new Employee(dept, departmentCode, empId, name, role, addRole, responseStatus, pwd);		
//	}
	
	
	private String addGetDeptDetailsParamsToUrl(String url, String deptCode) {		
		if(!url.endsWith("?"))
			url += "?";
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(IJSONConstants.REQUEST_DELEGATE_GET_DEPT_CODE, deptCode));	    
		String paramString = URLEncodedUtils.format(params, "utf-8");
		url += paramString;
		return url;
	}

	public boolean changeDelegate(boolean isClearDelegate) {

		Calendar startDate = dpController.getStartDate();
		Calendar endDate = dpController.getEndDate();
		
		String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_UPDATE_DELEGATE;
		Log.i("URI SetDelegateDetails", uri);

		// Create object
		JSONObject obj = JSONHandler.createUpdateDelegateObject(deptHead.getDeptCode(), isClearDelegate, delegate, startDate, endDate, employeeList);
		
		// Post new delete
		String data = JSONParser.postStream(uri, obj.toString());
		boolean result = false;
		if (data != null) {
			data = data.replace("\n", "").replace("\r", "");
			result = Boolean.valueOf(data);			
		}
		return result;
	}


	public Employee getDeptHead() {
		return this.deptHead;
	}


	public void setDelegate(Employee newDelegate) {
		this.delegate = newDelegate;
	}

	public DatePickerController getDatePickerController() {		
		return this.dpController;
	}


	public String getCurrentDelegateName() {
		if (delegate != null) {
			return delegate.getName();
		}
		return IConstants.EMPTY_DELEGATE;
	}
	
}
