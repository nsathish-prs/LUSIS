package sg.edu.nus.iss.mobilelusis.store.Adapter;

import java.util.ArrayList;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.Disbursement;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class DisbursementDetailListAdapter extends ArrayAdapter<StationeryItem> {
	
	private Context context;
	private Disbursement disbursement;
	private ArrayList<StationeryItem> items;
	
	
	
	public DisbursementDetailListAdapter(Context c ,Disbursement disbursement) {
		super(c, R.layout.activity_disbursement_detail);
		context = c ; 
		this.disbursement = disbursement;
		this.items = disbursement.getStationeryItems();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public StationeryItem getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
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
		View v = inflater.inflate(R.layout.disbursement_detail_list_item, null);
		TextView itemDesc = (TextView) v.findViewById(R.id.id_txt_disbDetail_itemdesc);
		TextView itemNeed = (TextView) v.findViewById(R.id.id_num_disbDetail_needed);
		final EditText itemRetrieved = (EditText) v.findViewById(R.id.id_num_disbDetail_retrieved);

		final StationeryItem item = items.get(position);
		itemDesc.setText(item.getItemName());	
		itemNeed.setText(item.getItemRequested());
		itemRetrieved.setText(item.getItemRetrieved());
		
		itemRetrieved.addTextChangedListener(new TextWatcher() {
			
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
					Integer rq = Integer.parseInt(item.getItemRequested());
					
					if (rt <= rq) {
							item.setItemRetrieved(s.toString());
					} else {
						item.setItemRetrieved(item.getItemRequested());
						notifyDataSetChanged();
					}
					
					disbursement.setStationeryItems(items);
				}
			}
		});
		return v;
	}

}
