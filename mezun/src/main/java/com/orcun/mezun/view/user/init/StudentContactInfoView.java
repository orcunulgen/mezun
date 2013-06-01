package com.orcun.mezun.view.user.init;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.Contact;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.ContactInfoService;
import com.orcun.mezun.service.user.init.InitStudentInfoService;

@ManagedBean
@ViewScoped
public class StudentContactInfoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private Contact contact;

	@ManagedProperty(value = "#{contactInfoService}")
	private ContactInfoService contactInfoService;

	@ManagedProperty(value = "#{initStudentInfoService}")
	private InitStudentInfoService initStudentInfoService;

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public Contact getContact() {

		if (contact == null) {
			contact = new Contact();
		}

		if (contact.getUser() == null) {
			if (getContactInfoService().findContactByUser(getLoggedUser()) != null) {
				setContact(getContactInfoService().findContactByUser(
						getLoggedUser()));
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

	public InitStudentInfoService getInitStudentInfoService() {
		return initStudentInfoService;
	}

	public void setInitStudentInfoService(
			InitStudentInfoService initStudentInfoService) {
		this.initStudentInfoService = initStudentInfoService;
	}

	public String saveContact() {
		if (getContactInfoService().findContactByUser(getLoggedUser()) != null) {
			getContactInfoService().updateContact(getContact());
		} else {
			getContact().setUser(getLoggedUser());
			getContactInfoService().addContact(getContact());
		}

		if (!getInitStudentInfoService()
				.IsValidInitStudentInfo(getLoggedUser())) {
			FacesMessage fm = new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Kaydınız başarıyla güncellendi.Lütfen diğer temel bilgilerinizi de tamamlayınız.",
					"");

			FacesContext.getCurrentInstance().addMessage(null, fm);

			Flash flash = FacesContext.getCurrentInstance()
					.getExternalContext().getFlash();
			flash.setKeepMessages(true);

			return "init_student_info.xhtml?faces-redirect=true&u="
					+ getLoggedUser().getTcno();
		} else {
			FacesMessage fm = new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Tüm temel bilgileriniz başarıyla kayıt altına alınmıştır.",
					"");

			FacesContext.getCurrentInstance().addMessage(null, fm);

			Flash flash = FacesContext.getCurrentInstance()
					.getExternalContext().getFlash();
			flash.setKeepMessages(true);

			/*
			 * FacesContext.getCurrentInstance().getExternalContext()
			 * .redirect("../index.xhtml");
			 */
			return "/user_profile/index.xhtml?faces-redirect=true";
		}

	}

}
