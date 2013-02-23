package com.orcun.mezun.dao.user;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.ParentInfo;
import com.orcun.mezun.model.User;


@Repository
@Transactional
public class ParentInfoDAO{
	
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
	
	public void addParentInfo(ParentInfo parentInfo) {
		getSession().save(parentInfo);
	}
	
	public void updateParentInfo(ParentInfo parentInfo){
		getSession().update(parentInfo);
	}

	public ParentInfo findParentInfoByUser(User loggedUser) {
		return (ParentInfo) getSession().createCriteria(ParentInfo.class).
				add(Restrictions.eq("user", loggedUser))
				.uniqueResult();
	}
	
	
}
