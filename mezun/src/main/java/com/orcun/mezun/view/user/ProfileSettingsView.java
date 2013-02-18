package com.orcun.mezun.view.user;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.User;

@ManagedBean
@ViewScoped
public class ProfileSettingsView implements Serializable {

	private static final long serialVersionUID = 1L;

	private MindmapNode root;

	private MindmapNode selectedNode;

	private User loggedUser;

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
		MindmapNode posts = new DefaultMindmapNode("Gönderiler", "", "6e9ebf",
				true);

		MindmapNode contactInfo = new DefaultMindmapNode("İletişim Bilgileri",
				"", "6e9ebf", true);

		root.addNode(cv);

		cv.addNode(personalInfo);
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

	public void onNodeSelect(SelectEvent event) throws IOException {

		MindmapNode node = (MindmapNode) event.getObject();

		if (node.getChildren().isEmpty()) {
			Object label = node.getLabel();

			if (label.equals("Kişisel Bilgiler")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"personal_info.xhtml?user="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Üniversite")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"university.xhtml?user="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Lise")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"high_school.xhtml?user="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Yabancı Dil")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"foreign_language.xhtml?user="
										+ getLoggedUser().getTcno());
			} else if (label.equals("İletişim Bilgileri")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"contact_info.xhtml?user="
										+ getLoggedUser().getTcno());
			} else if (label.equals("İş Tecrübeleri")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"job_experience.xhtml?user="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Ek Bilgiler")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"additional_info.xhtml?user="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Klasik CV")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"classical_cv.xhtml?user="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Sertifikalar")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"certification.xhtml?user="
										+ getLoggedUser().getTcno());
			} else if (label.equals("İlgi Alanları")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"area_of_interest.xhtml?user="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Kişi Listesi")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"chat_list.xhtml?user="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Fotoğraflar")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"photo.xhtml?user=" + getLoggedUser().getTcno());
			} else if (label.equals("Etkinlikler")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"event.xhtml?user=" + getLoggedUser().getTcno());
			} else if (label.equals("Duyurular")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"announcement.xhtml?user="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Gönderiler")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"post.xhtml?user=" + getLoggedUser().getTcno());
			}

		}

	}

	public void checkURL() throws IOException {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String userParameter = request.getParameter("user");

		if (userParameter == null || userParameter.equals("")) {

			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"profile_settings.xhtml?user="
									+ getLoggedUser().getTcno());

		} else if (!userParameter.equals(getLoggedUser().getTcno().toString())) {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"profile_settings.xhtml?user="
									+ getLoggedUser().getTcno());

		}
	}
}
