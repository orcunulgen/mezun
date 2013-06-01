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
import javax.faces.context.Flash;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.commons.FileUploadService;
import com.orcun.mezun.model.Certification;
import com.orcun.mezun.model.User;
import com.orcun.mezun.model.enums.UploadedFileDirectory;
import com.orcun.mezun.service.user.CertificationService;

@ManagedBean
@ViewScoped
public class CertificationView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private Certification certification;

	private List<Certification> certifications = new ArrayList<Certification>();

	private Certification selectedCertification;

	private UploadedFile uploadedFile;

	private Boolean updateFile = true;

	private Boolean isThereFile;

	@ManagedProperty(value = "#{certificationService}")
	private CertificationService certificationService;

	@ManagedProperty(value = "#{fileUploadService}")
	private FileUploadService fileUploadService;

	@PostConstruct
	public void init() {
		certification = new Certification();
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

	public String deleteSelectedCertification(
			Certification selectedCertification) throws IOException {

		initSelectedCertification(selectedCertification);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		if (this.selectedCertification.getFilePath() != null) {
			if (getFileUploadService().deleteFile(
					UploadedFileDirectory.CERTIFICATES_PATH.getPath() + "/"
							+ selectedCertification.getFilePath())) {

				getCertificationService().deleteCertification(
						getSelectedCertification());

				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Sertifika başarıyla silindi.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);
			} else {
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Sertifika silinemedi.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);

			}

		} else {

			getCertificationService().deleteCertification(
					getSelectedCertification());

			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Sertifika başarıyla silindi.", "");

			FacesContext.getCurrentInstance().addMessage(null, fm);
		}

		return "certification.xhtml?faces-redirect=true&u="
				+ getLoggedUser().getTcno();

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

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public Boolean getUpdateFile() {
		return updateFile;
	}

	public void setUpdateFile(Boolean updateFile) {
		this.updateFile = updateFile;
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

	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public String addCertification() {

		Date registeredDate = new Date();

		int differenceStartRegister = Days.daysBetween(
				new DateTime(getCertification().getCertificateDate()),
				new DateTime(registeredDate)).getDays();

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		if (differenceStartRegister > 0) {

			getCertification().setUser(getLoggedUser());

			if (uploadedFile != null) {
				try {
					getFileUploadService().saveFile(
							UploadedFileDirectory.CERTIFICATES_PATH.getPath(),
							getUploadedFile());

					getCertification().setFilePath(
							getFileUploadService().getFileName());

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			getCertificationService().addCertification(getCertification());

			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Kaydınız başarıyla tamamlandı.", "");

			FacesContext.getCurrentInstance().addMessage(null, fm);
		} else {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Sertifikanın alındığı tarih geçmiş zamana ait olmalıdır.",
					"Lütfen yeniden deneyiniz.");
			FacesContext.getCurrentInstance().addMessage(null, fm);

		}

		return "certification.xhtml?faces-redirect=true&u="
				+ getLoggedUser().getTcno();
	}

	public String updateCertification() {

		Date registeredDate = new Date();

		int differenceStartRegister = Days.daysBetween(
				new DateTime(getSelectedCertification().getCertificateDate()),
				new DateTime(registeredDate)).getDays();

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		if (differenceStartRegister > 0) {
			
			
			if (!updateFile && uploadedFile != null) {
				try {

					if (getFileUploadService().deleteFile(
							UploadedFileDirectory.CERTIFICATES_PATH
									.getPath()
									+ "/"
									+ getSelectedCertification().getFilePath())) {

						getFileUploadService()
								.saveFile(
										UploadedFileDirectory.CERTIFICATES_PATH
												.getPath(), getUploadedFile());

						getSelectedCertification().setFilePath(
								getFileUploadService().getFileName());

						getCertificationService().updateCertification(
								getSelectedCertification());

						FacesMessage fm = new FacesMessage(
								FacesMessage.SEVERITY_INFO,
								"Kaydınız başarıyla güncellendi.", "");

						FacesContext.getCurrentInstance().addMessage(null, fm);

					} else {
						FacesMessage fm = new FacesMessage(
								FacesMessage.SEVERITY_INFO,
								"Kaydınız güncellenemedi.", "");

						FacesContext.getCurrentInstance().addMessage(null, fm);

					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				getCertificationService().updateCertification(
						getSelectedCertification());

				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"Kaydınız başarıyla güncellendi.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);
			}
			
		} else {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Sertifikanın alındığı tarih geçmiş zamana ait olmalıdır.",
					"Lütfen yeniden deneyiniz.");
			FacesContext.getCurrentInstance().addMessage(null, fm);

		}

		return "certification.xhtml?faces-redirect=true&u="
				+ getLoggedUser().getTcno();

	}

}
