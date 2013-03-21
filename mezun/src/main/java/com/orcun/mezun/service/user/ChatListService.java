package com.orcun.mezun.service.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.ChatListDAO;
import com.orcun.mezun.model.ChatGroup;
import com.orcun.mezun.model.ChatList;
import com.orcun.mezun.model.ChatPerson;
import com.orcun.mezun.model.User;

@Service
public class ChatListService {
	
	@Autowired
	private ChatListDAO chatListDAO;


	public ChatListDAO getChatListDAO() {
		return chatListDAO;
	}


	public void setChatListDAO(ChatListDAO chatListDAO) {
		this.chatListDAO = chatListDAO;
	}


	public ChatList findChatListByUser(User user) {
		if (user != null) {
			return getChatListDAO().findChatListByUser(user);
		} else {
			return null;
		}

	}


	public void updateChatList(ChatList chatList) {
		getChatListDAO().updateChatList(chatList);
	}


	public void addChatList(ChatList chatList) {
		getChatListDAO().addChatList(chatList);
	}


	public void addChatGroup(ChatGroup chatGroup) {
		getChatListDAO().addChatGroup(chatGroup);
	}


	public void addChatPerson(ChatPerson newChatPerson) {
		getChatListDAO().addChatPerson(newChatPerson);
		
	}


	public void deleteChatGrup(ChatGroup selectedChatGroup) {
		getChatListDAO().deleteChatGroup(selectedChatGroup);
	}


	public void updateChatGroup(ChatGroup chatGroup) {
		getChatListDAO().updateChatGroup(chatGroup);
	}


	public void deleteChatPerson(ChatPerson selectedChatPerson) {
		getChatListDAO().deleteChatPerson(selectedChatPerson);
		
	}




}
