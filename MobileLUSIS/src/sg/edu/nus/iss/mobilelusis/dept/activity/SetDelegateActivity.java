package sg.edu.nus.iss.mobilelusis.dept.activity;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.dept.controller.DatePickerController;
import sg.edu.nus.iss.mobilelusis.dept.controller.SetDelegateController;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class SetDelegateActivity extends Activity {
	
//	private Employee selectedDelegate = null;
//	private Employee deptHead = null;

	private TextView delegateTextView = null;
	private TextView startDateTextView = null;
	private TextView endDateTextView = null;	

	private SetDelegateController controller = null;
	
	//private DatePickerController dpController = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_delegate);

		Employee deptHead = getIntent().getExtras().getParcelable("dept-head");

		// First, create controller
		controller = new SetDelegateController(this, deptHead);
		
		
		delegateTextView = (TextView)findViewById(R.id.delegate_textview);
		delegateTextView.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				Intent intent = new Intent(SetDelegateActivity.this, ChooseEmployeeListViewActivity.class);				
				intent.putExtra("parent-activity", 0);
				intent.putExtra("dept-head", controller.getDeptHead());
				intent.putExtra("request-type", "Delegate"); // Web side will check any employee that has a role other than Representative, and will filter them out before sending to mobile.
				startActivityForResult(intent, IConstants.CHOOSE_DELEGATE);
			}			
		});
		
		
		startDateTextView = (TextView)findViewById(R.id.start_date_textview);
		startDateTextView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				DatePickerController dpController = controller.getDatePickerController();
				dpController.setUIState(true, View.VISIBLE, dpController.getStartDate());
			}
		});

		endDateTextView = (TextView)findViewById(R.id.end_date_textview);
		endDateTextView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				DatePickerController dpController = controller.getDatePickerController();
				dpController.setUIState(false, View.VISIBLE, dpController.getEndDate());
			}
		});

		
		new GetInitialDelegateAsyncTask(this).execute();
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_delegate, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.delegate_save_menu_item) {
			
			
			new ChangeDelegateAsyncTask(this).execute(false);
			
		} else if (id == R.id.delegate_cancel_item) {
			Intent intent = new Intent(this, DeptHeadMainActivity.class);
			intent.putExtra(IConstants.EXTRA_EMPLOYEE, controller.getDeptHead());
			startActivity(intent);
		} else if (id == R.id.delegate_clear_item) {
			// Do alert box first
			new ChangeDelegateAsyncTask(this).execute(true);
		}
		
		
		
		
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request we're responding to
		if (requestCode == IConstants.CHOOSE_DELEGATE) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				Employee selectedDelegate = bundle.getParcelable("new-delegate");
				if (selectedDelegate != null) {
					controller.setDelegate(selectedDelegate);					
					delegateTextView.setText(selectedDelegate.getName());
				} else {					
					delegateTextView.setText(IConstants.EMPTY_DELEGATE);
				}
			}
		}
	}
	
	
	private class GetInitialDelegateAsyncTask extends AsyncTask<Void, Void, Boolean> {
		public GetInitialDelegateAsyncTask(SetDelegateActivity activity) {
			super();
		}
		
		
		@Override
		protected void onPostExecute(Boolean status) {
			if (status == true) {
				
				DatePickerController dpController = controller.getDatePickerController();
				// Initiate the date picker
				dpController.setDatePicker((DatePicker)findViewById(R.id.datePicker));
				dpController.initiateDatePicker();
				dpController.initiateSetDateButton((Button)findViewById(R.id.set_date_btn));
				
				dpController.initiateEndDateButton((Button)findViewById(R.id.clear_end_date_button));
				
				
				delegateTextView.setText(controller.getCurrentDelegateName());				
				startDateTextView.setText(controller.getDatePickerController().getDateString(true));
				endDateTextView.setText(controller.getDatePickerController().getDateString(false));
				
			}//end if(status == true)
		}
		
		protected Boolean doInBackground(Void... nothing) {
			return  controller.getInitialDelegates();
		}
	}
	

	private class ChangeDelegateAsyncTask extends AsyncTask<Boolean, Void, Boolean> {
		
		private SetDelegateActivity activity = null;
		public ChangeDelegateAsyncTask(SetDelegateActivity activity) {
			super();
			this.activity = activity;
		}
		
		
		@Override
		protected void onPostExecute(Boolean status) {
			if (status == true) {
					Toast.makeText(activity, "Save Delegate Authority succeeded.", Toast.LENGTH_LONG).show();
			} else {
					Toast.makeText(activity, "Save Delegate Authority failed.", Toast.LENGTH_LONG).show();
			}
				
			Intent intent = new Intent(activity, DeptHeadMainActivity.class);			
			intent.putExtra(IConstants.EXTRA_EMPLOYEE, controller.getDeptHead());
			startActivity(intent);

		}
		
		protected Boolean doInBackground(Boolean... isClears) {
			boolean isClear = isClears[0];
			return  controller.changeDelegate(isClear);
		}
	}

	
	

}
