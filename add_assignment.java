package com.example.c2e;

import geter_seter.time_tbl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class add_assignment extends Activity {
	String ip=new  ip().callip();
	private String URL_ASSIGNMENT = ip+"add_assignment.php";
	Button submit;
	EditText sem, sub, topic, date;
	String s, su, to, da;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_assignment);
		
		
		
		submit = (Button) findViewById(R.id.submit);
		sem = (EditText) findViewById(R.id.editText1);
		sub = (EditText) findViewById(R.id.editText2);
		topic = (EditText) findViewById(R.id.editText3);
		date = (EditText) findViewById(R.id.editText4);
		final Calendar mycalander = Calendar.getInstance();
		final DatePickerDialog.OnDateSetListener datee = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				mycalander.set(Calendar.YEAR, year);
				mycalander.set(Calendar.MONTH, monthOfYear);
				mycalander.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				getdate();

			}

			private void getdate() {
				// TODO Auto-generated method stub

				String myFormat = "yyyy-MM-dd"; // In which you need put here
				SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

				date.setText(sdf.format(mycalander.getTime()));
				da = date.getText().toString();
			}
		};
		date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(add_assignment.this, datee, mycalander
						.get(Calendar.YEAR), mycalander.get(Calendar.MONTH),
						mycalander.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				s = sem.getText().toString();
				su = sub.getText().toString();
				to = topic.getText().toString();

				if (s.equals("") || su.equals("") || to.equals("")) {
					Toast.makeText(add_assignment.this,
							"please provide necessary details ",
							Toast.LENGTH_LONG).show();
				}

				else {
					new addAssign().execute();
					Toast.makeText(add_assignment.this,
							"details addeds uccessfully", Toast.LENGTH_LONG)
							.show();

					// description.setText("");
					// coast.setText("");
					
					//sem.setText("");
					//sub.setText("");
					//topic.setText("");
				}
			}
		});
	}

	private class addAssign extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg) {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("date", da));
			nameValuePairs.add(new BasicNameValuePair("sem", s));
			nameValuePairs.add(new BasicNameValuePair("sub", su));
			nameValuePairs.add(new BasicNameValuePair("topic", to));

			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(URL_ASSIGNMENT,
					ServiceHandler.POST, nameValuePairs);

			Log.e("Response: ", "> " + json);
			return null;
		}
	}

}
