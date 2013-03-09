package com.orcun.mezun.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailMatchValidator")
public class EmailMatchValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {

		String formName=(String) component.getAttributes().get("formName"); 
		
		String email = (String) obj;
		UIInput email_confirm_input = (UIInput) context.getViewRoot()
				.findComponent(formName+":form_confirm_email");
		if (email_confirm_input.getSubmittedValue() != null) {
			String email_confirm = (String) email_confirm_input
					.getSubmittedValue().toString();

			if (!email.equalsIgnoreCase(email_confirm)) {
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Email adresleri eşleşmiyor.",
						"Lütfen yeniden deneyiniz.");
				throw new ValidatorException(fm);
			}
		}
	}

}
