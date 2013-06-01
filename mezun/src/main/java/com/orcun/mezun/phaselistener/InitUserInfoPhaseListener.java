package com.orcun.mezun.phaselistener;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.init.InitAlumniInfoService;
import com.orcun.mezun.service.user.init.InitStudentInfoService;

public class InitUserInfoPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	private User loggedUser;
	private InitStudentInfoService initStudentInfoService;
	private InitAlumniInfoService initAlumniInfoService;
	private ClassPathXmlApplicationContext appContext;
	private SecurityContext securityContext;

	public void afterPhase(PhaseEvent event) {
		// init

		this.appContext = new ClassPathXmlApplicationContext(
				new String[] { "appContext/appContext-mezun.xml" });

		this.securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");
		
		if (securityContext != null) {

			if (securityContext.getAuthentication().isAuthenticated()) {
				this.loggedUser = (User) securityContext.getAuthentication()
						.getPrincipal();

				if (securityContext.getAuthentication().getAuthorities()
						.iterator().next().getAuthority()
						.equals("ROLE_STUDENT")) {

					this.initStudentInfoService = (InitStudentInfoService) appContext
							.getBean("initStudentInfoService");

					if (!initStudentInfoService
							.IsValidInitStudentInfo(loggedUser)) {

						try {
							FacesContext
									.getCurrentInstance()
									.getExternalContext()
									.redirect(
											"init_student_info.xhtml?u="
													+ getLoggedUser().getTcno());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				} else if (securityContext.getAuthentication().getAuthorities()
						.iterator().next().getAuthority().equals("ROLE_AlUMNI")) {

					this.initAlumniInfoService = (InitAlumniInfoService) appContext
							.getBean("initAlumniInfoService");

					if (initAlumniInfoService.IsValidInitAlumniInfo(loggedUser)) {

						try {
							FacesContext
									.getCurrentInstance()
									.getExternalContext()
									.redirect(
											"init_alumni_info.xhtml?u="
													+ getLoggedUser().getTcno());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}

			}

		}

	}

	public void beforePhase(PhaseEvent event) {

	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
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
