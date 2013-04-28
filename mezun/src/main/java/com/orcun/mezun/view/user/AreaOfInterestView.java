package com.orcun.mezun.view.user;

import java.io.IOException;
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

import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.AreaOfInterest;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.AreaOfInterestService;

@ManagedBean
@ViewScoped
public class AreaOfInterestView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private AreaOfInterest areaOfInterest;

	private List<AreaOfInterest> areaOfInterests = new ArrayList<AreaOfInterest>();

	private AreaOfInterest selectedAreaOfInterest;

	@ManagedProperty(value = "#{areaOfInterestService}")
	private AreaOfInterestService areaOfInterestService;

	@PostConstruct
	public void init() {
		areaOfInterest = new AreaOfInterest();
		areaOfInterests = areaOfInterestService
				.allAreaOfInterest(getLoggedUser());
	}

	public void initSelectedAreaOfInterest(AreaOfInterest selectedAreaOfInterest) {
		this.selectedAreaOfInterest = selectedAreaOfInterest;
	}

	public void deleteSelectedAreaOfInterest(
			AreaOfInterest selectedAreaOfInterest) throws IOException {
		initSelectedAreaOfInterest(selectedAreaOfInterest);
		getAreaOfInterestService().deleteAreaOfInterest(
				getSelectedAreaOfInterest());

		FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.redirect(
						"area_of_interest.xhtml?user="
								+ getLoggedUser().getTcno());

	}

	public AreaOfInterest getAreaOfInterest() {
		return areaOfInterest;
	}

	public void setAreaOfInterest(AreaOfInterest areaOfInterest) {
		this.areaOfInterest = areaOfInterest;
	}

	public List<AreaOfInterest> getAreaOfInterests() {
		return areaOfInterests;
	}

	public void setAreaOfInterests(List<AreaOfInterest> areaOfInterests) {
		this.areaOfInterests = areaOfInterests;
	}

	public AreaOfInterest getSelectedAreaOfInterest() {
		return selectedAreaOfInterest;
	}

	public void setSelectedAreaOfInterest(AreaOfInterest selectedAreaOfInterest) {
		this.selectedAreaOfInterest = selectedAreaOfInterest;
	}

	public AreaOfInterestService getAreaOfInterestService() {
		return areaOfInterestService;
	}

	public void setAreaOfInterestService(
			AreaOfInterestService areaOfInterestService) {
		this.areaOfInterestService = areaOfInterestService;
	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public String addAreaOfInterest() {

		getAreaOfInterest().setUser(getLoggedUser());
		getAreaOfInterestService().addAreaOfInterest(getAreaOfInterest());

		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Kaydınız başarıyla tamamlandı.", "");

		FacesContext.getCurrentInstance().addMessage(null, fm);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		return "area_of_interest.xhtml?faces-redirect=true&user="
				+ getLoggedUser().getTcno();
	}

	public String updateAreaOfInterest() {

		getAreaOfInterestService().updateAreaOfInterest(
				getSelectedAreaOfInterest());

		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Kaydınız başarıyla güncellendi.", "");

		FacesContext.getCurrentInstance().addMessage(null, fm);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		return "area_of_interest.xhtml?faces-redirect=true&user="
				+ getLoggedUser().getTcno();
	}

}
