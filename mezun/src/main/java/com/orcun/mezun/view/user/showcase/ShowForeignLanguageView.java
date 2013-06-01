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

import com.orcun.mezun.model.ForeignLanguage;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.admin.InactiveUserService;
import com.orcun.mezun.service.user.ForeignLanguageService;
import com.orcun.mezun.util.StringConvertUtil;

@ManagedBean
@ViewScoped
public class ShowForeignLanguageView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private List<ForeignLanguage> foreignLanguages = new ArrayList<ForeignLanguage>();
	private ForeignLanguage selectedForeignLanguage;

	@ManagedProperty(value = "#{foreignLanguageService}")
	private ForeignLanguageService foreignLanguageService;

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

		foreignLanguages = foreignLanguageService
				.allForeignLanguage(getLoggedUser());

	}

	public void initSelectedForeignLanguage(
			ForeignLanguage selectedForeignLanguage) {
		this.selectedForeignLanguage = selectedForeignLanguage;
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
