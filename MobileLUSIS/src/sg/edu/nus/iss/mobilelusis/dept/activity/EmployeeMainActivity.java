package sg.edu.nus.iss.mobilelusis.dept.activity;

import sg.edu.nus.iss.mobilelusis.LoginActivity;
import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class EmployeeMainActivity extends Activity {
	
	private Employee employee = null;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employee_main);
		
		
		Bundle data = getIntent().getExtras();		
		employee = data.getParcelable(IConstants.EXTRA_EMPLOYEE);
		
		
		TextView newRequisitionTextView = (TextView)findViewById(R.id.employee_new_requisition_btn);
		newRequisitionTextView.setOnClickListener(new OnClickListener() {

			
			public void onClick(View v) {
				Intent intent = new Intent(EmployeeMainActivity.this, NewRequisitionSearchActivity.class);				
				intent.putExtra(IConstants.EXTRA_EMPLOYEE, employee);
				startActivity(intent);
			}
			
		});
		
		TextView viewRequisitionTextView = (TextView)findViewById(R.id.employee_view_requisition_btn);
		viewRequisitionTextView.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(EmployeeMainActivity.this, EmployeeViewRequisitionActivity.class);
				intent.putExtra(IConstants.EXTRA_EMPLOYEE, employee);
				startActivity(intent);
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.employee_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.employee_main_logout_menuitem) {
			this.employee = null; // Clear employee object
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
