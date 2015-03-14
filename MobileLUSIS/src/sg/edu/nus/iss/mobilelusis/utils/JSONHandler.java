package sg.edu.nus.iss.mobilelusis.utils;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sg.edu.nus.iss.mobilelusis.dept.controller.DeptDetailsController;
import sg.edu.nus.iss.mobilelusis.model.CollectionPoint;
import sg.edu.nus.iss.mobilelusis.model.Discrepancy;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.ROLE;
import sg.edu.nus.iss.mobilelusis.model.Requisition;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import sg.edu.nus.iss.mobilelusis.model.VoucherItem;
import android.util.Log;

/*
 * JSONHandler - parsing and creation of JSON objects
 * @author: Sim
 */


public class JSONHandler {


	/** Prepare JSON object to POST to webserver **/
	public static JSONObject login(String empID, String pwd) {
		JSONObject object = new JSONObject();
		try {			
			object.put(IJSONConstants.LOGIN_EMP_ID, empID);			  
			object.put(IJSONConstants.LOGIN_PASSWORD, pwd);
		} catch (JSONException e) {
			e.printStackTrace();
		}		  
		Log.i("JSONHandler.login", object.toString());
		return object;

	}
	
	/** Unparse JSON response object from login POST to webserver **/
	public static Employee parseLoginEmployee(JSONObject employeeObject) {
		Employee employee = null;
		try {
			employee = new Employee();
			
			String name = employeeObject.getString(IJSONConstants.LOGIN_RESP_EMPLOYEE);			
			employee.setName(name);


			String id = employeeObject.getString(IJSONConstants.LOGIN_RESP_EMPLOYEE_ID);
			employee.setId(id);

			String deptCode = employeeObject.getString(IJSONConstants.LOGIN_RESP_EMPLOYEE_DEPT_CODE);
			employee.setDeptCode(deptCode);

			String dept = employeeObject.getString(IJSONConstants.LOGIN_RESP_EMPLOYEE_DEPARTMENT);
			employee.setDept(dept);

			String role = employeeObject.getString(IJSONConstants.LOGIN_RESP_ROLE);
			if (role.equalsIgnoreCase("DeptHead")) {
				employee.setRole(ROLE.DEPT_HEAD);				
			} else if (role.equalsIgnoreCase("Employee")) {
				employee.setRole(ROLE.EMPLOYEE);
			} else if (role.equalsIgnoreCase("Clerk")) {
				employee.setRole(ROLE.CLERK);
			} else if (role.equalsIgnoreCase("Manager")) {
				employee.setRole(ROLE.MANAGER);
			} else if (role.equalsIgnoreCase("Supervisor")) {
				employee.setRole(ROLE.SUPERVISOR);
			}
			else {
				employee.setRole(ROLE.NONE);
			}

			
			String additionalRole = employeeObject.getString(IJSONConstants.LOGIN_RESP_ADDITIONAL_ROLE);
			if (additionalRole.equalsIgnoreCase("Delegate")) {
				employee.setAdditionalRole(ROLE.DELEGATE);
			} else if (additionalRole.equalsIgnoreCase(IConstants.REPRESENTATIVE)) {
				employee.setAdditionalRole(ROLE.REPRESENTATIVE);
			} else {
				employee.setAdditionalRole(ROLE.NONE);
			}
		} catch (JSONException jsone) {
			jsone.printStackTrace();
		}

		return employee;
	}

	
	
