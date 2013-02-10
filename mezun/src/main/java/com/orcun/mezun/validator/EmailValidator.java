package com.orcun.mezun.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.orcun.mezun.service.SignUpService;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private SignUpService signUpService;

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {

		String formName=(String) component.getAttributes().get("formName"); 
		
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
