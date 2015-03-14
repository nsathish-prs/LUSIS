package sg.edu.nus.iss.mobilelusis.store;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.Temp;
import sg.edu.nus.iss.mobilelusis.model.Disbursement;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.Retrieval;
import sg.edu.nus.iss.mobilelusis.store.Adapter.DisbursementDetailListAdapter;
import sg.edu.nus.iss.mobilelusis.store.dialog.DisbusementConfirmationDialog;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.MConnectionHelper;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class DisbursementDetailActivity extends Activity  {

	private DisbursementDetailListAdapter disbDetailAdapter;
	public  Switch switch_approve;
	private ImageView img_signature;
	private Disbursement disbursement ;
	private Employee employee;
	private TextView date;
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putParcelable("Disbursement", disbursement);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disbursement_detail);
		employee = Temp.getEmployee();
		
		Bundle data = getIntent().getExtras();
		if (savedInstanceState != null && savedInstanceState.containsKey("Disbursement")) {
			disbursement = savedInstanceState.getParcelable("Disbursement");	
		}else{
			disbursement = (Disbursement) data.getParcelable("Disbursement");
		}		
		
		
		TextView colPoint = (TextView) findViewById(R.id.id_txt_disb_colPoint);
		TextView deptRep = (TextView) findViewById(R.id.id_txt_disb_deptRepName);
		date = (TextView) findViewById(R.id.id_txt_disb_approved_date);
		switch_approve = (Switch) findViewById(R.id.id_switch_disb_approve);
		img_signature = (ImageView) findViewById(R.id.id_img_disb_signature);
		
		switch_approve.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		        	DisbusementConfirmationDialog dialog = new DisbusementConfirmationDialog(DisbursementDetailActivity.this,disbursement.getEmpID());
		        	
		        	dialog.show(getFragmentManager(), IConstants.disbursement_acknowledgementDialogTag);
		        	
		        } else {
		            Toast.makeText(getApplicationContext(), "Disbursement acknowledgement unapproved" , Toast.LENGTH_SHORT).show();
		        }
		    }
		});
		
		setTitle(disbursement.getDeptName());
		colPoint.setText(disbursement.getLocation());
		deptRep.setText(disbursement.getRepresentativeName());
		
		ListView disbItemListView = (ListView) findViewById(R.id.id_listview_disb_itemRequestedList);
		disbDetailAdapter = new DisbursementDetailListAdapter(this, disbursement);
		disbItemListView.setAdapter(disbDetailAdapter);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra(IConstants.EXTRA_EMPLOYEE, employee);
		super.finish();
	}
	
	public void approve(String pass, String empID, boolean flags) {
		// TODO Auto-generated method stub
		img_signature.setImageResource(R.drawable.ic_loader);
		
		if(flags){
			disbursement.setStatus("Disbursed");
		}
		new ApproveAsyncTask().execute(empID,pass);
		
		
	} 
	
	
	/********************************* Async Tasks ******************************/
	private class ApproveAsyncTask extends AsyncTask<String, Void, Bitmap >{

		@Override
		protected Bitmap doInBackground(String... params) {			
			Employee deptRep = new Employee();
			deptRep.setId(params[0]);
			deptRep.setPassword(params[1]);
			
			
			
			JSONObject response = MConnectionHelper.postDisbursementApproval(deptRep, disbursement);
			Bitmap bmp = null;
			Log.i("Response",response.toString());
			try {
				if (response.getString("Response") != null) {
					bmp = MConnectionHelper.getImageByName(deptRep.getId());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bmp;
		}
		
		@Override
		protected void onPostExecute(Bitmap bmp) {
			// TODO Auto-generated method stub
			img_signature.setImageBitmap(bmp);
			switch_approve.setVisibility(Switch.INVISIBLE);
			
			Calendar instance = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
			date.setText( formatter.format(instance.getTime()) );
			date.setVisibility(View.VISIBLE);
			super.onPostExecute(bmp);
		}
		
	}


	
}
