package com.orcun.mezun.dao.user;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.Department;
import com.orcun.mezun.model.EducationInfo;
import com.orcun.mezun.model.EducationLevel;
import com.orcun.mezun.model.Faculty;
import com.orcun.mezun.model.University;
import com.orcun.mezun.model.User;


@Repository
@Transactional
public class EducationInfoDAO{
	
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
	
	public void addEducationInfo(EducationInfo educationInfo) {
		getSession().save(educationInfo);
	}	
	
	public void updateEducationInfo(EducationInfo educationInfo){
		getSession().update(educationInfo);
	}

	@SuppressWarnings("unchecked")
	public List<University> allUniversities() {
		return getSession().createCriteria(University.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<Faculty> allFaculties(University university) {
		return getSession().createCriteria(Faculty.class).add(Restrictions.eq("university", university)).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Department> allDepartments(Faculty faculty) {
		return getSession().createCriteria(Department.class).add(Restrictions.eq("faculty",faculty)).list();
	}

	@SuppressWarnings("unchecked")
	public List<EducationLevel> allEducationLevels() {
		return getSession().createCriteria(EducationLevel.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<EducationInfo> allEducations(User loggedUser) {
		return getSession().createCriteria(EducationInfo.class)
				.add(Restrictions.eq("user", loggedUser)).list();
	}
	
	public void deleteEducationInfo(EducationInfo selectedEducationInfo) {
		getSession().createSQLQuery("delete from education_info where id = :id") 
	    .setParameter("id", selectedEducationInfo.getId())
	    .executeUpdate();
	}
	
	
}
