package com.sybildefender.controller;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.sybildefender.model.PDA;
import com.sybildefender.util.HibernateUtil;

public class PDAManager extends HibernateUtil  {
	public PDA add(PDA pda){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(pda);
		session.getTransaction().commit();
		return pda;
	}
    public PDA delete(Long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        PDA pda = (PDA) session.load(PDA.class, id);
        if(null != pda) {
            session.delete(pda);
        }
        session.getTransaction().commit();
        return pda;
    }
 
    public int deleteAll(){
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    	session.beginTransaction();
    	String hql = "delete from PDA";
        Query query = session.createQuery(hql);
        int row = query.executeUpdate();
        session.beginTransaction();
        return row;
    }
    
    public List<PDA> list() {
         
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<PDA> pdas = null;
        try {
             
            pdas = (List<PDA>)session.createQuery("from PDA").list();
             
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return pdas;
    }
}
