package sg.edu.nus.iss.mobilelusis.store.Adapter;

import java.util.List;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.Discrepancy;
import sg.edu.nus.iss.mobilelusis.model.PurchaseOrderDetail;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ApprovePODetailListAdapter extends ArrayAdapter<PurchaseOrderDetail>{
	
	private Context context;
	private List<PurchaseOrderDetail> purchaseOrderDetails;

	
	public ApprovePODetailListAdapter(Context c, List<PurchaseOrderDetail> result) {
		super(c, R.layout.activity_retrieval, result);
		this.context = c;
		purchaseOrderDetails = result;
	}

	@Override
	public int getCount() {
		return purchaseOrderDetails.size();
	}

	@Override
	public PurchaseOrderDetail getItem(int position) {
		return purchaseOrderDetails.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		
		View v = inflater.inflate(R.layout.approve_po_detail_list_item, null);
		
		PurchaseOrderDetail pod = purchaseOrderDetails.get(position);
		
		TextView descTV = (TextView)v.findViewById(R.id.id_txt_pod_description);
		descTV.setText(pod.getDescription());
		
		TextView minQtyTV = (TextView)v.findViewById(R.id.id_txt_pod_minQty);
		minQtyTV.setText(pod.getMinQty() + "");
		
		TextView ordQtyTV = (TextView)v.findViewById(R.id.id_txt_pod_ordQty);
		ordQtyTV.setText(pod.getOrdQty() + "");
		
		return v;
	}
	
	
	
	

}
