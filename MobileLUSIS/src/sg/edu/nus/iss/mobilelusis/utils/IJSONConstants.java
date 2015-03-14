package sg.edu.nus.iss.mobilelusis.utils;



public interface IJSONConstants {

	// Generic keys
	static final String JSON_EMPTY_STRING = "";
	static final String JSON_UNKNOWN = "Unknown";
	static final String UTF8 = "utf-8";
	
	// Keys for login
	// POST: http://10.10.3.71/LUSISService/Service.svc/login	
	static final String LOGIN_ADDITIONAL_ROLE = "AdditionalRole";
	static final String LOGIN_DEPARTMENT = "Department";
	static final String LOGIN_DEPT_CODE = "DeptCode";
	static final String LOGIN_EMP_ID = "EmpId";
	static final String LOGIN_EMP_NAME = "EmployeeName";
	static final String LOGIN_PASSWORD = "Password";
	static final String LOGIN_RESPONSE_STATUS = "ReponseStatus";
	static final String LOGIN_ROLE = "Role";
	static final String LOGIN_RESULT = "Result";
	static final String LOGIN_STATUS = "Status";

	// Return Keys from login
	// POST: http://10.10.3.71/LUSISService/Service.svc/login
	static final String LOGIN_RESP_EMPLOYEE = "EmployeeName";			
	static final String LOGIN_RESP_EMPLOYEE_ID = "EmpId";
	static final String LOGIN_RESP_EMPLOYEE_DEPT_CODE = "DeptCode";
	static final String LOGIN_RESP_EMPLOYEE_DEPARTMENT = "Department";
	static final String LOGIN_RESP_ROLE = "Role";
	static final String LOGIN_RESP_ADDITIONAL_ROLE = "AdditionalRole";

	// Keys for searching stationery items via keywords 
	// GET: http://10.10.3.71/LUSISService/Service.svc/searchItems/?query=eraser	
	static final String SEARCH_KEY = "query";
	
	// Return Keys for searching stationery items via keywords
	// GET: http://10.10.3.71/LUSISService/Service.svc/searchItems/?query=eraser
	static final String SEARCH_RESP_RESPONSE_STATUS = "ResponseStatus";
	static final String SEARCH_RESP_DATA = "Data";
	static final String SEARCH_RESP_ITEM_CODE = "ItemCode";
	static final String SEARCH_RESP_ITEM_DESC = "ItemDescription";
	static final String SEARCH_RESP_ITEM_UNIT = "Unit";
	
	// Keys for submitting New Requisition
	// POST: http://10.10.3.71/LUSISService/Service.svc/newRequisition
	static final String NEW_REQ_COMMENT = "Comment";
	static final String NEW_REQ_DATA = "Data";
	static final String NEW_REQ_CATEGORY = "Category";
	static final String NEW_REQ_ITEM_CODE = "ItemCode";
	static final String NEW_REQ_QTY = "Quantity";
	static final String NEW_REQ_UNIT = "Unit";
	static final String NEW_REQ_EMPLOYEE_KEY = "Emp";
	static final String NEW_REQ_ADD_ROLE = "AdditionalRole";
	static final String NEW_REQ_DEPT = "Department";
	static final String NEW_REQ_DEPT_CODE = "DeptCode";
	static final String NEW_REQ_EMP_ID = "EmpId";
	static final String NEW_REQ_EMPLOYEE_NAME = "EmployeeName";
	static final String NEW_REQ_PASSWORD = "Password";
	static final String NEW_REQ_RESPONSE_STATUS = "ResponseStatus";
	static final String NEW_REQ_ROLE = "Role";
	static final String NEW_REQ_REJ_COMMENT = "RejComment";
	static final String NEW_REQ_REQ_ID = "ReqId";
	static final String NEW_REQ_REQ_DATE = "ReqDate";
	static final String NEW_STATUS = "Status";
	
	
	// Key for getting list of requisitions for Employee's viewing
	// POST: http://10.10.3.71/LUSISService/Service.svc/viewRequisition
	static final String VIEW_REQS_EMP_ID = "EmpId";
	
