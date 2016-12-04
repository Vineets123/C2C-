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

public class staff_home extends Activity {
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
		user_id = sp.getInt("staff_login", 0);
		Log.e("login_idddd", "" + user_id);
		setContentView(R.layout.staff_home);
		b = (Button) findViewById(R.id.logout);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ed.clear();
				// ed.commit();
				Intent ob = new Intent(staff_home.this, MainActivity.class);
				startActivity(ob);
			}
		});
	}
	public void time(View v){
		Intent ob = new Intent(getApplicationContext(), add_time_table.class);
		startActivity(ob);
	}
	public void assignment(View v){
		Intent ob = new Intent(getApplicationContext(), add_assignment.class);
		startActivity(ob);
	}
	public void notes(View v){
		Intent ob = new Intent(getApplicationContext(), add_notes.class);
		startActivity(ob);
	}
	public void exam(View v){
		Intent ob = new Intent(getApplicationContext(), add_exam.class);
		startActivity(ob);
	}
	public void fee(View v){
		Intent ob = new Intent(getApplicationContext(), add_fee.class);
		startActivity(ob);
	}
	public void qa(View v){
		Intent ob = new Intent(getApplicationContext(), staff_feedback.class);
		startActivity(ob);
	}
	public void alumni(View v){
		Intent ob = new Intent(getApplicationContext(), view_alumni.class);
		startActivity(ob);
	}
	public void notice(View v){
		Intent ob = new Intent(getApplicationContext(), Add_notice.class);
		startActivity(ob);
	}

//	public boolean onCreateOptionsMenu(Menu menu) {
//		// TODO Auto-generated method stub
//		menu.add(1, 1, 1, "Time Table");
//		menu.add(1, 2, 2, "Add Assignments");
//		menu.add(1, 3, 3, "Add Notes");
//		menu.add(1, 4, 4, "Add Exam Details");
//		menu.add(1, 5, 5, "Add FeeDetails");
//		menu.add(1, 6, 6, "Q & A");
//		menu.add(1, 7, 7, "Alumni Details");
//		menu.add(1, 8, 8, "Notice");
//
//		return super.onCreateOptionsMenu(menu);
//
//	}
//
//
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// TODO Auto-generated method stub
//		if (item.getItemId() == 1) {
//			Intent ob = new Intent(getApplicationContext(), add_time_table.class);
//			startActivity(ob);
//		}
//		else if (item.getItemId() == 2) {
//			Intent ob = new Intent(getApplicationContext(), add_assignment.class);
//			startActivity(ob);
//		}
//		else if (item.getItemId() == 3) {
//			Intent ob = new Intent(getApplicationContext(), add_notes.class);
//			startActivity(ob);
//		}
//		else if (item.getItemId() == 4) {
//			Intent ob = new Intent(getApplicationContext(), add_exam.class);
//			startActivity(ob);
//		}
//		else if (item.getItemId() == 5) {
//			Intent ob = new Intent(getApplicationContext(), add_fee.class);
//			startActivity(ob);
//		}
//		else if (item.getItemId() == 6) {
//			Intent ob = new Intent(getApplicationContext(), staff_feedback.class);
//			startActivity(ob);
//		}
//		else if (item.getItemId() == 7) {
//			Intent ob = new Intent(getApplicationContext(), view_alumni.class);
//			startActivity(ob);
//		}
//		else if (item.getItemId() ==8) {
//			Intent ob = new Intent(getApplicationContext(), Add_notice.class);
//			startActivity(ob);
//		}
//
//		return super.onOptionsItemSelected(item);
//	}

}
