package sg.edu.nus.iss.mobilelusis.store.dialog;

import org.apache.http.ParseException;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import sg.edu.nus.iss.mobilelusis.store.DisbursementDetailActivity;
import sg.edu.nus.iss.mobilelusis.store.RaiseVoucherActivity;
import sg.edu.nus.iss.mobilelusis.store.Adapter.DiscrepancyListAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class UpdateDisbursementDialog extends DialogFragment {

	RaiseVoucherActivity raiseVoucherActivity;
	StationeryItem item;
	
	public UpdateDisbursementDialog(RaiseVoucherActivity raiseVoucherActivity, StationeryItem item) {
		this.raiseVoucherActivity = raiseVoucherActivity;
		this.item = item;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		final View v = inflater.inflate(R.layout.dialog_change_disbursement_item_detail, null);
		final EditText qty = (EditText)v.findViewById(R.id.id_num_disb_qtyInDialog);
		final EditText reason = (EditText)v.findViewById(R.id.id_num_disb_reasonInDialog);
		
		qty.setText(String.valueOf(item.getQuantity()));
		reason.setText(String.valueOf(item.getReason()));
		
        builder.setView(v)
        		.setTitle("Edit : "+item.getItemName())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   
                	   String newQty = qty.getText().toString();
                	   int quantity = 1;
                	   try {
                		   if (newQty != null && (!newQty.trim().equals(""))) {
                			   quantity = Integer.valueOf(newQty);
                		   }
                	   } catch (ParseException pe) {
                		   pe.printStackTrace();
                	   }
                	   item.setQuantity(quantity); 
                	   item.setReason(reason.getText().toString());
                	   raiseVoucherActivity.refreshData();
                   }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
	}
	
	
	
}
