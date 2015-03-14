package sg.edu.nus.iss.mobilelusis.dept.adapter;

import java.util.HashMap;
import java.util.Map;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.dept.controller.CartController;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import sg.edu.nus.iss.mobilelusis.utils.IJSONConstants;
import sg.edu.nus.iss.mobilelusis.utils.QuantityTextWatcher;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class CartListAdapter extends ArrayAdapter<StationeryItem> implements OnKeyListener {

	private final Context context;

	private CartController controller;	
	Map<String, EditText> editables = null;
	Map<EditText, String> editText2ItemCode = null;
	
	public CartListAdapter(Context context, 
			int textViewResourceId, CartController controller) {
		super(context, textViewResourceId, controller.getStationeryItem());
		this.context = context;
		this.controller = controller;
		editables = new HashMap<String, EditText>();
		editText2ItemCode = new HashMap<EditText, String>();
	}
	

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.new_requisition_cart_row, parent, false);
	            holder = new ViewHolder();
	            holder.itemDescriptionTextView = (TextView) convertView.findViewById(R.id.cart_checkout_item_name_textview);
	            holder.itemQtyEditText = (EditText) convertView.findViewById(R.id.cart_checkout_item_qty_textview);
	            holder.itemUnitTextView = (TextView)convertView.findViewById(R.id.cart_checkout_item_unit_textview);

	            
			    StationeryItem item = controller.getStationeryItem()[position];
			    holder.itemDescriptionTextView.setText(item.getItemName());
			    holder.itemUnitTextView.setText(item.getItemUnit());
			    holder.itemQtyEditText.setText(Integer.toString(item.getQuantity()));
			    
			    holder.itemQtyEditText.setOnKeyListener(this);
			    
			    
			    
			    
			    holder.itemDescriptionTextView.setOnClickListener(controller);
			    editables.put(item.getItemCode(), holder.itemQtyEditText);
			    
			    QuantityTextWatcher watcher = new QuantityTextWatcher(item);
			    holder.itemQtyEditText.addTextChangedListener(watcher);
			    
			    editText2ItemCode.put(holder.itemQtyEditText, item.getItemCode());
			    //editables.put(holder.itemQtyEditText.getEditableText(), item);

			} else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			return convertView;
	}	
	public Map<String, EditText> getQuantityEditText() {
		return editables;		
	}
	
	
	public void removeStationeryItem(StationeryItem itemToBeRemoved) {
		controller.removeStationeryItem(itemToBeRemoved);
		editables.remove(itemToBeRemoved.getItemCode());
	}
	
	public class ViewHolder {
		TextView itemDescriptionTextView;
		EditText itemQtyEditText;
		TextView itemUnitTextView;
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_UP) {
			EditText t = (EditText)v;
			
			String value = t.getText().toString();
			String itemCode = editText2ItemCode.get(t);
			if (value != null && !value.equalsIgnoreCase(IJSONConstants.JSON_EMPTY_STRING)) {
				controller.setItemQty(itemCode, value);
			}
			
		}
		
		
		return false;
	}


	
}
