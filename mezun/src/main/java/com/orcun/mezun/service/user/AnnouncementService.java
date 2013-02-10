package com.orcun.mezun.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.AnnouncementDAO;
import com.orcun.mezun.model.Announcement;
import com.orcun.mezun.model.AnnouncementType;
import com.orcun.mezun.model.User;

@Service
public class AnnouncementService {
	
	@Autowired
	private AnnouncementDAO announcementDAO;

	public AnnouncementDAO getAnnouncementDAO() {
		return announcementDAO;
	}

	public void setAnnouncementDAO(AnnouncementDAO announcementDAO) {
		this.announcementDAO = announcementDAO;
	}

	public void updateAnnouncement(Announcement announcement) {
		getAnnouncementDAO().updateAnnouncement(announcement);
	}

	public void addAnnouncement(Announcement announcement) {
		getAnnouncementDAO().addAnnouncement(announcement);
	}

	public List<AnnouncementType> allAnnouncementTypes() {
		return getAnnouncementDAO().allAnnouncementTypes();
	}

	public List<Announcement> allAnnouncement(User loggedUser) {
		return getAnnouncementDAO().allAnnouncement(loggedUser);
	}
	
	public void deleteAnnouncement(Announcement selectedAnnouncement) {
		getAnnouncementDAO().deleteAnnouncement(selectedAnnouncement);
		
	}
}
