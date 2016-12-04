package com.example.c2e;

import geter_seter.FaqAnswerAdapt;
import geter_seter.FaqAnswerGet;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class Q_A_Answer extends Activity{
	ProgressDialog pDialog = null;
	String ip=new  ip().callip();
	String URL_RESP = ip+"Get_Faq_Answers.php";
	String faq_id, faq;
	ListView lv;
	ArrayList<FaqAnswerGet> responss = new ArrayList<FaqAnswerGet>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.q_a_answer);
		faq_id = getIntent().getExtras().getString("faqId");
		faq = getIntent().getExtras().getString("Ques");
		TextView t = (TextView) findViewById(R.id.textView2);
		lv = (ListView) findViewById(R.id.listView1);
		t.setText("" + faq);
		new GetResp().execute();

}
	private class GetResp extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... arg) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", faq_id));
			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(URL_RESP,
					ServiceHandler.POST, params);

			Log.e("Response: ", "> " + json);

			if (json != null) {
				try {
					JSONObject jsonObj = new JSONObject(json);
					if (jsonObj != null) {
						JSONArray response = jsonObj.getJSONArray("aa");
						Log.e("Json ", "> " + response);
						responss.clear();

						for (int i = 0; i < response.length(); i++) {
							Log.e("Length ", "> " + response.length());
							JSONObject docObj = (JSONObject) response.get(i);
							Log.e("docObj ", "> " + docObj);
							FaqAnswerGet faq = new FaqAnswerGet(
									docObj.getString("faq_answer_id"),
									docObj.getString("faq_answer"),
									docObj.getString("staff_first_name"),
									docObj.getString("staff_last_name"));
							responss.add(faq);
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
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Q_A_Answer.this);
			pDialog.setMessage("Please Wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();

			FaqAnswerAdapt adapt = new FaqAnswerAdapt(getApplicationContext(),
					responss);
			lv.setAdapter(adapt);
		}

	}
}
