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

import com.orcun.mezun.model.Certification;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.CertificationService;

@ManagedBean
@ViewScoped
public class CertificationView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private Certification certification;

	private List<Certification> certifications = new ArrayList<Certification>();

	private Certification selectedCertification;

	@ManagedProperty(value = "#{certificationService}")
	private CertificationService certificationService;

	@PostConstruct
	public void init() {
		certification = new Certification();
		certifications = certificationService.allCertification(getLoggedUser());
	}

	public void initSelectedCertification(Certification selectedCertification) {
		this.selectedCertification = selectedCertification;
	}

	public void deleteSelectedCertification(Certification selectedCertification) throws IOException {
		initSelectedCertification(selectedCertification);
		try {
			getCertificationService().deleteCertification(
					getSelectedCertification());
			
			FacesContext.getCurrentInstance()
			.getExternalContext()
			.redirect(
					"certification.xhtml?user="
							+ getLoggedUser().getTcno());

		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	public Certification getCertification() {
		return certification;
	}

	public void setCertification(Certification certification) {
		this.certification = certification;
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

	public CertificationService getCertificationService() {
		return certificationService;
	}

	public void setCertificationService(
			CertificationService certificationService) {
		this.certificationService = certificationService;
	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public void checkURL() throws IOException {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String userParameter = request.getParameter("user");

		if (userParameter == null || userParameter.equals("")) {

			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"personal_info.xhtml?user="
									+ getLoggedUser().getTcno());

		} else if (!userParameter.equals(getLoggedUser().getTcno().toString())) {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"personal_info.xhtml?user="
									+ getLoggedUser().getTcno());

		}
	}

	public void addCertification() throws IOException {
		try {

			Date registeredDate = new Date();

			int differenceStartRegister = Days.daysBetween(
					new DateTime(getCertification().getCertificateDate()),
					new DateTime(registeredDate)).getDays();

			if (differenceStartRegister > 0) {

				getCertification().setUser(getLoggedUser());
				getCertificationService().addCertification(getCertification());

				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"certification.xhtml?user="
										+ getLoggedUser().getTcno());

			} else {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Sertifikanın alındığı tarih geçmiş zamana ait olmalıdır.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}

	public void updateCertification() throws IOException {
		try {

			Date registeredDate = new Date();

			int differenceStartRegister = Days.daysBetween(
					new DateTime(getSelectedCertification()
							.getCertificateDate()),
					new DateTime(registeredDate)).getDays();

			if (differenceStartRegister > 0) {

				getCertificationService().updateCertification(
						getSelectedCertification());

				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"certification.xhtml?user="
										+ getLoggedUser().getTcno());

			} else {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Sertifikanın alındığı tarih geçmiş zamana ait olmalıdır.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}

}
