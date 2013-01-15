package com.orcun.mezun.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.CertificationDAO;
import com.orcun.mezun.model.Certification;
import com.orcun.mezun.model.User;

@Service
public class CertificationService {
	
	@Autowired
	private CertificationDAO certificationDAO;


	
	public CertificationDAO getCertificationDAO() {
		return certificationDAO;
	}

	public void setCertificationDAO(CertificationDAO certificationDAO) {
		this.certificationDAO = certificationDAO;
	}

	public void updateCertification(Certification certification) {
		getCertificationDAO().updateCertification(certification);
	}

	public void addCertification(Certification certification) {
		getCertificationDAO().addCertification(certification);
	}

	public List<Certification> allCertification(User loggedUser) {
		return getCertificationDAO().allCertification(loggedUser);
	}

	public void deleteCertification(Certification selectedCertification) {
		getCertificationDAO().deleteCertification(selectedCertification);
		
	}

}
