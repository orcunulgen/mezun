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

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.commons.FileUploadService;
import com.orcun.mezun.model.Event;
import com.orcun.mezun.model.PostHistory;
import com.orcun.mezun.model.User;
import com.orcun.mezun.model.enums.ContentType;
import com.orcun.mezun.model.enums.UploadedFileDirectory;
import com.orcun.mezun.service.user.AllPostService;
import com.orcun.mezun.service.user.EventService;

@ManagedBean
@ViewScoped
public class EventView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private Event event;

	private List<Event> events = new ArrayList<Event>();
	private Event selectedEvent;

	private UploadedFile uploadedPoster;

	private Boolean updatePoster = true;

	private PostHistory postHistory;

	@ManagedProperty(value = "#{eventService}")
	private EventService eventService;

	@ManagedProperty(value = "#{fileUploadService}")
	private FileUploadService fileUploadService;

	@ManagedProperty(value = "#{allPostService}")
	private AllPostService allPostService;

	@PostConstruct
	public void init() {

		events = eventService.allEvent(getLoggedUser());

		if (event == null) {
			event = new Event();

		}

		if (this.postHistory == null) {
			this.postHistory = new PostHistory();
		}
	}

	public void initSelectedEvent(Event selectedEvent) {
		this.selectedEvent = selectedEvent;
	}

	public String shareSelectedEvent() {

		Date publishedDate = new Date();

		getPostHistory().setContentID(getSelectedEvent().getId());
		getPostHistory().setContentType(ContentType.EVENT);
		getPostHistory().setPublishedDate(publishedDate);
		getPostHistory().setUser(getLoggedUser());

		/*
		 * PostHistory savedPostHistory = getAllPostService().savePostHistory(
		 * getPostHistory());
		 */
		getAllPostService().savePostHistory(getPostHistory());

		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Etkinliğiniz başarıyla paylaşılmıştır.", "");

		FacesContext.getCurrentInstance().addMessage(null, fm);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		// shareToAllList(savedPostHistory);

		// getAllPostService().saveShareList(getShareList());

		return "my_profile.xhtml?faces-redirect=true&u="
				+ getLoggedUser().getTcno();

	}

	public String deleteSelectedEvent(Event selectedEvent) throws IOException {
		initSelectedEvent(selectedEvent);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		if (getFileUploadService().deleteFile(
				UploadedFileDirectory.EVENT_POSTER_PATH.getPath() + "/"
						+ selectedEvent.getPosterPath())) {

			getEventService().deleteEvent(getSelectedEvent());

			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Etkinlik başarıyla silindi.", "");

			FacesContext.getCurrentInstance().addMessage(null, fm);
		} else {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Etkinlik silinemedi.", "");

			FacesContext.getCurrentInstance().addMessage(null, fm);

		}

		return "event.xhtml?faces-redirect=true&u=" + getLoggedUser().getTcno();

	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Event getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(Event selectedEvent) {
		this.selectedEvent = selectedEvent;
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

	public PostHistory getPostHistory() {
		return postHistory;
	}

	public void setPostHistory(PostHistory postHistory) {
		this.postHistory = postHistory;
	}

	public EventService getEventService() {
		return eventService;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public AllPostService getAllPostService() {
		return allPostService;
	}

	public void setAllPostService(AllPostService allPostService) {
		this.allPostService = allPostService;
	}

	public String addEvent() {

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		int differenceStartEnd = Days.daysBetween(
				new DateTime(getEvent().getStartDate()),
				new DateTime(getEvent().getEndDate())).getDays();

		Date registeredDate = new Date();

		int differenceStartRegister = Days.daysBetween(
				new DateTime(getEvent().getStartDate()),
				new DateTime(registeredDate)).getDays();

		if (differenceStartEnd > 0 && differenceStartRegister <= 0) {

			getEvent().setUser(getLoggedUser());
			getEvent().setRegisteredDate(registeredDate);

			try {
				getFileUploadService().saveFile(
						UploadedFileDirectory.EVENT_POSTER_PATH.getPath(),
						getUploadedPoster());

				getEvent().setPosterPath(getFileUploadService().getFileName());

				getEventService().addEvent(getEvent());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Kaydınız başarıyla tamamlandı.", "");

			FacesContext.getCurrentInstance().addMessage(null, fm);

		} else {
			FacesMessage fm = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Başlangıç tarihi gelecek zamana ait olmalıdır ve başlangıç tarihi bitiş tarihinden ilerde olamaz.",
					"Lütfen yeniden deneyiniz.");
			FacesContext.getCurrentInstance().addMessage(null, fm);

		}
		return "event.xhtml?faces-redirect=true&u=" + getLoggedUser().getTcno();
	}

	public String updateEvent() {

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		int differenceStartEnd = Days.daysBetween(
				new DateTime(getSelectedEvent().getStartDate()),
				new DateTime(getSelectedEvent().getEndDate())).getDays();

		Date registeredDate = new Date();

		int differenceStartRegister = Days.daysBetween(
				new DateTime(getSelectedEvent().getStartDate()),
				new DateTime(registeredDate)).getDays();

		if (differenceStartEnd > 0 && differenceStartRegister <= 0) {

			if (!updatePoster && uploadedPoster != null) {
				try {

					if (getFileUploadService().deleteFile(
							UploadedFileDirectory.EVENT_POSTER_PATH.getPath()
									+ "/" + getSelectedEvent().getPosterPath())) {

						getFileUploadService()
								.saveFile(
										UploadedFileDirectory.EVENT_POSTER_PATH
												.getPath(),
										getUploadedPoster());

						getSelectedEvent().setPosterPath(
								getFileUploadService().getFileName());

						getEventService().updateEvent(getSelectedEvent());

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
				getEventService().updateEvent(getSelectedEvent());

				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Kaydınız başarıyla güncellendi.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);
			}

		} else {
			FacesMessage fm = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Başlangıç tarihi gelecek zamana ait olmalıdır ve başlangıç tarihi bitiş tarihinden ilerde olamaz.",
					"Lütfen yeniden deneyiniz.");
			FacesContext.getCurrentInstance().addMessage(null, fm);

		}
		return "event.xhtml?faces-redirect=true&u=" + getLoggedUser().getTcno();
	}

}