	// Return keys for getting list of requisitions for Employee's viewing
	// POST: http://10.10.3.71/LUSISService/Service.svc/viewRequisition
	static final String VIEW_REQS_RESP_DATA = "Data";
	static final String VIEW_REQS_RESP_REQ_ID = "RequestId";
	static final String VIEW_REQS_RESP_REQ_DATE = "Requestdate";
	
	
	// Key for getting Single Requisition for viewing
	// GET: http://10.10.3.71/LUSISService/Service.svc/viewRequisitionDetails/?reqID={ID}
	static final String SINGLE_REQ_KEY = "reqID";
	
	// Return Keys from getting Single Requisition for viewing
	// POST: http://10.10.3.71/LUSISService/Service.svc/viewRequisitionDetails/?reqID={ID}
	static final String SINGLE_REQ_RESP_COMMENT = "Comment";
	static final String SINGLE_REQ_RESP_DATA = "Data";
	static final String SINGLE_REQ_RESP_CATEGORY = "Category";
	static final String SINGLE_REQ_RESP_DESC = "Description";
	static final String SINGLE_REQ_RESP_ITEM_CODE = "ItemCode";
	static final String SINGLE_REQ_RESP_QTY = "Quantity";
	static final String SINGLE_REQ_RESP_UNIT = "Unit";
	static final String SINGLE_REQ_RESP_EMPLOYEE = "Emp";	
	static final String SINGLE_REQ_RESP_ADD_ROLE = "AdditionalRole";
	static final String SINGLE_REQ_RESP_DEPT = "Department";
	static final String SINGLE_REQ_RESP_DEPT_CODE = "DeptCode";
	static final String SINGLE_REQ_RESP_EMP_ID = "EmpId";
	static final String SINGLE_REQ_RESP_EMP_NAME = "EmployeeName";
	static final String SINGLE_REQ_RESP_PASSWORD = "Password";
	static final String SINGLE_REQ_RESP_RESPONSE_STATUS = "ResponseStatus";
	static final String SINGLE_REQ_RESP_ROLE = "Role";
	static final String SINGLE_REQ_RESP_REJECTION_COMMENT = "RejComment";
	static final String SINGLE_REQ_RESP_REQ_DATE = "ReqDate";
	static final String SINGLE_REQ_RESP_REQ_ID = "ReqId";
	static final String SINGLE_REQ_RESP_STATUS = "Status";
	
	
	// Return keys from getting department details for changing collection point and representative
	static final String GET_DEPT_DETAILS_COLLECTION_PT = "CollectionPoint";
	static final String GET_DEPT_DETAILS_CP_DESC = "Description";
	static final String GET_DEPT_DETAILS_CP_LOC = "Location";
	static final String GET_DEPT_DETAILS_CP_ID = "Id";
	static final String GET_DEPT_DETAILS_EMPLOYEES = "Employees";
	static final String GET_DEPT_DETAILS_CP_CURR_LOC = "Location"; // This is the same as CP_LOC, but we just want it to be separated for cleaner code and maintenance
	static final String GET_DEPT_DETAILS_CP_PREV_LOC = "PrevLocation";
	static final String GET_DEPT_DETAILS_REPRESENTATIVE = "Representative";
	static final String GET_DEPT_DETAILS_DELEGATE = "Delegate";
	static final String GET_DEPT_DETAILS_ADD_ROLE = "AdditionalRole";
	static final String GET_DEPT_DETAILS_COLLECT_PT = "Location";
	static final String GET_DEPT_DETAILS_COLLECT_PT_ID = "Id";
	static final String GET_DEPT_DETAILS_RESPONSE_STATUS = "ResponseStatus";
	static final String GET_DEPT_DETAILS_PREV_REP = "PrevRep";
	static final String GET_DEPT_DETAILS_NEW_REP = "NewRep";	
	static final String GET_DEPT_DETAILS_DESCRIPTION = "Description";
	static final String GET_DEPT_DETAILS_DEPT = "Department";
	static final String GET_DEPT_DETAILS_DEPT_CODE = "DeptCode";
	static final String GET_DEPT_DETAILS_EMPID = "EmpId";
	static final String GET_DEPT_DETAILS_EMPLOYEE_NAME = "EmployeeName";
	static final String GET_DEPT_DETAILS_PASSWORD = "Password";
	static final String GET_DEPT_DETAILS_ROLE = "Role";
	static final String GET_DEPT_DETAILS_SMALL_DEPT_CODE = "deptCode"; 
			

	
	
	
	// Key for Get Department Requisition List for Approval
	// GET: http://10.10.3.71/LUSISService/Service.svc/getDeptRequisitionList/?deptCode=REGR
	static final String APPROVAL_GET_DEPT_CODE = "deptCode";

	
	static final String REQUEST_DELEGATE_GET_DEPT_CODE = "deptCode";
	

	
	/////////////////////////////////////////////////////////////////////////////////////////

	
	// Return Keys for Get Department Requisition List for Approval
	// GET: http://10.10.3.71/LUSISService/Service.svc/getDeptRequisitionList/?deptCode=REGR	
	static final String APPROVAL_RESP_COMMENT = "Comment";
	static final String APPROVAL_RESP_DATA = "Data";
	static final String APPROVAL_RESP_CATEGORY = "Category";
	static final String APPROVAL_RESP_DESC = "Description";
	static final String APPROVAL_RESP_ITEM_CODE = "ItemCode";
	static final String APPROVAL_RESP_QTY = "Quantity";
	static final String APPROVAL_RESP_UNIT = "Unit";
	static final String APPROVAL_RESP_EMPLOYEE_KEY = "Emp";
	static final String APPROVAL_RESP_ADD_ROLE = "AdditionalRole";
	static final String APPROVAL_RESP_DEPT = "Department";
	static final String APPROVAL_RESP_EMP_ID = "EmpId";
	static final String APPROVAL_RESP_EMPLOYEE = "EmployeeName";
	static final String APPROVAL_RESP_PASSWORD = "Password";
	static final String APPROVAL_RESP_RESPONSE_STATUS = "ResponseStatus";
	static final String APPROVAL_RESP_ROLE = "Role";
	static final String APPROVAL_REJ_COMMENT = "RejComment";
	static final String APPROVAL_REQ_DATE = "ReqDate";
	static final String APPROVAL_REQ_ID = "ReqId";
	static final String APPROVAL_RESP_STATUS = "Status"; // Pending, Rejected, Approved, we expect only Pending here

