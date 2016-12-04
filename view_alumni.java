package com.example.c2e;

import geter_seter.ChapterAdapter;
import geter_seter.aluni_adapt;
import geter_seter.getset_alumni;
import geter_seter.time_tbl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

public class view_alumni extends Activity {
	String ip=new  ip().callip();
	SharedPreferences sp;
	Editor myedit;
	String u_id, top_id;
	ProgressDialog pDialog = null;
	String URL_ALUMNI = ip+"get_alumni.php";
	// String URL_SHEDULE = "http://192.168.1.11:8084/ServerCOde/Shedule.php";
	String URL_DOWNLOAD;
	ArrayList<getset_alumni> TopList = new ArrayList<getset_alumni>();
	ListView lv;

	String id, file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_alumni);
		sp = PreferenceManager.getDefaultSharedPreferences(view_alumni.this);
		myedit = sp.edit();

		lv = (ListView) findViewById(R.id.listView1);
		new GetChaps().execute();

	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(1, 1, 1, "Resumes");
		
		

		return super.onCreateOptionsMenu(menu);

	}
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {
			Intent ob = new Intent(getApplicationContext(),view_resume.class);
			startActivity(ob);
		}
		

		return super.onOptionsItemSelected(item);
	}


	
	

	private class GetChaps extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(view_alumni.this);
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
							getset_alumni c = new getset_alumni(
									job.getString("name"),
									job.getString("gender"),
									job.getString("occ"),
									job.getString("address"),
									job.getString("ph"),
									job.getString("email"),
									job.getString("course"),
									job.getString("year"));

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

			aluni_adapt ta = new aluni_adapt(view_alumni.this, TopList);
			lv.setAdapter(ta);

		}

	}

}
