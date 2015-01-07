package com.sybildefender.view;

import com.opensymphony.xwork2.ActionSupport;
import com.sybildefender.model.User;
import com.sybildefender.util.HibernateUtil;

import java.sql.*;  
import java.util.ArrayList;  
import java.util.List;

import org.hibernate.Session;

public class UserListAction extends ActionSupport {
	
	   
	List<User> list=new ArrayList<User>();  
	  
	public List<User> getList() {  
	    return list;  
	}  
	public void setList(ArrayList<User> list) {  
	    this.list = list;  
	}  
	public String execute(){  
	 try{  
	 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	 session.beginTransaction();
	 list = (List<User>)session.createQuery("from User").list(); 
	 // while(rs.next()){  
	  /* User user=new User();  
	   user.setId(rs.getInt(1));  
	   //user.setName(rs.getString(2));  
	   user.setPassword(rs.getString(2));  
	   user.setEmail(rs.getString(3));  
	   list.add(user);  
	  }  */
	  
	  session.getTransaction().commit(); 
	 }catch(Exception e){e.printStackTrace();}  
	          
	 return "success";  
	}  

}
