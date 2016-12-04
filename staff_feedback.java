package com.example.c2e;

import geter_seter.FaqClass;
import geter_seter.Faqadapter;
import geter_seter.adapt;
import geter_seter.qA;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class staff_feedback extends Activity {
	String re;
	ProgressDialog pDialog = null;
	int s_id;
	SharedPreferences sp;
	EditText compl;
	String compliant;
	
	String ip=new  ip().callip();
	String URL_REP = ip+"StaffReplay.php";
	String URL_RESP = ip+"Get_question.php";
	String faq_id;
	ListView lv;
	ArrayList<qA> responss = new ArrayList<qA>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.staff_feed);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		s_id = sp.getInt("login_id", 0);
		Log.e("stafff_id", "" +s_id);
		new GetResp().execute();
		lv = (ListView) findViewById(R.id.listView1);

	lv.setOnItemClickListener(new OnItemClickListener() {
	@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				qA s = (qA) parent.getItemAtPosition(position);
				faq_id = s.getId();
				String faq = s.getQuestion();
				// Intent ob = new Intent(staff_feedback.this,
				// staff_reply.class);
				// ob.putExtra("faqId", faq_id);
				// ob.putExtra("Ques", faq);
				// startActivity(ob);
				final Dialog d = new Dialog(staff_feedback.this);
				d.setTitle("Responds");
				d.setContentView(R.layout.staff_replay);
				Button b = (Button) d.findViewById(R.id.button1);
				final EditText e = (EditText) d.findViewById(R.id.editText1);
				TextView tv = (TextView) d.findViewById(R.id.textView1);
				tv.setText(faq);
				b.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						re = e.getText().toString();
						if (re.equals("")) {
							Toast.makeText(staff_feedback.this,
									"Type your replay", Toast.LENGTH_LONG)
									.show();
						} else {
							Toast.makeText(staff_feedback.this,
									"You send Replyal successfully",
									Toast.LENGTH_LONG).show();
							new sendreplay().execute();
						}
						d.dismiss();
					}

				});
				d.show();
			}

		});

	}

	private class sendreplay extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg) {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("replay", re));
			nameValuePairs.add(new BasicNameValuePair("feed", faq_id));
			nameValuePairs.add(new BasicNameValuePair("staff_id", "" + s_id));

			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(URL_REP,
					ServiceHandler.POST, nameValuePairs);

			Log.e("Response: ", "> " + json);
			return null;
		}
	}

	private class GetResp extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... arg) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", "" + s_id));
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
							qA faq = new qA(docObj.getString("id"),
									docObj.getString("name"),
									docObj.getString("question"));
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
			pDialog = new ProgressDialog(staff_feedback.this);
			pDialog.setMessage("Please Wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();

			adapt ada = new adapt(getApplicationContext(), responss);
			lv.setAdapter(ada);
		}

	}

}
