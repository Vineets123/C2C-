package com.example.c2e;

import geter_seter.FaqClass;
import geter_seter.Faqadapter;
import geter_seter.STAFF;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class student_Q_A extends Activity {
	String ip=new  ip().callip();
	ProgressDialog pDialog = null;
	int s_id;
	SharedPreferences sp;
	EditText compl;
	Spinner spin;
	String compliant;
	String URL_Staff = ip+"get_staff.php";
	String URL_FAQ = ip+"FAQ.php";
	String URL_RESP = ip+"Get_FAQ_RESP.php";
	ArrayList<STAFF> staffList;
	ListView lv;
	ArrayList<FaqClass> responss = new ArrayList<FaqClass>();
	int idd, staff_select;
	String Staff_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new Getstaff().execute();
		setContentView(R.layout.student_q_a);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		s_id = sp.getInt("login_id", 0);
		spin = (Spinner) findViewById(R.id.spin);
		Log.e("Student_identification", "" + s_id);
		staffList = new ArrayList<STAFF>();
		compl = (EditText) findViewById(R.id.questionedittext);
		lv = (ListView) findViewById(R.id.questionlistview);
		new GetResp().execute();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				FaqClass s = (FaqClass) parent.getItemAtPosition(position);
				String faq_id = s.getFaq_id();
				String faq = s.getFaq_question();
				Intent ob = new Intent(student_Q_A.this, Q_A_Answer.class);
				ob.putExtra("faqId", faq_id);
				ob.putExtra("Ques", faq);
				startActivity(ob);

			}
		});

	}

	public void submit(View v) {
		compliant = compl.getText().toString();
		new getsubmit().execute();

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
							FaqClass faq = new FaqClass(
									docObj.getString("faq_id"),
									docObj.getString("faq_question"));
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
			pDialog = new ProgressDialog(student_Q_A.this);
			pDialog.setMessage("Please Wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();

			Faqadapter adapt = new Faqadapter(getApplicationContext(), responss);
			lv.setAdapter(adapt);
		}

	}

	public class getsubmit extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(student_Q_A.this);
			pDialog.setMessage("Loading .");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("compliant", compliant));
			nameValuePairs.add(new BasicNameValuePair("student_id", "" + s_id));
			nameValuePairs.add(new BasicNameValuePair("staff", ""+staff_select));

			ServiceHandler service = new ServiceHandler();
			String json = service.makeServiceCall(URL_FAQ, ServiceHandler.POST,
					nameValuePairs);
			Log.e("values", json);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (pDialog.isShowing()) {
				pDialog.dismiss();
			}

			Toast.makeText(getApplicationContext(), "Successful",
					Toast.LENGTH_LONG).show();
			new GetResp().execute();
		}

	}

	// ...............async task class for staff .........//
	private class Getstaff extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
			//pDialog = new ProgressDialog(student_Q_A.this);
			//pDialog.setMessage("Loading .");
			//pDialog.setCancelable(false);
			//pDialog.show();
		}

		protected Void doInBackground(Void... params) {

			// get staff values from database//

			ServiceHandler shandler = new ServiceHandler();

			String jsonspin1 = shandler.makeServiceCall(URL_Staff,
					ServiceHandler.GET);
			Log.e("json ", "" + jsonspin1);

			if (jsonspin1 != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonspin1);
					if (jsonObj != null) {

						JSONArray distarray = jsonObj.getJSONArray("staff");

						Log.e("json array", "" + distarray);
						for (int i = 0; i < distarray.length(); i++) {

							JSONObject jobj = (JSONObject) distarray.get(i);
							Log.e("district", "" + jobj);

							STAFF getdist = new STAFF(jobj.getInt("id"),
									jobj.getInt("staff_id"),
									jobj.getString("staff_name"));
							Log.e("getdist ", "" + getdist);
							staffList.add(getdist);
							Log.e("list ", "> " + staffList);

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

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			//if (pDialog.isShowing()) {
			//	pDialog.dismiss();
			//}
			populatedistrictSpinner();
		}

	}

	private class MyAdapter extends BaseAdapter implements SpinnerAdapter {

		/**
		 * The internal data (the ArrayList with the Objects).
		 */
		private final ArrayList<STAFF> data;

		public MyAdapter(ArrayList<STAFF> specList) {
			this.data = specList;
		}

		/**
		 * Returns the Size of the ArrayList
		 */
		@Override
		public int getCount() {
			return data.size();
		}

		/**
		 * Returns one Element of the ArrayList at the specified position.
		 */
		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		/**
		 * Returns the View that is shown when a element was selected.
		 */
		@Override
		public View getView(int position, View recycle, ViewGroup parent) {
			TextView text;
			if (recycle != null) {
				// Re-use the recycled view here!
				text = (TextView) recycle;
			} else {
				// No recycled view, inflate the "original" from the platform:
				text = (TextView) getLayoutInflater().inflate(
						android.R.layout.simple_dropdown_item_1line, parent,
						false);
			}
			text.setTextColor(Color.BLACK);
			text.setText(data.get(position).getStaff_name());
			return text;
		}

	}

	// ...........//
	private void populatedistrictSpinner() {

		// List<String> distlist1 = new ArrayList<String>();

		MyAdapter myAdapter = new MyAdapter(staffList);
		// attaching data adapter to spinner
		spin.setAdapter(myAdapter);

		spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				STAFF dis = (STAFF) parent.getItemAtPosition(pos);
				idd = dis.getId();
				staff_select = dis.getStaff_id();
				Log.e("id", "" + staff_select);

				// .........getting district values by passing selected
				// state....//

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

}
