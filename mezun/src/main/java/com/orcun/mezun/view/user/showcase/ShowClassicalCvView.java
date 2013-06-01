package com.orcun.mezun.view.user.showcase;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.ClassicalCV;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.admin.InactiveUserService;
import com.orcun.mezun.service.user.ClassicalCvService;
import com.orcun.mezun.util.StringConvertUtil;

@ManagedBean
@ViewScoped
public class ShowClassicalCvView implements Serializable {

	private static final long serialVersionUID = 1L;

	private ClassicalCV classicalCV;

	private User loggedUser;

	private Boolean existUploadedFile = false;

	@ManagedProperty(value = "#{classicalCvService}")
	private ClassicalCvService classicalCvService;
	
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

		setClassicalCV(getClassicalCvService().findClassicalCvByUser(
				getLoggedUser()));
	}

	public ClassicalCV getClassicalCV() {
		return classicalCV;
	}

	public void setClassicalCV(ClassicalCV classicalCV) {
		this.classicalCV = classicalCV;
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public Boolean getExistUploadedFile() {
		return existUploadedFile;
	}

	public void setExistUploadedFile(Boolean existUploadedFile) {
		this.existUploadedFile = existUploadedFile;
	}

	public ClassicalCvService getClassicalCvService() {
		return classicalCvService;
	}

	public void setClassicalCvService(ClassicalCvService classicalCvService) {
		this.classicalCvService = classicalCvService;
	}

	public InactiveUserService getInactiveUserService() {
		return inactiveUserService;
	}

	public void setInactiveUserService(InactiveUserService inactiveUserService) {
		this.inactiveUserService = inactiveUserService;
	}

}
