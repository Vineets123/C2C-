package com.example.c2e;

import geter_seter.ChapterAdapter;
import geter_seter.ChapterAdapter_adapt;
import geter_seter.time_tbl;
import geter_seter.time_tbl_getset;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;



public class view_resume extends Activity {
	String ip=new  ip().callip();
	SharedPreferences sp;
	Editor myedit;
	String u_id, top_id;
	ProgressDialog pDialog = null;
	String URL_ChapDownload = ip+"view_resumes.php";
//	String URL_SHEDULE = "http://192.168.1.11:8084/ServerCOde/Shedule.php";
	String URL_DOWNLOAD;
	ArrayList<time_tbl_getset> TopList = new ArrayList<time_tbl_getset>();
	ListView lv;
	

String id,file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_resume);
		sp = PreferenceManager.getDefaultSharedPreferences(view_resume.this);
		myedit = sp.edit();
		
				
		lv = (ListView) findViewById(R.id.listView1);
		new GetChaps().execute();

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				time_tbl c = (time_tbl) parent.getItemAtPosition(pos);
				id = c.getId();
				file = c.getFile();

				Dialog d = new Dialog(getApplicationContext());
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						view_resume.this);
				dialog.setTitle("Download File");
				dialog.setMessage("Download" + " " + file + "?");
				dialog.setPositiveButton("Ok", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						URL_DOWNLOAD = ip+"uploads/" + file;
						Boolean result = isDownloadManagerAvailable(getApplicationContext());
						if (result)
						downloadFile();
					}
				});
				dialog.show();

				return false;
			}
		});

	}
	@SuppressLint("NewApi")
	public void downloadFile() {
		String DownloadUrl = URL_DOWNLOAD;
		DownloadManager.Request request = new DownloadManager.Request(
				Uri.parse(DownloadUrl));
		request.setDescription("Downloading..."); // appears the
													// same in
													// Notification
													// bar while
													// downloading
		request.setTitle("" +file);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			request.allowScanningByMediaScanner();
			request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		}
		request.setDestinationInExternalFilesDir(view_resume.this, null,
				"" + file);

		// get download service and enqueue file
		DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		manager.enqueue(request);
	}

	public static boolean isDownloadManagerAvailable(Context context) {
		try {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
				return false;
			}
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			intent.setClassName("com.android.providers.downloads.ui",
					"com.android.providers.downloads.ui.DownloadList");
			List<ResolveInfo> list = context.getPackageManager()
					.queryIntentActivities(intent,
							PackageManager.MATCH_DEFAULT_ONLY);
			return list.size() > 0;
		} catch (Exception e) {
			return false;
		}
	

//	private class Shedule extends AsyncTask<Void, Void, Void> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(view_Time.this);
//			pDialog.setMessage("Wait..");
//			pDialog.setCancelable(false);
//			pDialog.show();
//
//		}
//
//		@Override
//		protected Void doInBackground(Void... arg0) {
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("topid", top_id));
//			params.add(new BasicNameValuePair("chapid", chap_id));
//			params.add(new BasicNameValuePair("uid", u_id));
//			params.add(new BasicNameValuePair("date", dateString));
//			params.add(new BasicNameValuePair("time", timeString));
//
//			ServiceHandler jsonParser = new ServiceHandler();
//			String json = jsonParser.makeServiceCall(URL_SHEDULE,
//					ServiceHandler.POST, params);
//
//			Log.e("Response: ", "> " + json);
//
//			if (json != null) {
//				try {
//
//					JSONArray providers = new JSONArray(json);
//					Log.e("Json ", "> " + providers);
//					TopList.clear();
//					for (int i = 0; i < providers.length(); i++) {
//						Log.e("Length ", "> " + providers.length());
//						JSONObject topObj = (JSONObject) providers.get(i);
//						Log.e("catobj ", "> " + topObj);
//						Chapter c = new Chapter(topObj.getString("chapid"),
//								topObj.getString("chapter"),
//								topObj.getString("description"));
//
//						Log.e("cat ", "> " + c);
//						TopList.add(c);
//						Log.e("list ", "> " + TopList);
//					}
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//
//			} else {
//				Log.e("JSON Data", "Didn't receive any data from server!");
//			}
//
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Void result) {
//			super.onPostExecute(result);
//			if (pDialog.isShowing())
//				pDialog.dismiss();
//
//			ChapterAdapter ta = new ChapterAdapter(ChapDownload.this, TopList);
//			lv.setAdapter(ta);
//
		//}

	}

	private class GetChaps extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(view_resume.this);
			pDialog.setMessage("Wait..");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
		

			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(URL_ChapDownload,
					ServiceHandler.POST, params);

			Log.e("Response: ", "> " + json);
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
							time_tbl_getset c = new time_tbl_getset(job.getString("id"),
								
								job.getString("file"));

						Log.e("cat ", "> " + c);
						TopList.add(c);
						Log.e("list ", "> " + TopList);
					}}}

				 catch (JSONException e) {
					e.printStackTrace();
				}

			} else {
				Log.e("JSON Data", "Didn't receive any data from server!");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();

			ChapterAdapter_adapt ta = new ChapterAdapter_adapt(view_resume.this, TopList);
			lv.setAdapter(ta);

		}

	}

}
