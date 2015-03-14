package sg.edu.nus.iss.mobilelusis.utils;

import java.util.ArrayList;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sg.edu.nus.iss.mobilelusis.model.Department;
import sg.edu.nus.iss.mobilelusis.model.Disbursement;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.Retrieval;
import sg.edu.nus.iss.mobilelusis.model.RetrievalByDept;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import android.util.Log;

public class MJSONHandler {

	public static JSONObject createJRetrievalObjectFromList(
			ArrayList<Retrieval> rList) {
		// TODO Auto-generated method stub
		JSONObject jRetrievalListObj = new JSONObject();
		try {
			JSONArray jRetrievalList = new JSONArray();
			for (Retrieval r : rList) {
				JSONObject jRetrieval = new JSONObject();
					jRetrieval.put("AvailableQty", r.getAvaliability());
					jRetrieval.put("BinId", r.getItemBinID());
					jRetrieval.put("ItemCode", r.getItemCode());
					jRetrieval.put("ItemDescription", r.getItemDesc());
					jRetrieval.put("ItemNeeded", r.getRequestedQty());
					jRetrieval.put("RetrievalId", r.getRetrievalID());
					jRetrieval.put("RetrievedQty", r.getRetrievedQty());
					jRetrieval.put("Unit", r.getItemDesc());
					jRetrieval.put("DisbursementLists", createJDisbursementList(r.getDisbusementList()));
					jRetrieval.put("ItemByDept", createJDepartment(r.getRetrievalByDeptList()));
					jRetrievalList.put(jRetrieval);
					jRetrievalListObj.put("Data", jRetrievalList);
		   }
		   jRetrievalListObj.put("ResponseStatus", "true");
		}catch (JSONException e) {
			e.printStackTrace();
		}
		Log.i("Final JRetrievalObj",jRetrievalListObj.toString());
		return jRetrievalListObj;
	}
	
	private static JSONArray createJDisbursementList(ArrayList<Disbursement> disbusementList) throws JSONException {
		// TODO Auto-generated method stub
		JSONArray jDisbursementArray = new JSONArray();
		for (Disbursement disb : disbusementList) {
			  JSONObject jDisbObject = new JSONObject();	  
			  //Key
			  jDisbObject.put("Key", disb.getDeptCode());
			  //Value
			  JSONObject jDeptDetailObject = new JSONObject();
			  jDeptDetailObject.put("Status", disb.getStatus());
			  jDeptDetailObject.put("EmpId", disb.getRepresentativeName());
			  jDeptDetailObject.put("DisbDate", disb.getDate());
			  
			  int cPoint = 1;
			  try {
				  String collectionPoint = disb.getCollectionPoint();
				  cPoint = Integer.parseInt(collectionPoint);
			  } catch(ParseException pe) {
				  pe.printStackTrace();
			  }
			  jDeptDetailObject.put("CPoint", cPoint);
//			  jDeptDetailObject.put("CPoint", disb.getCollectionPoint());
			  jDisbObject.put("Value", jDeptDetailObject);
			  jDisbursementArray.put(jDisbObject);
		}
		return jDisbursementArray;
	}



	private static JSONArray createJDepartment(
			ArrayList<RetrievalByDept> retrievalByDeptList) throws JSONException {
		// TODO Auto-generated method stub
		JSONArray jDeptArray = new JSONArray();
		for (RetrievalByDept dept : retrievalByDeptList) {
			  JSONObject jDept = new JSONObject();
			  jDept.put("DeptCode",dept.getDeptCode());
			  jDept.put("DeptDescription",dept.getDeptDesc());
			  jDept.put("ItemRequested", dept.getRequestedQty());
			  jDept.put("ItemRetrieved", dept.getRetrievedQty());
			  jDept.put("RetrievalId", dept.getRetrievalID());
			  jDeptArray.put(jDept);
		}
		return jDeptArray;
	}



