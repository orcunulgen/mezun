package com.orcun.mezun.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.City;
import com.orcun.mezun.model.Country;
import com.orcun.mezun.model.Role;
import com.orcun.mezun.model.User;


@Repository
@Transactional
public class SignUpDAO{
	
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
	
	public void addUser(User user) {
		getSession().save(user);
	}	
		
	@SuppressWarnings("unchecked")
	public List<Country> allCountries() {
		return getSession().createCriteria(Country.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<City> allCities(Country country) {
		return getSession().createCriteria(City.class).add(Restrictions.eq("country", country)).list();
	}
	
	public boolean areThereSameUsername(String username){
		Query query=getSession().createSQLQuery("select * from user where username=:username");
		query.setParameter("username", username);
		
		if(query.list().size()!=0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean areThereSameEmail(String email){
		Query query=getSession().createSQLQuery("select * from user where email=:email");
		query.setParameter("email", email);
		
		if(query.list().size()!=0){
			return true;
		}else{
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Role> getRoles() {
		return getSession().createCriteria(Role.class).list();
	}

	public Role getRoleInfo(String role) {
		return (Role) getSession().createCriteria(Role.class).
				add(Restrictions.eq("role", role))
				.uniqueResult();
	}
}
