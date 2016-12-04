package com.example.c2e;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class add_exam extends Activity {
	String ip = new ip().callip();
	TextView textFile;
	String url = "";
	String path;
	EditText type, sem;
	String typ, se;
	Uri uri;
	private int serverResponseCode = 0;
	private ProgressDialog dialog = null;

	private String upLoadServerUri = ip + "add_exam.php";
	private String imagepath = null;
	SharedPreferences sp;
	Editor ed;
	private static final int PICKFILE_RESULT_CODE = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_exam);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		ed = sp.edit();
		sem = (EditText) findViewById(R.id.editText1);
		type = (EditText) findViewById(R.id.editText2);
		Button buttonPick = (Button) findViewById(R.id.button1);
		textFile = (TextView) findViewById(R.id.textView2);

		buttonPick.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				typ = type.getText().toString();
				se = sem.getText().toString();
				if (typ.equals("") || se.equals("")) {
					Toast.makeText(add_exam.this, "All fields are necessary",
							Toast.LENGTH_LONG).show();
				} else {
					new addAssign().execute();
				}
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("*/*");
				intent.addCategory(Intent.CATEGORY_OPENABLE);

				try {
					startActivityForResult(Intent.createChooser(intent,
							"Select a File to Upload"), PICKFILE_RESULT_CODE);
				} catch (android.content.ActivityNotFoundException ex) {
					// Potentially direct the user to the Market with a Dialog
					Toast.makeText(add_exam.this,
							"Please install a File Manager.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private class addAssign extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg) {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("sem", se));
			nameValuePairs.add(new BasicNameValuePair("type", typ));

			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(upLoadServerUri,
					ServiceHandler.POST, nameValuePairs);

			Log.e("Response: ", "> " + json);
			return null;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case PICKFILE_RESULT_CODE:
			if (resultCode == RESULT_OK) {
				// Get the Uri of the selected file
				uri = data.getData();
				final File auxFile = new File(uri.getPath());
				try {
					// HttpClient httpclient = new DefaultHttpClient();
					//
					// HttpPost httppost = new HttpPost(url);
					//
					// InputStreamEntity reqEntity = new InputStreamEntity(
					// new FileInputStream(auxFile), -1);
					// reqEntity.setContentType("binary/octet-stream");
					// reqEntity.setChunked(true); // Send in multiple parts if
					// needed
					// httppost.setEntity(reqEntity);
					// HttpResponse response = httpclient.execute(httppost);

					dialog = ProgressDialog.show(add_exam.this, "",
							"Uploading file...", true);
					textFile.setText("uploading started.....");
					new Thread(new Runnable() {
						public void run() {

							uploadFile(auxFile);

						}
					}).start();

					// Do something with response...

				} catch (Exception e) {
					// show error
				}
				Log.d("URI", "" + uri);
				Log.d("FIle", "File Uri: " + uri.toString());
				// Get the path

				try {
					path = FileUtils.getPath(add_exam.this, uri);
					Log.d("FIle", "File Path: " + path);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Get the file instance
				// File file = new File(path);
				// Initiate the upload
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public int uploadFile(File sourceFile) {
		int t = sp.getInt("Value", 0);
		ed.putInt("Value", t + 1);
		ed.commit();
		// ////////////////////
		// /////////////////////
		// ///////////////////////////////
		// //////////////file name Temp//////////
		// /////////////////////////////////////
		// /////////////////////////////////////////
		String paths = path;// it contain your path of image..im using a temp
							// string..
		String filenames = paths.substring(paths.lastIndexOf("/") + 1);
		// String fileName = filename+t+".txt";
		String fileName = filenames;
		//
		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		// File sourceFile = new File(sourceFileUri);

		if (!sourceFile.isFile()) {

			dialog.dismiss();

			Log.e("uploadFile", "Source File not exist :" + imagepath);

			runOnUiThread(new Runnable() {
				public void run() {
					textFile.setText("Source File not exist :" + imagepath);
				}
			});

			return 0;

		} else {
			try {

				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(
						sourceFile);
				URL url = new URL(upLoadServerUri);

				// Open a HTTP connection to the URL
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("uploaded_file", fileName);

				dos = new DataOutputStream(conn.getOutputStream());

				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
						+ fileName + "\"" + lineEnd);

				dos.writeBytes(lineEnd);

				// create a buffer of maximum size
				bytesAvailable = fileInputStream.available();

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {

					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				}

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();
				String serverResponseMessage = conn.getResponseMessage();

				Log.i("uploadFile", "HTTP Response is : "
						+ serverResponseMessage + ": " + serverResponseCode);

				if (serverResponseCode == 200) {

					runOnUiThread(new Runnable() {
						public void run() {
							String msg = "File Upload Completeds";
							textFile.setText(msg);
							Toast.makeText(add_exam.this,
									"File Upload Complete.", Toast.LENGTH_SHORT)
									.show();
						}
					});
				}

				// close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (MalformedURLException ex) {

				dialog.dismiss();
				ex.printStackTrace();

				runOnUiThread(new Runnable() {
					public void run() {
						textFile.setText("MalformedURLException Exception : check script url.");
						Toast.makeText(add_exam.this, "MalformedURLException",
								Toast.LENGTH_SHORT).show();
					}
				});

				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {

				dialog.dismiss();
				e.printStackTrace();

				runOnUiThread(new Runnable() {
					public void run() {
						textFile.setText("Got Exception : see logcat ");
						Toast.makeText(add_exam.this,
								"Got Exception : see logcat ",
								Toast.LENGTH_SHORT).show();
					}
				});
				Log.e("Upload file to server Exception",
						"Exception : " + e.getMessage(), e);
			}
			dialog.dismiss();
			return serverResponseCode;

		} // End else block
	}
}