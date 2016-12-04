package com.example.c2e;

import geter_seter.get_fee;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class view_fee extends Activity {
	String ip=new  ip().callip();
	private String URL_FEE = ip+"view_fee.php";
	EditText sem;
	TextView date,amount,type;
	Button view;
	String se,dates,rs,typ;
	ProgressDialog pdialog = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_fee);
		sem=(EditText) findViewById(R.id.editText1);
		amount=(TextView) findViewById(R.id.text1);
		date=(TextView) findViewById(R.id.text2);
		type=(TextView) findViewById(R.id.text);
		view=(Button) findViewById(R.id.button1);
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				se=sem.getText().toString();
				if(se.equals("")){
					Toast.makeText(view_fee.this, "input sem ", Toast.LENGTH_LONG).show();
				}
				else{
					new GetFEE().execute();
				}
			}
		});
	}
	private class GetFEE extends AsyncTask<Void, Void, Void> {
		protected void onPreExecute() {
			super.onPreExecute();
			pdialog = new ProgressDialog(view_fee.this);
			pdialog.setMessage("please wait ");
			pdialog.setCancelable(false);
			pdialog.show();

		}

		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			// nameValuePairs
			// .add(new BasicNameValuePair("date",date));
			nameValuePairs
					.add(new BasicNameValuePair("sem",se));
			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(URL_FEE,
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
							get_fee g = new get_fee(
									job.getString("date"),job.getString("type"),
									job.getString("amount"));
									dates=job.getString("date");
									rs=job.getString("amount");
									typ=job.getString("type");
							Log.e("facility_details", ">" + g);
							
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
			date.setText("Last Date"+" "+dates);
			amount.setText("Rs"+rs);
			type.setText(typ);

		}

	}
}
