package sg.edu.nus.iss.mobilelusis.utils;

import java.text.SimpleDateFormat;

import sg.edu.nus.iss.mobilelusis.model.Employee;

public interface IConstants {

	// URL
	static final String URL = "http://10.10.3.71:80";	
//	static final String URL = "http://10.10.3.176:8191";	
//	static final String URL = "http://10.10.2.202:8191";
//	static final String URL = "http://10.10.2.74:8191";
	static final String WEB_SIGNATURE = "LUSIS_WEB/Signature";
	static final String SERVLET = "LUSISService/Service.svc";
	static final String WCF_LOGIN = "login";	
	static final String WCF_SEARCH = "searchItems";
	static final String WCF_NEW_REQUISITION = "newRequisition";	
	static final String WCF_VIEW_REQ = "viewRequisition";
	static final String WCF_VIEW_SINGLE_REQ = "viewRequisitionDetails";	
	static final String WCF_GET_DEPT_REQ_LIST = "getDeptRequisitionList";	
	static final String WCF_GET_EMPLOYEE_LIST = "employeeList";
	static final String WCF_GET_DEPT_DETAILS = "getDeptDetails";
	static final String WCF_CHANGE_DEPT_DETAILS = "updateDeptDetails";
	static final String WCF_GET_DELEGATE = "getDelegate";
	static final String WCF_UPDATE_DELEGATE = "updateDelegate";	
	static final String WCF_UPDATE_REQUISITION_STATUS = "updateRequisitionStatus";
	static final String WCF_APPROVE_VOUCHER = "discrepancyApprove";
	static final String WCF_RAISE_VOUCHER = "raiseDiscrepancy";
	static final String WCF_APPROVE_PURCHASE = "approvePurchase";
	static final String WCF_POST_APPROVE_DISBURSEMENT = "approveDisbursement";
	static final String ACTION_GENERATE_RETIREVAL_LIST = "generateRetrievalList";
	static final String ACTION_VIEW_RETRIEVAL_LIST = "viewretrievallist";
	static final String ACTION_DISBURSEMENT_LIST = "disbursementList";
	
	
	static final String WCF_GET_DISCREPANCY_FOR_VIEWING = "discrepancyList";
	static final String WCF_DISCREPANCY_DETAILS_FOR_VIEWING = "discrepancyDetails";
	static final String WCF_GET_PURCHASE_ORDERS_FOR_APPROVAL = "purchaseOrder";
	static final String WCF_GET_PURCHASE_ORDER_DETAILS = "purchaseDetails";
	
	// Simple Date Format used by many classes
	static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	
	// EXTRA Keys
	static final String EXTRA_EMPLOYEE = "employee";
	static final String EXTRA_REQ_ID = "ReqID";
	static final String EXTRA_EXISTING_ITEMS = "existing_items";
	static final String PWD = "pwd";
	
	
	// GET String
//	static final String SMALL_DEPT_CODE = "deptCode";
	
	// ACTION Strings
	static final String ACTION_LOGIN = "login"; 
	static final String ACTION_SEARCH_KEYWORD = "search_items";	

	
	// DRAWER Strings
	static final String DRAWER_NEW_REQUISITION = "Submit A New Requisition";
	static final String DRAWER_VIEW_REQUISITION = "View Status Of Submitted Requisitions";
	
	static final String DRAWER_APPROVE_REQUISITION = "Authorize Requisitions";
	static final String DRAWER_CHANGE_DEPT_DETAILS = "Change Department Details";
	static final String DRAWER_DELEGATE_AUTHORITY = "Delegate Approving Authority";

	
	// REQUEST CODES
	static final int CHOOSE_SINGLE_ITEM_RESULT = 0;
	static final int CHOOSE_DELEGATE = 1;
	static final int CHOOSE_REPRESENTATIVE = 2;
	static final int CART_ACTIVITY = 3;
	static final int EDIT_SUBMITTED_CART_ACTIVITY = 4;
	static final int ADD_EXISTING_ITEM_ACTIVITY = 5;
	static final int DATE_DIALOG_ID = 999;
	static final int ACTIVITY_RDEPARTMENTBREAKDOWN_RQ = 100;
	static final int ACTIVITY_APPROVE_PURCHASE_ORDER_RQ = 90;
	
	
	
	// 	REQUISITION STATUS
	static final String REQUISITION_UNKNOWN = "Unknown";
	static final String REQUISITION_REJECTED = "Rejected";
	static final String REQUISITION_PENDING = "Pending";
	static final String REQUISITION_APPROVED = "Approved";

	
	static final String EMPID = "EmpId";
	static final String EMPLOYEES = "Employees";
	static final String REPRESENTATIVE = "Representative";
	static final String DELEGATE = "Delegate";
	static final String ADDITIONAL_ROLE = "AdditionalRole";
	static final String DEPARTMENT = "Department";
	
	static final String EMPTY = "";


	static final String DELEGATE_PREV_DELEGATE = "LoginDetails";
	static final String DELEGATE_EMP_LIST = "EmpList";
	static final String DELEGATE_START_DATE = "StartDate";
	static final String DELEGATE_END_DATE = "EndDate";
	static final String EMPTY_DATE = "No date selected";
	static final CharSequence NO_END_DATE = "No Ending Date";
	static final String EMPTY_DELEGATE = "(Tap here to select delegate)";
	
	//Dialog Tag
	static final String updateDisbursementDialogTag = "Update";
	static final String disbursement_acknowledgementDialogTag = "Acknowledge";
	static final String logoutDialogTag = "logout";
	
	
	
	
		
}
