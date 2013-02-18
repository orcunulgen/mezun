package com.orcun.mezun.dao.user;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.ForeignLanguage;
import com.orcun.mezun.model.Language;
import com.orcun.mezun.model.User;


@Repository
@Transactional
public class ForeignLanguageDAO{
	
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
	
	public void addForeignLanguage(ForeignLanguage foreignLanguage) {
		getSession().save(foreignLanguage);
	}	
	
	public void updateForeignLanguage(ForeignLanguage foreignLanguage){
		getSession().update(foreignLanguage);
	}

	@SuppressWarnings("unchecked")
	public List<Language> allLanguages() {
		return getSession().createCriteria(Language.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<ForeignLanguage> allForeignLanguage(User loggedUser) {
		return getSession().createCriteria(ForeignLanguage.class)
				.add(Restrictions.eq("user", loggedUser)).list();
	}
	
	public void deleteForeignLanguage(ForeignLanguage selectedForeignLanguage) {
		getSession().createSQLQuery("delete from foreign_language where id = :id") 
	    .setParameter("id", selectedForeignLanguage.getId())
	    .executeUpdate();
	}
	
	
}
