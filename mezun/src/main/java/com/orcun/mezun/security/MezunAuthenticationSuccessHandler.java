package com.orcun.mezun.security;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;



public class MezunAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	
	@Override
	@Transactional
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		if(authentication.getAuthorities().iterator().next().getAuthority().equals("ROLE_ADMIN")){
			
			ExternalContext exCtx = FacesContext.getCurrentInstance().getExternalContext();
			response.sendRedirect(exCtx.getRequestContextPath()+"/admin_profile/index.xhtml");
			
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}


}
