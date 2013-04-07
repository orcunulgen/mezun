package com.orcun.mezun.dao.user.helper;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.SearchCriteria;
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
	public List<User> searchChatPersonByName(SearchCriteria searchCriteria) {
		Criteria criteria=getSession().createCriteria(User.class);
		Disjunction or = Restrictions.disjunction();
				or.add(Restrictions.eq("name", searchCriteria.getSearchByName()));
				or.add(Restrictions.eq("surname",searchCriteria.getSearchBySurname()));
				or.add(Restrictions.eq("email", searchCriteria.getSearchByEmail()));
			return criteria.add(or).list();
	}
	
}