	static final String APPROVAL_RESP_REQUISITION_PENDING = "Pending";
	
	// Keys for Approve, Reject Requisition 
	// POST: http://10.10.3.71/LUSISService/Service.svc/updateRequisitionStatus
	static final String APPROVE_REJ_REQ_ID = "ReqId";
	static final String APPROVE_REJ_REJ_COMMENT = "RejComment";
	static final String APPROVE_REJ_STATUS = "Status";
	static final Object APPROVE_REJ_APPROVE = "Approved";
	static final Object APPROVE_REJ_REJECT = "Rejected";
	
	
	
	static final String EMPLOYEE_DELEGATE = "Delegate";
	
	
	
	
	// Key for getting discrepancy list from web server
	// GET: http://10.10.3.71/LUSISService/Service.svc/discrepancyList/?EmpId={EMPID}
	static final String VIEW_VOUCHER_EMP_ID = "EmpId";
	
	
	// Return keys for getting discrepancy list from web server
	// GET: http://10.10.3.71/LUSISService/Service.svc/discrepancyList/?EmpId={EMPID}
	static final String VIEW_VOUCHER_RESP_DATE = "Date";
	static final String VIEW_VOUCHER_RESP_RAISED_BY = "RaisedBy";
	static final String VIEW_VOUCHER_RESP_VOUCHER_ID = "VoucherId";
	static final String VIEW_VOUCHER_RESP_VOUCHER_STATUS = "Status";
	
	
	static final String VIEW_DDETAILS_EMP_ID = "empId";
	static final String VIEW_DDETAILS_VOUCHER_ID = "Voucher";
	

}
