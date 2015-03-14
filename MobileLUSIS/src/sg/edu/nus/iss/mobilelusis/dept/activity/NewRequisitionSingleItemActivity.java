package sg.edu.nus.iss.mobilelusis.dept.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.dept.controller.NewRequisitionController;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewRequisitionSingleItemActivity extends Activity {

	private boolean addToCart = false;

	private NewRequisitionController controller = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_requisition_single_item);
		
		Bundle bundle = getIntent().getExtras();
		Employee employee = bundle.getParcelable(IConstants.EXTRA_EMPLOYEE);
		StationeryItem stationeryItem = bundle.getParcelable("item");

		controller = new NewRequisitionController(employee, stationeryItem);
		
		initiateWidgets();

	}
	
	private void initiateWidgets() {
		TextView unitLabel = (TextView)findViewById(R.id.new_req_single_unit_label);
		unitLabel.setText("(" + controller.getItemUnit() + ")");
		
		EditText quantityText = (EditText)findViewById(R.id.new_req_number_items_textfield);
		controller.setQuantityText(quantityText);


		TextView employeeTextView = (TextView)findViewById(R.id.new_req_employee_label);
		employeeTextView.setText(controller.getEmployeeName());

		TextView dateTextView = (TextView)findViewById(R.id.new_req_date_label);
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		String date = IConstants.sdf.format(today);
		dateTextView.setText(date);

		TextView itemTextView = (TextView)findViewById(R.id.item_desc_textview);
		itemTextView.setText(controller.getItemName());

		Button plusBtn = (Button)findViewById(R.id.new_req_plus_number_btn);
		plusBtn.setOnClickListener(controller);

		Button minusBtn = (Button)findViewById(R.id.new_req_minus_number_btn);
		minusBtn.setOnClickListener(controller);
	}
	
	

	@Override
	public void finish() {
		Intent data = new Intent();
		if (addToCart)
			data.putExtra("item", controller.getStationeryItem());
		setResult(RESULT_OK, data);
		super.finish();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_requisition_single_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.new_req_single_item_add_to_cart_menuitem) {						
			if (controller.getQuantity() <= 0) {
				addToCart = false;
				new AlertDialog.Builder(this)
			    .setTitle("Invalid Quantity")
			    .setMessage("You have entered an invalid quantity for the item.")
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            
			        }
			     }).setIcon(android.R.drawable.ic_dialog_alert).show();
			} else {
				addToCart = true;
				finish();
			}
			
			return true;
		}
		else if (id == R.id.cancel_cart_submission_menuitem) {
			addToCart = false;
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

}
