package com.sybildefender.view;

import java.util.List;

import com.sybildefender.controller.ContactManager;
import com.sybildefender.model.Contact;
 
import com.opensymphony.xwork2.ActionSupport;
 
 
public class ContactAction extends ActionSupport {
 
    private static final long serialVersionUID = 9149826260758390091L;
    private Contact contact;
    private List<Contact> contactList;
    private Long id;
 
    private ContactManager contactManager;
 
    public ContactAction() {
        contactManager = new ContactManager();
    }
 
    public String execute() {
        this.contactList = contactManager.list();
        System.out.println("execute called");
        return SUCCESS;
    }
 
    public String add() {
        System.out.println(getContact());
        try {
            contactManager.add(getContact());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.contactList = contactManager.list();
        return SUCCESS;
    }
 
    public String delete() {
        contactManager.delete(getId());
        return SUCCESS;
    }
 
    public Contact getContact() {
        return contact;
    }
 
    public List<Contact> getContactList() {
        return contactList;
    }
 
    public void setContact(Contact contact) {
        this.contact = contact;
    }
 
    public void setContactList(List<Contact> contactsList) {
        this.contactList = contactsList;
    }
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
}