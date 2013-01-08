package com.orcun.mezun.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.ContactInfoDAO;
import com.orcun.mezun.model.Contact;
import com.orcun.mezun.model.User;

@Service
public class ContactInfoService {
	
	@Autowired
	private ContactInfoDAO contactInfoDAO;

	public ContactInfoDAO getContactInfoDAO() {
		return contactInfoDAO;
	}

	public void setContactInfoDAO(ContactInfoDAO contactInfoDAO) {
		this.contactInfoDAO = contactInfoDAO;
	}

	public Contact findContactByUser(User user) {
		if (user != null) {
			return contactInfoDAO.findContactByUser(user);
		} else {
			return null;
		}

	}

	public void updateContact(Contact contact) {
		getContactInfoDAO().updateContact(contact);
	}

	public void addContact(Contact contact) {
		getContactInfoDAO().addContact(contact);
	}

}
