package sg.edu.nus.iss.mobilelusis.dept.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.dept.controller.ApproveRequisitionController;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.Requisition;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.IJSONConstants;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DeptHeadApproveRequisitionActivity extends Activity implements
ApproveRequisitionNavigationDrawerFragment.ApproveReqNavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private ApproveRequisitionNavigationDrawerFragment mNavigationDrawerFragment; 
	private ApproveRequisitionController controller = null;

	private String chosenRequisition = null;
	
	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dept_head_approve_requisition);

		mNavigationDrawerFragment = (ApproveRequisitionNavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.ar_navigation_drawer);
		mTitle = getTitle();
		
		Employee deptHead = getIntent().getExtras().getParcelable("dept-head");
		

		controller = new ApproveRequisitionController(this, deptHead);		
		new GetDeptRequisitionsAsyncTask().execute(controller);		
	}

	public ApproveRequisitionController getController() {
		return this.controller;
	}
	
	public String getChosenRequisitionId() {
		return this.chosenRequisition;
	}

	public void onNavigationDrawerItemSelected(int position, String requisitionId) {
		// update the main content by replacing fragments
		chosenRequisition = requisitionId;
		
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager
		.beginTransaction()
		.replace(R.id.container,
				PlaceholderFragment.newInstance(position + 1, requisitionId, this)).commit();
	}

	public void onSectionAttached(int number, String requisitionId) {
		mTitle = requisitionId;
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.dept_head_approve_requisition,
					menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.approve_req_approve_menuitem) {
			
			
			new ApproveOrRejectRequisitionAsyncTask(this, true, IJSONConstants.JSON_EMPTY_STRING).execute(controller);
//			Toast.makeText(this, "Approve requisition to be implemented", Toast.LENGTH_LONG).show();
//			Intent intent = new Intent(this, DeptHeadMainActivity.class);
//			intent.putExtra(IConstants.EXTRA_EMPLOYEE, controller.getDeptHead());
//			startActivity(intent);
			//			return true;
		} else if (id == R.id.approve_req_reject_menuitem) {
			AlertDialog.Builder alert = showRejectDialog(this);
			alert.show();
		} else if (id == R.id.approve_done) {
			Intent intent = new Intent(this, DeptHeadMainActivity.class);
			intent.putExtra(IConstants.EXTRA_EMPLOYEE, controller.getDeptHead());
			startActivity(intent);
		} else if (id == R.id.approve_ok_menuitem) {					
			mNavigationDrawerFragment.openDrawer();
		}
		return super.onOptionsItemSelected(item);
	}



	private AlertDialog.Builder showRejectDialog(final DeptHeadApproveRequisitionActivity activity) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Reject Requisition");
		alert.setMessage("Please state reason (optional)");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				
				String rejectionReason = input.getText().toString();
				
				new ApproveOrRejectRequisitionAsyncTask(DeptHeadApproveRequisitionActivity.this, false, rejectionReason).execute(controller);
				
