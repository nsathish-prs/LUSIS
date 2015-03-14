package sg.edu.nus.iss.mobilelusis.dept.controller;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;

public class DatePickerController implements OnDateChangedListener, OnClickListener {

	private Activity activity = null;
	private DatePicker datePicker = null;
	private Button setDateBtn = null;

	private Button noEndDateBtn = null;
	
	private Calendar startDate = null;
	private Calendar endDate = null;

	private String temporalDateString = null;
	private boolean isStart = false;

	public DatePickerController(Activity activity) {
		Calendar cal = Calendar.getInstance();
		setTemporalDateString(cal);
		this.activity = activity;
		startDate = null;
		endDate = null;
	}

	public String getDateString() {
		return temporalDateString;
	}

	public void setTemporalDateString(Calendar cal) {		
		temporalDateString = IConstants.sdf.format(cal.getTime());		
	}

	public void setDatePicker(DatePicker datePicker) {
		this.datePicker = datePicker;
	}

	public void initiateDatePicker() {
		Calendar date = Calendar.getInstance();
		datePicker.init(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), this);
		datePicker.setVisibility(View.INVISIBLE);
	}

	public void initiateSetDateButton(Button button) {
		setDateBtn = button;
		setDateBtn.setOnClickListener(this);
		setDateBtn.setVisibility(View.INVISIBLE);
	}

	public void initiateEndDateButton(Button button) {
		noEndDateBtn = button;
		noEndDateBtn.setOnClickListener(this);
		noEndDateBtn.setVisibility(View.INVISIBLE);
	}
	
	
	public void setUIState(boolean state, int visibility, Calendar date) {
		isStart = state;

		if (date == null) {
			date = Calendar.getInstance();
			date.set(Calendar.HOUR, 0);
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.SECOND, 0);
			date.set(Calendar.MILLISECOND, 0);
		}

		datePicker.init(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), this);			
		datePicker.setVisibility(visibility);
		setDateBtn.setVisibility(visibility);
		noEndDateBtn.setVisibility(visibility);

	}

	public void onClick(View v) {
		if (v == setDateBtn) {
			datePicker.setVisibility(View.INVISIBLE);
			setDateBtn.setVisibility(View.INVISIBLE);
			noEndDateBtn.setVisibility(View.INVISIBLE);
		} else if (v == noEndDateBtn) {
			endDate = null;
			datePicker.setVisibility(View.INVISIBLE);
			setDateBtn.setVisibility(View.INVISIBLE);
			noEndDateBtn.setVisibility(View.INVISIBLE);		
			
			TextView endDateTextView = (TextView)activity.findViewById(R.id.end_date_textview);
			endDateTextView.setText(IConstants.NO_END_DATE);
			
			
		}
	}

	public void setDate(String date, boolean isStartDate) {
		if (date == null) {
			if (isStartDate) {
				startDate = null;
			} else {
				endDate = null;
			}
		} else {

			try {				
				Date dateDate = IConstants.sdf.parse(date);	

				String validate = validateDate(dateDate, isStartDate);
				if (validate != null) {
					new AlertDialog.Builder(activity)
					.setMessage(validate)
					.setTitle("Invalid Date Selected")
					.setCancelable(true)
					.setNeutralButton(android.R.string.cancel,
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton){}
					})
					.show();
				} else {
					if (isStartDate) { 
						startDate = Calendar.getInstance();
						startDate.setTime(dateDate);
						startDate.set(Calendar.HOUR, 0);
						startDate.set(Calendar.MINUTE, 0);
						startDate.set(Calendar.SECOND, 0);
					} else { 
						endDate = Calendar.getInstance();
						endDate.setTime(dateDate);
						endDate.set(Calendar.HOUR, 0);
						endDate.set(Calendar.MINUTE, 0);
						endDate.set(Calendar.SECOND, 0);
					}
				}
			} catch (ParseException pe) {
				pe.printStackTrace();
			}

		}//end else
	}
	private String validateDate(Date dateDate, boolean isStartDate) {
		String message = null;
		if (isStartDate) {
			if (endDate != null) {
				Date d = endDate.getTime();
				Log.i("enddate", d.toString());
				if (endDate != null && dateDate.after(endDate.getTime())) {
					message = "Start date cannot be after end date";					
				}				
			}
		} else {
			if (startDate != null) {
				Date d = startDate.getTime();
				Log.i("startdate", d.toString());
				if (startDate != null && dateDate.before(startDate.getTime())) {
					message = "End date cannot be before start date";
				}				
			}
		}

		// Just debugging
		if (startDate != null && endDate != null) {
			String sd = IConstants.sdf.format(startDate.getTime());
			String ed = IConstants.sdf.format(endDate.getTime());
			Log.i("Start:", sd);
			Log.i("End  :", ed);
			
		}
		
		return message;
	}


	public void onDateChanged(DatePicker view, int year, int month, int day) {

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, day);

		temporalDateString = IConstants.sdf.format(c.getTime());
		
		setDate(temporalDateString, isStart);

		TextView textView = null;
		if (isStart) {
			textView = (TextView)activity.findViewById(R.id.start_date_textview);
		} else {
			textView = (TextView)activity.findViewById(R.id.end_date_textview);
		}
		textView.setText(temporalDateString);
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public String getDateString(boolean isStart) {

		Calendar c = null;
		if (isStart) {
			c = startDate;
		} else {
			c = endDate;
		}

		String dateString = IConstants.EMPTY;
		if (c != null) {
			dateString = IConstants.sdf.format(c.getTime());
		} else {
			dateString = IConstants.EMPTY_DATE;
		}
		return dateString;
	}


}
