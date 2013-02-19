package com.orcun.mezun.validator.user;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.orcun.mezun.model.HighSchoolType;


@FacesValidator("highSchoolTypeValidator")
public class HighSchoolTypeValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {
		
		HighSchoolType highSchoolType=new HighSchoolType();
		highSchoolType=(HighSchoolType)obj;
		
		if(highSchoolType.getId()==0){
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,"Lise tipi seçilmedi.","Lütfen lise tipi seçiniz.");
            throw new ValidatorException(fm);
		}	
	}

}
