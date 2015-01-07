package com.sybildefender.controller;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sybildefender.model.PossibleDelay;
import com.sybildefender.util.HibernateUtil;

public class PossibleDelayManager extends HibernateUtil  {
	public PossibleDelay add(PossibleDelay possibleDelay){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(possibleDelay);
		session.getTransaction().commit();
		return possibleDelay;
	}
    public PossibleDelay delete(Long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        PossibleDelay possibleDelay = (PossibleDelay) session.load(PossibleDelay.class, id);
        if(null != possibleDelay) {
            session.delete(possibleDelay);
        }
        session.getTransaction().commit();
        return possibleDelay;
    }
 
    public List<PossibleDelay> list() {
         
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<PossibleDelay> possibleDelays = null;
        try {
             
            possibleDelays = (List<PossibleDelay>)session.createQuery("from PossibleDelay").list();
             
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return possibleDelays;
    }
}
