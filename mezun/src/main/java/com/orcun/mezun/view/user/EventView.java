package com.orcun.mezun.view.user;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.Event;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.EventService;

@ManagedBean
@ViewScoped
public class EventView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;
	
	private Event event;
	
	private List<Event> events = new ArrayList<Event>();
	private Event selectedEvent;

	@ManagedProperty(value = "#{eventService}")
	private EventService eventService;
	
	
	@PostConstruct
	public void init() {
		

		events=eventService.allEvent(getLoggedUser());
		
		if (event == null) {
			event = new Event();

		}
	}
	
	public void initSelectedEvent(Event selectedEvent){
		this.selectedEvent=selectedEvent;
	}
	
	public void deleteSelectedEvent(Event selectedEvent) throws IOException {
		initSelectedEvent(selectedEvent);
		try {
			getEventService().deleteEvent(
					getSelectedEvent());
			
			FacesContext.getCurrentInstance()
			.getExternalContext()
			.redirect(
					"event.xhtml?user="
							+ getLoggedUser().getTcno());

		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
	
	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Event getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(Event selectedEvent) {
		this.selectedEvent = selectedEvent;
	}

	public EventService getEventService() {
		return eventService;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	public void checkURL() throws IOException{
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String userParameter=request.getParameter("user");
		
		if(userParameter==null || userParameter.equals("")){
			
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("personal_info.xhtml?user="+getLoggedUser().getTcno());
			
		}else if(!userParameter.equals(getLoggedUser().getTcno().toString())){
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("personal_info.xhtml?user="+getLoggedUser().getTcno());
			
		}
	}
	
	public void addEvent() throws IOException {
		try {

			int differenceStartEnd = Days.daysBetween(
					new DateTime(getEvent().getStartDate()),
					new DateTime(getEvent().getEndDate())).getDays();

			Date registeredDate = new Date();

			int differenceStartRegister = Days.daysBetween(
					new DateTime(getEvent().getStartDate()),
					new DateTime(registeredDate)).getDays();

			if (differenceStartEnd > 0 && differenceStartRegister <= 0) {

				getEvent().setUser(getLoggedUser());
				getEvent().setRegisteredDate(registeredDate);
				getEventService().addEvent(getEvent());

				/*
				 * FacesMessage fm = new
				 * FacesMessage(FacesMessage.SEVERITY_ERROR,
				 * "Kaydınız tamamlandı.", "");
				 * 
				 * FacesContext.getCurrentInstance().addMessage(null, fm);
				 */

				/*
				 * FacesContext.getCurrentInstance().getExternalContext()
				 * .getFlash().setKeepMessages(true);
				 */
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"event.xhtml?user="
										+ getLoggedUser().getTcno());

			} else {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Başlangıç tarihi gelecek zamana ait olmalıdır ve başlangıç tarihi bitiş tarihinden ilerde olamaz.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}
	
	public void updateEvent() throws IOException {
		try {

			int differenceStartEnd = Days.daysBetween(
					new DateTime(getSelectedEvent().getStartDate()),
					new DateTime(getSelectedEvent().getEndDate())).getDays();

			Date registeredDate = new Date();

			int differenceStartRegister = Days.daysBetween(
					new DateTime(getSelectedEvent().getStartDate()),
					new DateTime(registeredDate)).getDays();

			if (differenceStartEnd > 0 && differenceStartRegister <= 0) {

				getEventService().updateEvent(getSelectedEvent());

				/*
				 * FacesMessage fm = new
				 * FacesMessage(FacesMessage.SEVERITY_ERROR,
				 * "Kaydınız tamamlandı.", "");
				 * 
				 * FacesContext.getCurrentInstance().addMessage(null, fm);
				 */

				/*
				 * FacesContext.getCurrentInstance().getExternalContext()
				 * .getFlash().setKeepMessages(true);
				 */
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"event.xhtml?user="
										+ getLoggedUser().getTcno());

			} else {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Başlangıç tarihi gelecek zamana ait olmalıdır ve başlangıç tarihi bitiş tarihinden ilerde olamaz.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}

}
