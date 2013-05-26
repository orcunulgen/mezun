package com.orcun.mezun.view;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;

import org.omnifaces.util.Faces;

import com.orcun.mezun.model.User;
import com.orcun.mezun.service.admin.InactiveUserService;
import com.orcun.mezun.util.CipherUtils;
import com.orcun.mezun.util.StringConvertUtil;


@ManagedBean
@ViewScoped
public class ActivationView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user;
	private String param;

	@ManagedProperty(value = "#{inactiveUserService}")
	private InactiveUserService inactiveUserService;

	@PostConstruct
	public void init(){
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		if(this.user==null){
			user=new User();
		}
		
		HttpServletRequest request=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();

		setParam(request.getParameter("u"));

	}
	
	public void activate() throws IOException {
				Long tcno=StringConvertUtil.stringToLong(CipherUtils.decrypt(getParam()));
		
		getUser().setTcno(tcno);
		
		setUser(getInactiveUserService().findUserByTcno(user));
		
		
		if(getUser()!=null){
			
			
			if(!getUser().isActivation() && !getUser().isEnabled()){
				getUser().setEnabled(false);
				getUser().setActivation(true);
				getInactiveUserService().sendAdminForActivation(user);
				
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Aktivasyonunuz başarı ile tamamlandı.Sisteme giriş yapmak için yönetici onayı gerekmektedir.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);

			}else{
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Aktivasyonunuz daha önce yapılmıştır.Sisteme giriş yapmak için yönetici onayı gerekmektedir.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);

			}
						
		}else{
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Aktivasyon başarısız.Hatalı aktivasyon denemesi.", "");

			FacesContext.getCurrentInstance().addMessage(null, fm);
		}
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);
		
		//return ("login.xhtml?faces-redirect=true");
		Faces.redirect("login.xhtml?faces-redirect=true",null);

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public InactiveUserService getInactiveUserService() {
		return inactiveUserService;
	}

	public void setInactiveUserService(InactiveUserService inactiveUserService) {
		this.inactiveUserService = inactiveUserService;
	}

}
