package com.orcun.mezun.dao.user.helper;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.User;

@Repository
@Transactional
public class SearchChatPersonHelperDAO{
	
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

	@SuppressWarnings("unchecked")
	public List<User> searchChatPersonByName(String searchByName) {
		return getSession().createCriteria(User.class)
				.add(Restrictions.eq("name", searchByName)).list();
	}
	
}
