package com.orcun.mezun.model.post;

import com.orcun.mezun.model.Announcement;
import com.orcun.mezun.model.PostHistory;

public class AnnouncementPostHistory{

	private Announcement announcement;
	private PostHistory postHistory;
	

	public Announcement getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}

	public PostHistory getPostHistory() {
		return postHistory;
	}

	public void setPostHistory(PostHistory postHistory) {
		this.postHistory = postHistory;
	}
	
	
	
}
