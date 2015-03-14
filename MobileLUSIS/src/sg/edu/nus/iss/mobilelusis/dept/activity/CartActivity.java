package sg.edu.nus.iss.mobilelusis.dept.activity;

import java.util.ArrayList;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.dept.controller.CartController;
import sg.edu.nus.iss.mobilelusis.model.Cart;
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
import android.view.WindowManager;
import android.widget.EditText;

public class CartActivity extends Activity {

	private Employee employee = null;
	private CartController cartController = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		
		Intent intent = getIntent();
		
		if (intent.getExtras().containsKey(IConstants.EXTRA_EMPLOYEE))
			employee = intent.getParcelableExtra(IConstants.EXTRA_EMPLOYEE);
		
		if (employee == null && savedInstanceState != null) {
			if (savedInstanceState.containsKey(IConstants.EXTRA_EMPLOYEE)) {
				employee = savedInstanceState.getParcelable(IConstants.EXTRA_EMPLOYEE);	
			}
		}
		
		
		String requisitionID = intent.getExtras().getString(IConstants.EXTRA_REQ_ID);
		
		Cart cart = intent.getParcelableExtra("checkout_cart");
		cartController = new CartController(this, cart);
		cartController.setRequisitionID(requisitionID);
		
		 this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cart, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.cancel_cart_submission_menuitem) {
			  finish();
		}
		else if (id == R.id.submit_new_req_menuitem) {
			
			// Update list first, user may have changed the quantity
			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("Requisition");
			alert.setMessage("Please enter any comments (optional)");

			// Set an EditText view to get user input 
			final EditText input = new EditText(this);
			alert.setView(input);

			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String comment = input.getText().toString();				
			  	cartController.submitNewRequisition(employee, cartController.getListAdapter().getQuantityEditText(), comment);		
			  }
			});

			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {
				  Intent intent = new Intent(CartActivity.this, EmployeeMainActivity.class);
				  intent.putExtra(IConstants.EXTRA_EMPLOYEE, employee);
				  startActivity(intent);
			  }
			});

			alert.show();
			return true;
		} else if (id == R.id.add_item_from_cart_menuItem) {
			StationeryItem [] existingItems = cartController.getStationeryItem();		
			Intent intent = new Intent(this, NewRequisitionSearchActivity.class);
			intent.putExtra(IConstants.EXTRA_EXISTING_ITEMS, existingItems);			
			intent.putExtra(IConstants.EXTRA_EMPLOYEE, employee);
			intent.putExtra(IConstants.EXTRA_REQ_ID, cartController.getRequisitionID());
			startActivityForResult(intent, IConstants.ADD_EXISTING_ITEM_ACTIVITY);			
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void finish() {
		Intent intent = new Intent();		
		setResult(RESULT_OK, intent);
		super.finish();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(IConstants.EXTRA_EMPLOYEE, employee);
		super.onSaveInstanceState(outState);		
	}

	
	@Override
	public void onResume() {
		
	}

}
