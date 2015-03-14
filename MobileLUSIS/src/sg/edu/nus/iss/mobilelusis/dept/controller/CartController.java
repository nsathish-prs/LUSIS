package sg.edu.nus.iss.mobilelusis.dept.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.dept.activity.CartActivity;
import sg.edu.nus.iss.mobilelusis.dept.activity.EmployeeMainActivity;
import sg.edu.nus.iss.mobilelusis.dept.adapter.CartListAdapter;
import sg.edu.nus.iss.mobilelusis.model.Cart;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.JSONHandler;
import sg.edu.nus.iss.mobilelusis.utils.JSONParser;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class CartController implements OnClickListener {

	private CartActivity activity = null;
	private Cart cart = null;
	
	private ListView listView = null;
	
	private CartListAdapter adapter = null;
	private List<StationeryItem> items = null;
	private String requisitionID = null;
	
	public CartController(CartActivity activity, Cart cart) {
		super();
		this.activity = activity;
		this.cart = cart;
		items = cart.getCheckoutItems();
		
		listView = (ListView)this.activity.findViewById(R.id.items_to_requisite_listview);
		adapter = new CartListAdapter(this.activity, R.layout.new_requisition_cart_row, this);
		listView.setAdapter(adapter);
	}
	
	public StationeryItem [] getStationeryItem() {
		return items.toArray(new StationeryItem [items.size()]);
	}

	public void setComment(String comment) {
		cart.setComment(comment);
		
	}

	// Submit new requisitions
	public void submitNewRequisition(Employee employee, Map<String, EditText> editables, String comment) {
		setComment(comment);
	
		// We get the items
		for (StationeryItem item : items) {
			String itemCode = item.getItemCode();
			EditText text = editables.get(itemCode);
			if (text != null) {
				String newQuantity = text.getText().toString();
				try {
					Integer qty = Integer.valueOf(newQuantity);
					int quantity = qty.intValue();					
					item.setQuantity(quantity);					
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
				}
			}
		}
		
		// Now remove all the items that have been deleted		
		JSONObject newRequisitionObject = JSONHandler.submitItemsForNewRequisition(employee, items, comment, requisitionID);
		SubmitNewRequisitionAsyncTask task = new SubmitNewRequisitionAsyncTask(this, employee);
		task.execute(newRequisitionObject);

		
	}
	
	
	public JSONObject submitItemsForNewRequisition(JSONObject jsonObj) {
		
		String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_NEW_REQUISITION;
		Log.i("URI new requisition", uri);
				
		// The result is either true or false, not a JSON string
		String data = JSONParser.postStream(uri, jsonObj.toString());
		Log.i("REQUEST: submitItemsForNewRequisition", data);
		
		
		boolean result = false;
		if (data != null) {
			data = data.replace("\n", "").replace("\r", "");
			result = Boolean.valueOf(data);			
		}
		JSONObject responseObject = null;
		try {
			responseObject = new JSONObject();
			responseObject.put("new_req_submission_result", result);
		} catch (JSONException jsone) {
			jsone.printStackTrace();
		}
		return responseObject;
	}
	

	private class SubmitNewRequisitionAsyncTask extends AsyncTask<JSONObject, Void, JSONObject> {

		private CartController controller;
		private Employee employee;
		
		public SubmitNewRequisitionAsyncTask(CartController controller, Employee employee) {
			super();
			this.controller = controller;
			this.employee = employee;
		}
		
		protected void onPostExecute(JSONObject result) {
			String submitResult = "Requisition submission ";
						
			try {
				if (result.getBoolean("new_req_submission_result")) {
					submitResult += "succeeded";
				} else {
					submitResult += "failed";
				}
				Toast.makeText(activity, submitResult, Toast.LENGTH_LONG).show();
			  Intent intent = new Intent(activity, EmployeeMainActivity.class);
			  intent.putExtra(IConstants.EXTRA_EMPLOYEE, employee);
			  activity.startActivity(intent);
			} catch (JSONException jsone) {
				jsone.printStackTrace();
			}
		}

		@Override
		protected JSONObject doInBackground(JSONObject ... object) {			
			JSONObject jsonObject = object[0];
			return controller.submitItemsForNewRequisition(jsonObject);
		}

	}//end SubmitNewRequisitionAsyncTask


	public void setRequisitionID(String requisitionID) {
		if (requisitionID == null || requisitionID.equals(""))
			this.requisitionID = "";
		else
			this.requisitionID = requisitionID;		
	}

	public void removeStationeryItem(StationeryItem itemToBeRemoved) {
		items.remove(itemToBeRemoved);
	}

	@Override
	public void onClick(View v) {
		
		// First, get all values from edittext
		Map<String,EditText> editables = adapter.getQuantityEditText();
		for (StationeryItem item : items) {
			String itemCode = item.getItemCode();
			EditText text = editables.get(itemCode);
			if (text != null) {
				String newQty = text.getText().toString();
				try {
					if (newQty != null && newQty.length() > 0) {
						Integer qty = Integer.valueOf(newQty);
						Log.i("Qty", item.getItemCode() + "[" + item.getQuantity() + ", " + qty + "]");
						item.setQuantity(qty);
					}
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
				}
			}
		}
		
		final int position = listView.getPositionForView(v);
		//StationeryItem selectedItem = items.get(position);
		
		if (items.size() > 1) {
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        switch (which){
			        case DialogInterface.BUTTON_POSITIVE:
			        	items.remove(position);
			    		adapter = new CartListAdapter(activity, R.layout.new_requisition_cart_row, CartController.this);
			    		listView.setAdapter(adapter);
			        	adapter.notifyDataSetChanged();
			            break;

			        case DialogInterface.BUTTON_NEGATIVE:
			            //No button clicked
			            break;
			        }
			    }
			};
			AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
			builder.setMessage("Do you want to delete this item?").setPositiveButton("Yes", dialogClickListener)
			    .setNegativeButton("No", dialogClickListener).show(); 			
		}
	}

	public CartListAdapter getListAdapter() {
		return this.adapter;
	}

	public String getRequisitionID() {
		return this.requisitionID;
	}

	public void setItemQty(String itemCode, String value) {
		if (items != null && items.size() > 0) {
			for (StationeryItem item : items) {
				String code = item.getItemCode();
				if (code.equalsIgnoreCase(itemCode)) {
					try {
						int quantity = Integer.valueOf(value);
						item.setQuantity(quantity);
					} catch (NumberFormatException nfe) {
						nfe.printStackTrace();
					}
					break;
				}
			}
		}		
	}
	
}
