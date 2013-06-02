package com.orcun.mezun.dao.admin;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.Role;
import com.orcun.mezun.model.User;

@Repository
@Transactional
public class InactiveUserDAO {

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
	public List<User> inactiveUserList(Date now) {

		DateTime currentTime = new DateTime(now.getTime());
		currentTime = currentTime.minusDays(1);
		Date yesterday = new Date(currentTime.getMillis());

		return getSession().createCriteria(User.class)
				.add(Restrictions.eq("activation", true))
				.add(Restrictions.eq("enabled", false))
				.add(Restrictions.ge("registeredDate", yesterday))
				.add(Restrictions.le("registeredDate", now)).list();
	}

	public User findUserByTcno(User user) {
		return (User) getSession().createCriteria(User.class).add(Restrictions.eq("tcno", user.getTcno())).uniqueResult();
	}

	public void sendAdminForActivation(User user) {
		getSession().update(user);
	}

	@SuppressWarnings("unchecked")
	public List<User> getInactiveUserList() {
		
		Criteria cr = getSession().createCriteria(User.class);
		cr.add(Restrictions.eq("activation", true));
		cr.add(Restrictions.eq("enabled",false));
		cr.addOrder(Order.desc("registeredDate"));
		
		return cr.list();
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllAdmin(List<Role> roles) {
		
		return getSession().createCriteria(User.class)
		        .createAlias("roles", "row")
		        .add(Restrictions.conjunction()
		            .add(Restrictions.eq("row.role", roles.get(0).getRole())))
		        .list();
	}
}
