package com.orcun.mezun.view.user;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.Contact;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.ContactInfoService;

@ManagedBean
@ViewScoped
public class ContactInfoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private Contact contact;

	@ManagedProperty(value = "#{contactInfoService}")
	private ContactInfoService contactInfoService;

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public Contact getContact() {
		
		if(contact == null){
			contact = new Contact();
		}
		
		if(contact.getUser()==null){
			if (getContactInfoService().findContactByUser(getLoggedUser()) != null) {
				setContact(getContactInfoService().findContactByUser(getLoggedUser()));
			}	
		}
		
		
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public ContactInfoService getContactInfoService() {
		return contactInfoService;
	}

	public void setContactInfoService(ContactInfoService contactInfoService) {
		this.contactInfoService = contactInfoService;
	}

	public void checkURL() throws IOException {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String userParameter = request.getParameter("user");

		if (userParameter == null || userParameter.equals("")) {

			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"personal_info.xhtml?user="
									+ getLoggedUser().getTcno());

		} else if (!userParameter.equals(getLoggedUser().getTcno().toString())) {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"personal_info.xhtml?user="
									+ getLoggedUser().getTcno());

		}
	}

	public void saveContact() throws IOException {
		try {
			if (getContactInfoService().findContactByUser(getLoggedUser()) != null) {
				getContactInfoService().updateContact(getContact());
			} else {
				getContact().setUser(getLoggedUser());
				getContactInfoService().addContact(getContact());
			}
			
			FacesContext
			.getCurrentInstance()
			.getExternalContext()
			.redirect(
					"contact_info.xhtml?user="
							+ getLoggedUser().getTcno());

		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

}
