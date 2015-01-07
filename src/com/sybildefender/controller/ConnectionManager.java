package com.sybildefender.controller;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;


import org.hibernate.Session;

import com.sybildefender.model.Connection;
import com.sybildefender.model.NodeInformation;
import com.sybildefender.util.HibernateUtil;

public class ConnectionManager extends HibernateUtil  {
	public Connection add(Connection connection){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(connection);
		session.getTransaction().commit();
		return connection;
	}
    public Connection delete(Long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Connection connection = (Connection) session.load(Connection.class, id);
        if(null != connection) {
            session.delete(connection);
        }
        session.getTransaction().commit();
        return connection;
    }
    
    public int deleteAll(){
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    	session.beginTransaction();
    	String hql = "delete from Connection";
        Query query = session.createQuery(hql);
        int row = query.executeUpdate();
        session.beginTransaction();
        return row;
    }
 
    public List<Connection> list() {
         
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Connection> connections = null;
        try {
             
            connections = (List<Connection>)session.createQuery("from Connection").list();
             
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return connections;
    }
	public List<Connection> getConnection(String query) {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	        session.beginTransaction();
	        List<Connection> connection = null;
	        try {             
	            connection = (List<Connection>)session.createQuery(query).list();             
	        } catch (HibernateException e) {
	            e.printStackTrace();
	            session.getTransaction().rollback();
	        }
	        session.getTransaction().commit();
	        return connection;
			
	}
}
