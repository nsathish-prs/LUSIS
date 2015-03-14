package sg.edu.nus.iss.mobilelusis.store;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.Temp;
import sg.edu.nus.iss.mobilelusis.model.Discrepancy;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.ROLE;
import sg.edu.nus.iss.mobilelusis.model.VoucherItem;
import sg.edu.nus.iss.mobilelusis.store.Adapter.ViewVoucherListAdapter;
import sg.edu.nus.iss.mobilelusis.supervisor.ApproveVoucherActivity;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.JSONHandler;
import sg.edu.nus.iss.mobilelusis.utils.MConnectionHelper;
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

public class ViewVoucherActivity extends Activity implements OnItemClickListener {
	
	private Employee employee = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_voucher);
		
		// Get Employee who is logged in
				
		employee = Temp.getEmployee();	
		new GetDiscrepancyListAsynctask().execute(employee);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_voucher, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/*
	 * Get Discrepancy List
	 * GET -> http://domain/LUSISService/Service.svc/viewretrievallist
	 */
	private class GetDiscrepancyListAsynctask extends AsyncTask<Employee, Void, List<Discrepancy>>{
		
		@Override
		protected List<Discrepancy> doInBackground(Employee... emps) {
			Employee employee = emps[0];

			JSONArray array = MConnectionHelper.getDiscrepancyList(employee);
			List<Discrepancy> discrepancies = JSONHandler.getDiscrepanciesForViewing(array, employee.getRole());
			return discrepancies;
		}
		
		@Override
		protected void onPostExecute(List<Discrepancy> result) {
			ViewVoucherListAdapter viewVoucherListAdapter = new ViewVoucherListAdapter(ViewVoucherActivity.this, result, employee);
			ListView listView = (ListView) findViewById(R.id.id_listview_viewVouher);
			listView.setAdapter(viewVoucherListAdapter);
			listView.setOnItemClickListener(ViewVoucherActivity.this);
			super.onPostExecute(result);
		}		
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Discrepancy item = (Discrepancy) parent.getAdapter().getItem(position);
		String voucherId = item.getVoucherID();
		String empId = employee.getId();

		new GetDiscrepancyDetailsAsynctask(item, empId).execute();

	} 
	
	
	
	/*
	 * Get Discrepancy List
	 * GET -> http://domain/LUSISService/Service.svc/viewretrievallist
	 */
	private class GetDiscrepancyDetailsAsynctask extends AsyncTask<Void, Void, List<VoucherItem>>{
		
		private Discrepancy discrepancy = null;
		private String empId = null;
		
		public GetDiscrepancyDetailsAsynctask(Discrepancy discrepancy, String empId) {
			super();
			this.discrepancy = discrepancy;
			this.empId = empId;
		}
		
//		IConstants.WCF_DISCREPANCY_DETAILS_FOR_VIEWING;
		
		
		// SANNY WHAT DO YOU WANT TO DO WITH THIS?

		@Override
		protected List<VoucherItem> doInBackground(Void... v) {
			JSONArray array = MConnectionHelper.getDiscrepancyDetail(empId, discrepancy);
			List<VoucherItem> items = JSONHandler.unparseVoucherItems(array);
			return items;
		}
		
		@Override
		protected void onPostExecute(List<VoucherItem> result) {
			
			boolean isClerk = employee.getRole() == ROLE.CLERK;
			Intent intent = new Intent(ViewVoucherActivity.this, ApproveVoucherActivity.class);
			intent.putExtra("VOUCHER_ID", discrepancy.getVoucherID());
			intent.putExtra("VOUCHERITEMS", result.toArray(new VoucherItem[result.size()]));
			intent.putExtra("DATE", discrepancy.getDate().getTime());
			intent.putExtra(IConstants.EXTRA_EMPLOYEE, employee);
			intent.putExtra("ISCLERK", isClerk);
			intent.putExtra("DISCREPANCY", discrepancy);
			if (isClerk)
				intent.putExtra("BY", discrepancy.getApprovedBy());
			else 
				intent.putExtra("BY", discrepancy.getRaisedBy());
			
			startActivity(intent);

		}
		
	}
	
	
}
