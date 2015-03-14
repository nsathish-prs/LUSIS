package sg.edu.nus.iss.mobilelusis.store.Adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.ParseException;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.Discrepancy;
import sg.edu.nus.iss.mobilelusis.model.VoucherItem;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ApproveVoucherListAdapter extends ArrayAdapter<VoucherItem> implements OnItemSelectedListener {
	
	private Context context;
	private List<VoucherItem> voucherItems;
	private boolean isClerk;
	private Discrepancy discrepancy;
	
	private Map<Spinner, VoucherItem> spinnerToVoucher = null;
	
	public ApproveVoucherListAdapter(Context c, List<VoucherItem> result, boolean isClerk, Discrepancy discrepancy) {
		super(c,R.layout.activity_retrieval,result);
		this.context = c;
		voucherItems = result;
		this.isClerk = isClerk;
		this.discrepancy = discrepancy;
		
		spinnerToVoucher = new HashMap<Spinner, VoucherItem>();
	}

	@Override
	public int getCount() {
		return voucherItems.size();
	}

	@Override
	public VoucherItem getItem(int position) {
		return voucherItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		
		int layoutId = R.layout.view_voucher_for_approval_row;
		if (isClerk) {
			layoutId = R.layout.view_voucher_list_item;
		}

		
		VoucherItem item = voucherItems.get(position);
		
		View v = inflater.inflate(layoutId, null);
		

		
		
		TextView id = (TextView) v.findViewById(R.id.id_txt_viewVoucher_voucherID); 
		id.setText(item.getItem());
		
		TextView adjQty = (TextView) v.findViewById(R.id.id_txt_viewVoucher_qtyAdjusted);
		String quantity = "";
		try {
			quantity = Integer.toString(item.getQty());
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		adjQty.setText(quantity);
		
		
		TextView approveBy = (TextView) v.findViewById(R.id.id_txt_viewVoucher_approvedBy);
		if (isClerk) {			
			approveBy.setText(discrepancy.getApprovedBy());
		} else {
			approveBy.setText(discrepancy.getRaisedBy());
		}
		
		
	    if (isClerk) {
	    	TextView voucherStatus = (TextView)v.findViewById(R.id.id_txt_viewVoucher_status);
	    	voucherStatus.setText(item.getStatus());
	    } else {
	    	Spinner spinner = (Spinner)v.findViewById(R.id.decision_spinner);
	    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,R.array.decision_array, android.R.layout.simple_spinner_item);
	    	// Specify the layout to use when the list of choices appears
	    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    	// Apply the adapter to the spinner
	    	spinner.setAdapter(adapter);
	    	spinnerToVoucher.put(spinner, item);
	    	spinner.setOnItemSelectedListener(this);
	    }
	    
//	    id_txt_viewVoucher_status
//	    decision_spinner
		
//		View v = inflater.inflate(R.layout.approve_voucher_list_item, null);
//		TextView adjQty = (TextView) v.findViewById(R.id.id_txt_viewVoucher_qtyAdjusted);
//		TextView approveBy = (TextView) v.findViewById(R.id.id_txt_viewVoucher_approvedBy);
//		TextView status = (TextView) v.findViewById(R.id.id_txt_viewVoucher_status);
//		TextView id = (TextView) v.findViewById(R.id.id_txt_viewVoucher_voucherID);
//		
//		Discrepancy r = discrepancyList.get(position);
		
		return v;
	}
	
	@Override
    public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
		String selection = (String)parent.getItemAtPosition(pos);
		if (selection.equalsIgnoreCase("Approve")) {
			selection = "Approved";
		}
		if (selection.equalsIgnoreCase("Reject")) {
			selection = "Rejected";
		}
		VoucherItem item = spinnerToVoucher.get(parent);
		Log.i("TEST", item.getItem() + ", " + selection);
		item.setStatus(selection);
		
    }

	@Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

	public List<VoucherItem> getItems() {
		return this.voucherItems;
	}
	
	
	

}
