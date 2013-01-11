package com.orcun.mezun.converter.user;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.orcun.mezun.model.WorkingType;


@FacesConverter("workingTypeConverter")
public class WorkingTypeConverter implements Converter {
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		
		Long workingTypeId=Long.parseLong(value);
		
		WorkingType workingType=new WorkingType();
		
		workingType.setId(workingTypeId);
		
		return workingType;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value==null)
			return null;
		
		return value.toString();
	}

}
