package com.orcun.mezun.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailFormatValidator")
public class EmailFormatValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {

		pattern = Pattern.compile(EMAIL_PATTERN);

		// [\w\.-]*[a-zA-Z0-9_]@[\w\.-]*[a-zA-Z0-9]\.[a-zA-Z][a-zA-Z\.]*[a-zA-Z]

		String email = (String) obj;
		matcher = pattern.matcher(email);
		if (!matcher.matches()) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Geçersiz email adresi.",
					"Lütfen geçerli bir email adresi giriniz.");
			throw new ValidatorException(fm);
		}
	}

}
