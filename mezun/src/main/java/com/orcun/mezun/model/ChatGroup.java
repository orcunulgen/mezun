package com.orcun.mezun.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name="chat_group")
public class ChatGroup implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="group_name",nullable=false,length=200)
	private String groupName;
	
	@ManyToOne
	@JoinColumn(name="chat_list_id")
    private ChatList chatList;
	
	@OneToMany(mappedBy="chatGroup",fetch= FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
    private List<ChatPerson> chatPerson=new ArrayList<ChatPerson>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public ChatList getChatList() {
		return chatList;
	}

	public void setChatList(ChatList chatList) {
		this.chatList = chatList;
	}

	public List<ChatPerson> getChatPerson() {
		return chatPerson;
	}

	public void setChatPerson(List<ChatPerson> chatPerson) {
		this.chatPerson = chatPerson;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatGroup other = (ChatGroup) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
