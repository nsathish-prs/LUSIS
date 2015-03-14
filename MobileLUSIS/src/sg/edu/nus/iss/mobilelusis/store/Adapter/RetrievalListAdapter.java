package sg.edu.nus.iss.mobilelusis.store.Adapter;

import java.util.ArrayList;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.Retrieval;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class RetrievalListAdapter extends ArrayAdapter<Retrieval>{
	
	private Context context;
	private ArrayList<Retrieval> rList;

	
	
	public RetrievalListAdapter(Context c ,ArrayList<Retrieval> r) {
		super(c,R.layout.activity_retrieval,r);
		this.context = c;
		rList = r;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rList.size();
	}

	@Override
	public Retrieval getItem(int position) {
		// TODO Auto-generated method stub
		return rList.get(position);
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
		
		
		View v = inflater.inflate(R.layout.retrieval_list_item, null);
		
		TextView binNo = (TextView) v.findViewById(R.id.id_txt_retrieval_itemcode);
		TextView avaliability = (TextView) v.findViewById(R.id.id_txt_retrieval_adjqty);
		TextView requested = (TextView) v.findViewById(R.id.id_txt_retrieval_needed);
		TextView retrieved = (TextView) v.findViewById(R.id.id_txt_retrieval_retrieved);
		TextView item_desc = (TextView) v.findViewById(R.id.id_txt_retrieval_itemdesc);
		
		Retrieval r = rList.get(position);
		
		binNo.setText(String.valueOf(r.getItemBinID()));
		item_desc.setText(r.getItemDesc());
		avaliability.setText(r.getAvaliability()+" "+r.getUnit());
		requested.setText(r.getRequestedQty()+" "+r.getUnit());
		retrieved.setText(r.getRetrievedQty()+" "+r.getUnit());
		
		return v;
	}
	
	
	
	

}
