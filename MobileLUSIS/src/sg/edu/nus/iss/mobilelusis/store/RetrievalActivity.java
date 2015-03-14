package sg.edu.nus.iss.mobilelusis.store;

import java.util.ArrayList;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.Retrieval;
import sg.edu.nus.iss.mobilelusis.store.Adapter.RetrievalListAdapter;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.MConnectionHelper;
import sg.edu.nus.iss.mobilelusis.utils.MJSONHandler;
import android.R.anim;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class RetrievalActivity extends Activity implements OnItemClickListener {

	
	
	private RetrievalListAdapter rListAdapter;
	ArrayList<Retrieval> rList;
	private Retrieval retrievalObj;
	int selectedPos;
	Employee employee;
	private Menu menu;
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retrieval);
		
		new GetRetrievalAsynctask().execute();
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.retrieval, menu);
		this.menu = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.id_action_r_finalize:
			new SaveRetrievalList().execute();
			break;

		case android.R.id.home:
			
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// Fetch the selected Retrieval Object
		retrievalObj = (Retrieval) parent.getAdapter().getItem(position);
		selectedPos = position;//selected object position on list view
		
		Intent i = new Intent(this,RDepartmentBreakdown.class);
		i.putExtra("RetrievalObj", retrievalObj);
		startActivityForResult(i, IConstants.ACTIVITY_RDEPARTMENTBREAKDOWN_RQ);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK ){
			switch (requestCode) {
				case IConstants.ACTIVITY_RDEPARTMENTBREAKDOWN_RQ:
						Retrieval update_r = (Retrieval) data.getExtras().getParcelable("UpdatedRetrievalObj");
						retrievalObj.setRetrievalByDeptList(update_r.getRetrievalByDeptList());
						retrievalObj.setRetrievedQty(update_r.getRetrievedQty());
						rListAdapter.notifyDataSetChanged();
					break;
				default:
					break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	

	
	/********************************* Async Tasks ******************************/
	 
	/*
	 * Get Retrieval Object
	 * GET -> http://domain/LUSISService/Service.svc/viewretrievallist
	 */
	private class GetRetrievalAsynctask extends AsyncTask<String, Void, ArrayList<Retrieval>>{

		@Override
		protected ArrayList<Retrieval> doInBackground(String... params) {
			
			 return MConnectionHelper.getViewRetrievalData();
		}
		
		@Override
		protected void onPostExecute(ArrayList<Retrieval> result) {
			
			rList = result;
			rListAdapter = new RetrievalListAdapter(RetrievalActivity.this,result);
			ListView listView = (ListView) findViewById(R.id.id_listview_rRetrieval);
			listView.setAdapter(rListAdapter);
			listView.setOnItemClickListener(RetrievalActivity.this);
			if (rList.size() == 0) {
				menu.findItem(R.id.id_action_r_finalize).setVisible(false);
			}
		
			super.onPostExecute(result);
		}		
		
	} 
	
	/*
	 * Save retrieval Object once the item are retrieved from store
	 * POST -> 
	 */
	
	private class SaveRetrievalList extends AsyncTask<String, Void, Boolean>{

		@Override
		protected Boolean doInBackground(String... params) {
			
			MConnectionHelper.SaveRetrievalList(MJSONHandler.createJRetrievalObjectFromList(rList));
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			
			if (result) {
				Toast.makeText(RetrievalActivity.this, "Changes has been finalized", Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
		}
	}
		
		
}