	public static Retrieval getRetrievalObj(JSONObject jRetrieval) throws JSONException {
		// TODO Auto-generated method stub
		 Retrieval r = new Retrieval();
		
		 r.setItemBinID(jRetrieval.getString("BinId"));
		 r.setItemCode(jRetrieval.getString("ItemCode"));
		 r.setItemDesc(jRetrieval.getString("ItemDescription"));
		 r.setRequestedQty(jRetrieval.getString("ItemNeeded"));
		 r.setRetrievedQty(jRetrieval.getString("RetrievedQty"));
		 r.setAvaliability(jRetrieval.getString("AvailableQty"));
		 r.setUnit(jRetrieval.getString("Unit"));
		 
		 JSONArray jDisbursementArray = new JSONArray(jRetrieval.getString("DisbursementLists"));
		 
		 ArrayList<Disbursement> disbursements = new ArrayList<Disbursement>();
		 
		 for (int i = 0; i < jDisbursementArray.length(); i++) {
			JSONObject jDisbDicts = jDisbursementArray.getJSONObject(i);
		 	Disbursement disb = new Disbursement();
		 	disb.setDeptCode(jDisbDicts.get("Key").toString());
			JSONObject disbDetail = jDisbDicts.getJSONObject("Value");
			disb.setCollectionPoint(disbDetail.getString("CPoint"));
			disb.setRepresentativeName(disbDetail.getString("EmpId"));
			disb.setStatus((disbDetail.getString("Status")));
			disb.setDate(disbDetail.getString("DisbDate"));
			disbursements.add(disb);
		 }
		 r.setDisbusementList(disbursements);

		 
		 JSONArray jRetrievalByDeptArray = new JSONArray(jRetrieval.getString("ItemByDept"));
		 ArrayList<RetrievalByDept> retrievalByDeptList = new ArrayList<RetrievalByDept>();
		 for (int j = 0; j < jRetrievalByDeptArray.length(); j++) {
			RetrievalByDept rdeptObj = getDepartmentObjFromJSON(jRetrievalByDeptArray.getJSONObject(j));
			retrievalByDeptList.add(rdeptObj);
		}
		r.setRetrievalByDeptList(retrievalByDeptList);

		return r;
	}



	private static RetrievalByDept getDepartmentObjFromJSON(
			JSONObject jRetrievalByDeptObj) throws JSONException {
		// TODO Auto-generated method stub
		RetrievalByDept deptObj = new RetrievalByDept();
			deptObj.setDeptCode(jRetrievalByDeptObj.getString("DeptCode"));
			deptObj.setDeptDesc(jRetrievalByDeptObj.getString("DeptDescription"));
			deptObj.setRequestedQty(jRetrievalByDeptObj.getString("ItemRequested"));
			deptObj.setRetrievedQty((jRetrievalByDeptObj.getString("ItemRetrieved").equalsIgnoreCase("null"))? "0" : jRetrievalByDeptObj.getString("ItemRetrieved"));
			deptObj.setRetrievalID(jRetrievalByDeptObj.getString("RetrievalId"));				

		return deptObj;
	}

