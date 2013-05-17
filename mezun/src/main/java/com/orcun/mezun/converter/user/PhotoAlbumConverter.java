package com.orcun.mezun.converter.user;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.orcun.mezun.model.PhotoAlbum;
import com.orcun.mezun.service.user.PhotoService;

@FacesConverter("photoAlbumConverter")
public class PhotoAlbumConverter implements Converter {

	private PhotoService photoService;

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				"appContext/appContext-mezun.xml");

		photoService = (PhotoService) appContext.getBean("photoService");
		
		Long photoAlbumId = Long.parseLong(value);
		

		PhotoAlbum photoAlbum = photoService.findPhotoAlbumById(photoAlbumId);

		return photoAlbum;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value == null)
			return null;

		return value.toString();
	}

}
