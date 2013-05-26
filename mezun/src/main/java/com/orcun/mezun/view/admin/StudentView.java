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
import com.orcun.mezun.service.admin.StudentService;

@ManagedBean
@ViewScoped
public class StudentView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private User student;
	
	private List<Integer> birthdayYears = new ArrayList<Integer>();

	private List<Country> countries = new ArrayList<Country>();

	private List<City> studentAddCities = new ArrayList<City>();

	private List<City> studentUpdateCities = new ArrayList<City>();

	private List<User> students = new ArrayList<User>();
	
	private User selectedStudent;

	@ManagedProperty(value = "#{studentService}")
	private StudentService studentService;
	
	@ManagedProperty(value = "#{signUpService}")
	private SignUpService signUpService;
	
	@ManagedProperty(value = "#{fileUploadService}")
	private FileUploadService fileUploadService;

	@PostConstruct
	public void init() {
		countries = studentService.allCountries();
		
		List<Role> userRoles=new ArrayList<Role>();
		
		Role defaultRole=new Role();
		defaultRole=signUpService.getRoleInfo("ROLE_STUDENT");
		
		userRoles.add(defaultRole);
		students = studentService.allStudents(userRoles);

		if (student == null) {
			student = new User();

		}
		
		if(selectedStudent==null){
			selectedStudent=new User();
		}
	}

	public void initSelectedStudent(User selectedStudent) {
		this.selectedStudent = selectedStudent;
		updateStudentChangeCountry();
	}

	public String deleteSelectedStudent(User selectedStudent) throws IOException {
		initSelectedStudent(selectedStudent);
		try {
			
			if (getFileUploadService().deleteFile(
					UploadedFileDirectory.PROFILE_PICTURE_PATH.getPath() + "/"
							+ getSelectedStudent().getProfilePhotoPath())) {
				
				getStudentService().deleteStudent(getSelectedStudent());

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

		return ("student.xhtml?faces-redirect=true&user=" + getLoggedUser()
				.getTcno());

	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
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

	public List<City> getStudentAddCities() {
		return studentAddCities;
	}

	public void setStudentAddCities(List<City> studentAddCities) {
		this.studentAddCities = studentAddCities;
	}

	public List<City> getStudentUpdateCities() {
		return studentUpdateCities;
	}

	public void setStudentUpdateCities(List<City> studentUpdateCities) {
		this.studentUpdateCities = studentUpdateCities;
	}

	public List<User> getStudents() {
		return students;
	}

	public void setStudents(List<User> students) {
		this.students = students;
	}

	public User getSelectedStudent() {
		return selectedStudent;
	}

	public void setSelectedStudent(User selectedStudent) {
		this.selectedStudent = selectedStudent;
	}

	public StudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
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

	public void addStudentChangeCountry() {
		studentAddCities = getStudentService().allCities(
				getStudent().getCountry());
	}

	public void updateStudentChangeCountry() {
		studentUpdateCities = getStudentService().allCities(
				getSelectedStudent().getCountry());
	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public void addStudent() throws IOException {
		try {

			if(checkUserTcno(student)){ // geçici olarak devre dışı
				
				List<Role> userRoles=new ArrayList<Role>();
				
				Role defaultRole=new Role();
				defaultRole=signUpService.getRoleInfo("ROLE_STUDENT");
				
				userRoles.add(defaultRole);
				student.setRoles(userRoles);
				
				getSignUpService().addUser(student);
			
				FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.redirect(
						"student.xhtml?user="
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

	public void updateStudent() throws IOException {
		try {

			if(checkUserTcno(getSelectedStudent())){ // geçici olarak devre dışı
				getStudentService().updateStudent(
						getSelectedStudent());

				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"student.xhtml?user="
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
