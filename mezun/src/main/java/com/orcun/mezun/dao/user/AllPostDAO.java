package com.orcun.mezun.dao.user;

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

import com.orcun.mezun.model.Announcement;
import com.orcun.mezun.model.ChatList;
import com.orcun.mezun.model.Event;
import com.orcun.mezun.model.Photo;
import com.orcun.mezun.model.PostHistory;
import com.orcun.mezun.model.ShareList;
import com.orcun.mezun.model.User;

@Repository
@Transactional
public class AllPostDAO {

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

	public PostHistory savePostHistory(PostHistory postHistory) {
		Long savedPostHistory = (Long) getSession().save(postHistory);
		postHistory.setId(savedPostHistory);
		return postHistory;
	}

	public void deletePostHistory(PostHistory postHistory) {
		getSession().delete(postHistory);
	}

	public void saveShareList(List<ShareList> shareList) {

		for (int i = 0; i < shareList.size(); i++) {
			getSession().save(shareList.get(i));
		}

	}

	public ChatList findChatListByUser(User user) {

		return (ChatList) getSession().createCriteria(ChatList.class)
				.add(Restrictions.eq("user", user)).uniqueResult();
	}

	public Photo findPhotoById(Long id) {

		return (Photo) getSession().createCriteria(Photo.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	public Event findEventById(Long id) {

		return (Event) getSession().createCriteria(Event.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	public Announcement findAnnouncementById(Long id) {

		return (Announcement) getSession().createCriteria(Announcement.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<PostHistory> postHistoryList(User user) {
		Criteria cr=getSession().createCriteria(PostHistory.class)
				.add(Restrictions.eq("user", user));
		cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return cr.list();
	}

	@SuppressWarnings("unchecked")
	public List<PostHistory> dailyPostHistoryList(User user, Date now) {

		DateTime currentTime = new DateTime(now.getTime());
		currentTime = currentTime.minusDays(1);
		Date yesterday = new Date(currentTime.getMillis());

		return getSession().createCriteria(PostHistory.class)
				.add(Restrictions.eq("user", user))
				.add(Restrictions.ge("publishedDate", yesterday))
				.add(Restrictions.le("publishedDate", now)).list();

	}

	@SuppressWarnings("unchecked")
	public List<PostHistory> postHistoryListOrderByDescDate(User user) {

		Criteria cr = getSession().createCriteria(PostHistory.class);
		cr.add(Restrictions.eq("user", user));
		cr.addOrder(Order.desc("publishedDate"));
		cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return cr.list();
	}

	@SuppressWarnings("unchecked")
	public List<User> allActiveUser() {
		return getSession().createCriteria(User.class)
				.add(Restrictions.eq("sendMail", true))
				.add(Restrictions.eq("activation", true))
				.add(Restrictions.eq("enabled", true)).list();

	}

	/*
	 * @SuppressWarnings("unchecked") public List<Post> allTextTypePosts(User
	 * loggedUser) { List<PostHistory> postHistories = new
	 * ArrayList<PostHistory>(); ChatList chatList =
	 * findChatListByUser(loggedUser);
	 * 
	 * if (chatList != null) { List<ChatGroup> chatGroupList =
	 * chatList.getChatGroups();
	 * 
	 * if (chatGroupList.size() != 0) { for (int i = 0; i <
	 * chatGroupList.size(); i++) { List<ChatPerson> chatPersonList =
	 * chatGroupList.get(i) .getChatPersons();
	 * 
	 * for (int j = 0; j < chatPersonList.size(); j++) {
	 * postHistories.addAll(getSession() .createCriteria(PostHistory.class)
	 * .add(Restrictions.eq("user", chatPersonList .get(j).getUser())).list());
	 * } } }
	 * 
	 * }
	 * 
	 * Collections.sort(postHistories, new Comparator<PostHistory>() { public
	 * int compare(PostHistory p1, PostHistory p2) { return
	 * p2.getPublishedDate().compareTo(p1.getPublishedDate()); } });
	 * 
	 * List<Post> posts = new ArrayList<Post>();
	 * 
	 * for (int i = 0; i < postHistories.size(); i++) { PostHistory
	 * currentPostHistory = postHistories.get(i);
	 * 
	 * if (currentPostHistory.getContentType().equals(ContentType.PHOTO)) {
	 * 
	 * PhotoPostHistory temp = new PhotoPostHistory();
	 * temp.setContentType(currentPostHistory.getContentType()); try {
	 * temp.setDescription(currentPostHistory.getDescription()); } catch
	 * (UnsupportedEncodingException e) { e.printStackTrace(); }
	 * temp.setId(currentPostHistory.getId());
	 * temp.setPublishedDate(currentPostHistory.getPublishedDate());
	 * temp.setUser(currentPostHistory.getUser());
	 * temp.setContentID(currentPostHistory.getContentID());
	 * temp.setPhoto(findPhotoById(temp.getContentID()));
	 * 
	 * // postHistories.set(i, temp); posts.add(temp);
	 * 
	 * } else if (currentPostHistory.getContentType().equals(
	 * ContentType.EVENT)) {
	 * 
	 * } else if (currentPostHistory.getContentType().equals(
	 * ContentType.ANNOUNCEMENT)) {
	 * 
	 * } else { posts.add(currentPostHistory); }
	 * 
	 * }
	 * 
	 * return posts;
	 */
	/*
	 * SQLQuery query = getSession().createSQLQuery(
	 * "SELECT {ph.*} FROM post_history ph JOIN share_list sl ON ph.id=sl.post_history_id "
	 * +" JOIN user u ON sl.user_tcno=u.tcno WHERE " + " sl.user_tcno=:tcno " +
	 * " GROUP BY ph.id ORDER BY ph.published_date DESC");
	 * 
	 * query.setParameter("tcno", loggedUser.getTcno()); query.addEntity("ph",
	 * PostHistory.class); return query.list();
	 */

	// }

	/*
	 * public List<Post> myTextTypePosts(User loggedUser) { Criteria cr =
	 * getSession().createCriteria(PostHistory.class);
	 * cr.add(Restrictions.eq("user", loggedUser));
	 * cr.addOrder(Order.desc("publishedDate"));
	 * 
	 * List<Post> myPosts = new ArrayList<Post>();
	 * 
	 * for (int i = 0; i < cr.list().size(); i++) { PostHistory
	 * currentPostHistory = (PostHistory) cr.list().get(i);
	 * 
	 * if (currentPostHistory.getContentType().equals(ContentType.PHOTO)) {
	 * 
	 * PhotoPostHistory temp = new PhotoPostHistory();
	 * temp.setContentType(currentPostHistory.getContentType()); try {
	 * temp.setDescription(currentPostHistory.getDescription()); } catch
	 * (UnsupportedEncodingException e) { e.printStackTrace(); }
	 * temp.setId(currentPostHistory.getId());
	 * temp.setPublishedDate(currentPostHistory.getPublishedDate());
	 * temp.setUser(currentPostHistory.getUser());
	 * temp.setContentID(currentPostHistory.getContentID());
	 * temp.setPhoto(findPhotoById(temp.getContentID()));
	 * 
	 * // postHistories.set(i, temp); myPosts.add(temp);
	 * 
	 * } else if (currentPostHistory.getContentType().equals(
	 * ContentType.EVENT)) {
	 * 
	 * } else if (currentPostHistory.getContentType().equals(
	 * ContentType.ANNOUNCEMENT)) {
	 * 
	 * } else { myPosts.add(currentPostHistory); }
	 * 
	 * 
	 * } return myPosts; }
	 */
}
