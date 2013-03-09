package com.orcun.mezun.dao.admin;



import java.util.List;

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
public class AlumniDAO{
	
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
	
	public void updateAlumni(User alumni){
		getSession().update(alumni);
	}

	@SuppressWarnings("unchecked")
	public List<Country> allCountries() {
		return getSession().createCriteria(Country.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<City> allCities(Country country) {
		return getSession().createCriteria(City.class).add(Restrictions.eq("country", country)).list();
	}

	@SuppressWarnings("unchecked")
	public List<User> allAlumnis(List<Role> roles) {
		
		return getSession().createCriteria(User.class)
		        .createAlias("roles", "row")
		        .add(Restrictions.conjunction()
		            .add(Restrictions.eq("row.role", roles.get(0).getRole())))
		        .list();
		
//		return getSession().createCriteria(User.class)
	//			.add(Restrictions.eq("roles",roles )).list();
	}
	
	public void deleteAlumni(User selectedAlumni) {
		
		getSession().delete(selectedAlumni);
	}
	
	
}
