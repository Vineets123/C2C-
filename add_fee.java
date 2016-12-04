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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class add_fee extends Activity {
	String ip=new  ip().callip();
	private String URL_FEE =ip+"add_fee.php";
	EditText sem,date,amount,typ;
	String se,dat,amnt,type;
	Button ok;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_fee);
		sem=(EditText) findViewById(R.id.editText1);
		typ=(EditText) findViewById(R.id.editText);
		date=(EditText) findViewById(R.id.editText2);
		amount=(EditText) findViewById(R.id.editText3);
		ok=(Button) findViewById(R.id.button1);
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
				dat = date.getText().toString();
			}
		};
		date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(add_fee.this, datee, mycalander
						.get(Calendar.YEAR), mycalander.get(Calendar.MONTH),
						mycalander.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				type=typ.getText().toString();
				se=sem.getText().toString();
				amnt=amount.getText().toString();
				if(amnt.equals("")||se.equals("")||dat.equals("")){
					Toast.makeText(add_fee.this, "All Fields are Necessary", Toast.LENGTH_LONG).show();
				}
				else{
					new ADDFEE().execute();	
					Toast.makeText(add_fee.this, "Fees Added Successfully", Toast.LENGTH_LONG).show();
					Intent ob=new Intent(add_fee.this,staff_home.class);
					startActivity(ob);
				}
			}
		});
	}
	private class ADDFEE extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg) {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("sem",se));
			nameValuePairs.add(new BasicNameValuePair("type",type));
			nameValuePairs.add(new BasicNameValuePair("date",dat ));
			nameValuePairs.add(new BasicNameValuePair("fee",amnt ));
			
		

			
			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(URL_FEE,
					ServiceHandler.POST, nameValuePairs);

			Log.e("Response: ", "> " + json);
			return null;
		}
	}	


}
