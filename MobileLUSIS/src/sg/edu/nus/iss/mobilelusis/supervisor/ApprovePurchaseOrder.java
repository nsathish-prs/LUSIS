package sg.edu.nus.iss.mobilelusis.supervisor;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sg.edu.nus.iss.mobilelusis.ApprovePurchaseOrderDetail;
import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.Discrepancy;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.PurchaseOrder;
import sg.edu.nus.iss.mobilelusis.model.ROLE;
import sg.edu.nus.iss.mobilelusis.store.Adapter.ApprovePOListAdapter;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.JSONParser;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ApprovePurchaseOrder extends Activity implements OnItemClickListener{

	private Employee supervisor = null;
	
	private ApprovePOListAdapter appPOListAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_approve_purchase_order);
		
		supervisor = getIntent().getExtras().getParcelable(IConstants.EXTRA_EMPLOYEE);
		
		
		new GetPurchaseOrderAsyncTask(this).execute();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.approve_purchase_order, menu);
		return true;
	}
	
	
	//test method
	public ArrayList<Discrepancy> getDummyData(){
		ArrayList<Discrepancy> deptList = new ArrayList<Discrepancy>();
		for (int j = 0; j < 5; j++) {
			Discrepancy d = new Discrepancy();
			
			deptList.add(d);
		}
		return deptList;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// Fetch the selected Retrieval Object
		PurchaseOrder selectedOrder = appPOListAdapter.getItem(position);
		Intent intent = new Intent(this, ApprovePurchaseOrderDetail.class);
		intent.putExtra("PO", selectedOrder);
		intent.putExtra(IConstants.EXTRA_EMPLOYEE, supervisor);
//		startActivityForResult(intent, IConstants.ACTIVITY_APPROVE_PURCHASE_ORDER);
		startActivity(intent);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == IConstants.ACTIVITY_APPROVE_PURCHASE_ORDER_RQ) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
//	        	Bundle bundle = data.getExtras();
//	        	Employee newRepresentative = bundle.getParcelable("new-representative");
//	        	if (newRepresentative != null) {		        	
//	        		controller.setNewRepresentative(newRepresentative);
//		        	representativeTextField.setText(newRepresentative.getName());
//	        	}
	        }
	    }
	}

	
	

	@Override
	public void finish() {
		Intent intent = new Intent();	
		intent.putExtra(IConstants.EXTRA_EMPLOYEE, this.supervisor);
		setResult(RESULT_OK, intent);
		super.finish();
	}
	
	
	
	
	private class GetPurchaseOrderAsyncTask extends AsyncTask<Void, Void, List<PurchaseOrder>> {
		
		
		private ApprovePurchaseOrder activity = null;
		
		public GetPurchaseOrderAsyncTask(ApprovePurchaseOrder activity) {
			super();
			this.activity = activity;
		}

		protected void onPostExecute(List<PurchaseOrder> purchaseOrders) {
//			appPOListAdapter = new ApprovePOListAdapter(ApprovePurchaseOrder.this,getDummyData());			
			appPOListAdapter = new ApprovePOListAdapter(activity, purchaseOrders);
			ListView listView = (ListView) findViewById(R.id.id_listview_po);
			listView.setAdapter(appPOListAdapter);
			listView.setOnItemClickListener(activity);
		}


		@Override
		protected List<PurchaseOrder> doInBackground(Void ... nothin) {

			// This is where we send the data
			String url = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_GET_PURCHASE_ORDERS_FOR_APPROVAL;
			
			List<PurchaseOrder> orders = new ArrayList<PurchaseOrder>();
			JSONArray result = JSONParser.getJSONArrayFromUrl(url);
			try {
				if (result != null) {
					for (int i = 0; i < result.length(); i++) {
						JSONObject obj = result.getJSONObject(i);
						int orderId = obj.getInt("OrderId");
						String dateStr = obj.getString("ExpDate");
						String status = obj.getString("Status");

						
						PurchaseOrder po = new PurchaseOrder();
						po.setId(orderId);
						po.setExpDate(dateStr);
						po.setStatus(status);

						if (supervisor.getRole() == ROLE.SUPERVISOR || supervisor.getRole() == ROLE.MANAGER) {
							if (status.equalsIgnoreCase("Pending")) {
								orders.add(po);
							}
						} else {
							orders.add(po);							
						}
					}
				}
			} catch (JSONException jsone) {
				jsone.printStackTrace();
			}
			return orders;
		}		
	}//end asynctask class
}
