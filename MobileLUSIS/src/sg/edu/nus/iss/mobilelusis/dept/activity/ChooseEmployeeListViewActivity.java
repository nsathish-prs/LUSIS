package sg.edu.nus.iss.mobilelusis.dept.activity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.ROLE;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.JSONHandler;
import sg.edu.nus.iss.mobilelusis.utils.JSONParser;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChooseEmployeeListViewActivity extends ListActivity {

	// Parent Activity identifies which activity called this activity
	private int parentActivity = 0; // 0 = SetDelegateActivity, 1 = ChangeDepartmentDetailsActivity 
	private List<Employee> employees = null;
	private Employee selectedEmployee = null;
	private Employee departmentHead = null;
	private ArrayAdapter<Employee> adapter = null;
	
	private String requestType = null; // either "Representative" or "Delegate";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_choose_employee_list_view);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		parentActivity = bundle.getInt("parent-activity");
		
		departmentHead = bundle.getParcelable("dept-head");

		requestType = bundle.getString("request-type");
 		
		employees = new ArrayList<Employee>();
		
		if (bundle.containsKey(IConstants.EMPLOYEES)) {
			Parcelable [] parcelables = bundle.getParcelableArray(IConstants.EMPLOYEES);
			for (Parcelable p : parcelables) {
				Employee e = (Employee)p;
				employees.add(e);
			}
			Log.i("ChooseEmployeeListActivity", "Getting employeelist from activity");
		} else {
			// We didn't get list of employees from previous activity, so we need to query
			Log.i("ChooseEmployeeListActivity", "Getting employeelist from database");
			new GetEmployeesAsyncTask().execute(departmentHead.getDeptCode());
		}
		
		adapter = new ArrayAdapter<Employee>(this, android.R.layout.simple_list_item_1, employees);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_employee_list_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onListItemClick (ListView l, View v, int position, long id) {
		selectedEmployee = employees.get(position);
		finish();
	}
	
	
	@Override
	public void finish() {
		Intent intent = null;
		if (parentActivity == 0) {
			intent = new Intent(this, SetDelegateActivity.class);
			intent.putExtra("new-delegate", selectedEmployee);			
			intent.putExtra("dept-head", departmentHead);
			
		} else if (parentActivity == 1) {
			intent = new Intent(this, ChangeDepartmentDetailsActivity.class);
			intent.putExtra("new-representative", selectedEmployee);			
			intent.putExtra("dept-head", departmentHead);
		}
		setResult(RESULT_OK, intent);
		super.finish();
	}
	
	

	private class GetEmployeesAsyncTask  extends AsyncTask<String, Void, JSONArray> {

		public GetEmployeesAsyncTask() {
			super();
		}

		protected void onPostExecute(JSONArray resultsArray) {
			employees.clear();
			try {
				for (int i = 0; i < resultsArray.length(); i++) {
					JSONObject obj = resultsArray.getJSONObject(i);
					Employee emp = JSONHandler.unparseEmployee(departmentHead, obj);
//					Employee emp = unparseEmployee(obj);
					if(!departmentHead.getId().equalsIgnoreCase(emp.getId())) {
						employees.add(emp);
					}
				}
			} catch (JSONException jsone) {
				jsone.printStackTrace();
			}
			adapter.notifyDataSetChanged();
			
		}


		@Override
		protected JSONArray doInBackground(String ... deptCodes) {
			String deptCode = deptCodes[0];
			JSONArray results = null;
			try {
				String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_GET_EMPLOYEE_LIST;
				Log.i("URI login", uri);
				String url = addDeptCodeParamToUrl(uri, deptCode);
				Log.i("URL", url);
				results = JSONParser.getJSONArrayFromUrl(url);
		    }
			catch (Exception e) {
				e.printStackTrace();
			}
			return results;
		}
		
		private String addDeptCodeParamToUrl(String url, String deptCode){
			if(!url.endsWith("?"))
				url += "?";
			List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("deptCode", deptCode));
			params.add(new BasicNameValuePair("addRole", requestType));
			String paramString = URLEncodedUtils.format(params, "utf-8");
			url += paramString;
			return url;
		}
		
		
//		private Employee unparseEmployee(JSONObject emp) throws JSONException {
//			Employee employee = new Employee();
//							
//			if (!emp.isNull("AdditionalRole"))
//				employee.setAdditionalRole(emp.getString("AdditionalRole"));						
//			else
//				employee.setAdditionalRole(ROLE.NONE);
//			
//			if (!emp.isNull("Department"))
//				employee.setDept(emp.getString("Department"));
//
//			if (!emp.isNull("DeptCode"))
//				employee.setDeptCode(emp.getString("DeptCode"));
//			else
//				employee.setDeptCode(departmentHead.getDeptCode());
//			
//			if (!emp.isNull(IConstants.EMPID))
//				employee.setId(emp.getString(IConstants.EMPID));
//			else 
//				employee.setId("");
//			
//			if (!emp.isNull("Employee"))
//				employee.setName(emp.getString("Employee"));
//			else
//				employee.setName("");
//			
//			if (!emp.isNull("Role"))
//				employee.setRoleByString(emp.getString("Role"));
//			
//			return employee;
//		}		
	}

}
