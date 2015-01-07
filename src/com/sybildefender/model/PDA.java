package com.sybildefender.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pda")
public class PDA {
	private String path;
	private Double cost;
	private Double delay;
	private String node;
	@Id
	@Column(name="path")
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	@Column(name="cost")
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	@Column(name="delay")
	public Double getDelay() {
		return delay;
	}
	public void setDelay(Double delay) {
		this.delay = delay;
	}
	
	@Column(name="node")
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	
	

}
