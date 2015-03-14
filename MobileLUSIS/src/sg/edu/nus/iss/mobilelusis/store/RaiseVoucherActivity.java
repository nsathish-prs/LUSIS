package sg.edu.nus.iss.mobilelusis.store;

import java.util.ArrayList;

import org.json.JSONObject;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.StoreClerkMenuActivity;
import sg.edu.nus.iss.mobilelusis.Temp;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import sg.edu.nus.iss.mobilelusis.store.Adapter.DiscrepancyListAdapter;
import sg.edu.nus.iss.mobilelusis.store.Adapter.SearchItemListAdapter;
import sg.edu.nus.iss.mobilelusis.store.dialog.UpdateDisbursementDialog;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.JSONHandler;
import sg.edu.nus.iss.mobilelusis.utils.JSONParser;
import sg.edu.nus.iss.mobilelusis.utils.MConnectionHelper;
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
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;


public class RaiseVoucherActivity extends Activity{

	private ArrayList<StationeryItem> itemsToDiscrepance;
	private SearchItemListAdapter searchItemListAdapter;
	private DiscrepancyListAdapter discrepancyListAdapter; 
	
	private Employee employee = null;
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		employee = Temp.getEmployee();
		outState.putParcelableArrayList("ItemsToDescrepance",itemsToDiscrepance);
		super.onSaveInstanceState(outState);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_raise_voucher);
		
		if( savedInstanceState != null && savedInstanceState.containsKey("ItemsToDescrepance")) {
			itemsToDiscrepance = savedInstanceState.getParcelableArrayList("ItemsToDescrepance");
		}else{
			itemsToDiscrepance = new ArrayList<StationeryItem>();
		}
		
		this.employee = Temp.getEmployee();
		new GetAllItemsAsyncTask().execute();//Fetch all items and put the result to autoComplete View
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.raise_voucher, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		//Create new discrepancy on clicking submit button on Menu Activity
		if (id == R.id.raise_adjustment_menuitem) {
			//Make a post request
			new RaiseAdjustmentVoucherAsyncTask().execute();
			
			for (StationeryItem sItem : itemsToDiscrepance) {
				Log.i("sItem", Integer.toString(sItem.getQuantity()));
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void refreshData() {
		discrepancyListAdapter.notifyDataSetChanged();
	}
	
	
	/********************************* Async Tasks ******************************/
	/*
	 * Get All Items 
	 * GET -> http://domain/LUSISService/Service.svc/searchItems/?query=
	 */
	private class GetAllItemsAsyncTask extends AsyncTask<String, ArrayList<StationeryItem> , ArrayList<StationeryItem>>{

		@Override
		protected ArrayList<StationeryItem> doInBackground(String... params) {
			
			 return  MConnectionHelper.getAllStationeryItems();//Select all items 
		}
		
		@Override
		protected void onPostExecute(ArrayList<StationeryItem> result) {
			
			//create new Adapter by giving the feteched items 
			searchItemListAdapter = new SearchItemListAdapter(RaiseVoucherActivity.this, result);
			//Create adapter to show the selected items from search box.initially fill with blank array list
			discrepancyListAdapter = new DiscrepancyListAdapter(RaiseVoucherActivity.this,itemsToDiscrepance);
			
			
			//AutoCompleteView
			final AutoCompleteTextView searchBox = (AutoCompleteTextView) findViewById(R.id.id_txt_raisevoucher_search_textbox);
			//fill data by setting the setting adapter
			searchBox.setAdapter(searchItemListAdapter);
			//Listen to ClickEvent
			searchBox.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					StationeryItem item = (StationeryItem) parent.getItemAtPosition(position);
					itemsToDiscrepance.add(item);
					
					if (!searchBox.getText().toString().isEmpty()) {
						searchBox.setText("");
					}
					discrepancyListAdapter.notifyDataSetChanged();
				}
			});			
			
			
			//Create ListView 
			ListView discpreanciesItemListView = (ListView) findViewById(R.id.id_listview_raisevoucher_discrepance_items);
			//S
			discpreanciesItemListView.setAdapter(discrepancyListAdapter);
			//Create dialog upon clicking item on list
			discpreanciesItemListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					new UpdateDisbursementDialog(RaiseVoucherActivity.this,discrepancyListAdapter.getItem(position)).show(getFragmentManager(), IConstants.updateDisbursementDialogTag);
				}
			});
			
			super.onPostExecute(result);
		}		
		
	}
	
	/*
	 * New Discrepancy 
	 * POST -> http://domain/LUSISService/Service.svc/searchItems/?query=
	 */
	private class RaiseAdjustmentVoucherAsyncTask extends AsyncTask<Void, Void, Boolean> {
		
		public RaiseAdjustmentVoucherAsyncTask() {
			super();
		}

		protected void onPostExecute(Boolean status) {
			// Return to main page
			if (status.booleanValue() == true) {
				Toast.makeText(RaiseVoucherActivity.this, "Voucher raised", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(RaiseVoucherActivity.this, "Failed to raise voucher", Toast.LENGTH_LONG).show();
			}
			Intent intent = new Intent(RaiseVoucherActivity.this, StoreClerkMenuActivity.class);
			startActivity(intent);

		}


		@Override
		protected Boolean doInBackground(Void ... nothin) {

			// This is where we send the data
			JSONObject object = JSONHandler.createVouchersToRaise(employee, itemsToDiscrepance);		
			Log.i("raise me up", object.toString());
			String url = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_RAISE_VOUCHER;

			String result = JSONParser.postStream(url, object.toString());
			
			boolean b = false;
			if (result != null) {
				Log.i("Raise result", result);
				result = result.replace("\n", "").replace("\r", "");
				b = Boolean.valueOf(result);
			}
			return b;			
		}		
	}
	
}
