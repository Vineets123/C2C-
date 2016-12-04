package com.example.c2e;

public class Feed {

	public int feedback_id;
	public String feedback_description;
	public String first_name;
	public String last_name;

	public Feed() {
	}

	public Feed(int feedback_id, String feedback_description,
			String first_name, String last_name) {
		this.feedback_id = feedback_id;
		this.feedback_description = feedback_description;
		this.first_name = first_name;
		this.last_name = last_name;
	}

	public void setId(int feedback_id) {
		this.feedback_id = feedback_id;
	}

	public void setDesc(String feedback_description) {
		this.feedback_description = feedback_description;
	}

	public void setfname(String first_name) {
		this.first_name = first_name;
	}

	public void setlname(String last_name) {
		this.last_name = last_name;
	}

	public int getId() {
		return this.feedback_id;
	}

	public String getDesc() {
		return this.feedback_description;
	}

	public String getfname() {
		return this.first_name;
	}

	public String getlname() {
		return this.last_name;
	}

}
