package sg.edu.nus.iss.mobilelusis.store.Adapter;

import java.util.List;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.PurchaseOrder;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ApprovePOListAdapter extends ArrayAdapter<PurchaseOrder>{
	
	private Context context;
	private List<PurchaseOrder> purchaseOrders;

	
	public ApprovePOListAdapter(Context c, List<PurchaseOrder> result) {
		super(c,R.layout.activity_retrieval,result);
		this.context = c;
		purchaseOrders = result;
	}

	@Override
	public int getCount() {
		return purchaseOrders.size();
	}

	@Override
	public PurchaseOrder getItem(int position) {
		return purchaseOrders.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		
		View v = inflater.inflate(R.layout.approve_po_list_item, null);
		
		
		PurchaseOrder po = purchaseOrders.get(position);
		
		TextView poIdentity = (TextView)v.findViewById(R.id.id_txt_purchaseOrder_ID);
		TextView poDate = (TextView)v.findViewById(R.id.id_txt_PurchaseOrderDate);
		TextView poStatus = (TextView)v.findViewById(R.id.id_txt_purchaseOrderStatus);
		
		
		String identity = Integer.toString(po.getId());				
		poIdentity.setText(identity);
		
		poDate.setText(po.getExpDate());
		poStatus.setText(po.getStatus());
		
//		TextView adjQty = (TextView) v.findViewById(R.id.id_txt_viewVoucher_qtyAdjusted);
//		TextView approveBy = (TextView) v.findViewById(R.id.id_txt_viewVoucher_approvedBy);
//		TextView status = (TextView) v.findViewById(R.id.id_txt_viewVoucher_status);
//		TextView id = (TextView) v.findViewById(R.id.id_txt_viewVoucher_voucherID);
//		
//		Discrepancy r = discrepancyList.get(position);
		
		
		
		return v;
	}
	
	
	
	

}
