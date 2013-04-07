package com.orcun.mezun.model;

import java.io.Serializable;

public class SearchCriteria implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String searchByName;
	private String searchBySurname;
	private String searchByEmail;
	
	public String getSearchByName() {
		return searchByName;
	}
	public void setSearchByName(String searchByName) {
		this.searchByName = searchByName;
	}
	public String getSearchBySurname() {
		return searchBySurname;
	}
	public void setSearchBySurname(String searchBySurname) {
		this.searchBySurname = searchBySurname;
	}
	public String getSearchByEmail() {
		return searchByEmail;
	}
	public void setSearchByEmail(String searchByEmail) {
		this.searchByEmail = searchByEmail;
	}
	

}
