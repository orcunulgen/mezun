package com.orcun.mezun.converter.user;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.orcun.mezun.model.HighSchoolType;


@FacesConverter("highSchoolTypeConverter")
public class HighSchoolTypeConverter implements Converter {
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		
		Long highSchoolTypeId=Long.parseLong(value);
		
		HighSchoolType highSchoolType=new HighSchoolType();
		
		highSchoolType.setId(highSchoolTypeId);
		
		return highSchoolType;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value==null)
			return null;
		
		return value.toString();
	}

}
