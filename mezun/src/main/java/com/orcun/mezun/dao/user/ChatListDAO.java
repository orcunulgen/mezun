package com.orcun.mezun.dao.user;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.ChatGroup;
import com.orcun.mezun.model.ChatList;
import com.orcun.mezun.model.ChatPerson;
import com.orcun.mezun.model.User;

@Repository
@Transactional
public class ChatListDAO {

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

	public ChatList findChatListByUser(User user) {

		return (ChatList) getSession().createCriteria(ChatList.class)
				.add(Restrictions.eq("user", user)).uniqueResult();
	}

	public void updateChatList(ChatList chatList) {
		getSession().update(chatList);

	}

	public void addChatList(ChatList chatList) {
		getSession().save(chatList);
	}

	public void addChatGroup(ChatGroup chatGroup) {
		getSession().save(chatGroup);
	}

	public void addChatPerson(ChatPerson newChatPerson) {
		getSession().save(newChatPerson);
	}

}
