package com.chat.application.dao.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.chat.application.dao.NodeDao;
import com.chat.application.domain.Node;
import com.chat.application.domain.User;

@Service("nodeDao")
public class NodeDaoImpl implements NodeDao {
	@PersistenceContext
	private EntityManager entityManager;

	public List<Node> listNode() {
		return entityManager.createQuery("FROM Node").getResultList();
	}

	public boolean isNodeExists(int id) {
		Query query = entityManager
				.createQuery("FROM Node u WHERE u.id = :id");
		query.setParameter("id", id);
		List list = query.getResultList();
		if(list.size()>0)
			return true;
		else
			return false;
	}

	public void addNode(Node node) {
		entityManager.merge(node);
		
	}

	public Node getNode(int id) {
		Query query = entityManager
				.createQuery("FROM Node u WHERE u.id = :id");
		query.setParameter("id", id);
		Node node = (Node) query.getSingleResult();
		return node;
	}

	public void deleteNode(Node node) {
		Node node1 = entityManager.merge(node);
		entityManager.remove(node1);
		
	}

	public Node getNode(String uName) {
		Query query = entityManager
				.createQuery("FROM Node u WHERE u.userName = :uName");
		query.setParameter("uName", uName);
		try {
			Node node = (Node) query.getSingleResult();
			return node;
		} catch (Exception e){
			return null;
		}
	}

}
