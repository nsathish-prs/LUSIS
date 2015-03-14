package sg.edu.nus.iss.mobilelusis.dept.controller;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.dept.activity.CartActivity;
import sg.edu.nus.iss.mobilelusis.dept.activity.EmployeeViewRequisitionActivity;
import sg.edu.nus.iss.mobilelusis.model.Cart;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.Requisition;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.IJSONConstants;
import sg.edu.nus.iss.mobilelusis.utils.JSONHandler;
import sg.edu.nus.iss.mobilelusis.utils.JSONParser;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class ViewRequisitionController implements OnClickListener {
	
	private Employee employee = null;
	private Map<String, Requisition> requisitions = null;
	
	private EmployeeViewRequisitionActivity activity;
	private String selectedRequisitionID = null;
	
	public ViewRequisitionController(EmployeeViewRequisitionActivity activity, Employee employee) {
		super();
		this.activity = activity;
		this.employee = employee;
		requisitions = new HashMap<String, Requisition>();		
	}
	
	
	public Map<String, Requisition> getRequisitionsPOST() {
		// Get the list of requisition items for the department for this particular employee.
		JSONObject requestPendingRequisitionsObject = JSONHandler.getRequestEmployeeRequisitionsObject(employee);
		JSONObject requisitionsObject = getEmployeeRequisitionsForViewing(requestPendingRequisitionsObject); 
		requisitions.clear();
		requisitions = JSONHandler.parseRequisitionsForViewing(requisitionsObject);
		return requisitions;
	}
	
	private JSONObject getEmployeeRequisitionsForViewing(JSONObject requestRequisitionsForViewingObject) {

		JSONObject responseObject = null;
		String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_VIEW_REQ;
		Log.i("URI View Requisitions", uri);
		String data = requestRequisitionsForViewingObject.toString();
		Log.i("REQUEST getEmployeeRequisitionsForViewing", data);
		String response = JSONParser.postStream(uri, data);
		Log.i("RESPONSE getEmployeeRequisitionsForViewing", response);
		try {
			responseObject = new JSONObject(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return responseObject;		
	}

	public Requisition getSingleRequisitionForViewingGET(String selectedRequisitionId) {

		Requisition requisition = null;
		String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_VIEW_SINGLE_REQ;
		Log.i("URI View One Req", uri);
		String url = addViewOneRequestParamsToUrl(uri, selectedRequisitionId);		
		Log.i("URL View One Req", url);
		JSONObject returnObject = JSONParser.getJSONFromUrl(url);		
        Log.i("View One Req response", returnObject.toString());			 	        
        requisition = JSONHandler.unparseSingleRequisitionForViewing(selectedRequisitionId, returnObject);
        
        
        String date = requisition.getSubmitDate();
        Calendar c = Calendar.getInstance();
        
        try {
        	Date d = IConstants.sdf.parse(date);
        	c.setTime(d);
        } catch (ParseException pe) {
        	pe.printStackTrace();
        }
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        

        String submissionDate = requisitions.get(selectedRequisitionId).getSubmitDate();
        if (submissionDate != null)
        	requisition.setSubmitDate(submissionDate);
        else {
        	Calendar cal = Calendar.getInstance();
        	cal.set(Calendar.HOUR, 0);
        	cal.set(Calendar.MINUTE, 0);
        	cal.set(Calendar.SECOND, 0);
        	cal.set(Calendar.MILLISECOND, 0);
        	String d = IConstants.sdf.format(cal.getTime());
        	requisition.setSubmitDate(d);
        }
		return requisition;		
	}
	
	private String addViewOneRequestParamsToUrl(String url, String requisitionId){
		if(!url.endsWith("?"))
			url += "?";
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(IJSONConstants.SINGLE_REQ_KEY, requisitionId));	    
		String paramString = URLEncodedUtils.format(params, IJSONConstants.UTF8);
		url += paramString;
		return url;
	}

	public Employee getEmployee() {
		return this.employee;
	}


	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.view_req_status_textview) {
			Requisition req = requisitions.get(selectedRequisitionID);
			
			// We show rejection reason alert only if the requisition is rejected.
			if (req.getStatus().equalsIgnoreCase(IConstants.REQUISITION_REJECTED)) {
				String rejectionReason = req.getRejectionReason();
				if (rejectionReason == null || rejectionReason.equalsIgnoreCase("")) {
					rejectionReason = "No reason given for rejection.";
				}
				
				AlertDialog.Builder alert = new AlertDialog.Builder(activity);
				alert.setTitle("Reject requisition");
				
				alert.setMessage(rejectionReason);
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {						
					  }
					});
				alert.show();				
			}
		}
	}


	public void setSelectedRequisition(String requisitionId) {
		this.selectedRequisitionID = requisitionId;		
	}


	public void updateRequisition(Requisition selectedRequisition) {
		
		String requisitionID = selectedRequisition.getRequisitionId();
		String dateString = selectedRequisition.getSubmitDate();
		Calendar c = Calendar.getInstance();
		if (dateString != null) {
			try {
				Date d = IConstants.sdf.parse(dateString);
				c.setTime(d);				
			} catch (ParseException pe) {
				pe.printStackTrace();				
			}
		}
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		requisitions.put(selectedRequisition.getRequisitionId(), selectedRequisition);
	}


	public String getSelectedRequisitionID() {
		return selectedRequisitionID;
	}


	public void editPendingItems() {		
		Intent intent = new Intent(activity, CartActivity.class);
		intent.putExtra(IConstants.EXTRA_EMPLOYEE, employee);
		intent.putExtra(IConstants.EXTRA_REQ_ID, selectedRequisitionID);
		Cart cart = new Cart();		
		Requisition selectedRequisition = requisitions.get(selectedRequisitionID);
		
		StationeryItem [] items = selectedRequisition.getStationeryItems();
		for (StationeryItem item : items) {
			cart.addToCart(item);
		}		
		intent.putExtra("checkout_cart", cart);			
		activity.startActivityForResult(intent, IConstants.EDIT_SUBMITTED_CART_ACTIVITY);		
	}
	
	public void setEditMenuItem(boolean visible) {
		MenuItem editMenuItem = activity.getMenu().findItem(R.id.view_single_req_edit_menuitem);
		if (editMenuItem != null)
			editMenuItem.setVisible(visible);
	}


	public boolean getSelectedRequisitionStatus() {
		boolean isPending = false;
		if (selectedRequisitionID != null) {
			Requisition r = requisitions.get(selectedRequisitionID);
			if (r != null) {
				if (r.getStatus().equalsIgnoreCase(IConstants.REQUISITION_PENDING))
					isPending = true;
			}
		}
		return isPending;
	}
	
}
