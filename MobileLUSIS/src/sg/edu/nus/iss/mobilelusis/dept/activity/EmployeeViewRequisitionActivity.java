package sg.edu.nus.iss.mobilelusis.dept.activity;

import java.util.Map;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.dept.adapter.PendingListAdapter;
import sg.edu.nus.iss.mobilelusis.dept.controller.ViewRequisitionController;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.Requisition;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class EmployeeViewRequisitionActivity extends Activity implements
ViewRequisitionNavigationDrawerFragment.ViewReqNavigationDrawerCallbacks {

	private ViewRequisitionController controller = null;
	private Menu menu = null;

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private ViewRequisitionNavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employee_view_requisition);

		mNavigationDrawerFragment = (ViewRequisitionNavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.vr_navigation_drawer);
		mTitle = getTitle();

		Employee employee = getIntent().getExtras().getParcelable(IConstants.EXTRA_EMPLOYEE);
		controller = new ViewRequisitionController(this, employee);
		new GetRequisitionsAsyncTask().execute(controller);
	}


	public void onNavigationDrawerItemSelected(int position, String requisitionId) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager
		.beginTransaction()
		.replace(R.id.container,
				PlaceholderFragment.newInstance(position + 1, requisitionId, this, controller)).commit();
	}

	public void onSectionAttached(int number) {
		mTitle = ""; // Placeholder, once the list is retrieved, the section will be populated accordingly.
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
				
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.employee_view_requisition, menu);
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
		if (id == R.id.view_single_req_ok_menuitem) {
			mNavigationDrawerFragment.openDrawer();
			return true;
		} else if (id == R.id.view_single_req_done_menuitem) {
			Intent intent = new Intent(this, EmployeeMainActivity.class);
			intent.putExtra(IConstants.EXTRA_EMPLOYEE, controller.getEmployee());
			startActivity(intent);
		} else if (id == R.id.view_single_req_edit_menuitem) {	
			boolean isPending = controller.getSelectedRequisitionStatus();
			if (isPending) {
				controller.editPendingItems();
			}
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request we're responding to
		if (requestCode == IConstants.EDIT_SUBMITTED_CART_ACTIVITY) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {
			}
		}
	}

	public Menu getMenu() {
		return menu;
	}


	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "vr_section_number";
		private static String selectedRequisitionId = null;
		private static Context context;

		private static ViewRequisitionController viewReqController;

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber,  String requisitionId, Context cxt, ViewRequisitionController controller) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			args.putString("requisitionId", requisitionId);
			fragment.setArguments(args);
			selectedRequisitionId = requisitionId;
			context = cxt;
			viewReqController = controller;
			return fragment;
		}


		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_employee_view_requisition, container,
					false);

			new SingleRequisitionViewAsyncTask(rootView, context).execute(selectedRequisitionId);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((EmployeeViewRequisitionActivity) activity)
			.onSectionAttached(getArguments()
					.getInt(ARG_SECTION_NUMBER));
		}


		private class SingleRequisitionViewAsyncTask  extends AsyncTask<String, Void, Requisition> {

			private View  rootView = null;
			private Context context = null;
			public SingleRequisitionViewAsyncTask(View rootView, Context context) {
				super();
				this.rootView = rootView;
				this.context = context;
			}

			protected void onPostExecute(Requisition selectedRequisition) {
				if (selectedRequisition != null) {						
					TextView employeeTextView = (TextView)rootView.findViewById(R.id.view_req_employee_textview);
					employeeTextView.setText(viewReqController.getEmployee().getName());

					TextView submitTextView = (TextView)rootView.findViewById(R.id.view_req_submit_date_textview);
					String submissionDate = selectedRequisition.getSubmitDate();
					submitTextView.setText(submissionDate);


					TextView reqStatusTextView = (TextView)rootView.findViewById(R.id.view_req_status_textview);
					reqStatusTextView.setText(selectedRequisition.getStatus());

					viewReqController.updateRequisition(selectedRequisition);
					
					viewReqController.setSelectedRequisition(selectedRequisition.getRequisitionId());
					reqStatusTextView.setOnClickListener(viewReqController);

					StationeryItem [] stationeryItems = selectedRequisition.getStationeryItems();

					ListView itemsListView = (ListView)rootView.findViewById(R.id.view_req_listtview);
					PendingListAdapter adapter = new PendingListAdapter(context, R.layout.view_requisitions_row, stationeryItems);

					itemsListView.setAdapter(adapter);

					// Show or hide edit menu item
					viewReqController.setEditMenuItem(selectedRequisition.getStatus().equalsIgnoreCase("Pending"));
				} else {
					// TODO: Show error dialog unable to retrieve information regarding requisition.
				}				
			}


			@Override
			protected Requisition doInBackground(String ... selectedReqIdentities) {
				String selectedRequisitionId = selectedReqIdentities[0];
				return viewReqController.getSingleRequisitionForViewingGET(selectedRequisitionId);
			}
		}
	}// end PlaceHolderFragment class

	// AsyncTask for obtaining list of Employee's requisitions for the Drawer.
	private class GetRequisitionsAsyncTask extends AsyncTask<ViewRequisitionController, Void, Map<String, Requisition>> {

		protected void onPostExecute(Map<String, Requisition> requisitions) {
			if (requisitions != null) {
				mNavigationDrawerFragment.setDrawerListData(requisitions);
				mNavigationDrawerFragment.setUp(R.id.vr_navigation_drawer,
						(DrawerLayout) findViewById(R.id.vr_drawer_layout));
				mNavigationDrawerFragment.openDrawer();
			}
		}

		@Override
		protected Map<String, Requisition> doInBackground(ViewRequisitionController ... controllers) {
			ViewRequisitionController controller = controllers[0];
			return controller.getRequisitionsPOST();
		}
	}

}
