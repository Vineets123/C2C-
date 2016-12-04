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

public class parent_home extends Activity {
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
		user_id = sp.getInt("parent_login", 0);
		Log.e("login_idddd", "" + user_id);
		setContentView(R.layout.parent_home);
		b = (Button) findViewById(R.id.logout);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ed.clear();
				// ed.commit();
				Intent ob = new Intent(parent_home.this, MainActivity.class);
				startActivity(ob);
			}
		});
	}
	public void fee(View v){
		Intent ob = new Intent(getApplicationContext(), view_fee.class);
		startActivity(ob);
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(1, 1, 1, "Child Fee Details");

		return super.onCreateOptionsMenu(menu);

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {
			Intent ob = new Intent(getApplicationContext(), view_fee.class);
			startActivity(ob);
		}

		return super.onOptionsItemSelected(item);
	}

}
