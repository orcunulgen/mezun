package com.orcun.mezun.validator;

import java.rmi.RemoteException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.orcun.mezun.util.StringConvertUtil;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

@FacesValidator("tcnoCheckValidator")
public class TcnoCheckValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {

		String formName=(String) component.getAttributes().get("formName");
		
		Long tcno = StringConvertUtil.stringToLong(obj.toString());

		UIInput name_input = (UIInput) context.getViewRoot().findComponent(
				formName+":form_name");
		String name = null;
		if (name_input.getSubmittedValue() != null) {
			name = name_input.getSubmittedValue().toString();
		}

		UIInput surname_input = (UIInput) context.getViewRoot().findComponent(
			formName+":form_surname");
		String surname = null;
		if (surname_input.getSubmittedValue() != null) {
			surname = surname_input.getSubmittedValue().toString();
		}

		UISelectItem birthdayYear_input = (UISelectItem) context.getViewRoot()
				.findComponent(formName+":form_birtday_year");
		Integer birthdayYear = null;

		if (birthdayYear_input.getItemValue() != null) {
			birthdayYear = Integer.parseInt(birthdayYear_input
					.getItemValue().toString());
		}

		if (tcno != null) {

			KPSPublicSoapProxy tcCheckProxy = new KPSPublicSoapProxy();

			try {
				if (!tcCheckProxy.TCKimlikNoDogrula(tcno, name, surname,
						birthdayYear)) {
					FacesMessage fm = new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"TC kimlik numarası doğrulanamadı",
							"Lütfen isim,soyisim,doğum yılı ve tc kimlik numarası alanlarını kontrol ediniz");
					throw new ValidatorException(fm);
				}

			} catch (RemoteException e) {
				e.printStackTrace();
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Bağlantı hatası", "TC kimlik numarası sorgulanamıyor.");
				throw new ValidatorException(fm);
			}

		} else {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"TC kimlik numarası geçerli değil",
					"Lütfen geçerli bir TC kimlik numarası giriniz.");
			throw new ValidatorException(fm);
		}

	}


}
