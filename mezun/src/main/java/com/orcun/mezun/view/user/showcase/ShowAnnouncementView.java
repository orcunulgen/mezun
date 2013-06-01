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

import com.orcun.mezun.model.Announcement;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.admin.InactiveUserService;
import com.orcun.mezun.service.user.AnnouncementService;
import com.orcun.mezun.util.StringConvertUtil;

@ManagedBean
@ViewScoped
public class ShowAnnouncementView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private List<Announcement> announcements = new ArrayList<Announcement>();
	private Announcement selectedAnnouncement;

	@ManagedProperty(value = "#{announcementService}")
	private AnnouncementService announcementService;

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

		announcements = announcementService.allAnnouncement(getLoggedUser());

	}

	public void initSelectedAnnouncement(Announcement selectedAnnouncement) {
		this.selectedAnnouncement = selectedAnnouncement;
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public List<Announcement> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(List<Announcement> announcements) {
		this.announcements = announcements;
	}

	public Announcement getSelectedAnnouncement() {
		return selectedAnnouncement;
	}

	public void setSelectedAnnouncement(Announcement selectedAnnouncement) {
		this.selectedAnnouncement = selectedAnnouncement;
	}

	public AnnouncementService getAnnouncementService() {
		return announcementService;
	}

	public void setAnnouncementService(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}

	public InactiveUserService getInactiveUserService() {
		return inactiveUserService;
	}

	public void setInactiveUserService(InactiveUserService inactiveUserService) {
		this.inactiveUserService = inactiveUserService;
	}

}
