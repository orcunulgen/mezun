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

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

@FacesValidator("tcnoCheckValidator")
public class TcnoCheckValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object obj)
			throws ValidatorException {

		Long tcno = stringToLong(obj.toString());

		UIInput name_input = (UIInput) context.getViewRoot().findComponent(
				"form_sign_up_panel:form_name");
		String name = null;
		if (name_input.getSubmittedValue() != null) {
			name = name_input.getSubmittedValue().toString();
		}

		UIInput surname_input = (UIInput) context.getViewRoot().findComponent(
				"form_sign_up_panel:form_surname");
		String surname = null;
		if (surname_input.getSubmittedValue() != null) {
			surname = surname_input.getSubmittedValue().toString();
		}

		UISelectItem birthdayYear_input = (UISelectItem) context.getViewRoot()
				.findComponent("form_sign_up_panel:form_birtday_year");
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
							"TC kimlik numarasý doðrulanamadý",
							"Lütfen isim,soyisim,doðum yýlý ve tc kimlik numarasý alanlarýný kontrol ediniz");
					throw new ValidatorException(fm);
				}

			} catch (RemoteException e) {
				e.printStackTrace();
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Baðlantý hatasý", "TC kimlik numarasý sorgulanamýyor.");
				throw new ValidatorException(fm);
			}

		} else {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"TC kimlik numarasý geçerli deðil",
					"Lütfen geçerli bir TC kimlik numarasý giriniz.");
			throw new ValidatorException(fm);
		}

	}

	public Long stringToLong(String str) {
		Long result = null;
		try {
			result = Long.valueOf(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
		return result;

	}

}
