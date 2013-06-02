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

import com.orcun.mezun.model.Photo;
import com.orcun.mezun.model.PhotoAlbum;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.admin.InactiveUserService;
import com.orcun.mezun.service.user.PhotoService;
import com.orcun.mezun.util.StringConvertUtil;

@ManagedBean
@ViewScoped
public class ShowPhotoView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private List<Photo> currentPhotos=new ArrayList<Photo>();
	
	private PhotoAlbum selectedPhotoAlbum;
	
	private List<PhotoAlbum> photoAlbumList=new ArrayList<PhotoAlbum>();

	private User loggedUser;
	
	@ManagedProperty(value = "#{photoService}")
	private PhotoService photoService;

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

	public PhotoAlbum getSelectedPhotoAlbum() {
		return selectedPhotoAlbum;
	}

	public void setSelectedPhotoAlbum(PhotoAlbum selectedPhotoAlbum) {
		this.selectedPhotoAlbum = selectedPhotoAlbum;
	}

	public List<PhotoAlbum> getPhotoAlbumList() {
		return photoAlbumList;
	}

	public void setPhotoAlbumList(List<PhotoAlbum> photoAlbumList) {
		this.photoAlbumList = photoAlbumList;
	}

	public User getLoggedUser() {
		return loggedUser;
	}
	
	public PhotoService getPhotoService() {
		return photoService;
	}

	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}


	public InactiveUserService getInactiveUserService() {
		return inactiveUserService;
	}


	public void setInactiveUserService(InactiveUserService inactiveUserService) {
		this.inactiveUserService = inactiveUserService;
	}

}