	/** Unparse JSON response object from Get pending requests for approval GET to web server **/	
	public static Map<String, Requisition> parsePendingRequisitionsForApproval(Employee deptHead, JSONArray responseArray) {
		Map<String, Requisition> requisitions = null;
		if (responseArray != null) {
			try {
				
				requisitions = new HashMap<String, Requisition>();
				
				for (int i = 0; i < responseArray.length(); i++) {

					JSONObject requisition = responseArray.getJSONObject(i);
					
					if (requisition != null) { Log.i("Response[" + i + "]", requisition.toString()); }
					
					String reqID = requisition.getString("ReqId");
					Requisition deptReq = new Requisition(reqID);

					JSONObject employeeObject = requisition.getJSONObject(IJSONConstants.APPROVAL_RESP_EMPLOYEE_KEY);
					Employee employee = unparseEmployee(deptHead, employeeObject);
					deptReq.setEmployee(employee);
					
					
					if (!requisition.isNull(IJSONConstants.APPROVAL_RESP_COMMENT)) {
						deptReq.setEmpComments(requisition.getString(IJSONConstants.APPROVAL_RESP_COMMENT));
					} else {
						deptReq.setEmpComments(IJSONConstants.JSON_EMPTY_STRING);
					}
					
					if (!requisition.isNull(IJSONConstants.APPROVAL_RESP_STATUS)) {
						deptReq.setStatus(requisition.getString(IJSONConstants.APPROVAL_RESP_STATUS));
					} else {
						deptReq.setStatus(IJSONConstants.APPROVAL_RESP_REQUISITION_PENDING);
					}
					
					if (!requisition.isNull(IJSONConstants.APPROVAL_REQ_DATE)) {
						deptReq.setSubmitDate(requisition.getString(IJSONConstants.APPROVAL_REQ_DATE));
					} else {
						deptReq.setSubmitDate(IJSONConstants.JSON_EMPTY_STRING);
					}
					
					// Because these are pending requisitions, there are no rejection reasons
					deptReq.setRejectionReason(IJSONConstants.JSON_EMPTY_STRING);
					
					JSONArray data = requisition.getJSONArray(IJSONConstants.APPROVAL_RESP_DATA);
					for (int j = 0; j < data.length(); j++) {
						JSONObject obj = data.getJSONObject(j);
						StationeryItem stationeryItem = new StationeryItem();								
						if (!obj.isNull(IJSONConstants.APPROVAL_RESP_CATEGORY)) {
							stationeryItem.setCategory(obj.getString(IJSONConstants.APPROVAL_RESP_CATEGORY));
						}
						if (!obj.isNull(IJSONConstants.APPROVAL_RESP_DESC))
							stationeryItem.setItemName(obj.getString(IJSONConstants.APPROVAL_RESP_DESC));
						 else
							 stationeryItem.setItemName(obj.getString(IJSONConstants.JSON_EMPTY_STRING));
						if (!obj.isNull(IJSONConstants.APPROVAL_RESP_ITEM_CODE))
							stationeryItem.setItemCode(obj.getString(IJSONConstants.APPROVAL_RESP_ITEM_CODE));
						else
							stationeryItem.setItemCode(IJSONConstants.JSON_EMPTY_STRING);
						
						if (!obj.isNull(IJSONConstants.APPROVAL_RESP_QTY)) {
							int qty = obj.getInt(IJSONConstants.APPROVAL_RESP_QTY);
							stationeryItem.setQuantity(qty);
						} else {
							stationeryItem.setQuantity(1);
						}
						if (!obj.isNull(IJSONConstants.APPROVAL_RESP_UNIT)) {
							stationeryItem.setItemUnit(obj.getString(IJSONConstants.APPROVAL_RESP_UNIT));
						} else {
							stationeryItem.setItemUnit(IJSONConstants.JSON_EMPTY_STRING);
						}
						deptReq.addItem(stationeryItem);
						requisitions.put(reqID, deptReq);
						
					}//end for j loop
				}
			} catch (JSONException jsone) {
				jsone.printStackTrace();
			}			
		}
		return requisitions;
	}

	
	// Helper method to unparse employee
	public static Employee unparseEmployee(Employee deptHead, JSONObject requisition) throws JSONException {
		Employee employee = new Employee();
						
		if (!requisition.isNull(IConstants.ADDITIONAL_ROLE))
			employee.setAdditionalRole(requisition.getString(IConstants.ADDITIONAL_ROLE));						
		else
			employee.setAdditionalRole(ROLE.NONE);
		
		if (!requisition.isNull(IConstants.DEPARTMENT))
			employee.setDept(requisition.getString(IConstants.DEPARTMENT));
		else 
			employee.setDept(deptHead.getDept());

		if (!requisition.isNull("DeptCode"))
			employee.setDeptCode(requisition.getString("DeptCode"));
		else
			employee.setDeptCode(deptHead.getDeptCode());
		
		if (!requisition.isNull(IConstants.EMPID))
			employee.setId(requisition.getString(IConstants.EMPID));
		else 
			employee.setId("");
		
		if (!requisition.isNull("Employee"))
			employee.setName(requisition.getString("Employee"));
		else if (!requisition.isNull("EmployeeName")) {
			employee.setName(requisition.getString("EmployeeName"));
		} else {
			employee.setName("");
		}
		
		if (!requisition.isNull("Role"))
			employee.setRoleByString(requisition.getString("Role"));
		
		return employee;
	}

	
	/** Unparse JSON response object from Get Stationery Items matching keyword **/
	public static ArrayList<StationeryItem> parseItemsByKeyword(JSONObject returnJSON) {
		ArrayList<StationeryItem> result = new ArrayList<StationeryItem>();
		try {

			boolean status = returnJSON.getBoolean(IJSONConstants.SEARCH_RESP_RESPONSE_STATUS);
			if (status == false) 
				return null; // Nothing found
			else {
				
				if (!returnJSON.isNull(IJSONConstants.SEARCH_RESP_DATA)) {					
					JSONArray array = returnJSON.getJSONArray(IJSONConstants.SEARCH_RESP_DATA);

					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						String itemCode = obj.getString(IJSONConstants.SEARCH_RESP_ITEM_CODE);
						String desc = obj.getString(IJSONConstants.SEARCH_RESP_ITEM_DESC);
						String unit = obj.getString(IJSONConstants.SEARCH_RESP_ITEM_UNIT);
						StationeryItem item = new StationeryItem();
						item.setItemCode(itemCode);
						item.setItemName(desc);
						item.setItemUnit(unit);
						item.setQuantity(0);
						result.add(item);
					}
				}
			}			
		} catch (JSONException jsone) {
			jsone.printStackTrace();
			return null;
		}
		return result;
	}

	
	/** Prepare JSON object containing New Requisition to be submitted **/	
	public static JSONObject submitItemsForNewRequisition(Employee emp, List<StationeryItem> items, String comments, String requisitionID) {
		JSONObject object = new JSONObject();
		
		try {
			if (requisitionID == null || requisitionID.equals(""))
				object.put(IJSONConstants.NEW_REQ_REQ_ID, JSONObject.NULL);
			else
				object.put(IJSONConstants.NEW_REQ_REQ_ID, requisitionID);
			object.put(IJSONConstants.NEW_REQ_COMMENT, comments);
			JSONArray array = new JSONArray();
			for (StationeryItem item : items) {
				JSONObject jItem = new JSONObject();
				jItem.put(IJSONConstants.NEW_REQ_ITEM_CODE, item.getItemCode());
				jItem.put(IJSONConstants.NEW_REQ_QTY, item.getQuantity());
				array.put(jItem);
			}
			object.put(IJSONConstants.NEW_REQ_DATA, array);
			JSONObject employee = new JSONObject();
			employee.put(IJSONConstants.NEW_REQ_EMP_ID, emp.getId());
			object.put(IJSONConstants.NEW_REQ_EMPLOYEE_KEY, employee);
		} catch (JSONException jsone) {
			jsone.printStackTrace();
		}
		Log.i("Submit new req", object.toString());
		return object;

	}

	/** Prepare JSON object to request for Requisition by Employee **/
	public static JSONObject getRequestEmployeeRequisitionsObject(Employee employee) {
		JSONObject obj = new JSONObject();
		try {
			obj.put(IJSONConstants.VIEW_REQS_EMP_ID, employee.getId());
		} catch (JSONException jsone) {
			jsone.printStackTrace();
		}
		return obj;
	}


	/** Unparse JSON response object from requesting for Employee's Pending Requisition **/
	public static Map<String, Requisition> parseRequisitionsForViewing(JSONObject json) {

		Map<String, Requisition> requisitions = new HashMap<String, Requisition>();
		try {
			JSONArray array = json.getJSONArray(IJSONConstants.VIEW_REQS_RESP_DATA);
			for (int i = 0; i < array.length(); i++) {
				JSONObject request = array.getJSONObject(i);
				String reqID = request.getString(IJSONConstants.VIEW_REQS_RESP_REQ_ID);
				String date = request.getString(IJSONConstants.VIEW_REQS_RESP_REQ_DATE);
								
				Requisition req = new Requisition(reqID, date);
				req.setSubmitDate(date);
				requisitions.put(reqID, req);				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return requisitions;
	}
	

	/** Unparse JSON response from Employee requesting to view Single Requisition **/ 
	public static Requisition unparseSingleRequisitionForViewing(String reqID, JSONObject json) {
				
		Requisition requisition = null;
		if (json != null) {
			try {
				requisition = new Requisition(reqID);
				String status = json.getString(IJSONConstants.SINGLE_REQ_RESP_STATUS);
				if (status != null && !status.equalsIgnoreCase(IJSONConstants.JSON_EMPTY_STRING)) {
					requisition.setStatus(status);
				} else {
					requisition.setStatus(IJSONConstants.JSON_UNKNOWN);
				}
				
				
				String submitDate = json.getString(IJSONConstants.SINGLE_REQ_RESP_REQ_DATE);
				requisition.setSubmitDate(submitDate);
				
				String comment = json.getString(IJSONConstants.SINGLE_REQ_RESP_COMMENT);
				if (comment != null)
					requisition.setEmpComments(comment);
				else
					requisition.setEmpComments(IJSONConstants.JSON_EMPTY_STRING);

				String rejectionComment = json.getString(IJSONConstants.SINGLE_REQ_RESP_REJECTION_COMMENT);				
				if (rejectionComment == null || rejectionComment.trim().equalsIgnoreCase(IJSONConstants.JSON_EMPTY_STRING)) {
					requisition.setRejectionReason(IJSONConstants.JSON_EMPTY_STRING);					
				} else {
					requisition.setRejectionReason(rejectionComment);
				}



				JSONArray array = json.getJSONArray(IJSONConstants.SINGLE_REQ_RESP_DATA);
				if (array != null) {
					for (int i = 0; i < array.length(); i++) {

						JSONObject obj = array.getJSONObject(i);

						String category = obj.getString(IJSONConstants.SINGLE_REQ_RESP_CATEGORY);
						String description = obj.getString(IJSONConstants.SINGLE_REQ_RESP_DESC);
						String itemCode = obj.getString(IJSONConstants.SINGLE_REQ_RESP_ITEM_CODE);
						int quantity = obj.getInt(IJSONConstants.SINGLE_REQ_RESP_QTY);
						String unit = obj.getString(IJSONConstants.SINGLE_REQ_RESP_UNIT);

						StationeryItem item = new StationeryItem();

						item.setItemCode(itemCode);
						item.setCategory(category);
						item.setItemUnit(unit);
						item.setItemName(description);
						item.setQuantity(quantity);

						requisition.addItem(item);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return requisition;
	}
	
	/** Create department details to be sent to web server **/
	public static JSONObject createNewDeptDetailsObject(DeptDetailsController controller, String deptCode) {

		JSONObject returnObject = new JSONObject();
		try {
			JSONArray cpArray = new JSONArray();
			CollectionPoint [] collectionPoints = controller.getCollectionPoints();
			for (CollectionPoint point : collectionPoints) {
				JSONObject cp = new JSONObject();
				cp.put(IJSONConstants.GET_DEPT_DETAILS_CP_ID, point.getIdentity());
				cp.put(IJSONConstants.GET_DEPT_DETAILS_CP_LOC, point.getLocation());
				if (point.getDescription() == null || point.getDescription().equals("")) {
					cp.put(IJSONConstants.GET_DEPT_DETAILS_CP_DESC, JSONObject.NULL);
				} else {
					cp.put(IJSONConstants.GET_DEPT_DETAILS_CP_DESC, point.getDescription());
				}
				cpArray.put(cp);
			}
			returnObject.put(IJSONConstants.GET_DEPT_DETAILS_COLLECTION_PT, cpArray);

			JSONArray empArray = new JSONArray();
			Employee [] empList = controller.getEmployeeList();
			for (Employee emp : empList) {
				JSONObject empObj = new JSONObject();
				if (emp.getAdditionalRole() == ROLE.NONE) {
					empObj.put(IJSONConstants.GET_DEPT_DETAILS_ADD_ROLE, JSONObject.NULL);
				} else {
					empObj.put(IJSONConstants.GET_DEPT_DETAILS_ADD_ROLE, emp.getAdditionalRole().toString());
				}
				empObj.put(IJSONConstants.GET_DEPT_DETAILS_DEPT, emp.getDept());
				empObj.put(IJSONConstants.GET_DEPT_DETAILS_DEPT_CODE, emp.getDeptCode());
				empObj.put(IJSONConstants.GET_DEPT_DETAILS_EMPID, emp.getId());
				empObj.put(IJSONConstants.GET_DEPT_DETAILS_EMPLOYEE_NAME, emp.getName());
				empObj.put(IJSONConstants.GET_DEPT_DETAILS_PASSWORD, emp.getPassword());
				empObj.put(IJSONConstants.GET_DEPT_DETAILS_RESPONSE_STATUS, emp.getReponseStatus());
				empObj.put(IJSONConstants.GET_DEPT_DETAILS_ROLE, emp.getRole().toString());
				empArray.put(empObj);			
			}
			returnObject.put(IConstants.EMPLOYEES, empArray);


			// Set collection points
			returnObject.put(IJSONConstants.GET_DEPT_DETAILS_CP_PREV_LOC, controller.getOldCollectionPoint().getLocation());

			returnObject.put(IJSONConstants.GET_DEPT_DETAILS_CP_CURR_LOC, controller.getNewCollectionPoint().getLocation());


			// Set Representatives
			Employee prevRep = controller.getOriginalRepresentative();
			JSONObject previousRepresentative = createEmployeeObject(prevRep);		
			returnObject.put(IJSONConstants.GET_DEPT_DETAILS_PREV_REP, previousRepresentative);

			Employee newRep = controller.getNewRepresentative();
			JSONObject newRepresentative = createEmployeeObject(newRep);
			returnObject.put(IJSONConstants.GET_DEPT_DETAILS_NEW_REP, newRepresentative);

		} catch (JSONException jsone) {
			jsone.printStackTrace();
		}
		return returnObject;

	}

	/** Utility class for creating employe objects */
	private static JSONObject createEmployeeObject(Employee emp) {
		return createEmployeeObject(emp, false);
	}

	private static JSONObject createEmployeeObject(Employee emp, boolean isClearDelegate) {
		JSONObject rep = new JSONObject();
		try {
			if (emp.getAdditionalRole() == null || emp.getAdditionalRole() == ROLE.NONE) {			
				rep.put(IJSONConstants.GET_DEPT_DETAILS_ADD_ROLE, JSONObject.NULL);
			} else {
				rep.put(IJSONConstants.GET_DEPT_DETAILS_ADD_ROLE, emp.getAdditionalRole().toString());
			}
			rep.put(IJSONConstants.GET_DEPT_DETAILS_DEPT, emp.getDept());
			rep.put(IJSONConstants.GET_DEPT_DETAILS_DEPT_CODE, emp.getDeptCode());
			if (isClearDelegate) {
				rep.put(IJSONConstants.GET_DEPT_DETAILS_EMPID, JSONObject.NULL);
			} else {
				rep.put(IJSONConstants.GET_DEPT_DETAILS_EMPID, emp.getId());
			}
			rep.put(IJSONConstants.GET_DEPT_DETAILS_EMPLOYEE_NAME, emp.getName());
			rep.put(IJSONConstants.GET_DEPT_DETAILS_PASSWORD, emp.getPassword());
			rep.put(IJSONConstants.GET_DEPT_DETAILS_RESPONSE_STATUS, emp.getReponseStatus());
			rep.put(IJSONConstants.GET_DEPT_DETAILS_ROLE, emp.getRole().toString());
		} catch (JSONException jsone) {
			jsone.printStackTrace();
		}
		return rep;
	}

	

	/** Create delegate object for changing approving delegate **/
	public static JSONObject createUpdateDelegateObject(String deptCode, boolean isClearDelegate, Employee delegate, Calendar startDate, Calendar endDate, List<Employee> employeeList) {

		if (delegate == null) {
			Employee emp = new Employee();
			emp.setId(IJSONConstants.JSON_EMPTY_STRING);
			emp.setAdditionalRole(ROLE.NONE);
			emp.setDept("");
			emp.setDeptCode("");
			emp.setId(deptCode);
			emp.setName(IJSONConstants.JSON_EMPTY_STRING);
			emp.setRole(ROLE.NONE);
			emp.setDept(IJSONConstants.JSON_EMPTY_STRING);
			delegate = emp;
		}


		JSONObject object = null;
		try {
			object = new JSONObject();
			JSONObject newDel = createEmployeeObject(delegate, isClearDelegate);
			object.put(IConstants.DELEGATE_PREV_DELEGATE,newDel);


			if (startDate != null) {
				String startingDate = IConstants.sdf.format(startDate.getTime());
				object.put(IConstants.DELEGATE_START_DATE, startingDate);
			} else {
				object.put(IConstants.DELEGATE_START_DATE, JSONObject.NULL);
			}

			if (endDate != null) {
				String endingDate = IConstants.sdf.format(endDate.getTime());
				object.put(IConstants.DELEGATE_END_DATE, endingDate);
			} else {
				object.put(IConstants.DELEGATE_END_DATE, JSONObject.NULL);
			}

			JSONArray empListArray = new JSONArray();
			for (Employee employee : employeeList) {				
				JSONObject emp = createEmployeeObject(employee);
				empListArray.put(emp);
			}
			object.put(IConstants.DELEGATE_EMP_LIST, empListArray);			
		} catch (JSONException jsone) {
			jsone.printStackTrace();
		}					
		return object;
	}
	

	/** Create JSON object for Approving/Rejecting a Requisition **/

	public static JSONObject createApproveRequisitionObject(Requisition requisition, Employee deptHead, boolean isApprove, String rejectionReason) {
		JSONObject object = null;
		try {
			object = new JSONObject();
			object.put(IJSONConstants.APPROVE_REJ_REQ_ID, requisition.getRequisitionId());
			if (isApprove) {
				object.put(IJSONConstants.APPROVE_REJ_STATUS, IJSONConstants.APPROVE_REJ_APPROVE);
				object.put(IJSONConstants.APPROVE_REJ_REJ_COMMENT, JSONObject.NULL);
			} else { // Dept. Head has rejected
				object.put(IJSONConstants.APPROVE_REJ_STATUS, IJSONConstants.APPROVE_REJ_REJECT);
				if (rejectionReason == null || rejectionReason.trim().equals("")) {
					object.put(IJSONConstants.APPROVE_REJ_REJ_COMMENT, JSONObject.NULL);
				} else {
					object.put(IJSONConstants.APPROVE_REJ_REJ_COMMENT, rejectionReason);
				}
			}
		} catch (JSONException jsone) {
			jsone.printStackTrace();
		}
		return object;
	}

	
	/** Unparse JSONArray of discrepancies for viewing **/
	public static List<Discrepancy> getDiscrepanciesForViewing(JSONArray array, ROLE role) {
		List<Discrepancy> result = new ArrayList<Discrepancy>();
		
		try {
		
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);

				String dateStr = obj.getString(IJSONConstants.VIEW_VOUCHER_RESP_DATE);
				Date date = IConstants.sdf.parse(dateStr);
				String raisedBy = obj.getString(IJSONConstants.VIEW_VOUCHER_RESP_RAISED_BY);
				String voucherId = obj.getString(IJSONConstants.VIEW_VOUCHER_RESP_VOUCHER_ID);
				String status = obj.getString(IJSONConstants.VIEW_VOUCHER_RESP_VOUCHER_STATUS);
				Discrepancy discrepancy = new Discrepancy();
				discrepancy.setDate(date);
				if (role == ROLE.CLERK) {
					discrepancy.setApprovedBy(raisedBy);
				} else {
					discrepancy.setRaisedBy(raisedBy);
				}
				discrepancy.setVoucherID(voucherId);
				discrepancy.setStatus(status);
				result.add(discrepancy);				
			}
		} catch (ParseException pe) {
			pe.printStackTrace();
		} catch (JSONException jsone) {
			jsone.printStackTrace();
		}
		return result;
	}

	public static List<VoucherItem> unparseVoucherItems(JSONArray array) {
		List<VoucherItem> items = new ArrayList<VoucherItem>();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				String description = obj.getString("Description");
				int discrepancyId = obj.getInt("DiscrepancyId");
				int qty = obj.getInt("Qty");
				String item = obj.getString("Item");
				String reason = obj.getString("Reason");
				String status = obj.getString("Status");
				VoucherItem vItem = new VoucherItem(description, item, qty, reason, status, discrepancyId);
				items.add(vItem);
			}
		} catch (JSONException jsone) {
			jsone.printStackTrace();
		}
		return items;
	}

	public static JSONArray createVoucherApprovalObject(Discrepancy discrepancy, List<VoucherItem> voucherItems) {
		
		JSONArray array = new JSONArray();
		
		try {
			for (VoucherItem item : voucherItems) {
				JSONObject obj = new JSONObject();			
				obj.put("Description", item.getDescription());
				obj.put("DiscrepancyId", item.getDiscrepancyId());
				obj.put("Item", item.getItem());
				obj.put("Qty", item.getQty());
				obj.put("Reason", item.getReason());
				obj.put("Status", item.getStatus());
				array.put(obj);
			}
			
		} catch (JSONException jsone) {
			jsone.printStackTrace();
		}		
		return array;
	}

	public static JSONObject createVouchersToRaise(Employee employee, ArrayList<StationeryItem> itemsToDiscrepance) {
		JSONObject result = new JSONObject();
		try {
						
			result.put("EmpId", employee.getId());
			JSONArray array = new JSONArray();
			
			for (StationeryItem item : itemsToDiscrepance) {
				JSONObject obj = new JSONObject();
				obj.put("Description", item.getItemName());
				obj.put("DiscrepancyId", 0);
				obj.put("Item", item.getItemCode());
				obj.put("Qty", item.getQuantity());
				obj.put("Reason", item.getReason());
				obj.put("Status", "Pending");
				array.put(obj);
			}	
			
			result.put("Details", array);
			
		} catch (JSONException jsone) {
			jsone.printStackTrace();
		}
		return result;
	}
	 
	

}
