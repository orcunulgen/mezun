package com.orcun.mezun.dao.admin;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.AdminBasicInfo;
import com.orcun.mezun.model.User;

@Repository
@Transactional
public class InitAdminBasicInfoDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {

		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return getSessionFactory().getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public List<AdminBasicInfo> areThereAdminBasicInfo(User loggedUser) {
		return getSession().createCriteria(AdminBasicInfo.class)
				.add(Restrictions.eq("user", loggedUser)).list();

	}


	public boolean saveInitAdminBasicInfo(AdminBasicInfo adminBasicInfo) {

		AdminBasicInfo savedInfo = (AdminBasicInfo) getSession().save(
				adminBasicInfo);

		if (savedInfo != null) {
			return true;

		} else {
			getSession().getTransaction().rollback();
			return false;

		}

	}
}
