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
import com.orcun.mezun.model.Announcement;
import com.orcun.mezun.model.AnnouncementType;
import com.orcun.mezun.model.Event;
import com.orcun.mezun.model.Photo;
import com.orcun.mezun.model.PhotoAlbum;
import com.orcun.mezun.model.PostHistory;
import com.orcun.mezun.model.User;
import com.orcun.mezun.model.enums.ContentType;
import com.orcun.mezun.model.enums.UploadedFileDirectory;
import com.orcun.mezun.service.user.AllPostService;
import com.orcun.mezun.service.user.AnnouncementService;
import com.orcun.mezun.service.user.EventService;
import com.orcun.mezun.service.user.PhotoService;

@ManagedBean
@ViewScoped
public class AllPostView implements Serializable {

	private static final long serialVersionUID = 1L;

	private PostHistory postHistory;

	private Event event;
	private UploadedFile uploadedEventPoster;

	private Announcement announcement;
	private UploadedFile uploadedAnnouncementPoster;
	private List<AnnouncementType> announcementTypes = new ArrayList<AnnouncementType>();

	private Photo photo;
	private UploadedFile uploadedPhoto;

	private List<PhotoAlbum> photoAlbumList = new ArrayList<PhotoAlbum>();

	private User loggedUser;

	@ManagedProperty(value = "#{allPostService}")
	private AllPostService allPostService;

	@ManagedProperty(value = "#{eventService}")
	private EventService eventService;

	@ManagedProperty(value = "#{announcementService}")
	private AnnouncementService announcementService;

	@ManagedProperty(value = "#{photoService}")
	private PhotoService photoService;

	@ManagedProperty(value = "#{fileUploadService}")
	private FileUploadService fileUploadService;

	@PostConstruct
	public void init() {
		if (this.postHistory == null) {
			this.postHistory = new PostHistory();
		}
		if (this.event == null) {
			this.event = new Event();
		}
		if (this.announcement == null) {
			this.announcement = new Announcement();
		}
		if (this.photo == null) {
			this.photo = new Photo();
		}

		announcementTypes = announcementService.allAnnouncementTypes();
		photoAlbumList = getPhotoService().allPhotoAlbum(getLoggedUser());

	}

	public PostHistory getPostHistory() {
		return postHistory;
	}

	public void setPostHistory(PostHistory postHistory) {
		this.postHistory = postHistory;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public UploadedFile getUploadedEventPoster() {
		return uploadedEventPoster;
	}

	public void setUploadedEventPoster(UploadedFile uploadedEventPoster) {
		this.uploadedEventPoster = uploadedEventPoster;
	}

	public Announcement getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}

	public UploadedFile getUploadedAnnouncementPoster() {
		return uploadedAnnouncementPoster;
	}

	public void setUploadedAnnouncementPoster(
			UploadedFile uploadedAnnouncementPoster) {
		this.uploadedAnnouncementPoster = uploadedAnnouncementPoster;
	}

	public List<AnnouncementType> getAnnouncementTypes() {
		return announcementTypes;
	}

	public void setAnnouncementTypes(List<AnnouncementType> announcementTypes) {
		this.announcementTypes = announcementTypes;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public UploadedFile getUploadedPhoto() {
		return uploadedPhoto;
	}

	public void setUploadedPhoto(UploadedFile uploadedPhoto) {
		this.uploadedPhoto = uploadedPhoto;
	}

	public List<PhotoAlbum> getPhotoAlbumList() {
		return photoAlbumList;
	}

	public void setPhotoAlbumList(List<PhotoAlbum> photoAlbumList) {
		this.photoAlbumList = photoAlbumList;
	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public AllPostService getAllPostService() {
		return allPostService;
	}

	public void setAllPostService(AllPostService allPostService) {
		this.allPostService = allPostService;
	}

	public EventService getEventService() {
		return eventService;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	public AnnouncementService getAnnouncementService() {
		return announcementService;
	}

	public void setAnnouncementService(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}

	public PhotoService getPhotoService() {
		return photoService;
	}

	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}

	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public String addTextSharePost() {
		
		Date registeredDate=new Date();
		
		getPostHistory().setContentType(ContentType.TEXT);
		getPostHistory().setUser(getLoggedUser());
		getPostHistory().setPublishedDate(registeredDate);
		

		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Kaydınız başarıyla güncellendi.", "");

		FacesContext.getCurrentInstance().addMessage(null, fm);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		return ("additional_info.xhtml?faces-redirect=true&user=" + getLoggedUser()
				.getTcno());
	}

	public String addEventSharePost() {

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
						getUploadedEventPoster());

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
		return "index.xhtml?faces-redirect=true&user="
				+ getLoggedUser().getTcno();
	}

	public String addAnnouncementSharePost() {

		Date registeredDate = new Date();

		getAnnouncement().setUser(getLoggedUser());
		getAnnouncement().setRegisteredDate(registeredDate);

		try {
			getFileUploadService().saveFile(
					UploadedFileDirectory.ANNOUNCEMENT_POSTER_PATH.getPath(),
					getUploadedAnnouncementPoster());

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

		return "index.xhtml?faces-redirect=true&user="
				+ getLoggedUser().getTcno();
	}

	public String addPhotoSharePost() {

		Date registerDate = new Date();

		getPhoto().setDate(registerDate);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		try {
			getFileUploadService().saveFile(
					UploadedFileDirectory.ALBUM_PHOTO_PATH.getPath(),
					getUploadedPhoto());

			getPhoto().setPhotoPath(getFileUploadService().getFileName());
			getPhotoService().addPhoto(getPhoto());

			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Fotoğrafınız başarıyla yüklenmiştir.", "");

			FacesContext.getCurrentInstance().addMessage(null, fm);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesMessage fm = new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Beklenmeyen bir hatayla karşılaşıldı.Fotoğraf yükleme işlemi başarısız.",
					"");

			FacesContext.getCurrentInstance().addMessage(null, fm);
		}

		return "index.xhtml?faces-redirect=true&user="
				+ getLoggedUser().getTcno();

	}

}
