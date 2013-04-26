package com.orcun.mezun.filter;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.init.InitAlumniInfoService;
import com.orcun.mezun.service.user.init.InitStudentInfoService;

public class InitUserInfoFilter implements Filter {

	private User loggedUser;
	private InitStudentInfoService initStudentInfoService;
	private InitAlumniInfoService initAlumniInfoService;
	private ClassPathXmlApplicationContext appContext;
	private SecurityContext securityContext;

	public void init(FilterConfig filterConfig) throws ServletException {
		
		this.securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.appContext = new ClassPathXmlApplicationContext(
				"appContext/appContext-mezun.xml");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		if (securityContext.getAuthentication().getAuthorities().iterator()
				.next().getAuthority().equals("ROLE_STUDENT")) {

			this.initStudentInfoService = (InitStudentInfoService) appContext
					.getBean("initStudentInfoService");

		} else if (securityContext.getAuthentication().getAuthorities()
				.iterator().next().getAuthority().equals("ROLE_AlUMNI")) {

			this.initAlumniInfoService = (InitAlumniInfoService) appContext
					.getBean("initAlumniInfoService");
		}

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (securityContext.getAuthentication().getAuthorities().iterator()
				.next().getAuthority().equals("ROLE_STUDENT")) {
			
			if (!initStudentInfoService.IsValidInitStudentInfo(loggedUser)) {
				ExternalContext exCtx = FacesContext.getCurrentInstance()
						.getExternalContext();
				
				request.getRequestDispatcher(exCtx.getRequestContextPath()
						+ "/user_profile/init/init_student_info.xhtml").forward(request, response);
			}

		} else if (securityContext.getAuthentication().getAuthorities()
				.iterator().next().getAuthority().equals("ROLE_AlUMNI")) {
			
			if(initAlumniInfoService.IsValidInitAlumniInfo(loggedUser)){
				
				ExternalContext exCtx = FacesContext.getCurrentInstance()
						.getExternalContext();
				
				request.getRequestDispatcher(exCtx.getRequestContextPath()
						+ "/user_profile/init/init_alumni_info.xhtml").forward(request, response);
			}

		}else{
			chain.doFilter(request, response);
		}

	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	public InitStudentInfoService getInitStudentInfoService() {
		return initStudentInfoService;
	}

	public void setInitStudentInfoService(
			InitStudentInfoService initStudentInfoService) {
		this.initStudentInfoService = initStudentInfoService;
	}

	public InitAlumniInfoService getInitAlumniInfoService() {
		return initAlumniInfoService;
	}

	public void setInitAlumniInfoService(
			InitAlumniInfoService initAlumniInfoService) {
		this.initAlumniInfoService = initAlumniInfoService;
	}

	public ClassPathXmlApplicationContext getAppContext() {
		return appContext;
	}

	public void setAppContext(ClassPathXmlApplicationContext appContext) {
		this.appContext = appContext;
	}

	public SecurityContext getSecurityContext() {
		return securityContext;
	}

	public void setSecurityContext(SecurityContext securityContext) {
		this.securityContext = securityContext;
	}

}
