package sg.edu.nus.iss.mobilelusis.supervisor;

import sg.edu.nus.iss.mobilelusis.LoginActivity;
import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.store.ViewVoucherActivity;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SupervisorMenuActivity extends Activity {
	
	private Employee supervisor;
	
	private static int APPROVE_PURCHASE_ORDER = 1;
	private static int APPROVE_VOUCHER = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_supervisor_menu);
		setTitle("Supervisor");
		
		supervisor = (Employee)getIntent().getExtras().get(IConstants.EXTRA_EMPLOYEE);
		
		Button appPo = (Button) findViewById(R.id.id_btn_approve_po);
		Button appAdj = (Button) findViewById(R.id.id_btn_approve_adj_voucher);
		
		appPo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SupervisorMenuActivity.this, ApprovePurchaseOrder.class);
				intent.putExtra(IConstants.EXTRA_EMPLOYEE, supervisor);
				startActivityForResult(intent, APPROVE_PURCHASE_ORDER);
//				startActivity(new Intent(SupervisorMenuActivity.this, ApprovePurchaseOrder.class));
			}
		});
		
		appAdj.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SupervisorMenuActivity.this, ViewVoucherActivity.class);
				intent.putExtra(IConstants.EXTRA_EMPLOYEE, supervisor);
				startActivityForResult(intent, APPROVE_VOUCHER);
//				startActivity(new Intent(SupervisorMenuActivity.this, ApproveVoucherActivity.class));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.supervisor_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.supervisor_logout_menuitem) {
			this.supervisor = null; // Clear employee object
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == APPROVE_PURCHASE_ORDER || requestCode == APPROVE_VOUCHER) {
	        if (resultCode == RESULT_OK) {
	        	Bundle bundle = data.getExtras();
	        	Employee supervisor = bundle.getParcelable(IConstants.EXTRA_EMPLOYEE);
	        	if (supervisor != null) {		        	
	        		this.supervisor = supervisor;
	        	}
	        }
	    }
	}

	
}
