package com.orcun.mezun.view.user;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.AdditionalInfo;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.AdditionalInfoService;

@ManagedBean
@ViewScoped
public class AdditionalInfoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private AdditionalInfo additionalInfo;

	@ManagedProperty(value = "#{additionalInfoService}")
	private AdditionalInfoService additionalInfoService;

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public AdditionalInfo getAdditionalInfo() {
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

	public void checkURL() throws IOException {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String userParameter = request.getParameter("user");

		if (userParameter == null || userParameter.equals("")) {

			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"personal_info.xhtml?user="
									+ getLoggedUser().getTcno());

		} else if (!userParameter.equals(getLoggedUser().getTcno().toString())) {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"personal_info.xhtml?user="
									+ getLoggedUser().getTcno());

		}
	}

	public String saveAdditionalInfo() {

		
		if (getAdditionalInfoService()
				.findAdditionalInfoByUser(getLoggedUser()) != null) {
			getAdditionalInfoService()
					.updateAdditionalInfo(getAdditionalInfo());
		} else {
			getAdditionalInfo().setUser(getLoggedUser());
			getAdditionalInfoService().addAdditionalInfo(getAdditionalInfo());
		}

		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Kaydınız başarıyla güncellendi.", "");

		FacesContext.getCurrentInstance().addMessage(null, fm);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		return ("additional_info.xhtml?faces-redirect=true&user=" + getLoggedUser()
				.getTcno());
	}

}
