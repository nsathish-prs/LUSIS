package sg.edu.nus.iss.mobilelusis;

import java.util.HashMap;
import java.util.Map;

import sg.edu.nus.iss.mobilelusis.dept.activity.DeptHeadMainActivity;
import sg.edu.nus.iss.mobilelusis.dept.activity.EmployeeMainActivity;
import sg.edu.nus.iss.mobilelusis.dept.controller.LoginController;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.ROLE;
import sg.edu.nus.iss.mobilelusis.supervisor.SupervisorMenuActivity;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/****************************************************
 * LoginActivity: Handles UI for Login
 * @author A0120502E
 *
 ****************************************************/

public class LoginActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setWidgets();		
	}

	private void setWidgets()
	// Pre-condition: None 
	// Postcondition: Widgets set up properly
	{
		// The loginButton uses LoginController to determine if login succeeds or not. 
		Button loginBtn = (Button)findViewById(R.id.login_btn);
		loginBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				// Get user entered data from edit text widgets
				EditText empIDTextField = (EditText)findViewById(R.id.emp_id_textfield);
				String empID = empIDTextField.getText().toString().trim();
				Log.i("LoginActivity.empID", empID);
				EditText pwdTextField = (EditText)findViewById(R.id.pwd_tf);
				String pwd = pwdTextField.getText().toString().trim();
				Log.i("LoginActivity.pwd", pwd);

				empIDTextField.setFocusableInTouchMode(true);
				empIDTextField.requestFocus();

				if (empID.equals("") || pwd.equals("")) {
					Toast.makeText(LoginActivity.this, "Please enter a valid username/password combination", Toast.LENGTH_LONG).show();
				} else {
					Map<String, String> map = new HashMap<String, String>();
					map.put(IConstants.EMPID, empID);
					map.put(IConstants.PWD, pwd);
					
					
					
					// Start login task as asynchronous task because this takes time and we do not want to block the UI
					new LoginAsyncTask(LoginActivity.this).execute(map);					
				}
			}
		});
		
		// On launching the application, we make the Employee ID EditText the focus.
		EditText empIDTextField = (EditText)findViewById(R.id.emp_id_textfield);
		empIDTextField.requestFocus();		
	}
 
	// Asynchronous task that handles logging
	private class LoginAsyncTask extends AsyncTask<Map<String, String>, Void, Employee> {		
		private Activity activity = null; // We need this for Toast if login fails.
		
		public LoginAsyncTask(Activity activity) {
			super();
			this.activity = activity;
		}
		
		protected void onPostExecute(Employee employee) {
			if (employee != null) {

				Intent intent;

				// Using the Role of the Employee to determine which activity to show.
				ROLE role = employee.getRole();
				if (employee.getRole() == ROLE.EMPLOYEE && employee.getAdditionalRole() != ROLE.DELEGATE) {
					intent = new Intent(LoginActivity.this, EmployeeMainActivity.class);
					intent.putExtra(IConstants.EXTRA_EMPLOYEE, employee);
					startActivity(intent);
				} else if (employee.getRole() == ROLE.DEPT_HEAD || employee.getAdditionalRole() == ROLE.DELEGATE) {
					intent = new Intent(LoginActivity.this, DeptHeadMainActivity.class);
					intent.putExtra(IConstants.EXTRA_EMPLOYEE, employee);
					startActivity(intent);
				} else if (employee.getRole() == ROLE.CLERK) {
					intent = new Intent(LoginActivity.this, StoreClerkMenuActivity.class);
					//Put the Employee object for global 
					Temp.putEmployee(employee);
					startActivity(intent);
				} else if (employee.getRole() == ROLE.SUPERVISOR || employee.getRole() == ROLE.MANAGER) {
					intent = new Intent(LoginActivity.this, SupervisorMenuActivity.class);
					intent.putExtra(IConstants.EXTRA_EMPLOYEE, employee);
					startActivity(intent);
				}				
			} else {
				// Login failed
				Toast.makeText(activity, "Login failed. Please try again.", Toast.LENGTH_LONG).show();			
			}
		}

		@Override
		protected Employee doInBackground(Map<String, String>... data) {
			
			LoginController controller = new LoginController();

			Map<String, String> datum = data[0];
			String username = datum.get(IConstants.EMPID);
			String pwd = datum.get(IConstants.PWD);
			Employee emp = controller.login(username, pwd);

			return emp;
		}
	}
}
