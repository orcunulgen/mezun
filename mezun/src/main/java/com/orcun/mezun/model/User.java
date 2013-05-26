package com.orcun.mezun.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.orcun.mezun.model.enums.UploadedFileDirectory;
import com.orcun.mezun.util.CipherUtils;
import com.orcun.mezun.util.MyURLUtil;

@Entity
@Table(name="user")
public class User implements Serializable,UserDetails{

	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long tcno;
	
	@Column(name="email", unique = true,nullable = false,length=200)
	private String email;

	@Column(name="password",nullable=false,length=200)
	private String password;
	
	@Column(name="name",nullable=true,length=200)
	private String name;
	
	@Column(name="surname",nullable=true,length=200)
	private String surname;
	
	@Column(name="birthdayYear",nullable=false)
	private Integer birthdayYear;
	
	@Column(name="registered_date",nullable=false)
	private Date registeredDate=new Date();
	
	@Column(name="profile_photo_path",nullable=true)
	private String profilePhotoPath;
	
	@SuppressWarnings("unused")
	@Transient
	private String profilePicURL;
	
	@Column(name="enabled",nullable = false)
	private boolean enabled=false;
	
	@Column(name="activation",nullable = false)
	private boolean activation=false;
	
	@Column(name="send_mail",nullable = false)
	private boolean sendMail=true;
	
	@OneToOne(fetch=FetchType.EAGER)
	private Country country;
	
	@OneToOne(fetch=FetchType.EAGER)
	private City city;
	
	@ManyToMany(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	private List<Role> roles=new ArrayList<Role>();
	
	public Long getTcno() {
		return tcno;
	}
	public void setTcno(Long tcno) {
		this.tcno = tcno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) throws UnsupportedEncodingException {
	    this.email=email;    
	}
	public String getPassword() {
		//this.password=CipherUtils.decrypt(password);
		return password;
	}
	public void setPassword(String password) throws UnsupportedEncodingException {
		this.password=password;
		//this.password = new String(password.getBytes("ISO-8859-1"), "UTF-8");
		//this.password=CipherUtils.encrypt(password);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) throws UnsupportedEncodingException {
		//this.name=name;
		this.name = new String(name.getBytes("ISO-8859-1"), "UTF-8").toUpperCase();
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) throws UnsupportedEncodingException {
		//this.surname=surname;
		this.surname = new String(surname.getBytes("ISO-8859-1"), "UTF-8").toUpperCase();
	}
	public Integer getBirthdayYear() {
		return birthdayYear;
	}
	public void setBirthdayYear(Integer birthdayYear) {
		this.birthdayYear = birthdayYear;
	}
	public Date getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}
	public String getProfilePhotoPath() {
		return profilePhotoPath;
	}
	public void setProfilePhotoPath(String profilePhotoPath) {
		this.profilePhotoPath = profilePhotoPath;
	}
	public String getProfilePicURL() throws MalformedURLException {
		return MyURLUtil.getBaseURL(FacesContext.getCurrentInstance())
				+ UploadedFileDirectory.PROFILE_PICTURE_PATH.getPath()
				+"/"
				+ getProfilePhotoPath();
	}
	public void setProfilePicURL(String profilePicURL) {
		this.profilePicURL = profilePicURL;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isActivation() {
		return activation;
	}
	public void setActivation(boolean activation) {
		this.activation = activation;
	}
	public boolean isSendMail() {
		return sendMail;
	}
	public void setSendMail(boolean sendMail) {
		this.sendMail = sendMail;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	@SuppressWarnings("deprecation")
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(Role role:roles){
			authorities.add(new GrantedAuthorityImpl(role.getRole()));		
		}
		//authorities.add(new GrantedAuthorityImpl(role.getRole()));
		return authorities;
	}
	public boolean isAccountNonExpired() {
		return true;
	}
	public boolean isAccountNonLocked() {
		return true;
	}
	public boolean isCredentialsNonExpired() {
		
		return true;
	}
	public String getUsername() {
		
		return email;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tcno == null) ? 0 : tcno.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (tcno == null) {
			if (other.tcno != null)
				return false;
		} else if (!tcno.equals(other.tcno))
			return false;
		return true;
	}
	
}
