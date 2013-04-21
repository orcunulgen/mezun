package com.orcun.mezun.view.admin;

import java.io.IOException;
import java.io.Serializable;

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
public class AdminPanelView implements Serializable {

	private static final long serialVersionUID = 1L;

	private MindmapNode root;

	private MindmapNode selectedNode;

	private User loggedUser;
	
	private Boolean isAdmin;
	
	private MezunAuthenticationSuccessHandler mezunAuthenticationSuccessHandler;

	public AdminPanelView() {

		/*
		 * ClassPathXmlApplicationContext appContext = new
		 * ClassPathXmlApplicationContext( new
		 * String[]{"appContext/appContext-mezun.xml"
		 * ,"appContext/securityContext.xml"});
		 */
		this.mezunAuthenticationSuccessHandler=new MezunAuthenticationSuccessHandler();
		
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();


		root = new DefaultMindmapNode(getLoggedUser().getName() + " "
				+ loggedUser.getSurname(), "", "FFCC00", false);

		// User List
		MindmapNode userList = new DefaultMindmapNode("Kullanıcı Listesi", "", "6e9ebf", true);

		MindmapNode student = new DefaultMindmapNode("Öğrenciler",
				"", "6e9ebf", true);

		MindmapNode alumni = new DefaultMindmapNode("Mezunlar", "",
				"6e9ebf", true);


		root.addNode(userList);
		
		userList.addNode(student);
		userList.addNode(alumni);

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

		return loggedUser;
	}

	public Boolean getIsAdmin() {
		
		if(this.mezunAuthenticationSuccessHandler.hasRole("ROLE_ADMIN", getLoggedUser())){
			isAdmin=true;
		}else{
			isAdmin=false;
		}
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void onNodeSelect(SelectEvent event) throws IOException {

		MindmapNode node = (MindmapNode) event.getObject();

		if (node.getChildren().isEmpty()) {
			Object label = node.getLabel();

			if (label.equals("Öğrenciler")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"student.xhtml?user="
										+ getLoggedUser().getTcno());
			} else if (label.equals("Mezunlar")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"alumni.xhtml?user="
										+ getLoggedUser().getTcno());
			} 
		}

	}

}
