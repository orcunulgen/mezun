package com.orcun.mezun.view;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.primefaces.model.UploadedFile;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

import com.orcun.mezun.commons.FileUploadService;
import com.orcun.mezun.model.City;
import com.orcun.mezun.model.Country;
import com.orcun.mezun.model.Role;
import com.orcun.mezun.model.User;
import com.orcun.mezun.model.enums.UploadedFileDirectory;
import com.orcun.mezun.service.SignUpService;
import com.orcun.mezun.util.MyURLUtil;

@ManagedBean
@ViewScoped
public class SignUpView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user;

	private List<Integer> birthdayYears = new ArrayList<Integer>();

	private List<Country> countries = new ArrayList<Country>();

	private List<City> userCities = new ArrayList<City>();

	private Boolean isAlumni = false;

	private UploadedFile uploadedProfilePic;

	@ManagedProperty(value = "#{signUpService}")
	private SignUpService signUpService;

	@ManagedProperty(value = "#{fileUploadService}")
	private FileUploadService fileUploadService;

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

	public UploadedFile getUploadedProfilePic() {
		return uploadedProfilePic;
	}

	public void setUploadedProfilePic(UploadedFile uploadedProfilePic) {
		this.uploadedProfilePic = uploadedProfilePic;
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

	/*
	 * public String getImage() {
	 * 
	 * try { image = MyURLUtil.getBaseURL(FacesContext.getCurrentInstance()); }
	 * catch (MalformedURLException e) { e.printStackTrace(); } image +=
	 * "/app_images/profile_pictures/1367685202268.jpg"; return image; }
	 */

	public void updateUserChangeCountry() {
		userCities = getSignUpService().allCities(getUser().getCountry());
	}

	public String saveUser() {

		if (checkUserTcno(user)) { // geçici olarak devre dışı

			List<Role> userRoles = new ArrayList<Role>();

			Role defaultRole = new Role();

			if (getIsAlumni()) {
				defaultRole = signUpService.getRoleInfo("ROLE_ALUMNI");
			} else {
				defaultRole = signUpService.getRoleInfo("ROLE_STUDENT");
			}

			userRoles.add(defaultRole);
			user.setRoles(userRoles);

			try {
				getFileUploadService().saveFile(
						UploadedFileDirectory.PROFILE_PICTURE_PATH.getPath(),
						getUploadedProfilePic());

				user.setProfilePhotoPath(getFileUploadService().getFileName());
				
				//getUser().setPassword(new String(getUser().getPassword().getBytes("ISO-8859-1"), "UTF-8"));
				//getUser().setPassword(CipherUtils.encrypt(user.getPassword()));

				getSignUpService().addUser(user);

				// -------------------Email sending-----------------

				final String username = "orcun.ulgen@gmail.com";
				final String password = "1986105?cos\"";

				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");

				Session session = Session.getInstance(props,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(username,
										password);
							}
						});

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(
						"orcun.ulgen@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(user.getEmail()));
				message.setSubject("Üyelik Aktivasyonu");
				String messageText = "Sayın "
						+ user.getName()
						+ " "
						+ user.getSurname()
						+ ",<br/><br/>"
						+ "YTÜ Bilgisayar Mühendisliği Mezun Bilgi Sistemine giriş yapabilmek için öncelikle aşağıdaki linke tıklayarak aktivasyonunuzu tamamlamanız gerekmektedir.<br/>"
						+ "<a href=\""
						+ MyURLUtil.getBaseURL(FacesContext
								.getCurrentInstance())
						+ "/activation.xhtml?u="
						+ user.getTcno().toString()
						+ "\">"
						+ MyURLUtil.getBaseURL(FacesContext
								.getCurrentInstance()) + "/activation.xhtml?u="
						+ user.getTcno().toString()
						+ "</a>";

				message.setContent(messageText,"text/html; charset=utf-8");

				Transport.send(message);

				// -------------------------------------------------

				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"Kaydınız başarıyla tamamlandı.Lütfen email adresinizi kontrol ediniz.",
						"");

				FacesContext.getCurrentInstance().addMessage(null, fm);

			} catch (IOException e) {
				e.printStackTrace();
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Kaydınız tamamlanamadı.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

			Flash flash = FacesContext.getCurrentInstance()
					.getExternalContext().getFlash();
			flash.setKeepMessages(true);

			return ("login.xhtml?faces-redirect=true");
		}

		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Kaydınız tamamlanamadı.Lütfen daha sonra tekrar deneyin.", "");

		FacesContext.getCurrentInstance().addMessage(null, fm);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		return ("sign_up.xhtml?faces-redirect=true");
	}

	public boolean checkUserTcno(User user) {

		if (user.getTcno() != null) {

			KPSPublicSoapProxy tcCheckProxy = new KPSPublicSoapProxy();

			try {
				if (!tcCheckProxy.TCKimlikNoDogrula(user.getTcno(),
						user.getName(), user.getSurname(),
						user.getBirthdayYear())) {
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

}
