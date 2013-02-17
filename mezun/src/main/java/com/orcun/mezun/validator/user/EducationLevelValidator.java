package com.orcun.mezun.validator.user;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.orcun.mezun.model.EducationLevel;

@FacesValidator("educationLevelValidator")
public class EducationLevelValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {

		EducationLevel educationLevel = new EducationLevel();
		educationLevel = (EducationLevel) obj;

		if (educationLevel.getId() == 0) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Eğitim seviyesi seçilmedi.",
					"Lütfen bir eğitim seviyesi seçiniz.");
			throw new ValidatorException(fm);
		}
	}

}
