package com.example.c2e;

import java.util.ArrayList;

import geter_seter.ServiceHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.apache.http.message.BasicNameValuePair;
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

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

public class MainActivity extends Activity {
	String ip=new  ip().callip();
	private String URL_login = ip+"login_check.php";
	// private String URL_STATE = "http://10.0.2.2/I-Starve/get_state.php";
	ProgressDialog pDialog = null;
	EditText uname, paswd;
	Button login, student, staff, parent;
	SharedPreferences sp;
	Editor ed;
	String un, pw;
	String u, p, utyp;

	int login_id, stud_id, staff_id, parent_id;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		ed = sp.edit();

		//ed.clear();
		//ed.commit();

		setContentView(R.layout.activity_main);
		
		uname = (EditText) findViewById(R.id.uname);
		paswd = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);

		staff = (Button) findViewById(R.id.staff);

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				un = uname.getText().toString();
				pw = paswd.getText().toString();
				if (un.equals("") || pw.equals("")) {
					Toast.makeText(MainActivity.this,
							"user name and password are Nessessary",
							Toast.LENGTH_LONG).show();

				} else {
					new getsubmit().execute();
				}
			}
		});

		staff.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent ob1 = new Intent(MainActivity.this,staff_reg.class);
				startActivity(ob1);

			}
		});
	}

	public void user(View v) {
		Intent on=new Intent(MainActivity.this,student_reg.class);
		startActivity(on);
		
	}

	public void parent(View v) {
		Intent obb = new Intent(MainActivity.this,parent_reg.class);
		startActivity(obb);

	}

	public void alumni(View v) {
		Intent obb = new Intent(MainActivity.this,alumni_reg.class);
		startActivity(obb);

	}
	

	public class getsubmit extends AsyncTask<Void, Void, Void> {

		protected Void doInBackground(Void... params) {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("uname",un));
			nameValuePairs.add(new BasicNameValuePair("password", pw));
			ServiceHandler service = new ServiceHandler();
			String json = service.makeServiceCall(URL_login,
					ServiceHandler.POST, nameValuePairs);
			Log.e("values", json);

			if (json != null) {
				try {
					JSONObject jsonObj = new JSONObject(json);
					if (jsonObj != null) {
						JSONArray loginarray = jsonObj.getJSONArray("user");

						Log.e("json array", "" + loginarray);
						for (int i = 0; i < loginarray.length(); i++) {

							JSONObject jobj = (JSONObject) loginarray.get(i);
							Log.e("user_id", "" + jobj);

							logincheck getid = new logincheck(
									jobj.getInt("login_id"),

									jobj.getString("uname"),
									jobj.getString("paswd"),
									jobj.getString("type"));

							// -----------------login_id------------//

							login_id = getid.getLogin_id();
							utyp = getid.getType();
ed.putInt("login_id", login_id);
ed.commit();
							Log.e("login_id ", "" + login_id);
							Log.e("type ", utyp);

							if (login_id == 0) {
								runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(MainActivity.this,
												"plz register first",
												Toast.LENGTH_LONG).show();
										uname.setText("");
										paswd.setText("");
									}
								});

							}

						else {
								ed.putString("uname", u);
								ed.putString("paswd", p);
								ed.putString("type", utyp);
								ed.putInt("login_id", login_id);

								ed.commit();
								u = sp.getString("uname", "");
								p = sp.getString("paswd", "");
								login_id = sp.getInt("login_id", 0);

								// utyp=sp.getString("type", "");
								// Log.e("type", utyp);
							}
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
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (utyp.equals("student")) {
				Intent ob = new Intent(MainActivity.this, student_home.class);
				startActivity(ob);

			} else if (utyp.equals("staff")) {
				Intent ob = new Intent(MainActivity.this, staff_home.class);
				startActivity(ob);
			} else if (utyp.equals("parent")) {
				Intent ob = new Intent(MainActivity.this, parent_home.class);
				startActivity(ob);
			}
			 else if (utyp.equals("alumni")) {
					Intent ob = new Intent(MainActivity.this, alumni_home.class);
					startActivity(ob);
				}

		}

	}

}
