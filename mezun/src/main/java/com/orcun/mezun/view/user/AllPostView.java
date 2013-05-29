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
import com.orcun.mezun.model.ChatGroup;
import com.orcun.mezun.model.ChatList;
import com.orcun.mezun.model.ChatPerson;
import com.orcun.mezun.model.Event;
import com.orcun.mezun.model.Photo;
import com.orcun.mezun.model.PhotoAlbum;
import com.orcun.mezun.model.PostHistory;
import com.orcun.mezun.model.ShareList;
import com.orcun.mezun.model.User;
import com.orcun.mezun.model.enums.ContentType;
import com.orcun.mezun.model.enums.UploadedFileDirectory;
import com.orcun.mezun.model.post.PhotoPostHistory;
import com.orcun.mezun.model.post.Post;
import com.orcun.mezun.service.user.AllPostService;
import com.orcun.mezun.service.user.AnnouncementService;
import com.orcun.mezun.service.user.ChatListService;
import com.orcun.mezun.service.user.EventService;
import com.orcun.mezun.service.user.PhotoService;

@ManagedBean
@ViewScoped
public class AllPostView implements Serializable {

	private static final long serialVersionUID = 1L;

	private PostHistory postHistory;
	private PhotoPostHistory photoPostHistory;

	private Event event;
	private UploadedFile uploadedEventPoster;

	private Announcement announcement;
	private UploadedFile uploadedAnnouncementPoster;
	private List<AnnouncementType> announcementTypes = new ArrayList<AnnouncementType>();

	private Photo photo;
	private UploadedFile uploadedPhoto;
	private List<PhotoAlbum> photoAlbumList = new ArrayList<PhotoAlbum>();

	private List<ShareList> shareList = new ArrayList<ShareList>();

	private List<Post> allPosts = new ArrayList<Post>();
	private List<Post> myPosts = new ArrayList<Post>();

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

	@ManagedProperty(value = "#{chatListService}")
	private ChatListService chatListService;

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

		if (this.photoPostHistory == null) {
			this.photoPostHistory = new PhotoPostHistory();
		}

		announcementTypes = announcementService.allAnnouncementTypes();
		photoAlbumList = getPhotoService().allPhotoAlbum(getLoggedUser());

		String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		if (viewId.equals("/user_profile/index.xhtml")) {
			allPosts = getAllPostService().allPosts(getLoggedUser());
		} else if (viewId.equals("/user_profile/my_profile.xhtml")) {
			myPosts = getAllPostService().myPosts(getLoggedUser());
		}

	}

	public PostHistory getPostHistory() {
		return postHistory;
	}

	public void setPostHistory(PostHistory postHistory) {
		this.postHistory = postHistory;
	}

	public PhotoPostHistory getPhotoPostHistory() {
		return photoPostHistory;
	}

	public void setPhotoPostHistory(PhotoPostHistory photoPostHistory) {
		this.photoPostHistory = photoPostHistory;
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

	public List<ShareList> getShareList() {
		return shareList;
	}

	public void setShareList(List<ShareList> shareList) {
		this.shareList = shareList;
	}

	public List<Post> getAllPosts() {
		return allPosts;
	}

	public void setAllPosts(List<Post> allPosts) {
		this.allPosts = allPosts;
	}

	public List<Post> getMyPosts() {
		return myPosts;
	}

	public void setMyPosts(List<Post> myPosts) {
		this.myPosts = myPosts;
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

	public ChatListService getChatListService() {
		return chatListService;
	}

	public void setChatListService(ChatListService chatListService) {
		this.chatListService = chatListService;
	}

	public void shareToAllList(PostHistory posHistory) {
		ChatList chatList = getChatListService().findChatListByUser(
				getLoggedUser());

		if (chatList != null) {
			List<ChatGroup> chatGroupList = chatList.getChatGroups();

			if (chatGroupList.size() != 0) {
				for (int i = 0; i < chatGroupList.size(); i++) {
					List<ChatPerson> chatPersonList = chatGroupList.get(i)
							.getChatPersons();

					for (int j = 0; j < chatPersonList.size(); j++) {
						ShareList temp = new ShareList();
						temp.setUser(chatPersonList.get(j).getUser());
						temp.setPostHistory(posHistory);
						this.shareList.add(temp);
					}
				}
			}

		}

		ShareList shareToMe = new ShareList();
		shareToMe.setUser(getLoggedUser());
		shareToMe.setPostHistory(posHistory);
		this.shareList.add(shareToMe);
	}

	public String addTextSharePost() {

		Date registeredDate = new Date();

		getPostHistory().setContentType(ContentType.TEXT);
		getPostHistory().setUser(getLoggedUser());
		getPostHistory().setPublishedDate(registeredDate);

		PostHistory savedPostHistory = getAllPostService().savePostHistory(
				getPostHistory());

		shareToAllList(savedPostHistory);

		getAllPostService().saveShareList(getShareList());

		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Kaydınız başarıyla güncellendi.", "");

		FacesContext.getCurrentInstance().addMessage(null, fm);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		return ("my_profile.xhtml?faces-redirect=true&user=" + getLoggedUser()
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

				Long savedEventID = getEventService().addEvent(getEvent());

				getPostHistory().setContentID(savedEventID);
				getPostHistory().setContentType(ContentType.EVENT);
				getPostHistory().setPublishedDate(registeredDate);
				getPostHistory().setUser(getLoggedUser());

				PostHistory savedPostHistory = getAllPostService()
						.savePostHistory(getPostHistory());

				shareToAllList(savedPostHistory);

				getAllPostService().saveShareList(getShareList());

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
		return "my_profile.xhtml?faces-redirect=true&user="
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
			Long savedAnnouncementID = getAnnouncementService()
					.addAnnouncement(getAnnouncement());

			getPostHistory().setContentID(savedAnnouncementID);
			getPostHistory().setContentType(ContentType.ANNOUNCEMENT);
			getPostHistory().setPublishedDate(registeredDate);
			getPostHistory().setUser(getLoggedUser());

			PostHistory savedPostHistory = getAllPostService().savePostHistory(
					getPostHistory());

			shareToAllList(savedPostHistory);

			getAllPostService().saveShareList(getShareList());

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

		return "my_profile.xhtml?faces-redirect=true&user="
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
			Long savedPhotoID = getPhotoService().addPhoto(getPhoto());

			getPostHistory().setContentID(savedPhotoID);
			getPostHistory().setContentType(ContentType.PHOTO);
			getPostHistory().setPublishedDate(registerDate);
			getPostHistory().setUser(getLoggedUser());

			PostHistory savedPostHistory = getAllPostService().savePostHistory(
					getPostHistory());

			shareToAllList(savedPostHistory);

			getAllPostService().saveShareList(getShareList());

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

		return "my_profile.xhtml?faces-redirect=true&user="
				+ getLoggedUser().getTcno();

	}

}
