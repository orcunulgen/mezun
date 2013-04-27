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
						"SELECT {e.*} FROM education_info e JOIN user u ON e.user_tcno=u.tcno WHERE "
								+ "u.name=:name OR "
								+ "u.surname=:surname OR "
								+ "u.email=:email OR "
								+ "e.start_year=:start_year OR "
								+ "e.end_year=:end_year "
								+ "GROUP BY e.user_tcno");
		query.setParameter("name", searchCriteria.getSearchByName());
		query.setParameter("surname", searchCriteria.getSearchBySurname());
		query.setParameter("email", searchCriteria.getSearchByEmail());
		query.setParameter("start_year",
				searchCriteria.getSearchByEduStartYear());
		query.setParameter("end_year", searchCriteria.getSearchByEduEndYear());
		query.addEntity("e",EducationInfo.class);
		//query.addEntity("e.user",User.class);
		//query.addJoin("u","e.user");
		//query.setResultTransformer(Transformers.aliasToBean(SearchChatPersonDTO.class));

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
