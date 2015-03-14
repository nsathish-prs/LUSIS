package sg.edu.nus.iss.mobilelusis.store.dialog;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.store.DisbursementDetailActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DisbusementConfirmationDialog extends DialogFragment {

	DisbursementDetailActivity disbDetailActivity;
	private String EmpID=null; 
	public DisbusementConfirmationDialog(DisbursementDetailActivity disbursementDetailActivity,String EmpID) {
		// TODO Auto-generated constructor stub
		disbDetailActivity = disbursementDetailActivity;
		this.EmpID = EmpID;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		View v = inflater.inflate(R.layout.dialog_disbursement_confirmation, null);
		final EditText password = (EditText) v.findViewById(R.id.id_txt_disbursement_approval_detail);
		final EditText empID = (EditText) v.findViewById(R.id.id_txt_disbursement_approval_empID);
		empID.setText(EmpID);
		empID.setEnabled(false);
		
         builder.setView(v)
        		.setTitle("Acknowledgement")
                .setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   
                	   disbDetailActivity.approve(password.getText().toString(),EmpID ,true);
                	   
                   }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   disbDetailActivity.switch_approve.setChecked(false);
                   }
               });
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                
                final Button positive_btn = ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                positive_btn.setEnabled(false);
                password.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						if (s.toString().length()>5) {
							positive_btn.setEnabled(true);
						}
					}
				});
            }
        });
       
        return dialog;
	}
	
}
