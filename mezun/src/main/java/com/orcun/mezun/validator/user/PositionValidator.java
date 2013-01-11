package com.orcun.mezun.validator.user;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.orcun.mezun.model.Position;


@FacesValidator("positionValidator")
public class PositionValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {
		
		Position position=new Position();
		position=(Position)obj;
		
		if(position.getId()==0){
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,"Pozisyon seçilmedi.","Lütfen bir pozisyon seçiniz.");
            throw new ValidatorException(fm);
		}	
	}

}
