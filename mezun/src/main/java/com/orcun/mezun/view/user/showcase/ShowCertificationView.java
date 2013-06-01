package com.orcun.mezun.view.user.showcase;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.Certification;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.admin.InactiveUserService;
import com.orcun.mezun.service.user.CertificationService;
import com.orcun.mezun.util.StringConvertUtil;

@ManagedBean
@ViewScoped
public class ShowCertificationView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private List<Certification> certifications = new ArrayList<Certification>();

	private Certification selectedCertification;

	private Boolean isThereFile;

	@ManagedProperty(value = "#{certificationService}")
	private CertificationService certificationService;
	
	@ManagedProperty(value = "#{inactiveUserService}")
	private InactiveUserService inactiveUserService;

	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		Long tcno = StringConvertUtil.stringToLong(request.getParameter("u"));

		if (tcno != null) {
			User tmp = new User();
			tmp.setTcno(tcno);

			tmp = getInactiveUserService().findUserByTcno(tmp);
			if (tmp != null) {
				this.loggedUser = tmp;
			}
		}

		// real loggedUser
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		User realLoggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();
		//

		if (getLoggedUser() == null || getLoggedUser().equals(realLoggedUser)) {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		certifications = certificationService.allCertification(getLoggedUser());
	}

	public void initSelectedCertification(Certification selectedCertification) {
		this.selectedCertification = selectedCertification;
		if (this.selectedCertification.getFilePath() != null) {
			setIsThereFile(true);
		} else {
			setIsThereFile(false);
		}
	}


	public List<Certification> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<Certification> certifications) {
		this.certifications = certifications;
	}

	public Certification getSelectedCertification() {
		return selectedCertification;
	}

	public void setSelectedCertification(Certification selectedCertification) {
		this.selectedCertification = selectedCertification;
	}

	public Boolean getIsThereFile() {
		return isThereFile;
	}

	public void setIsThereFile(Boolean isThereFile) {
		this.isThereFile = isThereFile;
	}

	public CertificationService getCertificationService() {
		return certificationService;
	}

	public void setCertificationService(
			CertificationService certificationService) {
		this.certificationService = certificationService;
	}

	public InactiveUserService getInactiveUserService() {
		return inactiveUserService;
	}

	public void setInactiveUserService(InactiveUserService inactiveUserService) {
		this.inactiveUserService = inactiveUserService;
	}

	public User getLoggedUser() {
		return loggedUser;
	}

}
