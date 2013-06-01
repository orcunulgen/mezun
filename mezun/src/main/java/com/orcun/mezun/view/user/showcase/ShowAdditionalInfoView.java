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

import com.orcun.mezun.model.AdditionalInfo;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.admin.InactiveUserService;
import com.orcun.mezun.service.user.AdditionalInfoService;
import com.orcun.mezun.util.StringConvertUtil;

@ManagedBean
@ViewScoped
public class ShowAdditionalInfoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private AdditionalInfo additionalInfo;

	@ManagedProperty(value = "#{additionalInfoService}")
	private AdditionalInfoService additionalInfoService;
	
	@ManagedProperty(value = "#{inactiveUserService}")
	private InactiveUserService inactiveUserService;

	public User getLoggedUser() {
		return loggedUser;
	}
	
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
		if (additionalInfo == null) {
			additionalInfo = new AdditionalInfo();
		}

		if (additionalInfo.getUser() == null) {
			if (getAdditionalInfoService().findAdditionalInfoByUser(
					getLoggedUser()) != null) {
				setAdditionalInfo(getAdditionalInfoService()
						.findAdditionalInfoByUser(getLoggedUser()));
			}
		}

	}
	
	public AdditionalInfo getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(AdditionalInfo additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public AdditionalInfoService getAdditionalInfoService() {
		return additionalInfoService;
	}

	public void setAdditionalInfoService(
			AdditionalInfoService additionalInfoService) {
		this.additionalInfoService = additionalInfoService;
	}

	public InactiveUserService getInactiveUserService() {
		return inactiveUserService;
	}

	public void setInactiveUserService(InactiveUserService inactiveUserService) {
		this.inactiveUserService = inactiveUserService;
	}

}
