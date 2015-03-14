package sg.edu.nus.iss.mobilelusis.supervisor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.dept.activity.ChangeDepartmentDetailsActivity;
import sg.edu.nus.iss.mobilelusis.dept.activity.DeptHeadMainActivity;
import sg.edu.nus.iss.mobilelusis.model.Discrepancy;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.ROLE;
import sg.edu.nus.iss.mobilelusis.model.VoucherItem;
import sg.edu.nus.iss.mobilelusis.store.ViewVoucherActivity;
import sg.edu.nus.iss.mobilelusis.store.Adapter.ApproveVoucherListAdapter;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.JSONHandler;
import sg.edu.nus.iss.mobilelusis.utils.JSONParser;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ApproveVoucherActivity extends Activity implements OnItemClickListener{

	private Employee employee = null;
	
	private List<VoucherItem> items = null;
	
	private Discrepancy discrepancy = null;
	
	private ApproveVoucherListAdapter appVoucherListAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_approve_voucher);
		
		items = new ArrayList<VoucherItem>();
		
		Bundle bundle = getIntent().getExtras();
		employee = bundle.getParcelable(IConstants.EXTRA_EMPLOYEE);

		discrepancy = bundle.getParcelable("DISCREPANCY");
		
		boolean isClerk = bundle.getBoolean("ISCLERK");
		
		String voucherId = bundle.getString("VOUCHER_ID");
		
		Parcelable [] vItems = bundle.getParcelableArray("VOUCHERITEMS");
		for (Parcelable p : vItems) {
			VoucherItem i = (VoucherItem)p;
			items.add(i);
		}
		
		long time = bundle.getLong("DATE");
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		Date date = c.getTime();
		String dateString = IConstants.sdf.format(date);

		String person = bundle.getString("BY");
		
		
		TextView voucherIdView = (TextView)findViewById(R.id.approve_voucher_id);
		voucherIdView.setText(voucherId);
		
		TextView voucherDateView = (TextView)findViewById(R.id.approve_voucher_date);
		voucherDateView.setText(dateString);
		
		
		TextView roleView = (TextView)findViewById(R.id.approve_voucher_role);
		if (isClerk) {
			roleView.setText("Clerk:");
		} else {
			roleView.setText("Supervisor:");
		}
		
		TextView personView = (TextView)findViewById(R.id.approve_voucher_person);
		personView.setText(person);
		
		
		
		
//		ApproveVoucherListAdapter appVoucherListAdapter = new ApproveVoucherListAdapter(ApproveVoucherActivity.this,getDummyData());
		appVoucherListAdapter = new ApproveVoucherListAdapter(this, items, isClerk, discrepancy);
		ListView listView = (ListView) findViewById(R.id.id_listview_approve_voucher);
		listView.setAdapter(appVoucherListAdapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.approve_voucher, menu);
		return true;
	}
	
//	public boolean onPrepareOptionsMenu (Menu menu) {
//		MenuItem mItem = menu.getItem(R.id.approve_voucher_menuItem);
//		if (employee.getRole() == ROLE.CLERK) {
//			mItem.setTitle("OK");
//		} else {
//			mItem.setTitle("Save");
//		}
//		
//		return true;
//	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.approve_voucher_menuItem) {
			
			if (employee.getRole() == ROLE.CLERK) {
				Intent intent = new Intent(this, ViewVoucherActivity.class);
				intent.putExtra(IConstants.EXTRA_EMPLOYEE, employee);
				startActivity(intent);
			} else {				
				List<VoucherItem> voucherItems = appVoucherListAdapter.getItems();
				new DiscrepancyApproveAsyncTask(discrepancy, voucherItems).execute();				
			}
			
			
			return true;
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
	

	@Override
	public void finish() {
		Intent intent = new Intent();	
		intent.putExtra(IConstants.EXTRA_EMPLOYEE, this.employee);
		setResult(RESULT_OK, intent);
		super.finish();
	}
	
	
	private class DiscrepancyApproveAsyncTask extends AsyncTask<Void, Void, Boolean> {
		
		private Discrepancy discrepancy = null;
		private List<VoucherItem> voucherItems = null;

		public DiscrepancyApproveAsyncTask(Discrepancy discrepancy, List<VoucherItem> voucherItems) {
			super();
			this.discrepancy = discrepancy;
			this.voucherItems = voucherItems;
		}

		protected void onPostExecute(Boolean status) {
			// Return to main page
			if (status.booleanValue() == true) {
				Toast.makeText(ApproveVoucherActivity.this, "Voucher items approval / rejection succeeded.", Toast.LENGTH_LONG).show();

			} else {
				Toast.makeText(ApproveVoucherActivity.this, "Voucher items approval / rejection failed.", Toast.LENGTH_LONG).show();
			}
			Intent intent = new Intent(ApproveVoucherActivity.this, ViewVoucherActivity.class);
			intent.putExtra(IConstants.EXTRA_EMPLOYEE, employee);
			startActivity(intent);
		}


		@Override
		protected Boolean doInBackground(Void ... voids) {
			
			JSONArray array = JSONHandler.createVoucherApprovalObject(discrepancy, voucherItems);
			
			String url = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_APPROVE_VOUCHER;			
			String result = JSONParser.postStream(url, array.toString());
			
			boolean b = false;
			if (result != null) {
				result = result.replace("\n", "").replace("\r", "");
				b = Boolean.valueOf(result);
			}
			return b;
		}		
	}

	
}
