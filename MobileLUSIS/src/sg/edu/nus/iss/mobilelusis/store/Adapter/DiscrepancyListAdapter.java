package sg.edu.nus.iss.mobilelusis.store.Adapter;

import java.util.ArrayList;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DiscrepancyListAdapter extends ArrayAdapter<StationeryItem>{
	
	private Context context;
	private ArrayList<StationeryItem> itemList;
//	private HashMap<String,Discrepancy> discrepancyList;

	
	public DiscrepancyListAdapter(Context c ,ArrayList<StationeryItem> itemlist) {
		super(c,R.layout.activity_raise_voucher,itemlist);
		this.context = c;
		this.itemList = itemlist;
//		this.discrepancyList = discrepanciesList;
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
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			v = inflater.inflate(R.layout.search_result_list_item, null);
		}
		
		
		TextView itemCode = (TextView) v.findViewById(R.id.id_txt_raisevoucher_itemcode);
		TextView itemDesc = (TextView) v.findViewById(R.id.id_txt_raisevoucher_itemdesc);
		TextView adjQty = (TextView) v.findViewById(R.id.id_txt_raisevoucher_adjqty);
		TextView reason = (TextView) v.findViewById(R.id.id_txt_raisevoucher_reason);
		
		StationeryItem item = itemList.get(position);
		
		itemCode.setText(item.getItemCode());
		itemDesc.setText(item.getItemName());
		adjQty.setText(item.getQuantity()+"");
		reason.setText(item.getReason());
		
		
//		final Discrepancy discrepancy = new Discrepancy();
//		discrepancy.setItemCode(item.getItemCode());
		
//		discrepancyList.put(item.getItemCode(), discrepancy);
		
		return v;
	}

	public void updateItem(StationeryItem item, int index) {
		// TODO Auto-generated method stub
		StationeryItem object = itemList.get(index);
		object.setQuantity(item.getQuantity());
		
	}
	
	
	
	

}
