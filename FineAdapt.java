package com.example.c2e;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FineAdapt extends ArrayAdapter<Fine> {

	String[] name;

	public FineAdapt(Context context, ArrayList<Fine> name) {
		super(context, 0, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Fine fin = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.finelist, parent, false);

		}
		TextView date = (TextView) convertView.findViewById(R.id.ldate);
		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView description = (TextView) convertView
				.findViewById(R.id.descrip);
		TextView fine = (TextView) convertView.findViewById(R.id.fine);

		date.setText(fin.last_date_fine);
		name.setText(fin.rule_name);
		description.setText(fin.description);
		fine.setText(fin.fine);
		return convertView;

	}

}
