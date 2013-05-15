package com.orcun.mezun.view.user;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.commons.FileUploadService;
import com.orcun.mezun.model.Announcement;
import com.orcun.mezun.model.AnnouncementType;
import com.orcun.mezun.model.User;
import com.orcun.mezun.model.enums.UploadedFileDirectory;
import com.orcun.mezun.service.user.AnnouncementService;

@ManagedBean
@ViewScoped
public class AnnouncementView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private Announcement announcement;

	private List<AnnouncementType> announcementTypes = new ArrayList<AnnouncementType>();

	private List<Announcement> announcements = new ArrayList<Announcement>();
	private Announcement selectedAnnouncement;

	private UploadedFile uploadedPoster;

	private Boolean updatePoster = true;

	@ManagedProperty(value = "#{announcementService}")
	private AnnouncementService announcementService;

	@ManagedProperty(value = "#{fileUploadService}")
	private FileUploadService fileUploadService;

	@PostConstruct
	public void init() {
		announcementTypes = announcementService.allAnnouncementTypes();
		announcements = announcementService.allAnnouncement(getLoggedUser());

		if (announcement == null) {
			announcement = new Announcement();

		}
	}

	public void initSelectedAnnouncement(Announcement selectedAnnouncement) {
		this.selectedAnnouncement = selectedAnnouncement;
	}

	public String deleteSelectedAnnouncement(Announcement selectedAnnouncement)
			throws IOException {
		initSelectedAnnouncement(selectedAnnouncement);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		if (getFileUploadService().deleteFile(
				UploadedFileDirectory.ANNOUNCEMENT_POSTER_PATH.getPath() + "/"
						+ selectedAnnouncement.getPosterPath())) {
			getAnnouncementService().deleteAnnouncement(
					getSelectedAnnouncement());

			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Duyuru başarıyla silindi.", "");

			FacesContext.getCurrentInstance().addMessage(null, fm);
		} else {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Duyuru silinemedi.", "");

			FacesContext.getCurrentInstance().addMessage(null, fm);

		}

		return "announcement.xhtml?faces-redirect=true&user="
				+ getLoggedUser().getTcno();
	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public Announcement getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}

	public List<AnnouncementType> getAnnouncementTypes() {
		return announcementTypes;
	}

	public void setAnnouncementTypes(List<AnnouncementType> announcementTypes) {
		this.announcementTypes = announcementTypes;
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

	public UploadedFile getUploadedPoster() {
		return uploadedPoster;
	}

	public void setUploadedPoster(UploadedFile uploadedPoster) {
		this.uploadedPoster = uploadedPoster;
	}

	public Boolean getUpdatePoster() {
		return updatePoster;
	}

	public void setUpdatePoster(Boolean updatePoster) {
		this.updatePoster = updatePoster;
	}

	public AnnouncementService getAnnouncementService() {
		return announcementService;
	}

	public void setAnnouncementService(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}

	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
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

	public String addAnnouncement() {

		Date registeredDate = new Date();

		getAnnouncement().setUser(getLoggedUser());
		getAnnouncement().setRegisteredDate(registeredDate);

		try {
			getFileUploadService().saveFile(
					UploadedFileDirectory.ANNOUNCEMENT_POSTER_PATH.getPath(),
					getUploadedPoster());

			getAnnouncement().setPosterPath(
					getFileUploadService().getFileName());
			getAnnouncementService().addAnnouncement(getAnnouncement());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Kaydınız başarıyla tamamlandı.", "");

		FacesContext.getCurrentInstance().addMessage(null, fm);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		return "announcement.xhtml?faces-redirect=true&user="
				+ getLoggedUser().getTcno();
	}

	public String updateAnnouncement() {

		if (!updatePoster && uploadedPoster != null) {
			try {

				if (getFileUploadService().deleteFile(
						UploadedFileDirectory.ANNOUNCEMENT_POSTER_PATH
								.getPath()
								+ "/"
								+ getSelectedAnnouncement().getPosterPath())) {

					getFileUploadService()
							.saveFile(
									UploadedFileDirectory.ANNOUNCEMENT_POSTER_PATH
											.getPath(), getUploadedPoster());

					getSelectedAnnouncement().setPosterPath(
							getFileUploadService().getFileName());

					getAnnouncementService().updateAnnouncement(
							getSelectedAnnouncement());

					FacesMessage fm = new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"Kaydınız başarıyla güncellendi.", "");

					FacesContext.getCurrentInstance().addMessage(null, fm);

				} else {
					FacesMessage fm = new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"Kaydınız güncellenemedi.", "");

					FacesContext.getCurrentInstance().addMessage(null, fm);

				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			getAnnouncementService().updateAnnouncement(
					getSelectedAnnouncement());

			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Kaydınız başarıyla güncellendi.", "");

			FacesContext.getCurrentInstance().addMessage(null, fm);
		}

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		return "announcement.xhtml?faces-redirect=true&user="
				+ getLoggedUser().getTcno();
	}

}
