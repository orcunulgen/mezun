package com.orcun.mezun.view.admin;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContext;


import com.orcun.mezun.commons.FileUploadService;
import com.orcun.mezun.model.City;
import com.orcun.mezun.model.Country;
import com.orcun.mezun.model.Role;
import com.orcun.mezun.model.User;
import com.orcun.mezun.model.enums.UploadedFileDirectory;
import com.orcun.mezun.service.SignUpService;
import com.orcun.mezun.service.admin.AlumniService;

@ManagedBean
@ViewScoped
public class AlumniView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private User alumni;
	
	private List<Integer> birthdayYears = new ArrayList<Integer>();

	private List<Country> countries = new ArrayList<Country>();

	private List<City> alumniAddCities = new ArrayList<City>();

	private List<City> alumniUpdateCities = new ArrayList<City>();

	private List<User> alumnis = new ArrayList<User>();
	
	private User selectedAlumni;

	@ManagedProperty(value = "#{alumniService}")
	private AlumniService alumniService;
	
	@ManagedProperty(value = "#{signUpService}")
	private SignUpService signUpService;
	
	@ManagedProperty(value = "#{fileUploadService}")
	private FileUploadService fileUploadService;

	@PostConstruct
	public void init() {
		countries = alumniService.allCountries();
		
		List<Role> userRoles=new ArrayList<Role>();
		
		Role defaultRole=new Role();
		defaultRole=signUpService.getRoleInfo("ROLE_ALUMNI");
		
		userRoles.add(defaultRole);
		alumnis = alumniService.allAlumnis(userRoles);

		if (alumni == null) {
			alumni = new User();

		}
		
		if(selectedAlumni==null){
			selectedAlumni=new User();
		}
	}

	public void initSelectedAlumni(User selectedAlumni) {
		this.selectedAlumni = selectedAlumni;
		updateAlumniChangeCountry();
	}

	public String deleteSelectedAlumni(User selectedAlumni) throws IOException {
		initSelectedAlumni(selectedAlumni);
		try {
			
			if (getFileUploadService().deleteFile(
					UploadedFileDirectory.PROFILE_PICTURE_PATH.getPath() + "/"
							+ getSelectedAlumni().getProfilePhotoPath())) {
				
				getAlumniService().deleteAlumni(getSelectedAlumni());

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

		return ("alumni.xhtml?faces-redirect=true&u=" + getLoggedUser()
				.getTcno());
 
	}

	public User getAlumni() {
		return alumni;
	}

	public void setAlumni(User alumni) {
		this.alumni = alumni;
	}

	public List<Integer> getBirthdayYears() {
		if (this.birthdayYears == null || this.birthdayYears.size() == 0) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy");
			Date date = new Date();
			int year = Integer.parseInt(dateFormat.format(date));
			for (int i = 1950; i < year - 17; i++) {
				this.birthdayYears.add(i);
			}
		}

		return birthdayYears;
	}

	public void setBirthdayYears(List<Integer> birthdayYears) {
		this.birthdayYears = birthdayYears;
	}

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	public List<City> getAlumniAddCities() {
		return alumniAddCities;
	}

	public void setAlumniAddCities(List<City> alumniAddCities) {
		this.alumniAddCities = alumniAddCities;
	}

	public List<City> getAlumniUpdateCities() {
		return alumniUpdateCities;
	}

	public void setAlumniUpdateCities(List<City> alumniUpdateCities) {
		this.alumniUpdateCities = alumniUpdateCities;
	}

	public List<User> getAlumnis() {
		return alumnis;
	}

	public void setAlumnis(List<User> alumnis) {
		this.alumnis = alumnis;
	}

	public User getSelectedAlumni() {
		return selectedAlumni;
	}

	public void setSelectedAlumni(User selectedAlumni) {
		this.selectedAlumni = selectedAlumni;
	}

	public AlumniService getAlumniService() {
		return alumniService;
	}

	public void setAlumniService(AlumniService alumniService) {
		this.alumniService = alumniService;
	}

	public SignUpService getSignUpService() {
		return signUpService;
	}

	public void setSignUpService(SignUpService signUpService) {
		this.signUpService = signUpService;
	}

	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public void addAlumniChangeCountry() {
		alumniAddCities = getAlumniService().allCities(
				getAlumni().getCountry());
	}

	public void updateAlumniChangeCountry() {
		alumniUpdateCities = getAlumniService().allCities(
				getSelectedAlumni().getCountry());
	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public void addAlumni() throws IOException {
		try {

			if(checkUserTcno(alumni)){ // geçici olarak devre dışı
				
				List<Role> userRoles=new ArrayList<Role>();
				
				Role defaultRole=new Role();
				defaultRole=signUpService.getRoleInfo("ROLE_ALUMNI");
				
				userRoles.add(defaultRole);
				alumni.setRoles(userRoles);
				
				getSignUpService().addUser(alumni);
			
				FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.redirect(
						"alumni.xhtml?u="
								+ getLoggedUser().getTcno());
			} else {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"TC kimlik numarası kullanıcı ile uyuşmamaktadır.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}

	public void updatealumni() throws IOException {
		try {

			if(checkUserTcno(getSelectedAlumni())){ // geçici olarak devre dışı
				getAlumniService().updateAlumni(
						getSelectedAlumni());

				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"alumni.xhtml?u="
										+ getLoggedUser().getTcno());

			} else {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"TC kimlik numarası kullanıcı ile uyuşmamaktadır.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}
	
	public boolean checkUserTcno(User user) {
		return true;
		/*if (user.getTcno() != null) {

			KPSPublicSoapProxy tcCheckProxy = new KPSPublicSoapProxy();

			try {
				if (!tcCheckProxy.TCKimlikNoDogrula(user.getTcno(), user
						.getName().toUpperCase(), user.getSurname()
						.toUpperCase(), user.getBirthdayYear())) {
					FacesMessage fm = new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"TC kimlik numarası doğrulanamadı",
							"Lütfen isim,soyisim,doğum yılı ve tc kimlik numarası alanlarını kontrol ediniz");

					FacesContext.getCurrentInstance().addMessage("form_tcno",
							fm);

					return false;

				} else {
					return true;
				}

			} catch (RemoteException e) {
				e.printStackTrace();
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Bağlantı hatası",
						"TC kimlik numarası sorgulanamıyor.");
				FacesContext.getCurrentInstance().addMessage("form_tcno", fm);
			}

			return false;

		} else {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"TC kimlik numarası geçerli değil",
					"Lütfen geçerli bir TC kimlik numarası giriniz.");
			FacesContext.getCurrentInstance().addMessage("form_tcno", fm);

			return false;

		}*/

	}
}
