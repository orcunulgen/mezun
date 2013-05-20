package com.orcun.mezun.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.EventDAO;
import com.orcun.mezun.model.Event;
import com.orcun.mezun.model.User;

@Service
public class EventService {
	
	@Autowired
	private EventDAO eventDAO;

	public EventDAO getEventDAO() {
		return eventDAO;
	}

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	public void updateEvent(Event event) {
		getEventDAO().updateEvent(event);
	}

	public Long addEvent(Event event) {
		return getEventDAO().addEvent(event);
	}

	public List<Event> allEvent(User loggedUser) {
		return getEventDAO().allEvent(loggedUser);
	}
	
	public void deleteEvent(Event selectedEvent) {
		getEventDAO().deleteEvent(selectedEvent);
		
	}
}
