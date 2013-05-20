package com.orcun.mezun.model.post;

import com.orcun.mezun.model.PostHistory;

public interface Post {

	public PhotoPostHistory getPhotoPost();
	public PostHistory getTextTypePost();
	public EventPostHistory getEventPost();
	public AnnouncementPostHistory getAnnouncementPost();
}
