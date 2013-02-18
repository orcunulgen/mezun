package com.orcun.mezun.validator.user;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.orcun.mezun.model.Language;


@FacesValidator("languageValidator")
public class LanguageValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {
		
		Language language=new Language();
		language=(Language)obj;
		
		if(language.getId()==0){
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,"Yabancı dil seçilmedi.","Lütfen bir yabancı dil seçiniz.");
            throw new ValidatorException(fm);
		}	
	}

}
