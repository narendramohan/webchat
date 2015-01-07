package com.sybildefender.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Connection")
public class Connection {
	
	private Long idconnection;
	private String NodeName;
	private String Neighbour;
	private Long Cost;
	private Long Delay;
	
	@Id
	   @GeneratedValue
	   @Column(name="Idconnection")
	public Long getIdconnection() {
		return idconnection;
	}
	
	public void setIdconnection(Long idconnection) {
		this.idconnection = idconnection;
	}
	
	@Column(name="NodeName")
	public String getNodeName() {
		return NodeName;
	}
	public void setNodeName(String nodeName) {
		NodeName = nodeName;
	}
	
	@Column(name="Neighbour")
	public String getNeighbour() {
		return Neighbour;
	}
	public void setNeighbour(String neighbour) {
		Neighbour = neighbour;
	}
	
	@Column(name="Cost")
	public Long getCost() {
		return Cost;
	}
	public void setCost(Long cost) {
		Cost = cost;
	}
	
	@Column(name="Delay")
	public Long getDelay() {
		return Delay;
	}
	public void setDelay(Long delay) {
		Delay = delay;
	}
	

}
