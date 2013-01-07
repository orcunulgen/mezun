package com.orcun.mezun.view;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


@ManagedBean
@ViewScoped
public class LoginView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void login() throws IOException, ServletException {

		ExternalContext context =FacesContext.getCurrentInstance().getExternalContext();

		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher("j_spring_security_user_check");
		dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());

		FacesContext.getCurrentInstance().responseComplete();
	}
}