//				Toast.makeText(activity, "Reject requisition to be implemented", Toast.LENGTH_LONG).show();
//				Intent intent = new Intent(DeptHeadApproveRequisitionActivity.this, DeptHeadMainActivity.class);
//				intent.putExtra(IConstants.EXTRA_EMPLOYEE, controller.getDeptHead());
//				startActivity(intent);
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				//			  Toast.makeText(activity, "Reject requisition to be implemented", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(DeptHeadApproveRequisitionActivity.this, DeptHeadMainActivity.class);
				intent.putExtra(IConstants.EXTRA_EMPLOYEE, controller.getDeptHead());
				startActivity(intent);
			}
		});

		return alert;

	}
	
	// AsyncTask for obtaining list of Employee's requisitions for the Drawer.
	private class GetDeptRequisitionsAsyncTask extends AsyncTask<ApproveRequisitionController, Void, Map<String, Requisition>> {
		
		protected void onPostExecute(Map<String, Requisition> requisitions) {
			if (requisitions != null) {
				mNavigationDrawerFragment.setDrawerListData(requisitions);
				mNavigationDrawerFragment.setUp(R.id.ar_navigation_drawer,
						(DrawerLayout) findViewById(R.id.ar_drawer_layout));
				mNavigationDrawerFragment.openDrawer();
			}
		}

		@Override
		protected Map<String, Requisition> doInBackground(ApproveRequisitionController ... controllers) {
			ApproveRequisitionController controller = controllers[0];
			return controller.getRequisitionsGET();
		}
	}
	
	
	
	// AsyncTask for approving or rejecting employee's requisition submission.
	private class ApproveOrRejectRequisitionAsyncTask extends AsyncTask<ApproveRequisitionController, Void, Boolean> {
		
		private boolean isApprove = false;
		private DeptHeadApproveRequisitionActivity activity = null;
		private String rejectionReason = null;
		public ApproveOrRejectRequisitionAsyncTask(DeptHeadApproveRequisitionActivity activity, boolean isApprove, String rejectionReason) {
			super();
			this.isApprove = isApprove;
			this.activity = activity;
			this.rejectionReason = rejectionReason;
		}
		
		protected void onPostExecute(Boolean status) {			
			if (status == true) {
				Toast.makeText(activity, "Reject requisition submission succeeded.", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(activity, "Reject requisition submission failed.", Toast.LENGTH_LONG).show();
			}
			Intent intent = new Intent(activity, DeptHeadMainActivity.class);
			intent.putExtra(IConstants.EXTRA_EMPLOYEE, controller.getDeptHead());
			startActivity(intent);				

		}

		@Override
		protected Boolean doInBackground(ApproveRequisitionController ... controllers) {
			ApproveRequisitionController controller = controllers[0];
			return controller.approveRequisition(this.isApprove, activity.getChosenRequisitionId(), this.rejectionReason);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "ar_section_number";
		private static String selectedRequisitionId = null;
		private static DeptHeadApproveRequisitionActivity activity;

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */		
		public static PlaceholderFragment newInstance(int sectionNumber, String requisitionId, DeptHeadApproveRequisitionActivity cxt) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);			
			args.putString("requisitionId", requisitionId);
			fragment.setArguments(args);
			selectedRequisitionId = requisitionId;
			activity = cxt;
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_dept_head_approve_requisition, container,
					false);

			
			ApproveRequisitionController controller = activity.getController();
			Requisition requisition = controller.getSelectedRequisition(selectedRequisitionId);
			if (requisition != null) {

				TextView employeeTextView = (TextView)rootView.findViewById(R.id.approve_req_emp_textview);
				employeeTextView.setText(requisition.getEmployee().getId());
	
				TextView dateTextView = (TextView)rootView.findViewById(R.id.approve_req_date_submitted_textview);
				dateTextView.setText(requisition.getSubmitDate());
	
				TextView commentTextView = (TextView)rootView.findViewById(R.id.approve_req_emp_comments_textview);
				commentTextView.setText(requisition.getEmpComments());
	
	
				StationeryItem [] stationeryItems = requisition.getStationeryItems();
				List<String> items = new ArrayList<String>();
				for (StationeryItem i : stationeryItems) {
					items.add(i.getItemName() + "   " + Integer.toString(i.getQuantity()) + " " + i.getItemUnit());
				}
				ListView itemsListView = (ListView)rootView.findViewById(R.id.id_listview_disb_itemRequestedList);
				String [] itemsAsList = items.toArray(new String[items.size()]);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, itemsAsList);
				itemsListView.setAdapter(adapter);
			}
			
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((DeptHeadApproveRequisitionActivity) activity)
			.onSectionAttached(getArguments()
					.getInt(ARG_SECTION_NUMBER), getArguments().getString("requisitionId"));
		}
	}
	
}
