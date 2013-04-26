package com.orcun.mezun.dao.user.init;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.Contact;
import com.orcun.mezun.model.EducationInfo;
import com.orcun.mezun.model.HighSchool;
import com.orcun.mezun.model.ParentInfo;
import com.orcun.mezun.model.User;

@Repository
@Transactional
public class InitStudentInfoDAO {

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
	public List<ParentInfo> studentParentInfo(User loggedUser) {
		return getSession().createCriteria(ParentInfo.class)
				.add(Restrictions.eq("user", loggedUser)).list();
	}

	@SuppressWarnings("unchecked")
	public List<HighSchool> studentThereHighSchoolInfo(User loggedUser) {
		return getSession().createCriteria(HighSchool.class)
				.add(Restrictions.eq("user", loggedUser)).list();

	}

	@SuppressWarnings("unchecked")
	public List<EducationInfo> studentThereUniversity(User loggedUser) {
		return getSession().createCriteria(EducationInfo.class)
				.add(Restrictions.eq("user", loggedUser)).list();

	}

	public boolean saveInitStudentInfo(Contact contact, ParentInfo parentInfo,
			HighSchool highSchool, EducationInfo educationInfo) {
		
		Contact savedContact=(Contact) getSession().save(contact);
		ParentInfo savedParentInfo=(ParentInfo)getSession().save(parentInfo);
		HighSchool savedHighSchoolInfo=(HighSchool)getSession().save(highSchool);
		Long savedEducationInfoId=(Long)getSession().save(educationInfo);
		
		if(savedContact!=null && savedParentInfo!=null && savedHighSchoolInfo!=null && savedEducationInfoId!=null){
			return true;
			
		}else{
			getSession().getTransaction().rollback();
			return false;
			
		}
		
	}

}
