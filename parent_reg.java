package com.example.c2e;

import java.util.ArrayList;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class parent_reg extends Activity {
	String ip=new  ip().callip();
	private String URL_USERREG = ip+"parent_reg.php";

	EditText fname, lname, dob, add, sph, stdem, studuname, studpword,
			studcped, cource, bach;
	RadioGroup rg;
	RadioButton rb;
	Button register;
	int studentid;
	SharedPreferences sp;
	Editor myedit;
	String s1, s2, uname, paswd;
	String stud_fname, stud_lname, stud_dob, stud_add, stud_phone, stud_email,
			stud_uname, stud_pword, stud_cpass, selected_course,
			selected_batch, gender, a;
	int user_id, login_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		myedit = sp.edit();

		s1 = sp.getString("uname", "");
		s2 = sp.getString("paswd", "");
		// ed.clear();
		// ed.commit();
		if (s1.equals(""))

		{
			setContentView(R.layout.parent_reg);
			fname = (EditText) findViewById(R.id.edit_fname);
			lname = (EditText) findViewById(R.id.edit_lname);

			add = (EditText) findViewById(R.id.address);
			sph = (EditText) findViewById(R.id.phone);

			studuname = (EditText) findViewById(R.id.studusername);
			studpword = (EditText) findViewById(R.id.studpassword);
			studcped = (EditText) findViewById(R.id.studcpassword);

			rg = (RadioGroup) findViewById(R.id.radioGroup1);

			cource = (EditText) findViewById(R.id.course);
			bach = (EditText) findViewById(R.id.department);

			register = (Button) findViewById(R.id.register);

			register.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					stud_fname = fname.getText().toString();
					stud_lname = lname.getText().toString();

					stud_add = add.getText().toString();
					stud_phone = sph.getText().toString();

					stud_uname = studuname.getText().toString();
					stud_pword = studpword.getText().toString();
					stud_cpass = studcped.getText().toString();
					selected_course = cource.getText().toString();
					selected_batch = bach.getText().toString();
					int id = rg.getCheckedRadioButtonId();
					rb = (RadioButton) findViewById(id);
					gender = rb.getText().toString();

					if (stud_fname.equals("") || stud_lname.equals("")
							|| stud_add.equals("") || stud_phone.equals("")
							 || stud_uname.equals("")
							|| stud_pword.equals("") || stud_cpass.equals("")
							|| gender.equals("")) {

						Toast.makeText(parent_reg.this,
								"All Fields Nessessary", Toast.LENGTH_LONG)
								.show();

					} else if (stud_pword.equals(stud_cpass) == false) {
						Toast.makeText(getApplicationContext(),
								"Password not match", Toast.LENGTH_SHORT)
								.show();
					}

					else {
						new getsubmit().execute();
						Toast.makeText(parent_reg.this,
								"YOur Registration is Success",
								Toast.LENGTH_SHORT).show();
						Intent ob = new Intent(parent_reg.this,
								parent_home.class);
						startActivity(ob);

					}

				}
			});

		}

		else {

			Intent ob = new Intent(getApplicationContext(), student_home.class);
			startActivity(ob);
		}

	}

	public class getsubmit extends AsyncTask<Void, Void, Void> {

		protected Void doInBackground(Void... params) {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs
					.add(new BasicNameValuePair("stud_fname", stud_fname));
			nameValuePairs
					.add(new BasicNameValuePair("stud_lname", stud_lname));
		
			nameValuePairs.add(new BasicNameValuePair("stud_add", stud_add));
			nameValuePairs
					.add(new BasicNameValuePair("stud_phone", stud_phone));
			nameValuePairs
					.add(new BasicNameValuePair("stud_email", stud_email));
			nameValuePairs
					.add(new BasicNameValuePair("stud_uname", stud_uname));
			nameValuePairs
					.add(new BasicNameValuePair("stud_pword",stud_pword));

			nameValuePairs.add(new BasicNameValuePair("gender", gender));
			nameValuePairs.add(new BasicNameValuePair("selected_course",
					selected_course));
			nameValuePairs.add(new BasicNameValuePair("selected_batch",
					selected_batch));
			ServiceHandler service = new ServiceHandler();
			String json = service.makeServiceCall(URL_USERREG,
					ServiceHandler.POST, nameValuePairs);
			Log.e("values", json);

			// .........................get login_id,user_id.................//

			if (json != null) {
				try {
					JSONObject jsonObj = new JSONObject(json);
					if (jsonObj != null) {

						JSONArray loginarray = jsonObj.getJSONArray("user");

						Log.e("json array", "" + loginarray);
						for (int i = 0; i < loginarray.length(); i++) {

							JSONObject jobj = (JSONObject) loginarray.get(i);
							Log.e("user_id", "" + jobj);

							login getid = new login(jobj.getInt("user_id"),
									jobj.getInt("login_id"));

							// -----------------login_id------------//
							user_id = getid.getUser_id();
							login_id = getid.getLogin_id();

							Log.e("user id ", "" + user_id);
							Log.e("login_id ", "" + login_id);

							myedit.putString("uname", stud_uname);
							myedit.putString("paswd", stud_pword);
							myedit.putInt("student_id", user_id);
							myedit.putInt("student_login", login_id);

							myedit.commit();

							Log.e("username", stud_uname);

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

	}
}
