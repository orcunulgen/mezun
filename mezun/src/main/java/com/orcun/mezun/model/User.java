package com.orcun.mezun.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

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
	
	@Column(name="registered_date",nullable=false)
	private Date registeredDate=new Date();
	
	@Column(name="profile_photo_path",nullable=true)
	private String profilePhotoPath;
	
	@Column(name="enabled",nullable = false)
	private boolean enabled=true;
	
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
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
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
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
