package com.orcun.mezun.model.post;

import com.orcun.mezun.model.PostHistory;

public class PostTypeAnnouncement implements Post {

	private AnnouncementPostHistory announcementPostHistory;

	public PostTypeAnnouncement() {
		if (this.announcementPostHistory == null) {
			announcementPostHistory = new AnnouncementPostHistory();
		}
	}

	public AnnouncementPostHistory getAnnouncementPostHistory() {
		return announcementPostHistory;
	}

	public void setAnnouncementPostHistory(
			AnnouncementPostHistory announcementPostHistory) {
		this.announcementPostHistory = announcementPostHistory;
	}

	public PhotoPostHistory getPhotoPost() {
		return null;
	}

	public PostHistory getTextTypePost() {

		return announcementPostHistory.getPostHistory();
	}

	public EventPostHistory getEventPost() {
		return null;
	}

	public AnnouncementPostHistory getAnnouncementPost() {
		return getAnnouncementPostHistory();
	}

}
