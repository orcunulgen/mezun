package com.orcun.mezun.dao.user;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.ChatGroup;
import com.orcun.mezun.model.ChatList;
import com.orcun.mezun.model.ChatPerson;
import com.orcun.mezun.model.PostHistory;
import com.orcun.mezun.model.ShareList;
import com.orcun.mezun.model.User;


@Repository
@Transactional
public class AllPostDAO{
	
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

	public PostHistory savePostHistory(PostHistory postHistory) {
		Long savedPostHistory=(Long) getSession().save(postHistory);
		postHistory.setId(savedPostHistory);
		return postHistory;
	}
	
	public void deletePostHistory(PostHistory postHistory){
		getSession().delete(postHistory);
	}

	public void saveShareList(List<ShareList> shareList) {
	
		for(int i=0;i<shareList.size();i++){
			getSession().save(shareList.get(i));
		}
		
	}

	public ChatList findChatListByUser(User user) {

		return (ChatList) getSession().createCriteria(ChatList.class)
				.add(Restrictions.eq("user", user)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<PostHistory> allTextTypePosts(User loggedUser) {
		List<PostHistory> postHistories=new ArrayList<PostHistory>();
		ChatList chatList=findChatListByUser(loggedUser);
		
		if (chatList != null) {
			List<ChatGroup> chatGroupList = chatList.getChatGroups();

			if (chatGroupList.size() != 0) {
				for (int i = 0; i < chatGroupList.size();i++) {
					List<ChatPerson> chatPersonList=chatGroupList.get(i).getChatPersons();
					
					for(int j=0;j<chatPersonList.size();j++){
						 postHistories.addAll(getSession().createCriteria(PostHistory.class)
						.add(Restrictions.eq("user", chatPersonList.get(j).getUser())).list());
					}
				}
			}

		}
		
		Collections.sort(postHistories, new Comparator<PostHistory>() {
		    public int compare(PostHistory p1, PostHistory p2) {
		        return p2.getPublishedDate().compareTo(p1.getPublishedDate());
		    }
		});
		
		return postHistories;
		/*SQLQuery query = getSession().createSQLQuery(
				"SELECT {ph.*} FROM post_history ph JOIN share_list sl ON ph.id=sl.post_history_id " 
						+" JOIN user u ON sl.user_tcno=u.tcno WHERE "
						+ " sl.user_tcno=:tcno "
						+ " GROUP BY ph.id ORDER BY ph.published_date DESC");
		
		query.setParameter("tcno", loggedUser.getTcno());
		query.addEntity("ph", PostHistory.class);
		return query.list();*/
		
		

	}
}
