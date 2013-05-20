package com.orcun.mezun.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.orcun.mezun.service.UserService;


@Service("userDetailsService")
public class MezunUserDetailsService implements UserDetailsService {
	@Autowired
	private UserService userService;
	
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		
			if ((username == null) || (username.length() == 0)) {
				//throw new BusinessRuleException(ErrorCodeCategory.AUTHENTICATION, "usernameisempty");
				//FacesContext context=FacesContext.getCurrentInstance();
				//FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN,"Geçersiz kullanıcı adı veya şifre.","Lütfen yeniden deneyiniz.");
				//context.addMessage("fail_auth", fm);
				return null;
			}
		
			return userService.findByUserId(username);

	}
	
}
