package com.orcun.mezun.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.ForeignLanguageDAO;
import com.orcun.mezun.model.ForeignLanguage;
import com.orcun.mezun.model.Language;
import com.orcun.mezun.model.User;

@Service
public class ForeignLanguageService {
	
	@Autowired
	private ForeignLanguageDAO foreignLanguageDAO;

	public ForeignLanguageDAO getForeignLanguageDAO() {
		return foreignLanguageDAO;
	}

	public void setForeignLanguageDAO(ForeignLanguageDAO foreignLanguageDAO) {
		this.foreignLanguageDAO = foreignLanguageDAO;
	}

	public void updateForeignLanguage(ForeignLanguage foreignLanguage) {
		getForeignLanguageDAO().updateForeignLanguage(foreignLanguage);
	}

	public void addForeignLanguage(ForeignLanguage foreignLanguage) {
		getForeignLanguageDAO().addForeignLanguage(foreignLanguage);
	}

	public List<Language> allLanguages() {
		return getForeignLanguageDAO().allLanguages();
	}

	public List<ForeignLanguage> allForeignLanguage(User loggedUser) {
		return getForeignLanguageDAO().allForeignLanguage(loggedUser);
	}
	
	public void deleteForeignLanguage(ForeignLanguage selectedForeignLanguage) {
		getForeignLanguageDAO().deleteForeignLanguage(selectedForeignLanguage);
		
	}
}
