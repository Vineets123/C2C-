package com.example.c2e;

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


public class Add_notice extends Activity {
	String ip=new  ip().callip();
	
	private String URL_NOTICE= ip+"add_notice.php";
	Button submit;
	EditText topic,msg, date;
	String t, m, da;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_notice);
		submit = (Button) findViewById(R.id.submit);
		topic = (EditText) findViewById(R.id.editText1);
		msg = (EditText) findViewById(R.id.editText3);
		
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
				new DatePickerDialog(Add_notice.this, datee, mycalander
						.get(Calendar.YEAR), mycalander.get(Calendar.MONTH),
						mycalander.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				t = topic.getText().toString();
				m = msg.getText().toString();
				

				if (t.equals("") || m.equals("")) {
					Toast.makeText(Add_notice.this,
							"please provide necessary details ",
							Toast.LENGTH_LONG).show();
				}

				else {
					new addAssign().execute();
					Toast.makeText(Add_notice.this,
							"details addeds Successfully", Toast.LENGTH_LONG)
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
		
			nameValuePairs.add(new BasicNameValuePair("msg", m));
			nameValuePairs.add(new BasicNameValuePair("topic", t));

			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(URL_NOTICE,
					ServiceHandler.POST, nameValuePairs);

			Log.e("Response: ", "> " + json);
			return null;
		}
	}

}
