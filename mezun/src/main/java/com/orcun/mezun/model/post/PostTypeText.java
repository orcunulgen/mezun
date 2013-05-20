package com.orcun.mezun.model.post;

import com.orcun.mezun.model.PostHistory;

public class PostTypeText implements Post{

	private PostHistory postHistory;
	
	public PostTypeText(){
		if(this.postHistory==null){
			postHistory=new PostHistory();
		}
	}
	
	public PostHistory getPostHistory() {
		return postHistory;
	}

	public void setPostHistory(PostHistory postHistory) {
		this.postHistory = postHistory;
	}

	public PhotoPostHistory getPhotoPost() {
		return null;
	}

	public PostHistory getTextTypePost() {
		return getPostHistory();
	}

	public EventPostHistory getEventPost() {
		return null;
	}

	public AnnouncementPostHistory getAnnouncementPost() {
		return null;
	}

}
