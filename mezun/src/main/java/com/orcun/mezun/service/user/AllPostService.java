package com.orcun.mezun.service.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.AllPostDAO;
import com.orcun.mezun.model.ChatGroup;
import com.orcun.mezun.model.ChatList;
import com.orcun.mezun.model.ChatPerson;
import com.orcun.mezun.model.PostHistory;
import com.orcun.mezun.model.ShareList;
import com.orcun.mezun.model.User;
import com.orcun.mezun.model.enums.ContentType;
import com.orcun.mezun.model.post.Post;
import com.orcun.mezun.model.post.PostTypeAnnouncement;
import com.orcun.mezun.model.post.PostTypeEvent;
import com.orcun.mezun.model.post.PostTypePhoto;
import com.orcun.mezun.model.post.PostTypeText;

@Service
public class AllPostService {

	@Autowired
	private AllPostDAO allPostDAO;

	public AllPostDAO getAllPostDAO() {
		return allPostDAO;
	}

	public void setAllPostDAO(AllPostDAO allPostDAO) {
		this.allPostDAO = allPostDAO;
	}

	public PostHistory savePostHistory(PostHistory postHistory) {
		return getAllPostDAO().savePostHistory(postHistory);
	}

	public void deletePostHistory(PostHistory postHistory) {
		getAllPostDAO().deletePostHistory(postHistory);
	}

	public void saveShareList(List<ShareList> shareList) {
		getAllPostDAO().saveShareList(shareList);
	}

	public List<Post> allPosts(User loggedUser) {

		List<PostHistory> postHistories = new ArrayList<PostHistory>();
		ChatList chatList = getAllPostDAO().findChatListByUser(loggedUser);

		if (chatList != null) {
			List<ChatGroup> chatGroupList = chatList.getChatGroups();

			if (chatGroupList.size() != 0) {
				for (int i = 0; i < chatGroupList.size(); i++) {
					List<ChatPerson> chatPersonList = chatGroupList.get(i)
							.getChatPersons();

					for (int j = 0; j < chatPersonList.size(); j++) {
						postHistories.addAll(getAllPostDAO().postHistoryList(
								chatPersonList.get(j).getUser()));
					}
				}
			}

		}
		
		Collections.sort(postHistories, new Comparator<PostHistory>() {
			public int compare(PostHistory p1, PostHistory p2) {
				return p2.getPublishedDate().compareTo(p1.getPublishedDate());
			}
		});

		List<Post> posts = new ArrayList<Post>();

		for (int i = 0; i < postHistories.size(); i++) {
			PostHistory currentPostHistory = postHistories.get(i);

			if (currentPostHistory.getContentType().equals(ContentType.PHOTO)) {

				PostTypePhoto temp = new PostTypePhoto();
				
				temp.getPhotoPostHistory().setPostHistory(currentPostHistory);
				temp.getPhotoPostHistory().setPhoto(getAllPostDAO().findPhotoById(temp.getPhotoPostHistory().getPostHistory().getContentID()));

				posts.add(temp);

			} else if (currentPostHistory.getContentType().equals(
					ContentType.EVENT)) {
				PostTypeEvent temp=new PostTypeEvent();
				temp.getEventPostHistory().setPostHistory(currentPostHistory);
				temp.getEventPostHistory().setEvent(getAllPostDAO().findEventById(temp.getEventPostHistory().getPostHistory().getContentID()));
				
				posts.add(temp);

			} else if (currentPostHistory.getContentType().equals(
					ContentType.ANNOUNCEMENT)) {
				PostTypeAnnouncement temp=new PostTypeAnnouncement();
				temp.getAnnouncementPostHistory().setPostHistory(currentPostHistory);
				temp.getAnnouncementPostHistory().setAnnouncement(getAllPostDAO().findAnnouncementById(temp.getAnnouncementPostHistory().getPostHistory().getContentID()));
				posts.add(temp);
			} else {
				PostTypeText temp=new PostTypeText();
				temp.setPostHistory(currentPostHistory);
				posts.add(temp);
			}

		}

		return posts;


	}

