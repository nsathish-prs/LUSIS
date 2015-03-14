package sg.edu.nus.iss.mobilelusis.dept.controller;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

public class NewRequisitionController implements OnValueChangeListener, OnClickListener {

	private Employee employee;
	private StationeryItem stationeryItem;

	private EditText quantityText;

	public NewRequisitionController(Employee employee,
			StationeryItem stationeryItem) {
		super();
		this.employee = employee;
		this.stationeryItem = stationeryItem;
	}

	public String getItemUnit() {
		return stationeryItem.getItemUnit();
	}

	public int getQuantity() {
		
		String quantity = quantityText.getText().toString();
		try {
			int qty = Integer.valueOf(quantity);
			stationeryItem.setQuantity(qty);
		} catch (NumberFormatException nfe) {
			stationeryItem.setQuantity(0); // make it wrong so that the user will see the toast
		}		
		return stationeryItem.getQuantity();
	}


	public String getEmployeeName() {
		return employee.getName();
	}

	public CharSequence getItemName() {
		return stationeryItem.getItemName();
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		quantityText.setText(Integer.toString(newVal));
	}

	@Override
	public void onClick(View v) {
		String originalQuantity = quantityText.getText().toString();
		int oldVal = 0;
		try {
			oldVal = Integer.valueOf(originalQuantity);					
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}

		int newVal = oldVal;
		if (v.getId() == R.id.new_req_plus_number_btn) {
			newVal++;
		} else {
			newVal--;
		}
		stationeryItem.setQuantity(newVal);
		quantityText.setText(Integer.toString(newVal));

	}

	public StationeryItem getStationeryItem() {
		return this.stationeryItem;
	}

	public void setQuantityText(EditText quantityText) {		
		this.quantityText = quantityText;
		if (stationeryItem != null) {
			this.quantityText.setText(Integer.toString(stationeryItem.getQuantity()));
		}
	}




}
