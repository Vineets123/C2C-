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



public class Fines extends Activity {
	String STUD_ID;
	SharedPreferences sp;
	ProgressDialog pDialog = null;
	ArrayList<Fine> FineList;
	ListView fineli;
	String ip=new  ip().callip();
	private String URL_CAL = ip+"get_fines.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fines);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		STUD_ID = sp.getString("studentid", "");
		FineList = new ArrayList<Fine>();
		fineli = (ListView) findViewById(R.id.fineelist);
		new GetFines().execute(STUD_ID);

	}

	private class GetFines extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Fines.this);
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
			String json = jsonParser.makeServiceCall(URL_CAL,
					ServiceHandler.POST, params);

			Log.e("Response: ", "> " + json);

			if (json != null) {
				try {
					JSONObject jsonObj = new JSONObject(json);
					if (jsonObj != null) {
						JSONArray result = jsonObj.getJSONArray("Fine");
						Log.e("Json ", "> " + result);

						for (int i = 0; i < result.length(); i++) {
							Log.e("Length ", "> " + result.length());
							JSONObject fineObj = (JSONObject) result.get(i);
							Log.e("fineObj ", "> " + fineObj);
							Fine fin = new Fine(fineObj.getInt("rule_violation_id"),
									fineObj.getString("last_date_fine"),
									fineObj.getString("rule_name"),
									fineObj.getString("description"),
									fineObj.getString("fine"));
							Log.e("calender ", "> " + fin);
							FineList.add(fin);
							Log.e("calenderList ", "> " + FineList);
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
			ArrayList<Fine> lables = new ArrayList<Fine>();
			for (int i = 0; i < FineList.size(); i++) {
				lables.add(i, FineList.get(i));
			}
			FineAdapt adapt = new FineAdapt(getApplicationContext(), lables);
			fineli.setAdapter(adapt);

		}

	}
}
