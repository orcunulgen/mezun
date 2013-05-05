package com.orcun.mezun.view.user;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.commons.FileUploadService;
import com.orcun.mezun.model.ClassicalCV;
import com.orcun.mezun.model.User;
import com.orcun.mezun.model.enums.UploadedFileDirectory;
import com.orcun.mezun.service.user.ClassicalCvService;

@ManagedBean
@ViewScoped
public class ClassicalCvView implements Serializable {

	private static final long serialVersionUID = 1L;

	private ClassicalCV classicalCV;

	private UploadedFile uploadedCV;

	private User loggedUser;

	private Boolean existUploadedFile = false;

	@ManagedProperty(value = "#{classicalCvService}")
	private ClassicalCvService classicalCvService;

	@ManagedProperty(value = "#{fileUploadService}")
	private FileUploadService fileUploadService;

	@PostConstruct
	public void init() {

		if (classicalCV == null) {
			classicalCV = new ClassicalCV();

		}

		if (classicalCV.getUser() == null) {
			if (getClassicalCvService().findClassicalCvByUser(getLoggedUser()) != null) {
				setClassicalCV(getClassicalCvService().findClassicalCvByUser(
						getLoggedUser()));
				setExistUploadedFile(true);
			}
		}

	}

	public ClassicalCV getClassicalCV() {
		return classicalCV;
	}

	public void setClassicalCV(ClassicalCV classicalCV) {
		this.classicalCV = classicalCV;
	}

	public UploadedFile getUploadedCV() {
		return uploadedCV;
	}

	public void setUploadedCV(UploadedFile uploadedCV) {
		this.uploadedCV = uploadedCV;
	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public Boolean getExistUploadedFile() {
		return existUploadedFile;
	}

	public void setExistUploadedFile(Boolean existUploadedFile) {
		this.existUploadedFile = existUploadedFile;
	}

	public ClassicalCvService getClassicalCvService() {
		return classicalCvService;
	}

	public void setClassicalCvService(ClassicalCvService classicalCvService) {
		this.classicalCvService = classicalCvService;
	}

	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public String saveClassicalCv() {

		if (existUploadedFile && uploadedCV!=null) {
			try {
				
				if (getFileUploadService().deleteFile(
						UploadedFileDirectory.CLASSICAL_CV_PATH.getPath() + "/"
								+ getClassicalCV().getCvPath())) {

					getFileUploadService().saveFile(
							UploadedFileDirectory.CLASSICAL_CV_PATH.getPath(),
							getUploadedCV());

					getClassicalCV().setCvPath(
							getFileUploadService().getFileName());
					getClassicalCV().setUser(getLoggedUser());

					getClassicalCvService().updateClassicalCv(getClassicalCV());

					FacesMessage fm = new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"Klasik Özgeçmişiniz başarıyla güncellendi.", "");

					FacesContext.getCurrentInstance().addMessage(null, fm);
				} else {
					FacesMessage fm = new FacesMessage(
							FacesMessage.SEVERITY_INFO, "Klasik özgeçmişiniz güncellenemedi.",
							"");

					FacesContext.getCurrentInstance().addMessage(null, fm);

				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			try {
				getFileUploadService().saveFile(
						UploadedFileDirectory.CLASSICAL_CV_PATH.getPath(),
						getUploadedCV());

				getClassicalCV()
						.setCvPath(getFileUploadService().getFileName());
				getClassicalCV().setUser(getLoggedUser());
				getClassicalCvService().addClassicalCv(getClassicalCV());
				
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Klasik özgeçmişiniz başarıyla güncellendi.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		return ("classical_cv.xhtml?faces-redirect=true&user=" + getLoggedUser()
				.getTcno());
	}

}
