package com.orcun.mezun.dao.user;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.Certification;
import com.orcun.mezun.model.User;


@Repository
@Transactional
public class CertificationDAO{
	
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
	
	public void addCertification(Certification certification) {
		getSession().save(certification);
	}	
	
	public void updateCertification(Certification certification){
		getSession().update(certification);
	}


	@SuppressWarnings("unchecked")
	public List<Certification> allCertification(User loggedUser) {
		return getSession().createCriteria(Certification.class)
				.add(Restrictions.eq("user", loggedUser)).list();
	}

	public void deleteCertification(Certification selectedCertification) {
		getSession().createSQLQuery("delete from certification where id = :id") 
	    .setParameter("id", selectedCertification.getId())
	    .executeUpdate();
	}
	
	
}
