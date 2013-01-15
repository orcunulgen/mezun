package com.orcun.mezun.dao.user;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.City;
import com.orcun.mezun.model.Country;
import com.orcun.mezun.model.JobExperience;
import com.orcun.mezun.model.Position;
import com.orcun.mezun.model.Sector;
import com.orcun.mezun.model.User;
import com.orcun.mezun.model.WorkingType;


@Repository
@Transactional
public class JobExperienceDAO{
	
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
	
	public void addJobExperience(JobExperience jobExperience) {
		getSession().save(jobExperience);
	}	
	
	public void updateJobExperience(JobExperience jobExperience){
		getSession().update(jobExperience);
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
	public List<Position> allPositions() {
		return getSession().createCriteria(Position.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<Sector> allSectors() {
		return getSession().createCriteria(Sector.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<WorkingType> allWorkingTypes() {
		return getSession().createCriteria(WorkingType.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<JobExperience> allJobExperience(User loggedUser) {
		return getSession().createCriteria(JobExperience.class)
				.add(Restrictions.eq("user", loggedUser)).list();
	}
	
	public void deleteJobExperience(JobExperience selectedJobExperience) {
		getSession().createSQLQuery("delete from job_experience where id = :id") 
	    .setParameter("id", selectedJobExperience.getId())
	    .executeUpdate();
	}
	
	
}
