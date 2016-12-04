package com.example.c2e;

import java.util.ArrayList;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FeedAdapt extends ArrayAdapter<Feed> {

	String[] name;

	public FeedAdapt(Context context, ArrayList<Feed> name) {
		super(context, 0, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Feed feed = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.feedlist, parent, false);

		}
		TextView fname = (TextView) convertView.findViewById(R.id.fn);
		TextView lname = (TextView) convertView.findViewById(R.id.ln);
		TextView feedbackz = (TextView) convertView.findViewById(R.id.feed);
		String fn = feed.first_name;

		fname.setText(fn.substring(0, 1).toUpperCase()+fn.substring(1));
		lname.setText(" " + feed.last_name);
		feedbackz.setText(feed.feedback_description);

		return convertView;

	}

}
