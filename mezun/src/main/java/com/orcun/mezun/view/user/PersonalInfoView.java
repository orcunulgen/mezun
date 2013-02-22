package com.orcun.mezun.view.user;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.ParentInfo;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.PersonalInfoService;

@ManagedBean
@ViewScoped
public class PersonalInfoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;
	
	private ParentInfo parentInfo;
	
	private String confirmEmail;
	private String confirmPassword;
	
	private List<Integer> birthdayYears = new ArrayList<Integer>();
	
	@ManagedProperty(value = "#{personalInfoService}")
	private PersonalInfoService personalInfoService;
	
	@PostConstruct
	public void init() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		
		
		if (parentInfo == null) {
			parentInfo=new ParentInfo();

		}
		
		if(parentInfo.getUser()==null){
			if (getPersonalInfoService().findParentInfoByUser(getLoggedUser()) != null) {
				setParentInfo(getPersonalInfoService().findParentInfoByUser(getLoggedUser()));
			}	
		}
	}

	public List<Integer> getBirthdayYears() {
		if (this.birthdayYears == null || this.birthdayYears.size() == 0) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy");
			Date date = new Date();
			int year = Integer.parseInt(dateFormat.format(date));
			for (int i = 1970; i < year - 17; i++) {
				this.birthdayYears.add(i);
			}
		}

		return birthdayYears;
	}

	public void setBirthdayYears(List<Integer> birthdayYears) {
		this.birthdayYears = birthdayYears;
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

	public ParentInfo getParentInfo() {
		return parentInfo;
	}

	public void setParentInfo(ParentInfo parentInfo) {
		this.parentInfo = parentInfo;
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
