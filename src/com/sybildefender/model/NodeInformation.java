package com.sybildefender.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="NodeInformation")
public class NodeInformation {
	private Long idNodeInformation;
	private String NodeName;
    private Long PortNo;
    private String SystemName;
    private String Status;
    private String NodeId;
    
	@Id
	   @GeneratedValue
	   @Column(name="idNodeInformation")
	public Long getIdNodeInformation() {
		return idNodeInformation;
	}
	@Column(name="NodeName")
	public String getNodeName() {
		return NodeName;
	}
	@Column(name="PortNo")
	public Long getPortNo() {
		return PortNo;
	}
	@Column(name="SystemName")
	public String getSystemName() {
		return SystemName;
	}
	@Column(name="Status")
	public String getStatus() {
		return Status;
	}
	@Column(name="NodeId")
	public String getNodeId() {
		return NodeId;
	}

	public void setIdNodeInformation(Long idNodeInformation) {
		this.idNodeInformation = idNodeInformation;
	}
	public void setNodeName(String nodeName) {
		NodeName = nodeName;
	}
	public void setPortNo(Long portNo) {
		PortNo = portNo;
	}
	public void setSystemName(String systemName) {
		SystemName = systemName;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public void setNodeId(String nodeId) {
		NodeId = nodeId;
	}

    
}
