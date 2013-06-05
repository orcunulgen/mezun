package com.orcun.mezun.service.user;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.ProfileSettingsDAO;
import com.orcun.mezun.model.User;

@Service
public class ProfileSettingsService {

	@Autowired
	private ProfileSettingsDAO profileSettingsDAO;


	public ProfileSettingsDAO getProfileSettingsDAO() {
		return profileSettingsDAO;
	}


	public void setProfileSettingsDAO(ProfileSettingsDAO profileSettingsDAO) {
		this.profileSettingsDAO = profileSettingsDAO;
	}


	public List<User> getFollowerList(User loggedUser) {
		//return getProfileSettingsDAO().getFollowerList(loggedUser);
		return null;
	}

}
