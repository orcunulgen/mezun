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

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.GradingSystem;
import com.orcun.mezun.model.HighSchool;
import com.orcun.mezun.model.HighSchoolType;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.HighSchoolService;

@ManagedBean
@ViewScoped
public class HighSchoolView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private HighSchool highSchool;

	private List<HighSchoolType> highSchoolTypes = new ArrayList<HighSchoolType>();
	private List<GradingSystem> gradingSystems = new ArrayList<GradingSystem>();

	@ManagedProperty(value = "#{highSchoolService}")
	private HighSchoolService highSchoolService;

	@PostConstruct
	public void init() {
		highSchoolTypes = highSchoolService.allHighSchoolTypes();
		gradingSystems = highSchoolService.allGradingSystems();
		
		if (highSchool == null) {
			highSchool = new HighSchool();

		}

		if (highSchool.getUser() == null) {
			if (getHighSchoolService().findHighSchoolByUser(getLoggedUser()) != null) {
				setHighSchool(getHighSchoolService().findHighSchoolByUser(
						getLoggedUser()));
			}
		}

	}

	public HighSchool getHighSchool() {
		return highSchool;
	}

	public void setHighSchool(HighSchool highSchool) {
		this.highSchool = highSchool;
	}

	public List<HighSchoolType> getHighSchoolTypes() {
		return highSchoolTypes;
	}

	public void setHighSchoolTypes(List<HighSchoolType> highSchoolTypes) {
		this.highSchoolTypes = highSchoolTypes;
	}

	public List<GradingSystem> getGradingSystems() {
		return gradingSystems;
	}

	public void setGradingSystems(List<GradingSystem> gradingSystems) {
		this.gradingSystems = gradingSystems;
	}

	public HighSchoolService getHighSchoolService() {
		return highSchoolService;
	}

	public void setHighSchoolService(HighSchoolService highSchoolService) {
		this.highSchoolService = highSchoolService;
	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public void saveHighSchool() throws IOException {
		try {

			Date registeredDate = new Date();

			int differenceEndRegister = Days.daysBetween(
					new DateTime(getHighSchool().getEndDate()),
					new DateTime(registeredDate)).getDays();

			if (differenceEndRegister > 0) {
				if (getHighSchoolService()
						.findHighSchoolByUser(getLoggedUser()) != null) {
					getHighSchoolService().updateHighSchool(getHighSchool());
				} else {
					getHighSchool().setUser(getLoggedUser());
					getHighSchoolService().addHighSchool(getHighSchool());
				}
				
				FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.redirect(
						"high_school.xhtml?user="
								+ getLoggedUser().getTcno());
			} else {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Bitiş tarihi geçmiş zamana ait olmalıdır.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

}
