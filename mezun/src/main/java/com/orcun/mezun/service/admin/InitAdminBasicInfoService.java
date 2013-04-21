package com.orcun.mezun.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.admin.InitAdminBasicInfoDAO;
import com.orcun.mezun.model.AdminBasicInfo;
import com.orcun.mezun.model.User;

@Service
public class InitAdminBasicInfoService {

	@Autowired
	private InitAdminBasicInfoDAO initAdminBasicInfoDAO;

	public InitAdminBasicInfoDAO getInitAdminBasicInfoDAO() {
		return initAdminBasicInfoDAO;
	}

	public void setInitAdminBasicInfoDAO(
			InitAdminBasicInfoDAO initAdminBasicInfoDAO) {
		this.initAdminBasicInfoDAO = initAdminBasicInfoDAO;
	}

	public boolean areThereAdminBasicInfo(User loggedUser) {
		if (getInitAdminBasicInfoDAO().areThereAdminBasicInfo(loggedUser)
				.size() == 0) {
			return false;
		} else {
			return true;
		}

	}

	public boolean IsValidInitAdminBasicInfo(User loggedUser) {

		if (areThereAdminBasicInfo(loggedUser)){
			return true;

		} else {
			return false;
		}
	}

	public boolean saveInitAdminBasicInfo(AdminBasicInfo adminBasicInfo) {

		if (getInitAdminBasicInfoDAO().saveInitAdminBasicInfo(adminBasicInfo)) {
			return true;
		} else {
			return false;
		}
	}
}
