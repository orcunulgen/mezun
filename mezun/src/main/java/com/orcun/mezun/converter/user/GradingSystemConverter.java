package com.orcun.mezun.converter.user;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.orcun.mezun.model.GradingSystem;


@FacesConverter("gradingSystemConverter")
public class GradingSystemConverter implements Converter {
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		
		Long gradingSystemId=Long.parseLong(value);
		
		GradingSystem gradingSystem=new GradingSystem();
		
		gradingSystem.setId(gradingSystemId);
		
		return gradingSystem;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value==null)
			return null;
		
		return value.toString();
	}

}
