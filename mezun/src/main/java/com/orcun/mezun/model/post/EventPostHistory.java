package com.orcun.mezun.model.post;

import com.orcun.mezun.model.Event;
import com.orcun.mezun.model.PostHistory;

public class EventPostHistory{


	private Event event;
	private PostHistory postHistory;
	

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public PostHistory getPostHistory() {
		return postHistory;
	}

	public void setPostHistory(PostHistory postHistory) {
		this.postHistory = postHistory;
	}
	
	
	
}
