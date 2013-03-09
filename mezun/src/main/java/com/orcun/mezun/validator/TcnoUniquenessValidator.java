package com.orcun.mezun.validator;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.orcun.mezun.service.SignUpService;
import com.orcun.mezun.util.StringConvertUtil;

@FacesValidator("tcnoUniquenessValidator")
public class TcnoUniquenessValidator implements Validator {

	private SignUpService signUpService;

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {

		Long tcno = StringConvertUtil.stringToLong(obj.toString());

		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				"appContext/appContext-mezun.xml");

		signUpService = (SignUpService) appContext.getBean("signUpService");

		if (signUpService.areThereSameTcno(tcno)) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"TC kimlik numarası kullanılmaktadır.",
					"Lütfen farklı bir TC kimlik giriniz.");
			throw new ValidatorException(fm);
		}

	}

}
