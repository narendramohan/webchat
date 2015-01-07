package com.chat.application.dao;

import java.util.List;

import com.chat.application.domain.Friend;

public interface FriendDao {
	public void inviteUser(Friend friend);
	public boolean checkUserAleadyinvited(Friend friend);
	public List<Friend> getInviteeList(String userName);
	public void accept(Friend friend);
	public Friend reject(Friend friend);
	public List<Friend> getFriends(String userName);
	int getRejectedRequestCount(String userName);
}
