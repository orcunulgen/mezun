package com.orcun.mezun.view.user.init;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.Contact;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.ContactInfoService;
import com.orcun.mezun.service.user.init.InitAlumniInfoService;

@ManagedBean
@ViewScoped
public class AlumniContactInfoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private Contact contact;

	@ManagedProperty(value = "#{contactInfoService}")
	private ContactInfoService contactInfoService;
	
	@ManagedProperty(value = "#{initAlumniInfoService}")
	private InitAlumniInfoService initAlumniInfoService;
	
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

	public InitAlumniInfoService getInitAlumniInfoService() {
		return initAlumniInfoService;
	}

	public void setInitAlumniInfoService(InitAlumniInfoService initAlumniInfoService) {
		this.initAlumniInfoService = initAlumniInfoService;
	}

	public void saveContact() throws IOException {
		try {
			if (getContactInfoService().findContactByUser(getLoggedUser()) != null) {
				getContactInfoService().updateContact(getContact());
			} else {
				getContact().setUser(getLoggedUser());
				getContactInfoService().addContact(getContact());
			}
			
			if(!getInitAlumniInfoService().IsValidInitAlumniInfo(getLoggedUser())){
				FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.redirect(
						"init_alumni_info.xhtml?user="
								+ getLoggedUser().getTcno());
			}else{
				FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.redirect(
						"../index.xhtml");
			}
			

		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

}
