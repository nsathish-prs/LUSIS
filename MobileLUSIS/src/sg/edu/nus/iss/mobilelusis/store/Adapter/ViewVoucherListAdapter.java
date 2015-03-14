package sg.edu.nus.iss.mobilelusis.store.Adapter;

import java.util.List;

import org.apache.http.ParseException;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.Discrepancy;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.ROLE;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ViewVoucherListAdapter extends ArrayAdapter<Discrepancy>{
	
	private Context context;
	private List<Discrepancy> discrepancyList;
	private ROLE role = ROLE.EMPLOYEE;
	
	public ViewVoucherListAdapter(Context c,List<Discrepancy> result, Employee employee) {
//		super(c,R.layout.activity_approve_purchase_order,result);
		super(c,R.layout.activity_view_voucher, result);
		this.context = c;
		discrepancyList = result;
		role = employee.getRole();
	}

//	@Override
//	public int getCount() {
//		return discrepancyList.size();
//	}
//
//	@Override
//	public Discrepancy getItem(int position) {
//		return discrepancyList.get(position);
//	}
//
//	@Override
//	public int getItemId(int position) {		
//		return 0;
//	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		
		View v = inflater.inflate(R.layout.view_voucher_list_item, parent, false);
		
		TextView adjDate = (TextView) v.findViewById(R.id.id_txt_viewVoucher_qtyAdjusted);
		
		TextView approveBy = (TextView) v.findViewById(R.id.id_txt_viewVoucher_approvedBy);
		
		TextView status = (TextView) v.findViewById(R.id.id_txt_viewVoucher_status);
		
		TextView id = (TextView) v.findViewById(R.id.id_txt_viewVoucher_voucherID);
		
		Discrepancy discrepancy = discrepancyList.get(position);
		
		id.setText(discrepancy.getVoucherID());
		String date = "";
		try {
			date = IConstants.sdf.format(discrepancy.getDate());
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		adjDate.setText(date);
		status.setText(discrepancy.getStatus());

		if (role == ROLE.CLERK) {
			approveBy.setText(discrepancy.getApprovedBy());			
		} else if (role == ROLE.SUPERVISOR || role == ROLE.MANAGER) {
			approveBy.setText(discrepancy.getRaisedBy());
		}
		
		
		
		
		return v;
	}
	
	
	
	

}
