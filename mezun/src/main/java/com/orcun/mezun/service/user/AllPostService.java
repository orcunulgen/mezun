package com.orcun.mezun.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.AllPostDAO;
import com.orcun.mezun.model.PostHistory;
import com.orcun.mezun.model.ShareList;
import com.orcun.mezun.model.User;

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
	
	public PostHistory savePostHistory(PostHistory postHistory){
		return getAllPostDAO().savePostHistory(postHistory);
	}
	
	public void deletePostHistory(PostHistory postHistory){
		getAllPostDAO().deletePostHistory(postHistory);
	}

	public void saveShareList(List<ShareList> shareList) {
		getAllPostDAO().saveShareList(shareList);
	}

	public List<PostHistory> allTextTypePosts(User loggedUser) {
		return getAllPostDAO().allTextTypePosts(loggedUser);
	}

}
