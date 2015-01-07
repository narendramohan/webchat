package com.sybildefender.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory =buildSessionFactory();
	
	private static SessionFactory buildSessionFactory(){
		try{
			//Create the Session Factory from hibernate.cfg.xml
			return new AnnotationConfiguration().configure().buildSessionFactory();
		} catch(Throwable t){
			System.err.println("Initial SessionFactory creation failed."+t);
			throw new ExceptionInInitializerError(t);
		}
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}

}
