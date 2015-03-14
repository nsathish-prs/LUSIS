package sg.edu.nus.iss.mobilelusis.dept.activity;

import sg.edu.nus.iss.mobilelusis.LoginActivity;
import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.ROLE;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class DeptHeadMainActivity extends Activity {

	
	private Employee deptHead = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dept_head_main);

		deptHead = getIntent().getExtras().getParcelable(IConstants.EXTRA_EMPLOYEE);
		
		
		TextView approveRequisitionTextView = (TextView)findViewById(R.id.dept_head_approve_req_btn);
		approveRequisitionTextView.setOnClickListener(new OnClickListener() {

			
			public void onClick(View v) {
				Intent intent = new Intent(DeptHeadMainActivity.this, DeptHeadApproveRequisitionActivity.class);
				intent.putExtra("dept-head", deptHead);
				startActivity(intent);
			}
			
		});
		
		
		
		TextView changeDeptDetailsTextView = (TextView)findViewById(R.id.dept_head_change_dept_details_btn);
		changeDeptDetailsTextView.setOnClickListener(new OnClickListener() {

			
			public void onClick(View v) {
				Intent intent = new Intent(DeptHeadMainActivity.this, ChangeDepartmentDetailsActivity.class);
				intent.putExtra("dept-head", deptHead);
				startActivity(intent);
			}
			
		});
		
		TextView delegateAuthorityTextView = (TextView)findViewById(R.id.dept_head_set_delegate_btn);
		delegateAuthorityTextView.setOnClickListener(new OnClickListener() {

			
			public void onClick(View v) {
				Intent intent = new Intent(DeptHeadMainActivity.this, SetDelegateActivity.class);
				intent.putExtra("dept-head", deptHead);
				startActivity(intent);
			}			
		});
		
		if (deptHead.getAdditionalRole() == ROLE.DELEGATE) {
			delegateAuthorityTextView.setVisibility(View.INVISIBLE);
		}
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dept_head_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.dept_head_logout_menuitem) {
			deptHead = null; // clear off 
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
