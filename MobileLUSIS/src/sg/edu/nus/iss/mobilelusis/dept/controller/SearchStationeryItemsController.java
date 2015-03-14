package sg.edu.nus.iss.mobilelusis.dept.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.model.StationeryItem;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.IJSONConstants;
import sg.edu.nus.iss.mobilelusis.utils.JSONHandler;
import sg.edu.nus.iss.mobilelusis.utils.JSONParser;
import android.util.Log;

/*
 * Controls the sending of keyword when employee searches for stationery items
 * @author: Sim
 * @date: 2014.09.01
 */


public class SearchStationeryItemsController {

	public SearchStationeryItemsController() {
		super();
	}
	
	public ArrayList<StationeryItem> getItemsByKeyword(Employee emp, String keyword) {
		ArrayList<StationeryItem> result = new ArrayList<StationeryItem>();

		// 1. We do not need an AsyncTask because this is invoked in an AsyncTask itself.
		// We use GET method for querying.
		String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_SEARCH;
		Log.i("URI login", uri);
		String url = addSearchParamToUrl(uri, keyword);
		Log.i("URL", url);
		JSONObject returnObject = JSONParser.getJSONFromUrl(url);
		result = JSONHandler.parseItemsByKeyword(returnObject);
		return result;
	}
	
	/*
	 * GET Search by keyword helper method
	 */
	private String addSearchParamToUrl(String url, String keyword){
		if(!url.endsWith("?"))
			url += "?";
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(IJSONConstants.SEARCH_KEY, keyword));	    
		String paramString = URLEncodedUtils.format(params, IJSONConstants.UTF8);
		url += paramString;
		return url;
	}	
}
