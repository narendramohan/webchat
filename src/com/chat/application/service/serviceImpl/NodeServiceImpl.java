package com.chat.application.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chat.application.dao.NodeDao;
import com.chat.application.domain.Node;
import com.chat.application.service.NodeServie;
@Service("nodeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NodeServiceImpl implements NodeServie {

	@Autowired
	private NodeDao nodeDao;
	public List<Node> listNode() {
		return nodeDao.listNode();
	}
	public boolean isNodeExists(int id) {
		return nodeDao.isNodeExists(id);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addNode(Node node) {
		 nodeDao.addNode(node);
		
	}
	public Node getNode(int id) {
		return nodeDao.getNode(id);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteNode(Node node) {
		nodeDao.deleteNode(node);
		
	}
	public Node getNode(String uName) {
		return nodeDao.getNode(uName);
	}

}
