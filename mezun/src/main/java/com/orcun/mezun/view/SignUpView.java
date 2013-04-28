package com.orcun.mezun.view;

import java.io.Serializable;
import java.rmi.RemoteException;
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

import org.primefaces.event.FlowEvent;
import org.springframework.dao.DataAccessException;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

import com.orcun.mezun.model.City;
import com.orcun.mezun.model.Country;
import com.orcun.mezun.model.Role;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.SignUpService;

@ManagedBean
@ViewScoped
public class SignUpView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user;

	private List<Integer> birthdayYears = new ArrayList<Integer>();

	private List<Country> countries = new ArrayList<Country>();

	private List<City> userCities = new ArrayList<City>();

	private Boolean isAlumni = false;

	@ManagedProperty(value = "#{signUpService}")
	private SignUpService signUpService;

	@PostConstruct
	public void init() {
		countries = signUpService.allCountries();

		if (user == null)
			user = new User();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public List<City> getUserCities() {
		return userCities;
	}

	public void setUserCities(List<City> userCities) {
		this.userCities = userCities;
	}

	public Boolean getIsAlumni() {
		return isAlumni;
	}

	public void setIsAlumni(Boolean isAlumni) {
		this.isAlumni = isAlumni;
	}

	public SignUpService getSignUpService() {
		return signUpService;
	}

	public void setSignUpService(SignUpService signUpService) {
		this.signUpService = signUpService;
	}

	public void updateUserChangeCountry() {
		userCities = getSignUpService().allCities(getUser().getCountry());
	}

	public String saveUser() {
		try {

			if (!checkUserTcno(user)) { // geçici olarak devre dışı

				List<Role> userRoles = new ArrayList<Role>();

				Role defaultRole = new Role();

				if (getIsAlumni()) {
					defaultRole = signUpService.getRoleInfo("ROLE_ALUMNI");
				} else {
					defaultRole = signUpService.getRoleInfo("ROLE_STUDENT");
				}

				userRoles.add(defaultRole);
				user.setRoles(userRoles);

				getSignUpService().addUser(user);
				
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Kaydınız başarıyla tamamlandı.", "");

				FacesContext.getCurrentInstance().addMessage(
						null, fm);

				Flash flash = FacesContext.getCurrentInstance()
						.getExternalContext().getFlash();
				flash.setKeepMessages(true);
				
				
				return ("login.xhtml?faces-redirect=true");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Kaydınız tamamlanamadı.Lütfen daha sonra tekrar deneyin.", "");

		FacesContext.getCurrentInstance().addMessage(
				null, fm);

		Flash flash = FacesContext.getCurrentInstance()
				.getExternalContext().getFlash();
		flash.setKeepMessages(true);

		return ("sign_up.xhtml?faces-redirect=true");
	}

	public boolean checkUserTcno(User user) {

		if (user.getTcno() != null) {

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
						"Bağlantı hatası", "TC kimlik numarası sorgulanamıyor.");
				FacesContext.getCurrentInstance().addMessage("form_tcno", fm);
			}

			return false;

		} else {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"TC kimlik numarası geçerli değil",
					"Lütfen geçerli bir TC kimlik numarası giriniz.");
			FacesContext.getCurrentInstance().addMessage("form_tcno", fm);

			return false;

		}

	}

	public String onFlowProcess(FlowEvent event) {

			return event.getNewStep();
	}
	
}
