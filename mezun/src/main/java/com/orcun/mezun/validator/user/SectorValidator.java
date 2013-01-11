package com.orcun.mezun.validator.user;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.orcun.mezun.model.Sector;


@FacesValidator("sectorValidator")
public class SectorValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {
		
		Sector sector= new Sector();
		sector=(Sector)obj;
		
		if(sector.getId()==0){
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,"Sektör seçilmedi.","Lütfen bir sektör seçiniz.");
            throw new ValidatorException(fm);
		}	
	}

}
