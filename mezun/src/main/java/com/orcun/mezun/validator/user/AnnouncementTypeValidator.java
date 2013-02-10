package com.orcun.mezun.validator.user;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.orcun.mezun.model.AnnouncementType;


@FacesValidator("announcementTypeValidator")
public class AnnouncementTypeValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {
		AnnouncementType announcementType=new AnnouncementType();
		announcementType=(AnnouncementType)obj;
		
		if(announcementType.getId()==0){
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,"Duyuru tipi seçilmedi.","Lütfen duyuru tipi seçiniz.");
            throw new ValidatorException(fm);
		}
			
	}

}
