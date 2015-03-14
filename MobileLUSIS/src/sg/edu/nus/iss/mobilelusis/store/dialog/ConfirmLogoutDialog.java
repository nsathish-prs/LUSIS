package sg.edu.nus.iss.mobilelusis.store.dialog;

import org.apache.http.ParseException;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.StoreClerkMenuActivity;
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

public class ConfirmLogoutDialog extends DialogFragment {

	StoreClerkMenuActivity storeClerkMenuActivity;
	
	
	public ConfirmLogoutDialog(StoreClerkMenuActivity storeClerkMenuActivity) {
		this.storeClerkMenuActivity = storeClerkMenuActivity;
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout")
        	   .setMessage("Are you sure to logout")
               .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // FIRE ZE MISSILES!
                	   
                   }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
	
	
}
