package sg.edu.nus.iss.mobilelusis.store;

import java.util.ArrayList;

import sg.edu.nus.iss.mobilelusis.R;
import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.Retrieval;
import sg.edu.nus.iss.mobilelusis.model.RetrievalByDept;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class RDepartmentBreakdown extends Activity implements OnItemClickListener{
	
	
	RDepartmentbreakdownDetail rDetailByDept;
	RDeptListAdapter rDeptListAdapter;
	ArrayList<RetrievalByDept> rDeptList = new ArrayList<RetrievalByDept>();
	TextView RetrievedQty;
	TextView RequestedQty;
	TextView binID;
	TextView Avaliability;
	Retrieval retrieval;
	Employee employee;
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putParcelable("retrieval", retrieval);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(sg.edu.nus.iss.mobilelusis.R.layout.activity_rdepartment_breakdown);
		
		Bundle data = getIntent().getExtras();
		
		if (data.containsKey(IConstants.EXTRA_EMPLOYEE)) {
			employee = data.getParcelable(IConstants.EXTRA_EMPLOYEE);
		}
		
		if (savedInstanceState != null && savedInstanceState.containsKey("retrieval")) {
			retrieval = savedInstanceState.getParcelable("retrieval");	
		}else{
			retrieval = (Retrieval) data.getParcelable("RetrievalObj");
		}
		
		setTitle(retrieval.getItemDesc());
		
		binID = (TextView) findViewById(R.id.id_txt_retrieval_binNo);
		Avaliability = (TextView) findViewById(R.id.id_txt_retrieval_avaliability );
		RequestedQty = (TextView) findViewById(R.id.id_txt_retrieval_required);
		RetrievedQty = (TextView) findViewById(R.id.id_txt_retrieval_retrieved);
		
		binID.setText(retrieval.getItemBinID());
		Avaliability.setText(retrieval.getAvaliability()+" "+retrieval.getUnit());
		RequestedQty.setText(retrieval.getRequestedQty()+" "+retrieval.getUnit());
		RetrievedQty.setText(retrieval.getRetrievedQty()+" "+retrieval.getUnit());
		
		ArrayList<RetrievalByDept> retrievalByDeptList = retrieval.getRetrievalByDeptList();

		
		rDeptList = retrieval.getRetrievalByDeptList();
		
		ListView rDptListView = (ListView) findViewById(R.id.id_listview_rdepartment_breakdown);
		rDeptListAdapter = new RDeptListAdapter(RDepartmentBreakdown.this,retrieval);
		rDptListView.setAdapter(rDeptListAdapter);
		rDptListView.setOnItemClickListener(RDepartmentBreakdown.this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rdepartment_breakdown, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id == android.R.id.home ){
			finish();
		}
		
		return true;
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		Intent i = new Intent();
		int update_retrievedQty = 0;
		for (int j = 0; j < rDeptList.size(); j++) {
			
			update_retrievedQty += Integer.parseInt(rDeptList.get(j).getRetrievedQty());
		}
		retrieval.setRetrievedQty(String.valueOf(update_retrievedQty));
		i.putExtra("UpdatedRetrievalObj", retrieval);
		i.putExtra(IConstants.EXTRA_EMPLOYEE,employee);
		setResult(RESULT_OK,i);
		super.finish();
		
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	
}
