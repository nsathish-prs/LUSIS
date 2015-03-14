package sg.edu.nus.iss.mobilelusis.dept.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sg.edu.nus.iss.mobilelusis.model.CollectionPoint;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.IJSONConstants;
import sg.edu.nus.iss.mobilelusis.utils.JSONHandler;
import sg.edu.nus.iss.mobilelusis.utils.JSONParser;
import android.util.Log;

public class DeptDetailsController {

	private Employee originalRepresentative = null;
	private Employee newRepresentative = null;
	private CollectionPoint oldCollectionPoint = null;
	private CollectionPoint newCollectionPoint = null;
	private List<CollectionPoint> collectionPoints = null;

	private List<Employee> employeeList = null;
	
	public DeptDetailsController() {
		super();
		this.collectionPoints = new ArrayList<CollectionPoint>();
		this.employeeList = new ArrayList<Employee>();
	}
	
	
	public boolean getDeptDetails(String deptCode) {
	
		String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_GET_DEPT_DETAILS;
		Log.i("URI ChangeDeptDetails", uri);
		String url = addGetDeptDetailsParamsToUrl(uri, deptCode);		
		Log.i("URLChangeDeptDetails", url);
		JSONObject returnObject = JSONParser.getJSONFromUrl(url);		
        Log.i("View One Req response", returnObject.toString());
        boolean result = false;
        if (returnObject != null) {
        	try {
        		
        		JSONArray collectionPointArray = returnObject.getJSONArray(IJSONConstants.GET_DEPT_DETAILS_COLLECTION_PT);        		
        		for (int i = 0; i < collectionPointArray.length(); i++) {
        			JSONObject cp = collectionPointArray.getJSONObject(i);
        			String desc = "";
        			if (!cp.isNull(IJSONConstants.GET_DEPT_DETAILS_CP_DESC)) {
        				desc = cp.getString(IJSONConstants.GET_DEPT_DETAILS_CP_DESC);
        			}
        			int id = cp.getInt(IJSONConstants.GET_DEPT_DETAILS_CP_ID);
        			String loc = cp.getString(IJSONConstants.GET_DEPT_DETAILS_CP_LOC);
        			CollectionPoint collectionPoint = new CollectionPoint(id, desc, loc);
        			collectionPoints.add(collectionPoint);
        		}
        		
        		JSONArray employeeArray = returnObject.getJSONArray(IJSONConstants.GET_DEPT_DETAILS_EMPLOYEES);
        		for (int i = 0; i < employeeArray.length(); i++) {
        			JSONObject emp = employeeArray.getJSONObject(i);
        			
        			String addRole = null;
        			if (!emp.isNull(IJSONConstants.GET_DEPT_DETAILS_ADD_ROLE)) {
        				addRole = emp.getString(IJSONConstants.GET_DEPT_DETAILS_ADD_ROLE);
        			}
        			
        			String dept = emp.getString(IJSONConstants.GET_DEPT_DETAILS_DEPT);
        			String departmentCode = emp.getString(IJSONConstants.GET_DEPT_DETAILS_DEPT_CODE);
        			String empId = emp.getString(IJSONConstants.GET_DEPT_DETAILS_EMPID);
        			String name = emp.getString(IJSONConstants.GET_DEPT_DETAILS_EMPLOYEE_NAME);
        			String pwd = emp.getString(IJSONConstants.GET_DEPT_DETAILS_PASSWORD);
        			boolean responseStatus = emp.getBoolean(IJSONConstants.GET_DEPT_DETAILS_RESPONSE_STATUS);
        			String role = emp.getString(IJSONConstants.GET_DEPT_DETAILS_ROLE);        			
        			Employee employee = new Employee(dept, departmentCode, empId, name, role, addRole, responseStatus, pwd);
        			this.employeeList.add(employee);
        		}
        		
        		
        		if (!returnObject.isNull(IJSONConstants.GET_DEPT_DETAILS_CP_PREV_LOC)) {
        			String location = returnObject.getString(IJSONConstants.GET_DEPT_DETAILS_CP_PREV_LOC);
        			for (CollectionPoint cp : collectionPoints) {
        				if (cp.getLocation().equalsIgnoreCase(location)) {
        					this.oldCollectionPoint = cp;
        					this.newCollectionPoint = cp; // Because this is to be set by the user, we simply use the original one first.
        					break;
        				}
        			}
        		} else {
        			// There isn't a previous Collection Point, which is unlikely, but just to be safe, we take the first.
        			this.oldCollectionPoint = collectionPoints.get(0); // We just take the first one
        		}
        		
        		if (!returnObject.isNull(IJSONConstants.GET_DEPT_DETAILS_PREV_REP)) {
        			JSONObject emp = returnObject.getJSONObject(IJSONConstants.GET_DEPT_DETAILS_PREV_REP);
        			String addRole = emp.getString(IJSONConstants.GET_DEPT_DETAILS_ADD_ROLE);
        			String dept = emp.getString(IJSONConstants.GET_DEPT_DETAILS_DEPT);
        			String departmentCode = emp.getString(IJSONConstants.GET_DEPT_DETAILS_DEPT_CODE);
        			String empId = emp.getString(IJSONConstants.GET_DEPT_DETAILS_EMPID);
        			String name = emp.getString(IJSONConstants.GET_DEPT_DETAILS_EMPLOYEE_NAME);
        			String pwd = emp.getString(IJSONConstants.GET_DEPT_DETAILS_PASSWORD);
        			boolean responseStatus = emp.getBoolean(IJSONConstants.GET_DEPT_DETAILS_RESPONSE_STATUS);
        			String role = emp.getString(IJSONConstants.GET_DEPT_DETAILS_ROLE);        			
        			this.originalRepresentative = new Employee(dept, departmentCode, empId, name, role, addRole, responseStatus, pwd);
        			// This is to be set by the user, we just take the original as the new representative first.
        			this.newRepresentative = new Employee(dept, deptCode, empId, name, role, addRole, responseStatus, pwd);
        		}    		

        		result = true;
        	} catch (JSONException jsone) { 
        		jsone.printStackTrace();
        		result = false;
        	}
        }
        return result;
	}

