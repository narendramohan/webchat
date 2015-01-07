package com.sybildefender.controller;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.sybildefender.model.PossiblePath;
import com.sybildefender.util.HibernateUtil;

public class PossiblePathManager extends HibernateUtil  {
	public PossiblePath add(PossiblePath possiblePath){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(possiblePath);
		session.getTransaction().commit();
		session.close();
		return possiblePath;
	}
    public PossiblePath delete(Long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        PossiblePath possiblePath = (PossiblePath) session.load(PossiblePath.class, id);
        if(null != possiblePath) {
            session.delete(possiblePath);
        }
        session.getTransaction().commit();
        return possiblePath;
    }
    
    public int deleteAll(){
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    	session.beginTransaction();
    	String hql = "delete from PossiblePath";
        Query query = session.createQuery(hql);
        int row = query.executeUpdate();
        session.beginTransaction();
        return row;
    }
 
    public List<PossiblePath> list() {
         
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<PossiblePath> possiblePaths = null;
        try {
             
            possiblePaths = (List<PossiblePath>)session.createQuery("from PossiblePath").list();
             
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return possiblePaths;
    }
}
