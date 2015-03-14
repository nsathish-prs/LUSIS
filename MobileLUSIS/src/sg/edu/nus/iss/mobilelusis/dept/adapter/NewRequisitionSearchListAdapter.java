package sg.edu.nus.iss.mobilelusis.dept.adapter;

import java.util.List;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NewRequisitionSearchListAdapter extends ArrayAdapter<StationeryItem> {
	
	private final Context context;
	private final List<StationeryItem> values;
	

	public NewRequisitionSearchListAdapter(Context context, 
			int textViewResourceId, List<StationeryItem> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.values = objects;	
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.found_item_row, parent, false);
		    TextView textView = (TextView) rowView.findViewById(R.id.found_items_row);
		    
		    StationeryItem item = values.get(position);
		    textView.setText(item.getItemName());
		    return rowView;
	}

}
