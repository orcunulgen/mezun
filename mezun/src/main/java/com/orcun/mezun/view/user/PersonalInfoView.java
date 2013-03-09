package com.orcun.mezun.view.user;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;
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

	public void checkURL() throws IOException{
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String userParameter=request.getParameter("user");
		
		if(userParameter==null || userParameter.equals("")){
			
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("personal_info.xhtml?user="+getLoggedUser().getTcno());
			
		}else if(!userParameter.equals(getLoggedUser().getTcno().toString())){
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("personal_info.xhtml?user="+getLoggedUser().getTcno());
			
		}
	}
	
	public String updatePersonalInfo() {
		try {
			if (getLoggedUser() != null) {
				getPersonalInfoService().updatePersonalInfo(getLoggedUser());
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return ("personal_info.xhtml?faces-redirect=true&user="+getLoggedUser().getTcno());
	}

}
