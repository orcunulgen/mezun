package com.orcun.mezun.model.post;



import com.orcun.mezun.model.PostHistory;

public class PostTypeEvent implements Post{

	private EventPostHistory eventPostHistory;
	
	
	public PostTypeEvent(){
		if(this.eventPostHistory==null){
			eventPostHistory=new EventPostHistory();
		}
	}


	public EventPostHistory getEventPostHistory() {
		return eventPostHistory;
	}


	public void setEventPostHistory(EventPostHistory eventPostHistory) {
		this.eventPostHistory = eventPostHistory;
	}


	public PhotoPostHistory getPhotoPost() {
		return null;
	}

	public PostHistory getTextTypePost() {
		
		return eventPostHistory.getPostHistory();
	}


	public EventPostHistory getEventPost() {
		return getEventPostHistory();
	}


	public AnnouncementPostHistory getAnnouncementPost() {
		return null;
	}

}
