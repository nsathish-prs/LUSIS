package sg.edu.nus.iss.mobilelusis.dept.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.dept.adapter.NewRequisitionSearchListAdapter;
import sg.edu.nus.iss.mobilelusis.dept.controller.SearchStationeryItemsController;
import sg.edu.nus.iss.mobilelusis.model.Cart;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class NewRequisitionSearchActivity extends Activity implements OnItemClickListener {

	private Employee employee = null;
	private ArrayList<StationeryItem> items = null;
	private NewRequisitionSearchListAdapter adapter = null;
	private ListView matchingItemsListView = null;
	private Cart cart = null;

	private String reqID = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_requisition_search);

		Bundle data = getIntent().getExtras();
		employee = data.getParcelable(IConstants.EXTRA_EMPLOYEE);

		cart = new Cart();


		matchingItemsListView = (ListView)findViewById(R.id.search_results_listview);

		matchingItemsListView.setOnItemClickListener(this);

		items = new ArrayList<StationeryItem>();
		
		if (data.containsKey(IConstants.EXTRA_EXISTING_ITEMS)) {
			Parcelable [] parcelables = data.getParcelableArray(IConstants.EXTRA_EXISTING_ITEMS);
			for (Parcelable p : parcelables) {
				StationeryItem item = (StationeryItem)p;
				cart.addToCart(item);
			}
		}

		
		if (data.containsKey(IConstants.EXTRA_REQ_ID))
			reqID = data.getString(IConstants.EXTRA_REQ_ID);
		else 
			reqID = ""; // Just to be safe.
		
		adapter = new NewRequisitionSearchListAdapter(this, R.id.found_items_row, items);
		matchingItemsListView.setAdapter(adapter);


		Button searchBtn = (Button)findViewById(R.id.search_button);
		searchBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {								
				EditText keywordTextField = (EditText)findViewById(R.id.search_textview);
				String keyword = keywordTextField.getText().toString();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("keyword", keyword);
				map.put(IConstants.EXTRA_EMPLOYEE, employee);				
				new SearchItemsAsyncTask(NewRequisitionSearchActivity.this).execute(map);									
			}

		});
	}


	private class SearchItemsAsyncTask extends AsyncTask<Map<String, Object>, Void, ArrayList<StationeryItem>> {

		private Activity activity = null;

		public SearchItemsAsyncTask(Activity activity) {
			super();
			this.activity = activity;
		}


		protected void onPostExecute(ArrayList<StationeryItem> returnedItems) {
			items = new ArrayList<StationeryItem>();
			items = returnedItems;
			if (items == null || items.size() == 0) {				
				items = new ArrayList<StationeryItem>();
				Toast.makeText(activity, "No items matching your keyword was found", Toast.LENGTH_LONG).show();				
			}
			adapter = new NewRequisitionSearchListAdapter(NewRequisitionSearchActivity.this, R.id.found_items_row, items);
			matchingItemsListView.setAdapter(adapter);
			adapter.notifyDataSetChanged();			
		}

		@Override
		protected ArrayList<StationeryItem> doInBackground(Map<String, Object>... data) {			
			SearchStationeryItemsController controller = new SearchStationeryItemsController();
			Map<String, Object> datum = data[0];
			Employee emp = (Employee)datum.get(IConstants.EXTRA_EMPLOYEE);
			String keyword = (String)datum.get("keyword");			
			return controller.getItemsByKeyword(emp, keyword);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_requisition_search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.new_req_goto_cart_menuitem) {

			if (this.cart.getNumberOfItems() == 0) {
				Toast.makeText(this, "You have no items in your cart", Toast.LENGTH_LONG).show();
			} else {
				Intent intent = new Intent(NewRequisitionSearchActivity.this, CartActivity.class);
				intent.putExtra(IConstants.EXTRA_EMPLOYEE, employee);
				intent.putExtra("checkout_cart", cart);					
				intent.putExtra(IConstants.EXTRA_REQ_ID, reqID);  
				startActivityForResult(intent, IConstants.CART_ACTIVITY);
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		StationeryItem item = items.get(position);
		
		if (cart.getItem(item.getItemCode()) != null) {
			StationeryItem i = cart.getItem(item.getItemCode());
			item.setQuantity(i.getQuantity());
		}
		Intent intent = new Intent(this, NewRequisitionSingleItemActivity.class);
		intent.putExtra(IConstants.EXTRA_EMPLOYEE, employee);
		intent.putExtra("item", item);
		startActivityForResult(intent, IConstants.CHOOSE_SINGLE_ITEM_RESULT);
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode ==  IConstants.CHOOSE_SINGLE_ITEM_RESULT) {
			StationeryItem item = data.getParcelableExtra("item");
			if (item != null) {
				cart.addToCart(item);
			}
			int itemCount = cart.getNumberOfItems();
			Log.i("itemCount in cart", Integer.toString(itemCount));
		} else if (resultCode == RESULT_OK && requestCode == IConstants.CART_ACTIVITY) {
			
		}
	}	
}
