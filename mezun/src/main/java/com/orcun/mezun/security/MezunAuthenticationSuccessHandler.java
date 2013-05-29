package com.orcun.mezun.security;

import java.io.IOException;
import java.util.Collection;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.User;
import com.orcun.mezun.service.admin.InitAdminBasicInfoService;
import com.orcun.mezun.service.user.init.InitAlumniInfoService;
import com.orcun.mezun.service.user.init.InitStudentInfoService;
import com.orcun.mezun.util.CipherUtils;

public class MezunAuthenticationSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	@Transactional
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		User loggedUser = (User) authentication.getPrincipal();

		// Authentication da yapilan karsilastirmada sifre
		// decrypt ediliyordu
		String pass = loggedUser.getPassword();
		//pass=new String(pass.getBytes("ISO-8859-1"), "UTF-8");
		loggedUser.setPasswordDirectly(CipherUtils.encrypt(pass));
		// Burada ise decrypt edilmis olan pass yeniden encrypt
		// ediliyor
		// amac ise getLoggedUser() metodlarında cagirilan userlarin sifresinin
		// encrypt edilmis sekilde sistemde gezmesini saglamak

		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				"appContext/appContext-mezun.xml");

		if (hasRole("ROLE_ADMIN", loggedUser)) {

			InitAdminBasicInfoService initAdminBasicInfoService = (InitAdminBasicInfoService) appContext
					.getBean("initAdminBasicInfoService");

			if (!initAdminBasicInfoService
					.IsValidInitAdminBasicInfo(loggedUser)) {
				ExternalContext exCtx = FacesContext.getCurrentInstance()
						.getExternalContext();
				response.sendRedirect(exCtx.getRequestContextPath()
						+ "/admin_profile/init_admin_basic_info.xhtml");
			} else if (hasRole("ROLE_ALUMNI", loggedUser)) {

				InitAlumniInfoService initAlumniInfoService = (InitAlumniInfoService) appContext
						.getBean("initAlumniInfoService");

				if (!initAlumniInfoService.IsValidInitAlumniInfo(loggedUser)) {
					ExternalContext exCtx = FacesContext.getCurrentInstance()
							.getExternalContext();
					response.sendRedirect(exCtx.getRequestContextPath()
							+ "/user_profile/init/init_alumni_info.xhtml");
				}
			}

		} else if (hasRole("ROLE_STUDENT", loggedUser)) {

			InitStudentInfoService initStudentInfoService = (InitStudentInfoService) appContext
					.getBean("initStudentInfoService");

			if (!initStudentInfoService.IsValidInitStudentInfo(loggedUser)) {
				ExternalContext exCtx = FacesContext.getCurrentInstance()
						.getExternalContext();
				response.sendRedirect(exCtx.getRequestContextPath()
						+ "/user_profile/init/init_student_info.xhtml");
			}

		} else if (hasRole("ROLE_ALUMNI", loggedUser)) {

			InitAlumniInfoService initAlumniInfoService = (InitAlumniInfoService) appContext
					.getBean("initAlumniInfoService");

			if (!initAlumniInfoService.IsValidInitAlumniInfo(loggedUser)) {
				ExternalContext exCtx = FacesContext.getCurrentInstance()
						.getExternalContext();
				response.sendRedirect(exCtx.getRequestContextPath()
						+ "/user_profile/init/init_alumni_info.xhtml");
			}

		}
		super.onAuthenticationSuccess(request, response, authentication);
	}

	public boolean isUserAdmin(Authentication authentication) {

		System.out.println(authentication.getAuthorities().iterator().next()
				.getAuthority());
		System.out.println(authentication.getAuthorities().iterator().next()
				.getAuthority());

		return true;
	}

	public boolean hasRole(String role, User loggedUser) {
		boolean hasRole = false;
		if (loggedUser != null) {
			@SuppressWarnings("unchecked")
			Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) loggedUser
					.getAuthorities();
			if (isRolePresent(authorities, role)) {
				hasRole = true;
			}
		}
		return hasRole;
	}

	/**
	 * Get info about currently logged in user
	 * 
	 * @return UserDetails if found in the context, null otherwise
	 */
	public UserDetails getUserDetails() {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		return userDetails;
	}

	/**
	 * Check if a role is present in the authorities of current user
	 * 
	 * @param authorities
	 *            all authorities assigned to current user
	 * @param role
	 *            required authority
	 * @return true if role is present in list of authorities assigned to
	 *         current user, false otherwise
	 */
	public boolean isRolePresent(Collection<GrantedAuthority> authorities,
			String role) {
		boolean isRolePresent = false;
		for (GrantedAuthority grantedAuthority : authorities) {
			isRolePresent = grantedAuthority.getAuthority().equals(role);
			if (isRolePresent)
				break;
		}
		return isRolePresent;
	}

}
