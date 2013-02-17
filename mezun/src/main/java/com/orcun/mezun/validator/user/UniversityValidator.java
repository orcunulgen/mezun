package com.orcun.mezun.validator.user;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.orcun.mezun.model.University;

@FacesValidator("universityValidator")
public class UniversityValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {

		University university = new University();
		university = (University) obj;

		if (university.getId() == 0) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Üniversite seçilmedi.", "Lütfen bir üniversite seçiniz.");
			throw new ValidatorException(fm);
		}
	}

}
