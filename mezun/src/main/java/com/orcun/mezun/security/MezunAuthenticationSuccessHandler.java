package com.orcun.mezun.security;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.InitStudentInfoService;

public class MezunAuthenticationSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	@Transactional
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		User loggedUser = (User) authentication.getPrincipal();

		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				"appContext/appContext-mezun.xml");

		if (authentication.getAuthorities().iterator().next().getAuthority()
				.equals("ROLE_ADMIN")) {

			ExternalContext exCtx = FacesContext.getCurrentInstance()
					.getExternalContext();
			response.sendRedirect(exCtx.getRequestContextPath()
					+ "/admin_profile/index.xhtml");

		} else if (authentication.getAuthorities().iterator().next()
				.getAuthority().equals("ROLE_STUDENT")) {

			InitStudentInfoService initStudentInfoService = (InitStudentInfoService) appContext
					.getBean("initStudentInfoService");
			
			if(!initStudentInfoService.IsValidInitStudentInfo(loggedUser)){
				ExternalContext exCtx = FacesContext.getCurrentInstance()
						.getExternalContext();
				response.sendRedirect(exCtx.getRequestContextPath()
						+ "/user_profile/init_student_info.xhtml");
			}

		} else if (authentication.getAuthorities().iterator().next()
				.getAuthority().equals("ROLE_ALUMNI")) {

			// contact_info
			// university

		}
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
