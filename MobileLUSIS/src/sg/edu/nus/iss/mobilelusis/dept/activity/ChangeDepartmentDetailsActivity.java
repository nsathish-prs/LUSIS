package sg.edu.nus.iss.mobilelusis.dept.activity;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.dept.controller.DeptDetailsController;
import sg.edu.nus.iss.mobilelusis.model.CollectionPoint;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeDepartmentDetailsActivity extends Activity implements OnItemClickListener {
	
	// Variables
	private Employee deptHead = null;
	DeptDetailsController controller = null;

	// UI
	private ListView collectionPointsListView  =null;	
	private TextView representativeTextField = null;
	private TextView collectionPointTextField = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.activity_change_department_details);

		deptHead = getIntent().getExtras().getParcelable("dept-head");

		controller = new DeptDetailsController();
		representativeTextField = (TextView)findViewById(R.id.rep_textview);
		representativeTextField.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ChangeDepartmentDetailsActivity.this, ChooseEmployeeListViewActivity.class);
				intent.putExtra("parent-activity", 1);
				intent.putExtra("dept-head", deptHead);
				intent.putExtra("request-type", "Representative"); // Web side will check any employee that has a role other than Representative, and will filter them out before sending to mobile.
				
				if (controller.getEmployeeList().length > 0) {
					intent.putExtra(IConstants.EMPLOYEES, controller.getEmployeeList());
				}
				
				
				startActivityForResult(intent, IConstants.CHOOSE_REPRESENTATIVE);
			}
			
		});

		new DepartmentDetailsAsyncTask().execute(deptHead.getDeptCode());
				
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == IConstants.CHOOSE_REPRESENTATIVE) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	Bundle bundle = data.getExtras();
	        	Employee newRepresentative = bundle.getParcelable("new-representative");
	        	if (newRepresentative != null) {		        	
	        		controller.setNewRepresentative(newRepresentative);
		        	representativeTextField.setText(newRepresentative.getName());
	        	}
	        }
	    }
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_department_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();		
		if (id == R.id.dept_details_save_menu_item) {
			
			ChangeDeptDetailsAsyncTask task = new ChangeDeptDetailsAsyncTask();
			task.execute();
			
		} else if (id == R.id.dept_details_cancel_menu_item) {
			Intent intent = new Intent(this, DeptHeadMainActivity.class);
			intent.putExtra(IConstants.EXTRA_EMPLOYEE, deptHead);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		CollectionPoint newCollectionPoint = controller.getCollectionPoints()[position];    	
		controller.setNewCollectionPoint(newCollectionPoint);
		collectionPointTextField.setText(newCollectionPoint.getLocation());		
	}
	
	
	// This is used to send the new department details to the server
	private class ChangeDeptDetailsAsyncTask extends AsyncTask<Void, Void, Boolean> {
		
		public ChangeDeptDetailsAsyncTask() {
			super();
		}

		protected void onPostExecute(Boolean status) {
			// Return to main page
			if (status.booleanValue() == true) {
				Toast.makeText(ChangeDepartmentDetailsActivity.this, "Department Details changed", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(ChangeDepartmentDetailsActivity.this, DeptHeadMainActivity.class);
				intent.putExtra(IConstants.EXTRA_EMPLOYEE, deptHead);
				startActivity(intent);
			}
		}


		@Override
		protected Boolean doInBackground(Void ... nothin) {
			return controller.changeDeptDetails(deptHead.getDeptCode());
		}		
	}
	
	
	// This is used to obtain the initial (current) state of the dept. details to populate the view.
	private class DepartmentDetailsAsyncTask extends AsyncTask<String, Void, Boolean> {
		
		protected void onPostExecute(Boolean status) {
			if (status == true) {
				collectionPointsListView = (ListView)findViewById(R.id.select_dept_details_listview);
				CollectionPoint [] collectionPoints = controller.getCollectionPoints();
				ArrayAdapter<CollectionPoint> adapter = new ArrayAdapter<CollectionPoint>(ChangeDepartmentDetailsActivity.this, android.R.layout.simple_list_item_1, collectionPoints);
				collectionPointsListView.setAdapter(adapter);
				collectionPointsListView.setOnItemClickListener(ChangeDepartmentDetailsActivity.this);

				Employee originalRepresentative = controller.getOriginalRepresentative();
				CollectionPoint oldCollectionPoint = controller.getOldCollectionPoint();
				
				int collectionPointIndex = adapter.getPosition(oldCollectionPoint);

				//set the default according to value
				collectionPointsListView.setSelection(collectionPointIndex);
				// Now get selected collection point
				collectionPointTextField = (TextView)findViewById(R.id.collection_point_textview);
				collectionPointTextField.setText(oldCollectionPoint.getLocation());
				representativeTextField.setText(originalRepresentative.getName());				
			}//end if (status == true)
		}
		
		
		@Override
		protected Boolean doInBackground(String... deptCodes) {
			String deptCode = deptCodes[0];			
			return  controller.getDeptDetails(deptCode);
		}
	}
	
	

}
