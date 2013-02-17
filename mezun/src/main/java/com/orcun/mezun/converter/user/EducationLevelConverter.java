package com.orcun.mezun.converter.user;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.orcun.mezun.model.EducationLevel;


@FacesConverter("educationLevelConverter")
public class EducationLevelConverter implements Converter {
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		
		Long educationLevelId=Long.parseLong(value);
		
		EducationLevel educationLevel=new EducationLevel();
		
		educationLevel.setId(educationLevelId);
		
		return educationLevel;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value==null)
			return null;
		
		return value.toString();
	}

}
