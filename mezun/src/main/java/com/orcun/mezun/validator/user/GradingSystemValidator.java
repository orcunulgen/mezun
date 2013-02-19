package com.orcun.mezun.validator.user;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.orcun.mezun.model.GradingSystem;

@FacesValidator("gradingSystemValidator")
public class GradingSystemValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {

		GradingSystem gradingSystem = new GradingSystem();
		gradingSystem = (GradingSystem) obj;

		if (gradingSystem.getId() == 0) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Diploma not sistemi seçilmedi.",
					"Lütfen not sistemini seçiniz.");
			throw new ValidatorException(fm);
		}
	}

}
