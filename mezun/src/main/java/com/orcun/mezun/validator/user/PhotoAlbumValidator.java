package com.orcun.mezun.validator.user;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.orcun.mezun.model.PhotoAlbum;


@FacesValidator("photoAlbumValidator")
public class PhotoAlbumValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {
		
		PhotoAlbum photoAlbum=new PhotoAlbum();
		photoAlbum=(PhotoAlbum)obj;
		
		if(photoAlbum.getId()==0){
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,"Albüm seçilmedi.","Lütfen bir albüm seçiniz.");
            throw new ValidatorException(fm);
		}	
	}

}