package com.chat.application.service;

import java.util.List;

import com.chat.application.domain.Node;

public interface NodeServie {

	public List<Node> listNode();

	public boolean isNodeExists(int id);

	public void addNode(Node node);

	public Object getNode(int id);

	public void deleteNode(Node node);

	public Node getNode(String uName);

}
