package com.orcun.mezun.service.user.helper;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.helper.SearchChatPersonHelperDAO;
import com.orcun.mezun.model.EducationInfo;
import com.orcun.mezun.model.SearchCriteria;

@Service
public class SearchChatPersonHelperService {
	
	@Autowired
	private SearchChatPersonHelperDAO searchChatPersonHelperDAO;

	public SearchChatPersonHelperDAO getSearchChatPersonHelperDAO() {
		return searchChatPersonHelperDAO;
	}


	public void setSearchChatPersonHelperDAO(
			SearchChatPersonHelperDAO searchChatPersonHelperDAO) {
		this.searchChatPersonHelperDAO = searchChatPersonHelperDAO;
	}


	public List<EducationInfo> searchChatPerson(SearchCriteria searchCriteria) {
		return getSearchChatPersonHelperDAO().searchChatPerson(searchCriteria);
	}



}
