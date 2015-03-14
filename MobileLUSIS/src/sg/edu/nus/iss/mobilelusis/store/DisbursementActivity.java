package sg.edu.nus.iss.mobilelusis.store;

import java.util.ArrayList;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.Disbursement;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.store.Adapter.DisbursementListAdapter;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.MConnectionHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class DisbursementActivity extends Activity implements OnItemClickListener{
	
	DisbursementListAdapter disbListAdapter;
	Disbursement disbursement;
	ArrayList<Disbursement> disbursementList;
	private Employee employee;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disbursement);
		setTitle(R.string.title_activity_disbursement);	
		new GetDisbursementListAsynctask().execute();
		
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, DisbursementDetailActivity.class);
		Disbursement selectedDisburseItem = (Disbursement) parent.getAdapter().getItem(position);
		i.putExtra("Disbursement", selectedDisburseItem);
		startActivity(i);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return true;
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra(IConstants.EXTRA_EMPLOYEE, employee);
		super.finish();
	}
	
	
	
	/*
	 * Get Disbursement Object
	 * GET -> http://domain/LUSISService/Service.svc/viewretrievallist
	 */
	private class GetDisbursementListAsynctask extends AsyncTask<String, Void, ArrayList<Disbursement>>{

		@Override
		protected ArrayList<Disbursement> doInBackground(String... params) {
			
			 return MConnectionHelper.getDisbursementData();
			 
		}
		
		@Override
		protected void onPostExecute(ArrayList<Disbursement> result) {
			
			ListView disbListView = (ListView) findViewById(R.id.id_disbursement_listview);
			disbListAdapter = new DisbursementListAdapter(DisbursementActivity.this, result);
			disbListView.setAdapter(disbListAdapter);
			disbListView.setOnItemClickListener(DisbursementActivity.this);
			super.onPostExecute(result);
		}		
		
	} 
		
	
	
}

						