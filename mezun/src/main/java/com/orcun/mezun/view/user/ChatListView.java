package com.orcun.mezun.view.user;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.ChatGroup;
import com.orcun.mezun.model.ChatList;
import com.orcun.mezun.model.ChatPerson;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.ChatListService;

@ManagedBean
@ViewScoped
public class ChatListView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private ChatList chatList;

	private ChatGroup chatGroup;

	private ChatGroup selectedChatGroup;

	private List<ChatGroup> chatGroups = new ArrayList<ChatGroup>();

	private User searchedPerson;

	@ManagedProperty(value = "#{chatListService}")
	private ChatListService chatListService;

	@PostConstruct
	public void init() {
		if (getChatListService().findChatListByUser(getLoggedUser()) == null) {
			this.chatList = new ChatList();
			getChatList().setUser(getLoggedUser());
			getChatListService().addChatList(getChatList());
		} else {
			setChatList(getChatListService()
					.findChatListByUser(getLoggedUser()));
		}

		if (chatGroup == null) {
			chatGroup = new ChatGroup();
		}

	}

	public void initSelectedChatGroup(ChatGroup selectedChatGroup) {
		this.selectedChatGroup = selectedChatGroup;
	}
	
	public void deleteSelectedChatGroup(ChatGroup selectedChatGroup) throws IOException{
		
		getChatListService().deleteChatGrup(selectedChatGroup);
		
		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("chat_list.xhtml?user=" + getLoggedUser().getTcno());
		
	}
	
	public void deleteSelectedChatPerson(ChatPerson selectedChatPerson) throws IOException{
		
		getChatListService().deleteChatPerson(selectedChatPerson);
		
		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("chat_list.xhtml?user=" + getLoggedUser().getTcno());
		
	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public ChatList getChatList() {
		return chatList;
	}

	public void setChatList(ChatList chatList) {
		this.chatList = chatList;
	}

	public ChatGroup getChatGroup() {
		return chatGroup;
	}

	public void setChatGroup(ChatGroup chatGroup) {
		this.chatGroup = chatGroup;
	}

	public ChatGroup getSelectedChatGroup() {
		return selectedChatGroup;
	}

	public void setSelectedChatGroup(ChatGroup selectedChatGroup) {
		this.selectedChatGroup = selectedChatGroup;
	}

	public List<ChatGroup> getChatGroups() {
		return chatGroups;
	}

	public void setChatGroups(List<ChatGroup> chatGroups) {
		this.chatGroups = chatGroups;
	}

	public User getSearchedPerson() {
		return searchedPerson;
	}

	public void setSearchedPerson(User searchedPerson) {
		this.searchedPerson = searchedPerson;
	}

	public ChatListService getChatListService() {
		return chatListService;
	}

	public void setChatListService(ChatListService chatListService) {
		this.chatListService = chatListService;
	}
	
	public boolean findPersonInChatGroup(User user){
		
		int size=getSelectedChatGroup().getChatPersons().size();
		boolean validate=false;
		
		for(int i=0;i<size;i++){
			
			if(getSelectedChatGroup().getChatPersons().get(i).getUser().equals(user)){
				validate=true;
			}			
		}
		
		if(validate){
			return true;
		}else{
			return false;
		}
		
	}

	public void followSearchedPerson(User searchedPerson) throws IOException {
		
		if(!findPersonInChatGroup(searchedPerson)){
			this.searchedPerson = searchedPerson;

			ChatPerson newChatPerson = new ChatPerson();
			newChatPerson.setUser(searchedPerson);
			newChatPerson.setChatGroup(getSelectedChatGroup());
			
			int indexChatGroupInChatList=getChatList().getChatGroups().indexOf(getSelectedChatGroup());
			getChatList().getChatGroups().get(indexChatGroupInChatList).getChatPersons().add(newChatPerson);
			
			getChatListService().updateChatList(getChatList());
			
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("chat_list.xhtml?user=" + getLoggedUser().getTcno());

		}else{
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Ekleme Hatası", "Eklemek istediğiniz kişi grubunuzun içerisinde bulunmaktadır.");
			FacesContext.getCurrentInstance().addMessage("form_tcno", fm);
		}
		
	}

	public void addChatGroup() throws IOException {
		try {

			
			getChatGroup().setChatList(getChatList());
			getChatList().getChatGroups().add(getChatGroup());
			getChatListService().updateChatList(getChatList());
			

			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"chat_list.xhtml?user=" + getLoggedUser().getTcno());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}

}
