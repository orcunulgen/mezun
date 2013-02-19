package com.orcun.mezun.dao.user;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.GradingSystem;
import com.orcun.mezun.model.HighSchool;
import com.orcun.mezun.model.HighSchoolType;
import com.orcun.mezun.model.User;


@Repository
@Transactional
public class HighSchoolDAO{
	
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
	
	public void addHighSchool(HighSchool highSchool) {
		getSession().save(highSchool);
	}	
	
	public void updateHighSchool(HighSchool highSchool){
		getSession().update(highSchool);
	}
	@SuppressWarnings("unchecked")
	public List<HighSchoolType> allHighSchoolTypes() {
		return getSession().createCriteria(HighSchoolType.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<GradingSystem> allGradingSystems() {
		return getSession().createCriteria(GradingSystem.class).list();
	}
	
	public HighSchool findHighSchoolByUser(User user) {
		
		return (HighSchool) getSession().createCriteria(HighSchool.class).
				add(Restrictions.eq("user", user))
				.uniqueResult();
	}
	
}
