package com.orcun.mezun.view.user.showcase;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.ChatGroup;
import com.orcun.mezun.model.ChatList;
import com.orcun.mezun.model.ChatPerson;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.admin.InactiveUserService;
import com.orcun.mezun.service.user.ChatListService;
import com.orcun.mezun.util.StringConvertUtil;

@ManagedBean
@ViewScoped
public class ShowChatListView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private ChatList chatList;

	private ChatGroup selectedChatGroup;

	private List<ChatGroup> chatGroups = new ArrayList<ChatGroup>();

	@ManagedProperty(value = "#{chatListService}")
	private ChatListService chatListService;

	@ManagedProperty(value = "#{inactiveUserService}")
	private InactiveUserService inactiveUserService;

	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		Long tcno = StringConvertUtil.stringToLong(request.getParameter("u"));

		if (tcno != null) {
			User tmp = new User();
			tmp.setTcno(tcno);

			tmp = getInactiveUserService().findUserByTcno(tmp);
			if (tmp != null) {
				this.loggedUser = tmp;
			}
		}

		// real loggedUser
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		User realLoggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();
		//

		if (getLoggedUser() == null || getLoggedUser().equals(realLoggedUser)) {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		setChatList(getChatListService().findChatListByUser(getLoggedUser()));

	}

	public void initSelectedChatGroup(ChatGroup selectedChatGroup) {
		this.selectedChatGroup = selectedChatGroup;
	}

	public void showSelectedChatPerson(ChatPerson selectedChatPerson)
			throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("index.xhtml?u=" + selectedChatPerson.getUser().getTcno());
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public ChatList getChatList() {
		return chatList;
	}

	public void setChatList(ChatList chatList) {
		this.chatList = chatList;
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

	public ChatListService getChatListService() {
		return chatListService;
	}

	public void setChatListService(ChatListService chatListService) {
		this.chatListService = chatListService;
	}

	public InactiveUserService getInactiveUserService() {
		return inactiveUserService;
	}

	public void setInactiveUserService(InactiveUserService inactiveUserService) {
		this.inactiveUserService = inactiveUserService;
	}

}
