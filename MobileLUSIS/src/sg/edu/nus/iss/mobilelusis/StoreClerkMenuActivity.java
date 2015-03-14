package sg.edu.nus.iss.mobilelusis;

import java.util.ArrayList;

import org.json.JSONObject;


import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.Retrieval;
import sg.edu.nus.iss.mobilelusis.store.DisbursementActivity;
import sg.edu.nus.iss.mobilelusis.store.RaiseVoucherActivity;
import sg.edu.nus.iss.mobilelusis.store.RetrievalActivity;
import sg.edu.nus.iss.mobilelusis.store.ViewVoucherActivity;
import sg.edu.nus.iss.mobilelusis.store.dialog.ConfirmLogoutDialog;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.MConnectionHelper;
import android.app.Activity;
import android.app.PendingIntent.OnFinished;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class StoreClerkMenuActivity extends Activity {

	private Employee employee;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_clerk_menu);
		employee = Temp.getEmployee();
		
		Button btn_generate_retrieval_list = (Button) findViewById(R.id.btn_generate_retireval_list);
		btn_generate_retrieval_list.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				new GenerateRetreivalListAsyncTask().execute();
				
			}
		});
		
		Button btn_view_retrieval_list = (Button) findViewById(R.id.btn_view_retireval_list);
		btn_view_retrieval_list.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// View Retrieval List
				Intent intent = new Intent(StoreClerkMenuActivity.this,RetrievalActivity.class);
				startActivity(intent);
			}
		});
		
		Button btn_disbursement_list = (Button) findViewById(R.id.btn_disbursement_list);
		btn_disbursement_list.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// Disbursement List
				Intent intent = new Intent(StoreClerkMenuActivity.this,DisbursementActivity.class);					
				startActivity(intent);
			}
		});
		
		
		Button btn_raiseVoucher = (Button) findViewById(R.id.btn_raise_adj_voucher);
		btn_raiseVoucher.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(StoreClerkMenuActivity.this,RaiseVoucherActivity.class);					
				startActivity(intent);
			}
		});
		
		
		Button btn_viewVoucher = (Button) findViewById(R.id.btn_view_adj_voucher);
		btn_viewVoucher.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(StoreClerkMenuActivity.this, ViewVoucherActivity.class);					
				startActivity(intent);
			}	
		});
		Temp.putEmployee(employee);
		
	}
	
	@Override
	public void finish() {
		new ConfirmLogoutDialog(this).show(getFragmentManager(), IConstants.logoutDialogTag);
		super.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.store_clerk_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.store_logout_menuitem) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	
	/********************************* Async Tasks ******************************/
	 
	/*
	 * Get Retrieval Object
	 * GET -> http://domain/LUSISService/Service.svc/viewretrievallist
	 */
	private class GenerateRetreivalListAsyncTask extends AsyncTask<String, Void, JSONObject>{

		@Override
		protected JSONObject doInBackground(String... params) {
			
			 return MConnectionHelper.generateRetirevalList();
		}
		
		@Override
		protected void onPostExecute(JSONObject result) {
			
			Toast.makeText(StoreClerkMenuActivity.this, "Retrieval List Generated Successfully", Toast.LENGTH_SHORT).show();
			if (result == null) {
				Log.i("response","Null");
			}
			
			
		}		
		
	}
}
