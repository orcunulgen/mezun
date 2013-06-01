package com.orcun.mezun.view.user.init;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.init.InitStudentInfoService;

@ManagedBean
@ViewScoped
public class InitStudentInfoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private MindmapNode root;

	private MindmapNode selectedNode;

	private User loggedUser;
	
	@ManagedProperty(value = "#{initStudentInfoService}")
	private InitStudentInfoService initStudentInfoService;

	@PostConstruct
	public void init() {


		root = new DefaultMindmapNode(getLoggedUser().getName() + " "
				+ loggedUser.getSurname(), "", "FFCC00", false);

		MindmapNode contactInfo=null;
		MindmapNode parentInfo=null;
		MindmapNode highSchool=null;
		MindmapNode university=null;
		
		if(getInitStudentInfoService().areThereContactInfo(getLoggedUser())){
			contactInfo = new DefaultMindmapNode("İletişim Bilgileri",
					"", "14ed11", true);	
		}else{
			contactInfo = new DefaultMindmapNode("İletişim Bilgileri",
					"", "ed1111", true);
		}
		
		if(getInitStudentInfoService().areThereParentInfo(getLoggedUser())){
			parentInfo = new DefaultMindmapNode("Aile Bilgileri", "",
					"14ed11", true);
		}else{
			parentInfo = new DefaultMindmapNode("Aile Bilgileri", "",
					"ed1111", true);
		}
		
		if(getInitStudentInfoService().areThereHighSchoolInfo(getLoggedUser())){
			highSchool = new DefaultMindmapNode("Lise", "", "14ed11",
					true);
		}else{
			highSchool = new DefaultMindmapNode("Lise", "", "ed1111",
					true);
		}
		
		if(getInitStudentInfoService().areThereUniversity(getLoggedUser())){
			university = new DefaultMindmapNode("Üniversite", "",
					"14ed11", true);
		}else{
			university = new DefaultMindmapNode("Üniversite", "",
					"ed1111", true);
		}


		root.addNode(contactInfo);
		root.addNode(parentInfo);
		root.addNode(highSchool);
		root.addNode(university);

	}

	public MindmapNode getRoot() {
		return root;
	}

	public void setRoot(MindmapNode root) {
		this.root = root;
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

	public InitStudentInfoService getInitStudentInfoService() {
		return initStudentInfoService;
	}

	public void setInitStudentInfoService(
			InitStudentInfoService initStudentInfoService) {
		this.initStudentInfoService = initStudentInfoService;
	}

	public void onNodeSelect(SelectEvent event) throws IOException {

		MindmapNode node = (MindmapNode) event.getObject();

		if (node.getChildren().isEmpty()) {
			Object label = node.getLabel();

			if (label.equals("Aile Bilgileri")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"student_parent_info.xhtml?u="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Üniversite")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"student_university.xhtml?u="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Lise")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"student_highschool.xhtml?u="
										+ getLoggedUser().getTcno());
			} else if (label.equals("İletişim Bilgileri")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"student_contact_info.xhtml?u="
										+ getLoggedUser().getTcno());
			} 
		}

	}

}
