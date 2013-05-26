package com.orcun.mezun.view.admin;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.commons.FileUploadService;
import com.orcun.mezun.model.User;
import com.orcun.mezun.model.enums.UploadedFileDirectory;
import com.orcun.mezun.service.admin.InactiveUserService;
import com.orcun.mezun.service.admin.StudentService;

@ManagedBean
@ViewScoped
public class NewUserView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private List<User> newUsers = new ArrayList<User>();

	private User selectedNewUser;

	@ManagedProperty(value = "#{studentService}")
	private StudentService studentService;

	@ManagedProperty(value = "#{inactiveUserService}")
	private InactiveUserService inactiveUserService;

	@ManagedProperty(value = "#{fileUploadService}")
	private FileUploadService fileUploadService;

	@PostConstruct
	public void init() {

		newUsers = getInactiveUserService().getInactiveUserList();

		if (selectedNewUser == null) {
			selectedNewUser = new User();
		}
	}

	public void initSelectedNewUser(User selectedNewUser) {
		this.selectedNewUser = selectedNewUser;
	}

	public String deleteSelectedUser() throws IOException {
		initSelectedNewUser(getSelectedNewUser());
		try {
			if (getFileUploadService().deleteFile(
					UploadedFileDirectory.PROFILE_PICTURE_PATH.getPath() + "/"
							+ getSelectedNewUser().getProfilePhotoPath())) {
				getStudentService().deleteStudent(getSelectedNewUser());

				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Kullanıcı sistemden kaldırıldı.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);

			} else {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"Kullanıcı sistemden kaldırılamadı.Lütfen daha sonra tekrar deneyin",
						"");

				FacesContext.getCurrentInstance().addMessage(null, fm);

			}

		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		return ("new_user.xhtml?faces-redirect=true&user=" + getLoggedUser()
				.getTcno());
	}

	public List<User> getNewUsers() {
		return newUsers;
	}

	public void setNewUsers(List<User> newUsers) {
		this.newUsers = newUsers;
	}

	public User getSelectedNewUser() {
		return selectedNewUser;
	}

	public void setSelectedNewUser(User selectedNewUser) {
		this.selectedNewUser = selectedNewUser;
	}

	public StudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public InactiveUserService getInactiveUserService() {
		return inactiveUserService;
	}

	public void setInactiveUserService(InactiveUserService inactiveUserService) {
		this.inactiveUserService = inactiveUserService;
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

	public String updateNewUser() throws IOException {
		try {

			if (checkUserTcno(getSelectedNewUser())) { // geçici olarak devre
														// dışı

				getSelectedNewUser().setEnabled(true);
				getStudentService().updateStudent(getSelectedNewUser());

				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Kullanıcı başarıyla onaylandı.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);

			} else {
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"TC kimlik numarası kullanıcı ile uyuşmamaktadır.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		return ("new_user.xhtml?faces-redirect=true&user=" + getLoggedUser()
				.getTcno());

	}

	public boolean checkUserTcno(User user) {
		return true;
		/*
		 * if (user.getTcno() != null) {
		 * 
		 * KPSPublicSoapProxy tcCheckProxy = new KPSPublicSoapProxy();
		 * 
		 * try { if (!tcCheckProxy.TCKimlikNoDogrula(user.getTcno(), user
		 * .getName().toUpperCase(), user.getSurname() .toUpperCase(),
		 * user.getBirthdayYear())) { FacesMessage fm = new FacesMessage(
		 * FacesMessage.SEVERITY_INFO, "TC kimlik numarası doğrulanamadı",
		 * "Lütfen isim,soyisim,doğum yılı ve tc kimlik numarası alanlarını kontrol ediniz"
		 * );
		 * 
		 * FacesContext.getCurrentInstance().addMessage("form_tcno", fm);
		 * 
		 * return false;
		 * 
		 * } else { return true; }
		 * 
		 * } catch (RemoteException e) { e.printStackTrace(); FacesMessage fm =
		 * new FacesMessage(FacesMessage.SEVERITY_INFO, "Bağlantı hatası",
		 * "TC kimlik numarası sorgulanamıyor.");
		 * FacesContext.getCurrentInstance().addMessage("form_tcno", fm); }
		 * 
		 * return false;
		 * 
		 * } else { FacesMessage fm = new
		 * FacesMessage(FacesMessage.SEVERITY_INFO,
		 * "TC kimlik numarası geçerli değil",
		 * "Lütfen geçerli bir TC kimlik numarası giriniz.");
		 * FacesContext.getCurrentInstance().addMessage("form_tcno", fm);
		 * 
		 * return false;
		 * 
		 * }
		 */

	}
}
