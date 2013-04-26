package com.orcun.mezun.view.user.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.EducationInfo;
import com.orcun.mezun.model.SearchCriteria;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.helper.SearchChatPersonHelperService;

@ManagedBean
@ViewScoped
public class SearchChatPersonHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private SearchCriteria searchCriteria;

	private List<User> searchedPersons = new ArrayList<User>();

	@ManagedProperty(value = "#{searchChatPersonHelperService}")
	private SearchChatPersonHelperService searchChatPersonHelperService;

	@PostConstruct
	public void init() {
		this.searchCriteria = new SearchCriteria();
	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public SearchCriteria getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
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

			List<EducationInfo> eduInfos = new ArrayList<EducationInfo>();

			eduInfos = getSearchChatPersonHelperService().searchChatPerson(
					getSearchCriteria());
			searchedPersons = new ArrayList<User>();

			for (Iterator iterator = eduInfos.iterator(); iterator.hasNext();) {
				EducationInfo edInfo = (EducationInfo) iterator.next();
				searchedPersons.add(edInfo.getUser());
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
}