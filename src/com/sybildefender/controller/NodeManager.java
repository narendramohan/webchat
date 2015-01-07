package com.sybildefender.controller;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;


import org.hibernate.Session;

import com.sybildefender.model.NodeInformation;
import com.sybildefender.util.HibernateUtil;

public class NodeManager extends HibernateUtil  {
	public NodeInformation add(NodeInformation nodeInformation){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(nodeInformation);
		session.getTransaction().commit();
		return nodeInformation;
	}
    public NodeInformation delete(Long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        NodeInformation nodeInformation = (NodeInformation) session.load(NodeInformation.class, id);
        if(null != nodeInformation) {
            session.delete(nodeInformation);
        }
        session.getTransaction().commit();
        return nodeInformation;
    }
    
    public int deleteAll(){
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    	session.beginTransaction();
    	String hql = "delete from NodeInformation";
        Query query = session.createQuery(hql);
        int row = query.executeUpdate();
        session.beginTransaction();
        return row;
    }
 
    public List<NodeInformation> list() {
         
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<NodeInformation> nodeInformations = null;
        try {
             
            nodeInformations = (List<NodeInformation>)session.createQuery("from NodeInformation").list();
             
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return nodeInformations;
    }
	public List<NodeInformation> get(String nodeName) {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<NodeInformation> nodeInformations = null;
        try {             
            nodeInformations = (List<NodeInformation>)session.createQuery("from NodeInformation where NodeName like '"+nodeName+"'").list();             
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return nodeInformations;
		
	}
}
