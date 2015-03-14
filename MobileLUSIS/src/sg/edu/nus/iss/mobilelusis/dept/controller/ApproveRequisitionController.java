package sg.edu.nus.iss.mobilelusis.dept.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sg.edu.nus.iss.mobilelusis.dept.activity.DeptHeadApproveRequisitionActivity;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.Requisition;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.IJSONConstants;
import sg.edu.nus.iss.mobilelusis.utils.JSONHandler;
import sg.edu.nus.iss.mobilelusis.utils.JSONParser;
import android.util.Log;

public class ApproveRequisitionController {

	private Employee deptHead = null; 
	private Map<String, Requisition> requisitions = null;
	
	public ApproveRequisitionController(DeptHeadApproveRequisitionActivity activity, Employee deptHead) {
		super();
		this.deptHead = deptHead;		
		requisitions = new HashMap<String, Requisition>();		
	}

	public Employee getDeptHead() {
		return deptHead;
	}

	// Calling webserver with GET method to obtain list of pending requests
	public Map<String, Requisition> getRequisitionsGET()
	// Pre-condition: DeptHead has deptCode
	// Post-condition: requisitions map filled with pending requisitions for this department.
	{
		JSONArray responseArray = null;
		String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_GET_DEPT_REQ_LIST;
		Log.i("URI Approve Requisitions Get Requisitions", uri);
		String url = addSearchParamToUrl(uri, deptHead.getDeptCode());		
		responseArray = JSONParser.getJSONArrayFromUrl(url);				
		requisitions = JSONHandler.parsePendingRequisitionsForApproval(deptHead, responseArray);
		return requisitions;
		
	}
	
	/*
	 * GET Dept Requisitions by keyword helper method
	 */
	private String addSearchParamToUrl(String url, String keyword)
	
	{
		if(!url.endsWith("?"))
			url += "?";
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(IJSONConstants.APPROVAL_GET_DEPT_CODE, keyword));	    
		String paramString = URLEncodedUtils.format(params, IJSONConstants.UTF8);
		url += paramString;
		return url;
	}

	public Map<String, Requisition> getRequisitions() {		
		return this.requisitions;
	}

	public Requisition getSelectedRequisition(String selectedRequisitionId) {
		Requisition req = null;
		req = requisitions.get(selectedRequisitionId);		
		return req;
		
	}

	public Boolean approveRequisition(boolean isApprove) {
		return null;
	}

	public Boolean approveRequisition(boolean isApprove,
			String chosenRequisitionId, String rejectionReason) {
		boolean result = false;
		Requisition requisition = requisitions.get(chosenRequisitionId);
		if (requisition != null) {
			JSONObject jsonObj = JSONHandler.createApproveRequisitionObject(requisition, deptHead, isApprove, rejectionReason);			
			if (jsonObj != null) {
				String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_UPDATE_REQUISITION_STATUS;
				Log.i("URI update req status", uri);
				String data = jsonObj.toString();
				Log.i("Approve String", data);
				String response = JSONParser.postStream(uri, data);
				Log.i("res", response);
				
				if (response != null) {
					response = response.replace("\n", "").replace("\r", "");
					result = Boolean.valueOf(response);			
				}
			}
		} 
		return result;
	}
	
	
}
