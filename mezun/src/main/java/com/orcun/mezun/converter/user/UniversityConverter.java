package com.orcun.mezun.converter.user;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.orcun.mezun.model.University;


@FacesConverter("universityConverter")
public class UniversityConverter implements Converter {
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		
		Long universityId=Long.parseLong(value);
		
		University university=new University();
		
		university.setId(universityId);
		
		return university;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value==null)
			return null;
		
		return value.toString();
	}

}
