package com.orcun.mezun.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
