package sg.edu.nus.iss.mobilelusis.store.Adapter;

import java.util.ArrayList;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.Department;
import sg.edu.nus.iss.mobilelusis.model.Disbursement;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DisbursementListAdapter extends ArrayAdapter<Disbursement>{

	private ArrayList<Disbursement> disbursementList;
	private Context context;
	
	public DisbursementListAdapter(Context c ,ArrayList<Disbursement> result) {
		super(c, R.layout.activity_disbursement);
		this.context = c;
		disbursementList = result;	
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return disbursementList.size();
	}

	@Override
	public Disbursement getItem(int position) {
		// TODO Auto-generated method stub
		return disbursementList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View v = inflater.inflate(R.layout.disbursement_list_item, null);
		
		TextView deptName = (TextView) v.findViewById(R.id.id_txt_disb_deptName);
		TextView date = (TextView) v.findViewById(R.id.id_txt_disb_date);
		Disbursement disbursement = disbursementList.get(position);
		deptName.setText(disbursement.getDeptName());
		date.setText(disbursement.getDate());
		return v;
	}

}
