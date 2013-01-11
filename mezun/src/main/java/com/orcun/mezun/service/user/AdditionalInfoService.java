package com.orcun.mezun.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.AdditionalInfoDAO;
import com.orcun.mezun.model.AdditionalInfo;
import com.orcun.mezun.model.User;

@Service
public class AdditionalInfoService {
	
	@Autowired
	private AdditionalInfoDAO additionalInfoDAO;

	
	public AdditionalInfoDAO getAdditionalInfoDAO() {
		return additionalInfoDAO;
	}

	public void setAdditionalInfoDAO(AdditionalInfoDAO additionalInfoDAO) {
		this.additionalInfoDAO = additionalInfoDAO;
	}

	public AdditionalInfo findAdditionalInfoByUser(User user) {
		if (user != null) {
			return additionalInfoDAO.findAdditionalInfoByUser(user);
		} else {
			return null;
		}

	}

	public void updateAdditionalInfo(AdditionalInfo additionalInfo) {
		getAdditionalInfoDAO().updateAdditionalInfo(additionalInfo);
	}

	public void addAdditionalInfo(AdditionalInfo additionalInfo) {
		getAdditionalInfoDAO().addAdditionalInfo(additionalInfo);
	}

}
