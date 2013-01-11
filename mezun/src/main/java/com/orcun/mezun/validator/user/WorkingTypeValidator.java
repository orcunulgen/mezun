package com.orcun.mezun.validator.user;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.orcun.mezun.model.WorkingType;

@FacesValidator("workingTypeValidator")
public class WorkingTypeValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {

		WorkingType workingType = new WorkingType();
		workingType = (WorkingType) obj;

		if (workingType.getId() == 0) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Çalışma şekli seçilmedi.",
					"Lütfen bir çalışma şekli seçiniz.");
			throw new ValidatorException(fm);
		}
	}

}
