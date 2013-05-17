package com.orcun.mezun.view.user;

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

import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.commons.FileUploadService;
import com.orcun.mezun.model.Photo;
import com.orcun.mezun.model.PhotoAlbum;
import com.orcun.mezun.model.User;
import com.orcun.mezun.model.enums.UploadedFileDirectory;
import com.orcun.mezun.service.user.PhotoService;

@ManagedBean
@ViewScoped
public class PhotoView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private List<Photo> currentPhotos=new ArrayList<Photo>();
	
	private Photo photo;
	private PhotoAlbum photoAlbum;
	
	private PhotoAlbum selectedPhotoAlbum;
	
	private UploadedFile uploadedPhoto;
	
	private List<PhotoAlbum> photoAlbumList=new ArrayList<PhotoAlbum>();

	private User loggedUser;
	
	@ManagedProperty(value = "#{photoService}")
	private PhotoService photoService;

	@ManagedProperty(value = "#{fileUploadService}")
	private FileUploadService fileUploadService;


	@PostConstruct
	public void init(){
		if(photo==null){
			photo=new Photo();
		}
		if(photoAlbum==null){
			photoAlbum=new PhotoAlbum();
		}
		
		photoAlbumList=getPhotoService().allPhotoAlbum(getLoggedUser());
		if(photoAlbumList.size()!=0){
			initSelectedPhotoAlbum(photoAlbumList.get(0));
		}
		
	}
	

	public void initSelectedPhotoAlbum(PhotoAlbum selectedPhotoAlbum){
		this.selectedPhotoAlbum=selectedPhotoAlbum;
		
		currentPhotos=new ArrayList<Photo>();
		
		for(int i=0;i<this.selectedPhotoAlbum.getPhotos().size();i++){
			currentPhotos.add(this.selectedPhotoAlbum.getPhotos().get(i));
		}
	}

	public List<Photo> getCurrentPhotos() {
		return currentPhotos;
	}

	public void setCurrentPhotos(List<Photo> currentPhotos) {
		this.currentPhotos = currentPhotos;
	}

	public Photo getPhoto() {
		return photo;
	}


	public void setPhoto(Photo photo) {
		this.photo = photo;
	}


	public PhotoAlbum getPhotoAlbum() {
		return photoAlbum;
	}


	public void setPhotoAlbum(PhotoAlbum photoAlbum) {
		this.photoAlbum = photoAlbum;
	}


	public PhotoAlbum getSelectedPhotoAlbum() {
		return selectedPhotoAlbum;
	}

	public void setSelectedPhotoAlbum(PhotoAlbum selectedPhotoAlbum) {
		this.selectedPhotoAlbum = selectedPhotoAlbum;
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

	public String addPhoto(){
		
		Date registerDate=new Date();
		
		getPhoto().setDate(registerDate);
		

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		try {
			getFileUploadService().saveFile(
					UploadedFileDirectory.ALBUM_PHOTO_PATH.getPath(),
					getUploadedPhoto());

			getPhoto().setPhotoPath(
					getFileUploadService().getFileName());
			getPhotoService().addPhoto(getPhoto());
			
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Fotoğrafınız başarıyla yüklenmiştir.", "");

			FacesContext.getCurrentInstance().addMessage(null, fm);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Beklenmeyen bir hatayla karşılaşıldı.Fotoğraf yükleme işlemi başarısız.", "");

			FacesContext.getCurrentInstance().addMessage(null, fm);
		}

		


		return "photo.xhtml?faces-redirect=true&user="
				+ getLoggedUser().getTcno();

	}
	public String addPhotoAlbum(){
		
		Date registerDate=new Date();
		
		getPhotoAlbum().setUser(getLoggedUser());
		getPhotoAlbum().setDate(registerDate);
		
		getPhotoService().addPhotoAlbum(getPhotoAlbum());
		
		FacesMessage fm = new FacesMessage(
				FacesMessage.SEVERITY_INFO,
				"Fotoğraf albümünüz başarıyla oluşturuldu.", "");

		FacesContext.getCurrentInstance().addMessage(null, fm);
		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		return "photo.xhtml?faces-redirect=true&user="
				+ getLoggedUser().getTcno();

		
	}

}
