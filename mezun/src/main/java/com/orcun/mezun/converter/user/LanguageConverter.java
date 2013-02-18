package com.orcun.mezun.converter.user;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.orcun.mezun.model.Language;

@FacesConverter("languageConverter")
public class LanguageConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		Long languageId = Long.parseLong(value);

		Language language = new Language();

		language.setId(languageId);

		return language;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value == null)
			return null;

		return value.toString();
	}

}
