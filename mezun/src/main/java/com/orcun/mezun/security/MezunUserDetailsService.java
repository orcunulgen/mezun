package com.orcun.mezun.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.orcun.mezun.model.User;
import com.orcun.mezun.service.UserService;
import com.orcun.mezun.util.CipherUtils;

@Service("userDetailsService")
public class MezunUserDetailsService implements UserDetailsService {
	@Autowired
	private UserService userService;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		if ((username == null) || (username.length() == 0)) {
			return null;
		}

		User user = userService.findByUserId(username);
		String pass = user.getPassword();
		user.setPasswordDirectly(CipherUtils.decrypt(pass));

		return user;
	}

}
