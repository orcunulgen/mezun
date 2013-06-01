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

import com.orcun.mezun.model.AreaOfInterest;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.admin.InactiveUserService;
import com.orcun.mezun.service.user.AreaOfInterestService;
import com.orcun.mezun.util.StringConvertUtil;

@ManagedBean
@ViewScoped
public class ShowAreaOfInterestView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private List<AreaOfInterest> areaOfInterests = new ArrayList<AreaOfInterest>();

	private AreaOfInterest selectedAreaOfInterest;

	@ManagedProperty(value = "#{areaOfInterestService}")
	private AreaOfInterestService areaOfInterestService;
	
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
		areaOfInterests = areaOfInterestService
				.allAreaOfInterest(getLoggedUser());
	}

	public void initSelectedAreaOfInterest(AreaOfInterest selectedAreaOfInterest) {
		this.selectedAreaOfInterest = selectedAreaOfInterest;
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
