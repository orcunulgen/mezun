package com.orcun.mezun.view.user.init;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.joda.time.DateTime;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.GradingSystem;
import com.orcun.mezun.model.HighSchool;
import com.orcun.mezun.model.HighSchoolType;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.HighSchoolService;
import com.orcun.mezun.service.user.init.InitStudentInfoService;

@ManagedBean
@ViewScoped
public class StudentHighschoolView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private HighSchool highSchool;

	private List<HighSchoolType> highSchoolTypes = new ArrayList<HighSchoolType>();
	private List<GradingSystem> gradingSystems = new ArrayList<GradingSystem>();

	@ManagedProperty(value = "#{highSchoolService}")
	private HighSchoolService highSchoolService;

	@ManagedProperty(value = "#{initStudentInfoService}")
	private InitStudentInfoService initStudentInfoService;

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

	public InitStudentInfoService getInitStudentInfoService() {
		return initStudentInfoService;
	}

	public void setInitStudentInfoService(
			InitStudentInfoService initStudentInfoService) {
		this.initStudentInfoService = initStudentInfoService;
	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public String saveHighSchool()  {

			DateTime registeredDate = new DateTime();

			int differenceEndRegister = registeredDate.getYear()
					- getHighSchool().getEndYear();
			if (differenceEndRegister > 0) {
				if (getHighSchoolService()
						.findHighSchoolByUser(getLoggedUser()) != null) {
					getHighSchoolService().updateHighSchool(getHighSchool());
				} else {
					getHighSchool().setUser(getLoggedUser());
					getHighSchoolService().addHighSchool(getHighSchool());
				}

				if (!getInitStudentInfoService().IsValidInitStudentInfo(
						getLoggedUser())) {
					FacesMessage fm = new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"Kaydınız başarıyla güncellendi.Lütfen diğer temel bilgilerinizi de tamamlayınız.",
							"");

					FacesContext.getCurrentInstance().addMessage(null, fm);

					Flash flash = FacesContext.getCurrentInstance()
							.getExternalContext().getFlash();
					flash.setKeepMessages(true);

					return "init_student_info.xhtml?faces-redirect=true&u="
							+ getLoggedUser().getTcno();
				} else {
					FacesMessage fm = new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"Tüm temel bilgileriniz başarıyla kayıt altına alınmıştır.",
							"");

					FacesContext.getCurrentInstance().addMessage(null, fm);

					Flash flash = FacesContext.getCurrentInstance()
							.getExternalContext().getFlash();
					flash.setKeepMessages(true);

					/*
					 * FacesContext.getCurrentInstance().getExternalContext()
					 * .redirect("../index.xhtml");
					 */
					return "/user_profile/index.xhtml?faces-redirect=true";
				}

			} else {
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Bitiş tarihi geçmiş zamana ait olmalıdır.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);
				
				return "init_student_info.xhtml?faces-redirect=true&u="+getLoggedUser().getTcno();
			}
	}

}
