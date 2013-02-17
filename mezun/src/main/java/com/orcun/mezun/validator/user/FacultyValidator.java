package com.orcun.mezun.validator.user;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.orcun.mezun.model.Faculty;

@FacesValidator("facultyValidator")
public class FacultyValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {

		Faculty faculty = new Faculty();
		faculty = (Faculty) obj;

		if (faculty.getId() == 0) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Fakülte seçilmedi.", "Lütfen bir fakülte seçiniz.");
			throw new ValidatorException(fm);
		}
	}

}
