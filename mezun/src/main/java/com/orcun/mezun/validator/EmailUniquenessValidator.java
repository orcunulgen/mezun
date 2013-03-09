package com.orcun.mezun.validator;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.orcun.mezun.service.SignUpService;

@FacesValidator("emailUniquenessValidator")
public class EmailUniquenessValidator implements Validator {

	private SignUpService signUpService;

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {

		String email = (String) obj;

		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				"appContext/appContext-mezun.xml");

		signUpService = (SignUpService) appContext.getBean("signUpService");

		if (signUpService.areThereSameEmail(email)) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Email adresi kullanılmaktadır.",
					"Lütfen farklı bir email adresi giriniz.");
			throw new ValidatorException(fm);
		}

	}

}
