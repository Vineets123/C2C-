package com.example.c2e;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class alumni_home  extends Activity {
	SharedPreferences sp;
	Button b;
	int user_id;
	Editor ed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		ed = sp.edit();
		user_id = sp.getInt("login_id", 0);
		Log.e("alumniiiiiiiiiiiii", "" + user_id);
		setContentView(R.layout.student_home);
		b = (Button) findViewById(R.id.logout);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ed.clear();
				// ed.commit();
				Intent ob = new Intent(alumni_home.this, MainActivity.class);
				startActivity(ob);
			}
		});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		menu.add(1, 1, 1, "Exam Details");
		menu.add(1, 2, 2, "FeeDetails");
		menu.add(1, 3, 3, "Upload Resume");
		menu.add(1, 4, 4, "Update Details");
		

		return super.onCreateOptionsMenu(menu);

	}
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {
			Intent ob = new Intent(getApplicationContext(), view_exam.class);
			startActivity(ob);
		}
		else if (item.getItemId() == 2) {
			Intent ob = new Intent(getApplicationContext(), view_fee.class);
			startActivity(ob);
		}
		else if (item.getItemId() == 3) {
			Intent ob = new Intent(getApplicationContext(), upload_resume.class);
			startActivity(ob);
		}
		else if (item.getItemId() == 4) {
			Intent ob = new Intent(getApplicationContext(), alumni_update.class);
			startActivity(ob);
		}
		

		return super.onOptionsItemSelected(item);
	}

}