	public List<Post> myPosts(User loggedUser) {
		
		List<Post> myPosts = new ArrayList<Post>();
		List<PostHistory> postHistories=getAllPostDAO().postHistoryListOrderByDescDate(loggedUser);
		
		for (int i = 0; i < postHistories.size(); i++) {
			PostHistory currentPostHistory = postHistories.get(i);

			if (currentPostHistory.getContentType().equals(ContentType.PHOTO)) {

				PostTypePhoto temp = new PostTypePhoto();
				
				temp.getPhotoPostHistory().setPostHistory(currentPostHistory);
				temp.getPhotoPostHistory().setPhoto(getAllPostDAO().findPhotoById(temp.getPhotoPostHistory().getPostHistory().getContentID()));

				myPosts.add(temp);

			} else if (currentPostHistory.getContentType().equals(
					ContentType.EVENT)) {
				PostTypeEvent temp=new PostTypeEvent();
				temp.getEventPostHistory().setPostHistory(currentPostHistory);
				temp.getEventPostHistory().setEvent(getAllPostDAO().findEventById(temp.getEventPostHistory().getPostHistory().getContentID()));
				
				myPosts.add(temp);

			} else if (currentPostHistory.getContentType().equals(
					ContentType.ANNOUNCEMENT)) {

				PostTypeAnnouncement temp=new PostTypeAnnouncement();
				temp.getAnnouncementPostHistory().setPostHistory(currentPostHistory);
				temp.getAnnouncementPostHistory().setAnnouncement(getAllPostDAO().findAnnouncementById(temp.getAnnouncementPostHistory().getPostHistory().getContentID()));
				myPosts.add(temp);
			} else {
				PostTypeText temp=new PostTypeText();
				temp.setPostHistory(currentPostHistory);
				myPosts.add(temp);
			}

			
		}
		return myPosts;

	}

	public List<Post> dailyPostList(Date now,User loggedUser) {
		List<PostHistory> postHistories = new ArrayList<PostHistory>();
		ChatList chatList = getAllPostDAO().findChatListByUser(loggedUser);

		if (chatList != null) {
			List<ChatGroup> chatGroupList = chatList.getChatGroups();

			if (chatGroupList.size() != 0) {
				for (int i = 0; i < chatGroupList.size(); i++) {
					List<ChatPerson> chatPersonList = chatGroupList.get(i)
							.getChatPersons();

					for (int j = 0; j < chatPersonList.size(); j++) {
						postHistories.addAll(getAllPostDAO().dailyPostHistoryList(
								chatPersonList.get(j).getUser(),now));
					}
				}
			}

		}
		
		Collections.sort(postHistories, new Comparator<PostHistory>() {
			public int compare(PostHistory p1, PostHistory p2) {
				return p2.getPublishedDate().compareTo(p1.getPublishedDate());
			}
		});

		List<Post> posts = new ArrayList<Post>();

		for (int i = 0; i < postHistories.size(); i++) {
			PostHistory currentPostHistory = postHistories.get(i);

			if (currentPostHistory.getContentType().equals(ContentType.PHOTO)) {

				PostTypePhoto temp = new PostTypePhoto();
				
				temp.getPhotoPostHistory().setPostHistory(currentPostHistory);
				temp.getPhotoPostHistory().setPhoto(getAllPostDAO().findPhotoById(temp.getPhotoPostHistory().getPostHistory().getContentID()));

				posts.add(temp);

			} else if (currentPostHistory.getContentType().equals(
					ContentType.EVENT)) {
				PostTypeEvent temp=new PostTypeEvent();
				temp.getEventPostHistory().setPostHistory(currentPostHistory);
				temp.getEventPostHistory().setEvent(getAllPostDAO().findEventById(temp.getEventPostHistory().getPostHistory().getContentID()));
				
				posts.add(temp);

			} else if (currentPostHistory.getContentType().equals(
					ContentType.ANNOUNCEMENT)) {
				PostTypeAnnouncement temp=new PostTypeAnnouncement();
				temp.getAnnouncementPostHistory().setPostHistory(currentPostHistory);
				temp.getAnnouncementPostHistory().setAnnouncement(getAllPostDAO().findAnnouncementById(temp.getAnnouncementPostHistory().getPostHistory().getContentID()));
				posts.add(temp);
			} else {
				PostTypeText temp=new PostTypeText();
				temp.setPostHistory(currentPostHistory);
				posts.add(temp);
			}

		}

		return posts;
	}
	
	public List<User> allActiveUser(){
		return getAllPostDAO().allActiveUser();
	}

}
