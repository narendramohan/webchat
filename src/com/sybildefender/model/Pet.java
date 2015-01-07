package com.sybildefender.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Embeddable
@Table(name="pet")
public class Pet {
	private String name;
	private String owner;
	private String species;
	private String sex;
    private java.sql.Date birth;
    private java.sql.Date death;
	
    @Column(name="name")
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="owner")
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	@Column(name="species")
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	
	@Column(name="sex")
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Column(name="birth")
	public java.sql.Date getBirth() {
		return birth;
	}
	public void setBirth(java.sql.Date birth) {
		this.birth = birth;
	}
	
	@Column(name="death")
	public java.sql.Date getDeath() {
		return death;
	}
	public void setDeath(java.sql.Date death) {
		this.death = death;
	}
    
    
    

    
}
