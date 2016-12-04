package com.example.c2e;

import geter_seter.adapt_notice;
import geter_seter.aluni_adapt;
import geter_seter.getset_alumni;
import geter_seter.getset_notice;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class student_home extends Activity {
	String ip=new  ip().callip();
	String URL_ALUMNI = ip+"get_notice.php";
	SharedPreferences sp;
	Button b;
	int user_id;
	Editor ed;
	ListView list;
	ProgressDialog pDialog = null;
	ArrayList<getset_notice> TopList = new ArrayList<getset_notice>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		ed = sp.edit();
		user_id = sp.getInt("student_login", 0);
		Log.e("login_idddd", "" + user_id);
		setContentView(R.layout.student_home);
		b = (Button) findViewById(R.id.logout);
		list=(ListView) findViewById(R.id.listView1);
		new GETNotice().execute();
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ed.clear();
				// ed.commit();
				Intent ob = new Intent(student_home.this, MainActivity.class);
				startActivity(ob);
			}
		});
	}
	public void time(View v){
		Intent ob = new Intent(getApplicationContext(), view_Time.class);
		startActivity(ob);
	}
	public void assignment(View v){
		Intent ob = new Intent(getApplicationContext(), view_assignment.class);
		startActivity(ob);
	}
	public void notes(View v){
		Intent ob = new Intent(getApplicationContext(), view_notes.class);
		startActivity(ob);
	}
	public void exam(View v){
		Intent ob = new Intent(getApplicationContext(), view_exam.class);
		startActivity(ob);
	}
	public void fee(View v){
		Intent ob = new Intent(getApplicationContext(), view_fee.class);
		startActivity(ob);
	}
	public void qa(View v){
		Intent ob = new Intent(student_home.this, student_Q_A.class);
		startActivity(ob);
	}

//	public boolean onCreateOptionsMenu(Menu menu) {
//		// TODO Auto-generated method stub
//		menu.add(1, 1, 1, "Time Table");
//		menu.add(1, 2, 2, "Assignments");
//		menu.add(1, 3, 3, "Notes");
//		menu.add(1, 4, 4, "Exam Details");
//		menu.add(1, 5, 5, "FeeDetails");
//		menu.add(1, 6, 6, "Q & A");
//		
//
//		return super.onCreateOptionsMenu(menu);
//
//	}
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// TODO Auto-generated method stub
//		if (item.getItemId() == 1) {
//			Intent ob = new Intent(getApplicationContext(), view_Time.class);
//			startActivity(ob);
//		}
//		else if (item.getItemId() == 2) {
//			Intent ob = new Intent(getApplicationContext(), view_assignment.class);
//			startActivity(ob);
//		}
//		else if (item.getItemId() == 3) {
//			Intent ob = new Intent(getApplicationContext(), view_notes.class);
//			startActivity(ob);
//		}
//		else if (item.getItemId() == 4) {
//			Intent ob = new Intent(getApplicationContext(), view_exam.class);
//			startActivity(ob);
//		}
//		else if (item.getItemId() == 5) {
//			Intent ob = new Intent(getApplicationContext(), view_fee.class);
//			startActivity(ob);
//		}
//		else if (item.getItemId() == 6) {
//			Intent ob = new Intent(getApplicationContext(), student_Q_A.class);
//			startActivity(ob);
//		}
//
//		return super.onOptionsItemSelected(item);
//	}
	private class GETNotice extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(student_home.this);
			pDialog.setMessage("Wait..");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(URL_ALUMNI,
					ServiceHandler.POST, params);

			Log.e("Response: ", "> " + json);
			if (json != null) {
				try {
					JSONObject jsonobj = new JSONObject(json);
					if (jsonobj != null) {
						JSONArray pre = jsonobj.getJSONArray("aa");
						Log.e("json", ">" + pre);
						for (int i = 0; i < pre.length(); i++) {
							Log.e("length", ">" + pre.length());
							JSONObject job = (JSONObject) pre.get(i);
							Log.e("jsonobjct", ">" + job);
							getset_notice c = new getset_notice(
									job.getString("topic"),
									job.getString("msg"),
									job.getString("date"));
									

							Log.e("cat ", "> " + c);
							TopList.add(c);
							Log.e("list ", "> " + TopList);
						}
					}
				}

				catch (JSONException e) {
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

			adapt_notice ta = new adapt_notice(student_home.this, TopList);
			list.setAdapter(ta);

		}

	}




}
