package com.orcun.mezun.view;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.springframework.dao.DataAccessException;

import com.orcun.mezun.model.User;
import com.orcun.mezun.service.SignUpService;

@ManagedBean
@ViewScoped
public class SignUpView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user;

	private List<Integer> birthdayYears = new ArrayList<Integer>();

	@ManagedProperty(value = "#{signUpService}")
	private SignUpService signUpService;

	public User getUser() {
		if (user == null)
			user = new User();

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
			for (int i = 1970; i < year - 17; i++) {
				this.birthdayYears.add(i);
			}
		}

		return birthdayYears;
	}

	public void setBirthdayYears(List<Integer> birthdayYears) {
		this.birthdayYears = birthdayYears;
	}

	public SignUpService getSignUpService() {
		return signUpService;
	}

	public void setSignUpService(SignUpService signUpService) {
		this.signUpService = signUpService;
	}

	public String saveUser() {
		try {

			getSignUpService().addUser(user);
			return ("index");
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return ("success");
	}

}
