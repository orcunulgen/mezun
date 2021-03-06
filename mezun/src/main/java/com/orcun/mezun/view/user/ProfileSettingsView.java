package com.orcun.mezun.view.user;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.User;
import com.orcun.mezun.security.MezunAuthenticationSuccessHandler;

@ManagedBean
@ViewScoped
public class ProfileSettingsView implements Serializable {

	private static final long serialVersionUID = 1L;

	private MindmapNode root;

	private MindmapNode selectedNode;

	private User loggedUser;
	
	private MezunAuthenticationSuccessHandler mezunAuthenticationSuccessHandler;
	
	private Boolean isAlumni;
	
	public ProfileSettingsView() {

		/*
		 * ClassPathXmlApplicationContext appContext = new
		 * ClassPathXmlApplicationContext( new
		 * String[]{"appContext/appContext-mezun.xml"
		 * ,"appContext/securityContext.xml"});
		 */

		root = new DefaultMindmapNode(getLoggedUser().getName() + " "
				+ loggedUser.getSurname(), "", "FFCC00", false);

		// CV
		MindmapNode cv = new DefaultMindmapNode("Özgeçmiş", "", "6e9ebf", true);

		MindmapNode personalInfo = new DefaultMindmapNode("Kişisel Bilgiler",
				"", "6e9ebf", true);

		MindmapNode parentInfo = new DefaultMindmapNode("Aile Bilgileri", "",
				"6e9ebf", true);

		MindmapNode eduInfo = new DefaultMindmapNode("Eğitim Bilgileri", "",
				"6e9ebf", true);
		MindmapNode university = new DefaultMindmapNode("Üniversite", "",
				"6e9ebf", true);
		MindmapNode highSchool = new DefaultMindmapNode("Lise", "", "6e9ebf",
				true);
		MindmapNode foreignLanguage = new DefaultMindmapNode("Yabancı Dil", "",
				"6e9ebf", true);

		MindmapNode jobExperience = new DefaultMindmapNode("İş Tecrübeleri",
				"", "6e9ebf", true);
		MindmapNode additionalInfo = new DefaultMindmapNode("Ek Bilgiler", "",
				"6e9ebf", true);

		MindmapNode classicalCV = new DefaultMindmapNode("Klasik CV", "",
				"6e9ebf", true);

		// Skills
		MindmapNode skills = new DefaultMindmapNode("Yetkinlikler", "",
				"6e9ebf", true);

		MindmapNode certifications = new DefaultMindmapNode("Sertifikalar", "",
				"6e9ebf", true);
		MindmapNode areaOfInterest = new DefaultMindmapNode("İlgi Alanları",
				"", "6e9ebf", true);

		// interactions
		MindmapNode chatList = new DefaultMindmapNode("Kişi Listesi", "",
				"6e9ebf", true);
		MindmapNode photos = new DefaultMindmapNode("Fotoğraflar", "",
				"6e9ebf", true);
		MindmapNode events = new DefaultMindmapNode("Etkinlikler", "",
				"6e9ebf", true);
		MindmapNode announcements = new DefaultMindmapNode("Duyurular", "",
				"6e9ebf", true);
		MindmapNode posts = new DefaultMindmapNode("Profilim", "", "6e9ebf",
				true);

		MindmapNode contactInfo = new DefaultMindmapNode("İletişim Bilgileri",
				"", "6e9ebf", true);

		root.addNode(cv);

		cv.addNode(personalInfo);
		cv.addNode(parentInfo);
		cv.addNode(eduInfo);
		eduInfo.addNode(university);
		eduInfo.addNode(highSchool);
		eduInfo.addNode(foreignLanguage);
		cv.addNode(jobExperience);
		cv.addNode(additionalInfo);
		cv.addNode(classicalCV);

		root.addNode(skills);
		skills.addNode(certifications);
		skills.addNode(areaOfInterest);

		root.addNode(chatList);
		root.addNode(photos);
		root.addNode(events);
		root.addNode(announcements);
		root.addNode(posts);

		root.addNode(contactInfo);

	}
	
	@PostConstruct
	public void init(){
		this.mezunAuthenticationSuccessHandler=new MezunAuthenticationSuccessHandler();
	}

	public MindmapNode getRoot() {
		return root;
	}

	public MindmapNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(MindmapNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public User getLoggedUser() {

		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;
	}

	public MezunAuthenticationSuccessHandler getMezunAuthenticationSuccessHandler() {
		return mezunAuthenticationSuccessHandler;
	}

	public void setMezunAuthenticationSuccessHandler(
			MezunAuthenticationSuccessHandler mezunAuthenticationSuccessHandler) {
		this.mezunAuthenticationSuccessHandler = mezunAuthenticationSuccessHandler;
	}

	public Boolean getIsAlumni() {
		
		if(this.mezunAuthenticationSuccessHandler.hasRole("ROLE_ALUMNI", getLoggedUser())){
			isAlumni=true;
		}else{
			isAlumni=false;
		}
		return isAlumni;
	}

	public void setIsAlumni(Boolean isAlumni) {
		this.isAlumni = isAlumni;
	}

	public void onNodeSelect(SelectEvent event) throws IOException {

		MindmapNode node = (MindmapNode) event.getObject();

		if (node.getChildren().isEmpty()) {
			Object label = node.getLabel();

			if (label.equals("Kişisel Bilgiler")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"personal_info.xhtml?u="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Aile Bilgileri")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"parent_info.xhtml?u="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Üniversite")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"university.xhtml?u="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Lise")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"high_school.xhtml?u="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Yabancı Dil")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"foreign_language.xhtml?u="
										+ getLoggedUser().getTcno());
			} else if (label.equals("İletişim Bilgileri")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"contact_info.xhtml?u="
										+ getLoggedUser().getTcno());
			} else if (label.equals("İş Tecrübeleri")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"job_experience.xhtml?u="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Ek Bilgiler")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"additional_info.xhtml?u="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Klasik CV")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"classical_cv.xhtml?u="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Sertifikalar")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"certification.xhtml?u="
										+ getLoggedUser().getTcno());
			} else if (label.equals("İlgi Alanları")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"area_of_interest.xhtml?u="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Kişi Listesi")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"chat_list.xhtml?u="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Fotoğraflar")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"photo.xhtml?u=" + getLoggedUser().getTcno());
			} else if (label.equals("Etkinlikler")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"event.xhtml?u=" + getLoggedUser().getTcno());
			} else if (label.equals("Duyurular")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"announcement.xhtml?u="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Profilim")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"my_profile.xhtml?u=" + getLoggedUser().getTcno());
			}

		}

	}

}
