package com.orcun.mezun.view.user;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.PersonalInfoService;

@ManagedBean
@ViewScoped
public class PersonalInfoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private String confirmEmail;
	private String confirmPassword;

	@ManagedProperty(value = "#{personalInfoService}")
	private PersonalInfoService personalInfoService;

	@PostConstruct
	public void init() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

	}

	public PersonalInfoService getPersonalInfoService() {
		return personalInfoService;
	}

	public void setPersonalInfoService(PersonalInfoService personalInfoService) {
		this.personalInfoService = personalInfoService;
	}

	public User getLoggedUser() {

		return loggedUser;

	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String updatePersonalInfo() {
		if (getLoggedUser() != null) {
			getPersonalInfoService().updatePersonalInfo(getLoggedUser());
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Kaydınız başarıyla güncellendi.", "");

			FacesContext.getCurrentInstance().addMessage(null, fm);

		}

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		return ("personal_info.xhtml?faces-redirect=true&u=" + getLoggedUser()
				.getTcno());
	}

}