	private String addGetDeptDetailsParamsToUrl(String url, String deptCode) {		
		if(!url.endsWith("?"))
			url += "?";
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(IJSONConstants.GET_DEPT_DETAILS_SMALL_DEPT_CODE, deptCode));	    
		String paramString = URLEncodedUtils.format(params, "utf-8");
		url += paramString;
		return url;
	}

	public Employee getOriginalRepresentative() {
		return originalRepresentative;
	}

	public void setOriginalRepresentative(Employee originalRepresentative) {
		this.originalRepresentative = originalRepresentative;
	}

	public Employee getNewRepresentative() {
		return newRepresentative;
	}

	public void setNewRepresentative(Employee newRepresentative) {
		this.newRepresentative = newRepresentative;
	}

	public CollectionPoint getOldCollectionPoint() {
		return oldCollectionPoint;
	}

	public void setOldCollectionPoint(CollectionPoint oldCollectionPoint) {
		this.oldCollectionPoint = oldCollectionPoint;
	}

	public CollectionPoint getNewCollectionPoint() {
		return newCollectionPoint;
	}

	public void setNewCollectionPoint(CollectionPoint newCollectionPoint) {
		this.newCollectionPoint = newCollectionPoint;
	}

	public CollectionPoint [] getCollectionPoints() {
		return collectionPoints.toArray(new CollectionPoint[collectionPoints.size()]);
	}


	public Employee [] getEmployeeList() {
		return employeeList.toArray(new Employee[employeeList.size()]);
	}


	public Boolean changeDeptDetails(String deptCode) {
		// This is where we send the data
		JSONObject deptDetailsObj = JSONHandler.createNewDeptDetailsObject(this, deptCode);						
		String url = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_CHANGE_DEPT_DETAILS;			
		String result = JSONParser.postStream(url, deptDetailsObj.toString());
		
		boolean b = false;
		if (result != null) {
			result = result.replace("\n", "").replace("\r", "");
			b = Boolean.valueOf(result);
		}
		return b;
	}
}
