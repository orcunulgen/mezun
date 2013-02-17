package com.orcun.mezun.converter.user;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.orcun.mezun.model.Department;


@FacesConverter("departmentConverter")
public class DepartmentConverter implements Converter {
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		
		Long departmentId=Long.parseLong(value);
		
		Department department=new Department();
		
		department.setId(departmentId);
		
		return department;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value==null)
			return null;
		
		return value.toString();
	}

}
