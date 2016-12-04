package com.example.c2e;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;

public class FeedBack extends Activity {

	String STUD_ID;
	SharedPreferences sp;
	ProgressDialog pDialog = null;
	ArrayList<Feed> FeedbackList;
	ListView resultli;
	String ip=new  ip().callip();
	private String URL_RES = ip+"get_feedback.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		STUD_ID = sp.getString("studentid", "");
		FeedbackList = new ArrayList<Feed>();
		resultli = (ListView) findViewById(R.id.feedlist);
		new GetResults().execute(STUD_ID);

	}

	private class GetResults extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(FeedBack.this);
			pDialog.setMessage("Please Wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(String... arg) {
			String id = arg[0];
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("stud_id", id));
			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(URL_RES,
					ServiceHandler.POST, params);

			Log.e("Response: ", "> " + json);

			if (json != null) {
				try {
					JSONObject jsonObj = new JSONObject(json);
					if (jsonObj != null) {
						JSONArray result = jsonObj.getJSONArray("Feedback");
						Log.e("Json ", "> " + result);

						for (int i = 0; i < result.length(); i++) {
							Log.e("Length ", "> " + result.length());
							JSONObject feedObj = (JSONObject) result.get(i);
							Log.e("resObj ", "> " + feedObj);
							Feed feed = new Feed(feedObj.getInt("feedback_id"),
									feedObj.getString("feedback_description"),
									feedObj.getString("first_name"),
									feedObj.getString("last_name"));
							Log.e("FeedBack ", "> " + feed);
							FeedbackList.add(feed);
							Log.e("Feedbacklist ", "> " + FeedbackList);
						}
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			} else {
				Log.e("JSON Data", "Didn't receive any data from server!");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();
			ArrayList<Feed> lables = new ArrayList<Feed>();
			for (int i = 0; i < FeedbackList.size(); i++) {
				lables.add(i, FeedbackList.get(i));
			}
			FeedAdapt adapt = new FeedAdapt(getApplicationContext(), lables);
			resultli.setAdapter(adapt);

		}

	}
}
