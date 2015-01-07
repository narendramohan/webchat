package com.sybildefender.controller;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.chat.application.domain.User;
import com.sybildefender.util.HibernateUtil;

public class LoginManager extends HibernateUtil 
{
	public boolean isValidUser(String userName, String password){
		boolean isValid = false;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		 List<User> user = null;
        try {             
            user = (List<User>)session.createQuery("from User where ucase(login_id) like ucase('"+userName+"') and password like '"+password+"'").list();
            if(user.size()>0) isValid = true;
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
		return isValid;
	}

}
