package com.example.c2e;

public class Fine {

	public int rule_violation_id;
	public String last_date_fine;
	public String rule_name;
	public String description;
	public String fine;

	public Fine() {
	}

	public Fine(int rule_violation_id, String last_date_fine, String rule_name, String description, String fine) {
		this.rule_violation_id = rule_violation_id;
		this.last_date_fine = last_date_fine;
		this.rule_name = rule_name;
		this.description = description;
		this.fine = fine;

	}

	public void setId(int rule_violation_id) {
		this.rule_violation_id = rule_violation_id;
	}

	public void setldate(String last_date_fine) {
		this.last_date_fine = last_date_fine;
	}

	public void setname(String rule_name) {
		this.rule_name = rule_name;
	}
	public void setldesc(String description) {
		this.description = description;
	}

	public void setfine(String fine) {
		this.fine = fine;
	}

	public int getId() {
		return this.rule_violation_id;
	}

	public String getldate() {
		return this.last_date_fine;
	}

	public String getname() {
		return this.description;
	}
	public String getdesc() {
		return this.last_date_fine;
	}

	public String getfine() {
		return this.fine;
	}

}
