package sg.edu.nus.iss.mobilelusis.dept.controller;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import sg.edu.nus.iss.mobilelusis.model.Employee;
import sg.edu.nus.iss.mobilelusis.utils.IConstants;
import sg.edu.nus.iss.mobilelusis.utils.JSONHandler;
import sg.edu.nus.iss.mobilelusis.utils.JSONParser;
import android.util.Log;

/*************************************************************************
 * LoginController: Controller that handles checking login credentials
 * @author A0120502E
 *
 *************************************************************************/

public class LoginController {
	
//	boolean isGETMethod = false;
	
	public LoginController() {
		super();
	}
	
	
	public Employee login(String empID, String pwd) {
		empID.trim();
		pwd.trim();
		JSONObject loginRequestObject = JSONHandler.login(empID, pwd);
		return sendLoginPOST(loginRequestObject);
	}

	/*
	 * GET Login helper method
	 */
	private String addLoginParamsToUrl(String url, String username, String password){
		if(!url.endsWith("?"))
			url += "?";
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("user", username));
		params.add(new BasicNameValuePair("password", password));	    
		String paramString = URLEncodedUtils.format(params, "utf-8");
		url += paramString;
		return url;
	}


	private Employee sendLoginPOST(JSONObject jsonObj) {

		Employee employee = null;
		String uri = IConstants.URL + "/" + IConstants.SERVLET + "/" + IConstants.WCF_LOGIN;
		Log.i("URI login", uri);
		String data = jsonObj.toString();
		String response = JSONParser.postStream(uri, data);
		Log.i("res", response);
		if (response != null && !response.equalsIgnoreCase("")) {
			try {
				JSONObject obj = new JSONObject(response);
				employee = JSONHandler.parseLoginEmployee(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return employee;
	}
	
	
}
