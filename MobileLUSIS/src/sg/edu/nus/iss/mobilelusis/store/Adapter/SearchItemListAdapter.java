package sg.edu.nus.iss.mobilelusis.store.Adapter;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class SearchItemListAdapter extends ArrayAdapter<StationeryItem> implements Filterable{
	
	private Context context;
	private ArrayList<StationeryItem> itemList;
	private ArrayList<StationeryItem> itemList_original;
	
	public SearchItemListAdapter(Context c ,ArrayList<StationeryItem> r) {
		super(c,R.layout.activity_raise_voucher,r);
		this.context = c;
		itemList = r;
		itemList_original = r;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	@Override
	public StationeryItem getItem(int position) {
		// TODO Auto-generated method stub
		return itemList.get(position);
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
		View v = inflater.inflate(android.R.layout.simple_list_item_1, null);
		TextView item_desc = (TextView) v.findViewById(android.R.id.text1);
		StationeryItem item = itemList.get(position);
		item_desc.setText(item.getItemName());
		return v;
	}
	
	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		
		return new Filter() {
			
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				// TODO Auto-generated method stub
				if (results.count == 0) {
					notifyDataSetInvalidated();
				}else{
					itemList = (ArrayList<StationeryItem>) results.values;
					notifyDataSetChanged();
				}
			}
			
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				// TODO Auto-generated method stub
				FilterResults results = new FilterResults();
				
				if ( constraint == null) {
					results.values = itemList_original;
					results.count = itemList_original.size();
					Log.i("fileter status","no text and size of items size is "+results.count);
					
					
				}else{
					Log.i("fileter status","Be in else state");
					ArrayList<StationeryItem> filter_items = new ArrayList<StationeryItem>();	
					for (StationeryItem item : itemList) {
						if (item.getItemName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
					
							filter_items.add(item);
						}
					}
					results.values =  filter_items ;
					results.count = filter_items.size();
				}
				return results;
			}
		};
	}

}