	public static ArrayList<StationeryItem> getStationeryArrayListFromJSON(JSONObject jItems) {
		
		ArrayList<StationeryItem> items = new ArrayList<StationeryItem>();
		try {
			JSONArray jItemArray = new JSONArray(jItems.getString("Data"));
			for (int i = 0; i < jItemArray.length(); i++) {
				items.add(getStationeryItemFromJSONObject((JSONObject)jItemArray.get(i)));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return items;
	}

	private static StationeryItem getStationeryItemFromJSONObject(JSONObject object) {
		// TODO Auto-generated method stub
		StationeryItem item = new StationeryItem();
		try {
			item.setItemCode(object.getString("ItemCode"));
			item.setItemName(object.getString("ItemDescription"));
			item.setItemUnit(object.getString("Unit"));
			item.setQuantity(0);
			item.setReason("");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}

	public static ArrayList<Disbursement> getDisbursementObjFromJSONArrary(
			JSONArray jDisbursementArray) {
		// TODO Auto-generated method stub
		ArrayList<Disbursement> disbursementList = new ArrayList<Disbursement>();
		for (int i = 0; i < jDisbursementArray.length(); i++) {
			try {
				 JSONObject jDisbursementObj = jDisbursementArray.getJSONObject(i);
				 disbursementList.add(getDisbursementObjFromJSON(jDisbursementObj));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return disbursementList;
	}

	public static Disbursement getDisbursementObjFromJSON(JSONObject jDisbursementObj) throws JSONException{
		Disbursement disbObj = new Disbursement();
		disbObj.setDate(jDisbursementObj.getString("DisbDate"));
		disbObj.setDeptName(jDisbursementObj.getString("DeptName"));
		disbObj.setCollectionPoint(jDisbursementObj.getString("CPoint"));
		disbObj.setRepresentativeName(jDisbursementObj.getString("EmpId"));
		disbObj.setLocation(jDisbursementObj.getString("Location"));
		disbObj.setStatus(jDisbursementObj.getString("Status"));
		disbObj.setEmpID(jDisbursementObj.getString("EmpId"));
		disbObj.setStationeryItems(getItemsFromJSONArray(new JSONArray(jDisbursementObj.getString("DisbDetails"))));
		return disbObj;
	}

	private static ArrayList<StationeryItem> getItemsFromJSONArray(
			JSONArray jItemsArray) throws JSONException {
		// TODO Auto-generated method stub
		ArrayList<StationeryItem> items = new ArrayList<StationeryItem>();
		for (int i = 0; i < jItemsArray.length(); i++) {
			JSONObject jItem = jItemsArray.getJSONObject(i);
			StationeryItem item = new StationeryItem();
			item.setItemCode(jItem.getString("ItemCode"));
			item.setDesbID(jItem.getString("DesbId"));
			item.setCategory(jItem.getString("Category"));
			item.setItemName(jItem.getString("Description"));
			item.setItemUnit(jItem.getString("Unit"));
			item.setQuantity(jItem.getInt("RetrievedQuantity"));
			item.setStatus(jItem.getString("Status"));
			item.setItemRequested(jItem.getString("RequestedQuantity"));
			item.setItemRetrieved(jItem.getString("RetrievedQuantity"));
			items.add(item);
		}
		return items;
	}

	public static JSONObject createJDisbursementApproval(Employee employee,
			Disbursement disbursement) {

		
		JSONObject jDisbursementApproval = new JSONObject();
		try {
			jDisbursementApproval.put("DisbDetail",createJDisbursement(disbursement));
			jDisbursementApproval.put("Login",createJEmployeeObject(employee));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.i("Check", jDisbursementApproval.toString());
		
		return jDisbursementApproval;
	}

	private static JSONObject createJEmployeeObject(Employee employee) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject jEmployee = new JSONObject();
		
		jEmployee.put("AdditionalRole", "");
		jEmployee.put("Department", "");
		jEmployee.put("DeptCode", "");
		jEmployee.put("EmpId", employee.getId());
		jEmployee.put("Password", employee.getPassword());
		jEmployee.put("EmployeeName", "");
		jEmployee.put("ResponseStatus", true);
		jEmployee.put("Role", "");
		
		return jEmployee;
	}

	private static JSONObject createJDisbursement(Disbursement d) throws JSONException {

		JSONObject jDisbursement = new JSONObject();

		String cPoint = d.getCollectionPoint();
		int collectionPoint = 1;
		try {
			collectionPoint = Integer.parseInt(cPoint);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		
		jDisbursement.put("CPoint", collectionPoint);
		jDisbursement.put("DeptName", d.getDeptName());
		jDisbursement.put("DisbDate", d.getDate());
		jDisbursement.put("DisbDetails", createJStationeryArray(d.getStationeryItems()));
		jDisbursement.put("EmpId", d.getRepresentativeName());
		jDisbursement.put("Location", d.getLocation() );
		jDisbursement.put("Status", d.getStatus());
		
		
		
/*		
		{
			"DisbDetail":
		   {
		      "CPoint":1,
		      "DeptName":"Architecture",
		      "DisbDate":"11-Sep-2014",
		      "DisbDetails":[
		         {
		            "Category":"Clip ",
		"DesbId": 176,
		            "Description":"Clips Double 1\"",
		"ItemCode": "C001",
		            "RequestedQuantity":20,
		            "RetrievedQuantity":10,
		            "Status":null,
		            "Unit":"Dozen"
		         }],
		      "EmpId":"AR54103",
		      "Location":"Computer Science",
		      "Status":"Ready"
		   },
		   	"Login":{
				"AdditionalRole":"String content",
				"Department":"String content",
				"DeptCode":"String content",
				"EmpId":"AR54103",
				"EmployeeName":"String content",
				"Password":"AR54103",
				"ResponseStatus":true,
				"Role":"String content"
			}
		}
		
*/		
		
		
		
		return jDisbursement;
	}

	private static int getInteger(String intStr) {
		int i = 0;
		try {
			i = Integer.parseInt(intStr);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return i;
	}
	
	private static JSONArray createJStationeryArray(
			ArrayList<StationeryItem> stationeryItems) throws JSONException {
		// TODO Auto-generated method stub
		JSONArray jStationeryArray = new JSONArray();
		for (StationeryItem item : stationeryItems) {
			JSONObject jStationery = new JSONObject();
			jStationery.put("Category", item.getCategory());

			int desbID = getInteger(item.getDesbID());
			jStationery.put("DesbId", desbID);
//			jStationery.put("DesbId", item.getDesbID());
			jStationery.put("Description", item.getItemName());
			jStationery.put("ItemCode", item.getItemCode());

			int reqQty = getInteger(item.getItemRequested());
			jStationery.put("RequestedQuantity",reqQty);
			int itemRetr = getInteger(item.getItemRetrieved());
			jStationery.put("RetrievedQuantity", itemRetr);
			
//			jStationery.put("RequestedQuantity",item.getItemRequested() );
//			jStationery.put("RetrievedQuantity", item.getItemRetrieved());
			jStationery.put("Status", item.getStatus());
			jStationery.put("Unit", item.getItemUnit());
			jStationeryArray.put(jStationery);
		}		
		return jStationeryArray;
	}
	
	
	
}

;