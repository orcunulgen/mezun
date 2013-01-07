package com.orcun.mezun.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.User;

@Repository
@Transactional
public class UserDAO {
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
	
	public User findByUserId(String email) {
		return (User) getSession().createCriteria(User.class).
				add(Restrictions.eq("email", email))
				.uniqueResult();
	}

}
