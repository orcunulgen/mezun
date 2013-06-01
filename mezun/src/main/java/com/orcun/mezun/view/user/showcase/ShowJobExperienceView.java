package com.orcun.mezun.view.user.showcase;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.JobExperience;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.admin.InactiveUserService;
import com.orcun.mezun.service.user.JobExperienceService;
import com.orcun.mezun.util.StringConvertUtil;

@ManagedBean
@ViewScoped
public class ShowJobExperienceView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private List<JobExperience> jobExperiences = new ArrayList<JobExperience>();
	private JobExperience selectedJobExperience;
	private Boolean selectedJobExperienceIsContinue;

	@ManagedProperty(value = "#{jobExperienceService}")
	private JobExperienceService jobExperienceService;

	@ManagedProperty(value = "#{inactiveUserService}")
	private InactiveUserService inactiveUserService;

	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		Long tcno = StringConvertUtil.stringToLong(request.getParameter("u"));

		if (tcno != null) {
			User tmp = new User();
			tmp.setTcno(tcno);

			tmp = getInactiveUserService().findUserByTcno(tmp);
			if (tmp != null) {
				this.loggedUser = tmp;
			}
		}

		// real loggedUser
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		User realLoggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();
		//

		if (getLoggedUser() == null || getLoggedUser().equals(realLoggedUser)) {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		jobExperiences = jobExperienceService.allJobExperience(getLoggedUser());

	}

	public void initSelectedJobEx(JobExperience selectedJobExperience) {
		this.selectedJobExperience = selectedJobExperience;
		if (getSelectedJobExperience().getEndDate() == null) {
			this.selectedJobExperienceIsContinue = true;
		} else {
			this.selectedJobExperienceIsContinue = false;
		}
	}

	public List<JobExperience> getJobExperiences() {
		return jobExperiences;
	}

	public void setJobExperiences(List<JobExperience> jobExperiences) {
		this.jobExperiences = jobExperiences;
	}

	public JobExperience getSelectedJobExperience() {
		return selectedJobExperience;
	}

	public void setSelectedJobExperience(JobExperience selectedJobExperience) {
		this.selectedJobExperience = selectedJobExperience;
	}

	public Boolean getSelectedJobExperienceIsContinue() {
		return selectedJobExperienceIsContinue;
	}

	public void setSelectedJobExperienceIsContinue(
			Boolean selectedJobExperienceIsContinue) {
		this.selectedJobExperienceIsContinue = selectedJobExperienceIsContinue;
	}

	public JobExperienceService getJobExperienceService() {
		return jobExperienceService;
	}

	public void setJobExperienceService(
			JobExperienceService jobExperienceService) {
		this.jobExperienceService = jobExperienceService;
	}

	public InactiveUserService getInactiveUserService() {
		return inactiveUserService;
	}

	public void setInactiveUserService(InactiveUserService inactiveUserService) {
		this.inactiveUserService = inactiveUserService;
	}

	public User getLoggedUser() {
		return loggedUser;
	}

}
