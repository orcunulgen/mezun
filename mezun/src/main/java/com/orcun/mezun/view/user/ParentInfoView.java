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

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.ParentInfo;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.ParentInfoService;

@ManagedBean
@ViewScoped
public class ParentInfoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;
	
	private ParentInfo parentInfo;
	
	private List<Integer> birthdayYears = new ArrayList<Integer>();
	
	@ManagedProperty(value = "#{parentInfoService}")
	private ParentInfoService parentInfoService;
	
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
			if (getParentInfoService().findParentInfoByUser(getLoggedUser()) != null) {
				setParentInfo(getParentInfoService().findParentInfoByUser(getLoggedUser()));
			}	
		}
	}

	public List<Integer> getBirthdayYears() {
		if (this.birthdayYears == null || this.birthdayYears.size() == 0) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy");
			Date date = new Date();
			int year = Integer.parseInt(dateFormat.format(date));
			for (int i = 1950; i < year - 17; i++) {
				this.birthdayYears.add(i);
			}
		}

		return birthdayYears;
	}

	public void setBirthdayYears(List<Integer> birthdayYears) {
		this.birthdayYears = birthdayYears;
	}

	
	public ParentInfoService getParentInfoService() {
		return parentInfoService;
	}

	public void setParentInfoService(ParentInfoService parentInfoService) {
		this.parentInfoService = parentInfoService;
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

	public void saveParentInfo() throws IOException {
		try {
			if (getParentInfoService().findParentInfoByUser(getLoggedUser()) != null) {
				getParentInfoService().updateParentInfo(getParentInfo());
			} else {
				getParentInfo().setUser(getLoggedUser());
				getParentInfoService().addParentInfo(getParentInfo());
			}
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("parent_info.xhtml?user="+getLoggedUser().getTcno());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

}
