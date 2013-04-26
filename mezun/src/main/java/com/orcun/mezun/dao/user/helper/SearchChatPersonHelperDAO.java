package com.orcun.mezun.dao.user.helper;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.EducationInfo;
import com.orcun.mezun.model.SearchCriteria;
import com.orcun.mezun.model.User;

@Repository
@Transactional
public class SearchChatPersonHelperDAO {

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
	public List<EducationInfo> searchChatPerson(SearchCriteria searchCriteria) {

		SQLQuery query = getSession()
				.createSQLQuery(
						"SELECT * FROM education_info,user WHERE education_info.user_tcno=user.tcno AND "
								+ "user.name=:name OR "
								+ "user.surname=:surname OR "
								+ "user.email=:email OR "
								+ "education_info.start_year=:start_year OR "
								+ "education_info.end_year=:end_year "
								+ "GROUP BY user.tcno");
		query.setParameter("name", searchCriteria.getSearchByName());
		query.setParameter("surname", searchCriteria.getSearchBySurname());
		query.setParameter("email", searchCriteria.getSearchByEmail());
		query.setParameter("start_year",
				searchCriteria.getSearchByEduStartYear());
		query.setParameter("end_year", searchCriteria.getSearchByEduEndYear());
		query.addEntity("education_info",EducationInfo.class);
		query.addEntity("education_info.user",User.class);
		query.addJoin("user","education_info.user");

		return query.list();

		/*
		 * Criteria criteria = getSession().createCriteria(EducationInfo.class,
		 * "educationInfo").createAlias("user", "user", Criteria.INNER_JOIN);
		 * 
		 * Disjunction or = Restrictions.disjunction();
		 * or.add(Restrictions.eq("user.name",
		 * searchCriteria.getSearchByName()));
		 * or.add(Restrictions.eq("user.surname"
		 * ,searchCriteria.getSearchBySurname()));
		 * or.add(Restrictions.eq("user.email",
		 * searchCriteria.getSearchByEmail()));
		 * or.add(Restrictions.eq("startYear"
		 * ,searchCriteria.getSearchByEduStartYear()));
		 * or.add(Restrictions.eq("endYear"
		 * ,searchCriteria.getSearchByEduEndYear()));
		 */

		// ProjectionList projectionList = Projections.projectionList();
		// projectionList.add(Projections.groupProperty("user"));
		// criteria.setProjection(projectionList);

		// return criteria.add(or).list();
	}
}
