package com.orcun.mezun.view.user;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.ForeignLanguage;
import com.orcun.mezun.model.Language;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.ForeignLanguageService;

@ManagedBean
@ViewScoped
public class ForeignLanguageView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private ForeignLanguage foreignLanguage;

	private List<Language> languages = new ArrayList<Language>();

	private List<ForeignLanguage> foreignLanguages = new ArrayList<ForeignLanguage>();
	private ForeignLanguage selectedForeignLanguage;

	@ManagedProperty(value = "#{foreignLanguageService}")
	private ForeignLanguageService foreignLanguageService;

	@PostConstruct
	public void init() {
		languages = foreignLanguageService.allLanguages();
		foreignLanguages = foreignLanguageService
				.allForeignLanguage(getLoggedUser());

		if (foreignLanguage == null) {
			foreignLanguage = new ForeignLanguage();

		}
	}

	public void initSelectedForeignLanguage(
			ForeignLanguage selectedForeignLanguage) {
		this.selectedForeignLanguage = selectedForeignLanguage;
	}

	public void deleteSelectedForeignLanguage(
			ForeignLanguage selectedForeignLanguage) throws IOException {
		initSelectedForeignLanguage(selectedForeignLanguage);
		getForeignLanguageService().deleteForeignLanguage(
				getSelectedForeignLanguage());

		FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.redirect(
						"foreign_language.xhtml?u="
								+ getLoggedUser().getTcno());

	}

	public ForeignLanguage getForeignLanguage() {
		return foreignLanguage;
	}

	public void setForeignLanguage(ForeignLanguage foreignLanguage) {
		this.foreignLanguage = foreignLanguage;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public List<ForeignLanguage> getForeignLanguages() {
		return foreignLanguages;
	}

	public void setForeignLanguages(List<ForeignLanguage> foreignLanguages) {
		this.foreignLanguages = foreignLanguages;
	}

	public ForeignLanguage getSelectedForeignLanguage() {
		return selectedForeignLanguage;
	}

	public void setSelectedForeignLanguage(
			ForeignLanguage selectedForeignLanguage) {
		this.selectedForeignLanguage = selectedForeignLanguage;
	}

	public ForeignLanguageService getForeignLanguageService() {
		return foreignLanguageService;
	}

	public void setForeignLanguageService(
			ForeignLanguageService foreignLanguageService) {
		this.foreignLanguageService = foreignLanguageService;
	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public String addForeignLanguage() {

		Date registeredDate = new Date();

		getForeignLanguage().setUser(getLoggedUser());
		getForeignLanguage().setRegisteredDate(registeredDate);
		getForeignLanguageService().addForeignLanguage(getForeignLanguage());

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Kaydınız başarıyla tamamlandı.", "");

		FacesContext.getCurrentInstance().addMessage(null, fm);

		return "foreign_language.xhtml?faces-redirect=true&u="
				+ getLoggedUser().getTcno();
	}

	public String updateForeignLanguage() {

		getForeignLanguageService().updateForeignLanguage(
				getSelectedForeignLanguage());

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Kaydınız başarıyla güncellendi.", "");

		FacesContext.getCurrentInstance().addMessage(null, fm);

		return "foreign_language.xhtml?faces-redirect=true&u="
				+ getLoggedUser().getTcno();
	}
}
