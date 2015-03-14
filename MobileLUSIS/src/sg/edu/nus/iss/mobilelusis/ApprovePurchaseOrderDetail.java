package sg.edu.nus.iss.mobilelusis;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sg.edu.nus.iss.mobilelusis.model.Discrepancy;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.PurchaseOrder;
import sg.edu.nus.iss.mobilelusis.model.PurchaseOrderDetail;
import sg.edu.nus.iss.mobilelusis.store.Adapter.ApprovePODetailListAdapter;
import sg.edu.nus.iss.mobilelusis.supervisor.ApprovePurchaseOrder;
import sg.edu.nus.iss.mobilelusis.supervisor.SupervisorMenuActivity;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.JSONParser;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ApprovePurchaseOrderDetail extends Activity implements OnItemClickListener{

	
	private ApprovePODetailListAdapter appPODetailListAdapter = null;
	
	private PurchaseOrder order = null;
	private Employee supervisor = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_approve_purchase_order_detail);
		
		order = getIntent().getExtras().getParcelable("PO");  
		supervisor = getIntent().getExtras().getParcelable(IConstants.EXTRA_EMPLOYEE);
		
		TextView supplierView = (TextView)findViewById(R.id.approve_detail_supplier_code);
		String supplier = order.getSupplier();
		if (supplier != null) {
			supplierView.setText(supplier);
		} else {
			supplierView.setText(IConstants.EMPTY);
		}
		
		
		new GetPurchaseOrderDetailsAsyncTask(this).execute(order);
		
		

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.approve_purchase_order_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.approve_po_menuitem) {			
			new ApprovePOAsyncTask(this).execute(order);			
		} else if (id == R.id.cancel_approve_po_menuitem) {
			Intent intent = new Intent(this, ApprovePurchaseOrder.class);
			intent.putExtra(IConstants.EXTRA_EMPLOYEE, supervisor);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	private class ApprovePOAsyncTask  extends AsyncTask<PurchaseOrder, Void, Boolean> {
		
		private ApprovePurchaseOrderDetail activity = null;
		
		public ApprovePOAsyncTask(ApprovePurchaseOrderDetail activity) {
			super();
			this.activity = activity;
		}
		
		protected void onPostExecute(Boolean status) {
			
			if (status == true) {
				Toast.makeText(activity, "Purchase Order Approval succeeded.", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(activity, "Purchase Order Approval failed.", Toast.LENGTH_LONG).show();
			} 
			Intent intent = new Intent(activity, ApprovePurchaseOrder.class);
			intent.putExtra(IConstants.EXTRA_EMPLOYEE, supervisor);
			startActivity(intent);
		}
		
		@Override
		protected Boolean doInBackground(PurchaseOrder ... orders) {

			
			// This is where we send the data
			String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_APPROVE_PURCHASE;
			
			PurchaseOrder po = orders[0];
			int purchaseOrderId = po.getId();
			String url = addPurchaseOrderIdParamsToUrl(uri, po.getId());

			String data = JSONParser.getStream(url);			
			boolean result = false;
			if (data != null) {
				data = data.replace("\n", "").replace("\r", "");
				result = Boolean.valueOf(data);			
			}
			return result;
			
		}
		
		private String addPurchaseOrderIdParamsToUrl(String url, int purchaseOrderId) {		
			if(!url.endsWith("?"))
				url += "?";
			
			String id = purchaseOrderId + "";
			
			List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("POId", id));	    
			String paramString = URLEncodedUtils.format(params, "utf-8");
			url += paramString;
			return url;
		}

		
	}
	
	
	
	private class GetPurchaseOrderDetailsAsyncTask extends AsyncTask<PurchaseOrder, Void, List<PurchaseOrderDetail>> {
		
		
		private ApprovePurchaseOrderDetail activity = null;
		
		public GetPurchaseOrderDetailsAsyncTask(ApprovePurchaseOrderDetail activity) {
			super();
			this.activity = activity;
		}

		protected void onPostExecute(List<PurchaseOrderDetail> order) {
			
			
			appPODetailListAdapter = new ApprovePODetailListAdapter(activity, order);
			
			
//			appPODetailListAdapter = new ApprovePODetailListAdapter(ApprovePurchaseOrderDetail.this,getDummyData());
			ListView listView = (ListView) findViewById(R.id.listView1);
			listView.setAdapter(appPODetailListAdapter);
			listView.setOnItemClickListener(activity);
		}
		

		@Override
		protected List<PurchaseOrderDetail> doInBackground(PurchaseOrder ... orders) {

			// This is where we send the data
			String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_GET_PURCHASE_ORDER_DETAILS;
			
			PurchaseOrder po = orders[0];			
			int purchaseOrderId = po.getId();

			String url = addPurchaseOrderIdParamsToUrl(uri, po.getId());
			
			Log.i("URL", url);
			JSONArray array = JSONParser.getJSONArrayFromUrl(url);			
			List<PurchaseOrderDetail> poDetails = new ArrayList<PurchaseOrderDetail>();
			if (array != null) {
				try {
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						String description = obj.getString("Description");
						String item = obj.getString("Item");
						int minQty = obj.getInt("MinQty");
						int ordQty = obj.getInt("OrdQty");
						String orderDate = obj.getString("OrderDate");
						String supplier = obj.getString("Supplier");
						PurchaseOrderDetail pod = new PurchaseOrderDetail(description, item, minQty, ordQty, orderDate, supplier);
						poDetails.add(pod);
					}
				} catch (JSONException jsone) {
					jsone.printStackTrace();
				}
			}
			
			return poDetails;
		}		
		
		private String addPurchaseOrderIdParamsToUrl(String url, int purchaseOrderId) {		
			if(!url.endsWith("?"))
				url += "?";
			
			String id = purchaseOrderId + "";
			
			List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("POId", id));	    
			String paramString = URLEncodedUtils.format(params, "utf-8");
			url += paramString;
			return url;
		}


	}//end asynctask class
	
}
