package com.orcun.mezun.view.user.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.helper.SearchChatPersonHelperService;

@ManagedBean
@ViewScoped
public class SearchChatPersonHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private String searchByName;
	
	
	private List<User> searchedPersons=new ArrayList<User>();
	
	
	@ManagedProperty(value = "#{searchChatPersonHelperService}")
	private SearchChatPersonHelperService searchChatPersonHelperService;

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public String getSearchByName() {
		return searchByName;
	}

	public void setSearchByName(String searchByName) {
		this.searchByName = searchByName;
	}

	public List<User> getSearchedPersons() {
		return searchedPersons;
	}

	public void setSearchedPersons(List<User> searchedPersons) {
		this.searchedPersons = searchedPersons;
	}

	public SearchChatPersonHelperService getSearchChatPersonHelperService() {
		return searchChatPersonHelperService;
	}

	public void setSearchChatPersonHelperService(
			SearchChatPersonHelperService searchChatPersonHelperService) {
		this.searchChatPersonHelperService = searchChatPersonHelperService;
	}
	
	public void searchChatPerson() {
		try {
			
			searchedPersons=getSearchChatPersonHelperService().searchChatPersonByName(getSearchByName());
			
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
}