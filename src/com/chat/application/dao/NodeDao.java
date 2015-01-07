package com.chat.application.dao;

import java.util.List;

import com.chat.application.domain.Node;

public interface NodeDao {

	List<Node> listNode();

	boolean isNodeExists(int id);

	void addNode(Node node);

	Node getNode(int id);

	void deleteNode(Node node);

	Node getNode(String uName);

}
