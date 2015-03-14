package sg.edu.nus.iss.mobilelusis.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sg.edu.nus.iss.mobilelusis.model.Disbursement;
import sg.edu.nus.iss.mobilelusis.model.Discrepancy;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.Retrieval;
import sg.edu.nus.iss.mobilelusis.model.RetrievalByDept;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class MConnectionHelper {
	private Context context;
	
	public MConnectionHelper(Context c) {
		// TODO Auto-generated constructor stub
		this.context = c;
	}
	
	public static ArrayList<Retrieval> getViewRetrievalData() {
		// TODO Auto-generated method stub
		
	   String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.ACTION_VIEW_RETRIEVAL_LIST;
	   JSONObject jRetrievalObj = JSONParser.getJSONFromUrl(uri);
	   
	   ArrayList<Retrieval> retrievalList = new ArrayList<Retrieval>();
	   
		try {
			JSONArray jRetrievalArray = new JSONArray(jRetrievalObj.getString("Data"));
			
			for (int i = 0; i < jRetrievalArray.length(); i++) {
				//Convert to Normal obj and add to list
				 retrievalList.add(MJSONHandler.getRetrievalObj(jRetrievalArray.getJSONObject(i)));
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			   
	   return retrievalList;
		
	}
	
	public static JSONObject SaveRetrievalList(JSONObject jObject) {
		JSONObject jsonObject = null;
		
		//url need to change
		String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + "updateRetrievalList" ;
		
		String response = JSONParser.postStream(uri, jObject.toString());
		Log.i("Response",response.toString());
		return jsonObject;
	}	
	


	public static ArrayList<RetrievalByDept> getRetrievalByDepartmentBreakdownData() {
		// TODO Auto-generated method stub
		String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.ACTION_VIEW_RETRIEVAL_LIST;
		   JSONObject jRetrievalObj = JSONParser.getJSONFromUrl(uri);
		   
		   ArrayList<RetrievalByDept> retrievaldeptList = new ArrayList<RetrievalByDept>();
			try {
				JSONArray jRtrievalArray = new JSONArray(jRetrievalObj.getString("Data"));
				for (int i = 0; i < jRtrievalArray.length(); i++) {
					 JSONObject jRetrieval = jRtrievalArray.getJSONObject(i);
					 RetrievalByDept r = new RetrievalByDept();
					 r.setRetrievalID(jRetrieval.getString("RetrievalId"));
					 r.setItemCode(jRetrieval.getString("ItemCode"));
					 r.setDeptCode(jRetrieval.getString("DeptCode"));
					 r.setDeptDesc(jRetrieval.getString("DeptDescription"));
					 r.setRequestedQty(jRetrieval.getString("ItemNeeded"));
					 r.setRetrievedQty(jRetrieval.getString("AvailableQty"));
					 retrievaldeptList.add(r);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   
		   return retrievaldeptList;
	}

	public static ArrayList<Disbursement> getDisbursementData() {
		// TODO Auto-generated method stub
		 String uri = IConstants.URL + "/" + IConstants.SERVLET + "/disbursementList";
		 JSONArray jDisbursementArray = JSONParser.getJSONArrayFromUrl(uri);
		 return MJSONHandler.getDisbursementObjFromJSONArrary(jDisbursementArray);
	}

	public static JSONObject generateRetirevalList() {
		// TODO Auto-generated method stub
		 String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.ACTION_GENERATE_RETIREVAL_LIST;
		 JSONObject response = JSONParser.getJSONFromUrl(uri);
		 return response;
	}

	public static ArrayList<StationeryItem> getAllStationeryItems() {
		// TODO Auto-generated method stub
		String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_SEARCH +"/?query=";
		JSONObject jItems = JSONParser.getJSONFromUrl(uri);
		return MJSONHandler.getStationeryArrayListFromJSON(jItems);
		
	}

	public static JSONObject postDisbursementApproval(Employee employee, Disbursement disbursement) {
		// TODO Auto-generated method stub
		
		JSONObject jObject = MJSONHandler.createJDisbursementApproval(employee, disbursement);
		Log.i("Response",jObject.toString());
		String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_POST_APPROVE_DISBURSEMENT ;
		
		String response = JSONParser.postStream(uri, jObject.toString());
		JSONObject jResponse = null;
		try {
			jResponse =  new JSONObject().put("Response",  response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jResponse;
	}
	

	public static JSONArray getDiscrepancyList(Employee employee) {
		JSONArray discrepancies = new JSONArray();
		
				
		String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_GET_DISCREPANCY_FOR_VIEWING;
		String url = addEmployeeIDParamToUrl(uri, employee.getId());
		discrepancies = JSONParser.getJSONArrayFromUrl(url);
		return discrepancies;
	}
	
	private static String addEmployeeIDParamToUrl(String url, String empId){
		if(!url.endsWith("?"))
			url += "?";
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(IJSONConstants.VIEW_VOUCHER_EMP_ID, empId));	    
		String paramString = URLEncodedUtils.format(params, IJSONConstants.UTF8);
		url += paramString;
		return url;
	}

	public static JSONArray getDiscrepancyDetail(String empId, Discrepancy discrepancy) {
		
		String voucher = discrepancy.getVoucherID();
		
		JSONArray discrepancyDetail = new JSONArray();
		String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_DISCREPANCY_DETAILS_FOR_VIEWING;
		String url = addDiscrepancyDetailsParamToUrl(uri, empId, voucher);
		discrepancyDetail = JSONParser.getJSONArrayFromUrl(url);
		return discrepancyDetail;		
	}

	private static String addDiscrepancyDetailsParamToUrl(String url,
			String empId, String voucher) {
		if(!url.endsWith("?"))
			url += "?";
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(IJSONConstants.VIEW_DDETAILS_EMP_ID, empId));
		params.add(new BasicNameValuePair(IJSONConstants.VIEW_DDETAILS_VOUCHER_ID, voucher));
		String paramString = URLEncodedUtils.format(params, IJSONConstants.UTF8);
		url += paramString;
		return url;

	}

	public static Bitmap getImageByName(String EmpId) {
		// TODO Auto-generated method stub
		URL url = null;
		Bitmap bmp = null;
		try {
			url = new URL(IConstants.URL + "/" + IConstants.WEB_SIGNATURE + "/" + EmpId + ".png");
			Log.i("Image url",url.toString());
			bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bmp;
	}
}
