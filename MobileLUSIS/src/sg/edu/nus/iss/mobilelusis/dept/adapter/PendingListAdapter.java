package sg.edu.nus.iss.mobilelusis.dept.adapter;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PendingListAdapter extends ArrayAdapter<StationeryItem> {

	private Context context = null;
	private StationeryItem [] itemList = null;
	private boolean hasEdits = false;
	
	public PendingListAdapter(Context context, int resource, StationeryItem [] itemList) {
		super(context, resource, itemList);
		this.context = context;
		this.itemList = itemList;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.view_requisitions_row, parent, false);
		    TextView itemNameTextView = (TextView) rowView.findViewById(R.id.view_stationery_item_name);
		    TextView itemQtyTextView = (TextView) rowView.findViewById(R.id.view_stationery_item_qty);		    		   
		    TextView itemUnitTextView = (TextView) rowView.findViewById(R.id.view_stationery_item_unit);		    
		    StationeryItem item = itemList[position];
		    
		    itemNameTextView.setText(item.getItemName());
		    itemQtyTextView.setText(Integer.toString(item.getQuantity()));
		    itemUnitTextView.setText(item.getItemUnit());
//		    
//		    itemQtyTextView.setOnClickListener(this);
//		    textViews.put(itemQtyTextView, position);
//
//		    
//		    itemNameTextView.setText(item.getItemName());
//		    itemQtyTextView.setText(Integer.toString(item.getQuantity()));
//		    itemUnitTextView.setText(item.getItemUnit());
		    return rowView;
	}
	
	public boolean hasEdits() {
		return this.hasEdits;
	}
	
//	public List<StationeryItem> getModifiedStationeryItems() {
//		return itemList;	
//	}
//
//
//	@Override
//	public void onClick(View v) {
//		int position = textViews.get(v);
//		StationeryItem item = itemList.get(position);
//		Log.i("Selected item", item.getItemCode());
//		
//	}
	
	
	
	
	
	

}
