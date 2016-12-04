package com.example.c2e;

import geter_seter.getset_assignment;
import geter_seter.view_assignment_adapter;

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
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class view_assignment extends Activity {
	String ip=new  ip().callip();
	ProgressDialog pdialog = null;
	private String URL_ViewAssignment = ip+"view_assignment.php";
	ListView list;
	ArrayList<getset_assignment> view_assignment;
	EditText e;
	String sem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_assignment);
		view_assignment = new ArrayList<getset_assignment>();
		list = (ListView) findViewById(R.id.listView1);
		e=(EditText) findViewById(R.id.editText1);
	}
	public void view(View v) {
		sem=e.getText().toString();
		if(sem.equals("")){
			Toast.makeText(getApplicationContext(),
					"Please input sem", Toast.LENGTH_SHORT).show();	
		}
		else{
		new GetAssignment().execute();
		}
	}

	private class GetAssignment extends AsyncTask<Void, Void, Void> {
		protected void onPreExecute() {
			super.onPreExecute();
			pdialog = new ProgressDialog(view_assignment.this);
			pdialog.setMessage("please wait");
			pdialog.setCancelable(false);
			pdialog.show();

		}

		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			nameValuePairs.add(new BasicNameValuePair("sem",sem));
			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(URL_ViewAssignment,
					ServiceHandler.POST, nameValuePairs);
			Log.e("response", ">" + json);
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
							getset_assignment g = new getset_assignment(
									
									job.getString("sem"),
									job.getString("subject"),
									job.getString("topic"),
									job.getString("date"));
							Log.e("view_seed_status", ">" + g);
							view_assignment.add(g);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pdialog.isShowing())
				pdialog.dismiss();

			view_assignment_adapter adapt = new view_assignment_adapter(
					getApplicationContext(), view_assignment);
			list.setAdapter(adapt);

		}

	}
}
