package sg.edu.nus.iss.mobilelusis.store;

import java.util.ArrayList;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.Retrieval;
import sg.edu.nus.iss.mobilelusis.model.RetrievalByDept;
import sg.edu.nus.iss.mobilelusis.model.RetrievalByDept;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class RDeptListAdapter extends ArrayAdapter<RetrievalByDept>{

	private Context context;
	private Retrieval retrieval;
	private ArrayList<RetrievalByDept> rDeptList;
	
	public RDeptListAdapter(Context c, Retrieval r) {
		
		super(c,R.layout.activity_rdepartment_breakdown);
		this.context = c;
		this.retrieval = r;
		this.rDeptList = r.getRetrievalByDeptList();
		
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rDeptList.size();
	}

	@Override
	public RetrievalByDept getItem(int position) {
		// TODO Auto-generated method stub
		return rDeptList.get(position);
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
		
		
		View v = inflater.inflate(R.layout.retrieval_break_down_list_item, null);
		TextView deptCode = (TextView) v.findViewById(R.id.id_txt_rdeptBreakdown_deptCode);
		TextView deptName = (TextView) v.findViewById(R.id.id_txt_rdeptBreakdown_deptName);
		TextView requested = (TextView) v.findViewById(R.id.id_num_rdeptBreakdown_needed);
		final EditText retrieved = (EditText) v.findViewById(R.id.id_num_rdeptBreakdown_retrieved);
		
		final RetrievalByDept rByDept = rDeptList.get(position);
		
		deptCode.setText(rByDept.getDeptCode());
		deptName.setText(rByDept.getDeptDesc());
		
		requested.setText(rByDept.getRequestedQty());
		retrieved.setText(rByDept.getRetrievedQty());
		
		if (Integer.parseInt(retrieval.getAvaliability()) > 0) {
			retrieved.setText(rByDept.getRetrievedQty());

			
		} else {
			retrieved.setEnabled(false);
		}
		
		retrieved.addTextChangedListener(new TextWatcher() {
			
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
				
				if (s.length() > 0) {
					Integer rt = Integer.parseInt(s.toString());
					Integer rq = Integer.parseInt(rByDept.getRequestedQty());
					
					if (rt <= rq) {
							rByDept.setRetrievedQty(s.toString());
					} else {
						rByDept.setRetrievedQty(rByDept.getRequestedQty());
						notifyDataSetChanged();
					}
					
					retrieval.setRetrievalByDeptList(rDeptList);
					
				}
			}
		});
		return v;
	}
	
	
	
	
	
	
	
	
	
	

}
