package com.orcun.mezun.validator.user;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.orcun.mezun.model.City;


@FacesValidator("cityValidator")
public class CityValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {
		
		City city=new City();
		city=(City)obj;
		
		if(city.getId()==0){
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,"Şehir seçilmedi.","Lütfen bir şehir seçiniz.");
            throw new ValidatorException(fm);
		}	
	}

}
