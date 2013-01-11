package com.orcun.mezun.validator.user;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.orcun.mezun.model.Country;


@FacesValidator("countryValidator")
public class CountryValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {
		Country country=new Country();
		country=(Country)obj;
		
		if(country.getCode().equals("0")){
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,"Ülke seçilmedi.","Lütfen bir ülke seçiniz.");
            throw new ValidatorException(fm);
		}
			
	}

}
