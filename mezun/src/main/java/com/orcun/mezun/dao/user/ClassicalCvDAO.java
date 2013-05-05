package com.orcun.mezun.dao.user;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.ClassicalCV;
import com.orcun.mezun.model.User;


@Repository
@Transactional
public class ClassicalCvDAO{
	
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
	
	public void addClassicalCv(ClassicalCV classicalCV) {
		getSession().save(classicalCV);
	}	
	
	public void updateClassicalCv(ClassicalCV classicalCV){
		getSession().update(classicalCV);
	}
	
	public ClassicalCV findClassicalCvByUser(User user) {
		
		return (ClassicalCV) getSession().createCriteria(ClassicalCV.class).
				add(Restrictions.eq("user", user))
				.uniqueResult();
	}
	
}
