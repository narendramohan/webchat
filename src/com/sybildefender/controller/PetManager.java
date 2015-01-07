package com.sybildefender.controller;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sybildefender.model.Pet;
import com.sybildefender.util.HibernateUtil;

public class PetManager extends HibernateUtil  {
	public Pet add(Pet pet){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(pet);
		session.getTransaction().commit();
		return pet;
	}
    public Pet delete(Long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Pet pet = (Pet) session.load(Pet.class, id);
        if(null != pet) {
            session.delete(pet);
        }
        session.getTransaction().commit();
        return pet;
    }
 
    public List<Pet> list() {
         
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Pet> pets = null;
        try {
             
            pets = (List<Pet>)session.createQuery("from Pet").list();
             
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return pets;
    }
}
