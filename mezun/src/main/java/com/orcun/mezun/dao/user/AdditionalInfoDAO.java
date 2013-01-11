package com.orcun.mezun.dao.user;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.AdditionalInfo;
import com.orcun.mezun.model.User;


@Repository
@Transactional
public class AdditionalInfoDAO{
	
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
	
	public void addAdditionalInfo(AdditionalInfo additionalInfo) {
		getSession().save(additionalInfo);
	}	
	
	public void updateAdditionalInfo(AdditionalInfo additionalInfo){
		getSession().update(additionalInfo);
	}
	
	public AdditionalInfo findAdditionalInfoByUser(User user) {
		
		return (AdditionalInfo) getSession().createCriteria(AdditionalInfo.class).
				add(Restrictions.eq("user", user))
				.uniqueResult();
	}
	
}
