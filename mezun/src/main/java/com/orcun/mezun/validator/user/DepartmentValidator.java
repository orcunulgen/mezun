package com.orcun.mezun.validator.user;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.orcun.mezun.model.Department;

@FacesValidator("departmentValidator")
public class DepartmentValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {

		Department department = new Department();
		department = (Department) obj;

		if (department.getId() == 0) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Bölüm seçilmedi.", "Lütfen bir bölüm seçiniz.");
			throw new ValidatorException(fm);
		}
	}

}
