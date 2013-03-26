package com.orcun.mezun.dao.user;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.Contact;
import com.orcun.mezun.model.EducationInfo;
import com.orcun.mezun.model.User;

@Repository
@Transactional
public class InitAlumniInfoDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {

		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public List<Contact> studentContactInfo(User loggedUser) {
		return getSession().createCriteria(Contact.class)
				.add(Restrictions.eq("user", loggedUser)).list();
	}

	@SuppressWarnings("unchecked")
	public List<EducationInfo> studentThereUniversity(User loggedUser) {
		return getSession().createCriteria(EducationInfo.class)
				.add(Restrictions.eq("user", loggedUser)).list();

	}

	public boolean saveInitAlumniInfo(Contact contact,EducationInfo educationInfo) {
		
		Contact savedContact=(Contact) getSession().save(contact);
		Long savedEducationInfoId=(Long)getSession().save(educationInfo);
		
		if(savedContact!=null && savedEducationInfoId!=null){
			return true;
			
		}else{
			getSession().getTransaction().rollback();
			return false;
			
		}
		
	}

}
