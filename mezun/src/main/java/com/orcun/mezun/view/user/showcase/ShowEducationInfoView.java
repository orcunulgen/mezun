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

import com.orcun.mezun.model.EducationFeedback;
import com.orcun.mezun.model.EducationInfo;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.SignUpService;
import com.orcun.mezun.service.admin.InactiveUserService;
import com.orcun.mezun.service.user.EducationInfoService;
import com.orcun.mezun.util.StringConvertUtil;

@ManagedBean
@ViewScoped
public class ShowEducationInfoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private Boolean isStudent = true;

	private Boolean isThereTranscript;

	private List<EducationInfo> educations = new ArrayList<EducationInfo>();
	private EducationInfo selectedEducationInfo;
	private Boolean selectedEduInfoIsStudent;

	private EducationFeedback educationFeedback;

	@ManagedProperty(value = "#{educationInfoService}")
	private EducationInfoService educationInfoService;

	@ManagedProperty(value = "#{signUpService}")
	private SignUpService signUpService;

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

		educations = educationInfoService.allEducations(getLoggedUser());
	}

	public void initSelectedEducationInfo(EducationInfo selectedEducationInfo) {
		this.selectedEducationInfo = selectedEducationInfo;
		if (getSelectedEducationInfo().getEndYear() == null) {
			this.selectedEduInfoIsStudent = true;
		} else {
			this.selectedEduInfoIsStudent = false;
		}

		if (this.selectedEducationInfo.getTranscriptPath() != null
				&& selectedEduInfoIsStudent) {
			setIsThereTranscript(true);
		} else {
			setIsThereTranscript(false);
		}

	}

	public void initFeedbackSelectedEducation(
			EducationInfo selectedEducationInfo) {

		setSelectedEducationInfo(selectedEducationInfo);
		
		EducationFeedback eduFeed=getEducationInfoService().findFeedbackInfoByEduInfo(selectedEducationInfo);
		if(eduFeed!=null){
			setEducationFeedback(eduFeed);
		}else{
			getEducationFeedback().setEducationInfo(getSelectedEducationInfo());
		}
		
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public Boolean getIsStudent() {
		return isStudent;
	}

	public void setIsStudent(Boolean isStudent) {
		this.isStudent = isStudent;
	}

	public Boolean getIsThereTranscript() {
		return isThereTranscript;
	}

	public void setIsThereTranscript(Boolean isThereTranscript) {
		this.isThereTranscript = isThereTranscript;
	}

	public List<EducationInfo> getEducations() {
		return educations;
	}

	public void setEducations(List<EducationInfo> educations) {
		this.educations = educations;
	}

	public EducationInfo getSelectedEducationInfo() {
		return selectedEducationInfo;
	}

	public void setSelectedEducationInfo(EducationInfo selectedEducationInfo) {
		this.selectedEducationInfo = selectedEducationInfo;
	}

	public EducationInfoService getEducationInfoService() {
		return educationInfoService;
	}

	public void setEducationInfoService(
			EducationInfoService educationInfoService) {
		this.educationInfoService = educationInfoService;
	}

	public InactiveUserService getInactiveUserService() {
		return inactiveUserService;
	}

	public void setInactiveUserService(InactiveUserService inactiveUserService) {
		this.inactiveUserService = inactiveUserService;
	}

	public SignUpService getSignUpService() {
		return signUpService;
	}

	public void setSignUpService(SignUpService signUpService) {
		this.signUpService = signUpService;
	}

	public Boolean getSelectedEduInfoIsStudent() {
		return selectedEduInfoIsStudent;
	}

	public void setSelectedEduInfoIsStudent(Boolean selectedEduInfoIsStudent) {
		this.selectedEduInfoIsStudent = selectedEduInfoIsStudent;
	}

	public EducationFeedback getEducationFeedback() {
		return educationFeedback;
	}

	public void setEducationFeedback(EducationFeedback educationFeedback) {
		this.educationFeedback = educationFeedback;
	}

}
