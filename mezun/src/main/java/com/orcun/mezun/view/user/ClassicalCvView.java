package com.orcun.mezun.view.user;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.User;

@ManagedBean
@ViewScoped
public class ClassicalCvView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;


	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}
	
}
