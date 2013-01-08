package com.orcun.mezun.dao.user;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.Contact;
import com.orcun.mezun.model.User;


@Repository
@Transactional
public class ContactInfoDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	
	public SessionFactory getSessionFactory() {
		
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	public Session getSession(){
		return getSessionFactory().getCurrentSession();
	}
	
	public void addContact(Contact contact) {
		getSession().save(contact);
	}	
	
	public void updateContact(Contact contact){
		getSession().update(contact);
	}
	
	public Contact findContactByUser(User user) {
		
		return (Contact) getSession().createCriteria(Contact.class).
				add(Restrictions.eq("user", user))
				.uniqueResult();
	}
	
}
