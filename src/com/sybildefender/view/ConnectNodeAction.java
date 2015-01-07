package com.sybildefender.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;



import org.hibernate.Session;

import com.opensymphony.xwork2.ActionSupport;
import com.sybildefender.controller.ConnectionManager;
import com.sybildefender.controller.NodeManager;
import com.sybildefender.model.Connection;
import com.sybildefender.model.NodeInformation;
import com.sybildefender.util.HibernateUtil;


public class ConnectNodeAction  extends ActionSupport {
 
    private static final long serialVersionUID = 9149826260758390091L;
    private NodeInformation nodeInformation;
    private List<NodeInformation> nodeInformationList;
    private Long id;
    private String message;
    private NodeManager nodeManager;
    private ConnectionManager connectionManager;
 
    public ConnectNodeAction() {
        nodeManager = new NodeManager();
        connectionManager = new ConnectionManager();
    }
 

    public String execute() {
    	connect();
    	myFlag1="true";
    	myFlag2="false";
        return SUCCESS;
    }
    private static String nNode="0";
    private String nodeName;
    private String noOfNode;
    private String sourceNode;
    private String neighbourNode;
    
    List<Select> sourceNodeList = new ArrayList<Select>();
    
    class Select {
    	String id;
    	String name;
    	
    	public String getName(){
    		return name;
    	}
    	
    	public String getId(){
    		return id;
    	}
    	
    	public void setName(String name){
    		this.name=name;
    	}
    	
    	public void setId(String id){
    		this.id=id;
    	}
    }
    private void doClear() {
    	nodeName="";
    	sourceNode="0";
    	neighbourNode="0";
   	    	
    }
    
	public String addNode() {
		boolean isAdded = false;
		String nodeName = getNodeName();

		try {
			List<NodeInformation> list= nodeManager.get(nodeName);
			if (list.size()>0) {
				message=//JOptionPane.showMessageDialog(this,
						"The given Data already Exists";
			} else {
				NodeInformation node = new NodeInformation();
				node.setNodeName(nodeName);
				node.setPortNo(new Long(0));
				node.setSystemName("192.168.1.4");
				node.setStatus("ON");
				nodeManager.add(node);
				message=		"Registration  Sucess fully Completed";
				isAdded = true;
			}
			connect();
			myFlag1="false";
			myFlag2="true";
		} catch (Exception ee) {
			message = "Specify the Correct PortNo";
			System.out.println("Connectivity Error");
			ee.printStackTrace();
		}
		return SUCCESS;

	}

	private String getNodeName() {
		// TODO Auto-generated method stub
		return nodeName;
	}

	public String doConnection() {
		try {

			if (sourceNode.equals("Select") || neighbourNode.equals("Select")) {
				message = "Specify the Nodes";
			} else if (sourceNode.equals(neighbourNode)) {
				message ="Specify a Valid Neighbour";
			}

			else {

				try {
					List list = connectionManager.getConnection("from Connection where NodeName LIKE '"
							+ sourceNode + "' AND Neighbour LIKE '" + neighbourNode + "'"); // OR
																			// SystemName
																			// LIKE
																			// '"+sysname+"'
					System.out.println("1");
					if (list.size()>0) {
						//JOptionPane.showMessageDialog(this,
						message = "The given Data already Exists";
					} else {
						Connection connection = new Connection();
						connection.setNodeName(sourceNode);
						connection.setNeighbour(neighbourNode);
						connection.setCost(1L);
						connection.setDelay(0L);
						System.out.println(sourceNode+" "+neighbourNode+" "+1+" "+0);
						connectionManager.add(connection);
						
						connection = new Connection();
						connection.setNodeName(neighbourNode);
						connection.setNeighbour(sourceNode);
						connection.setCost(1L);
						connection.setDelay(0L);
						System.out.println(neighbourNode+" "+sourceNode+" "+1+" "+0);
						connectionManager.add(connection);
						message = "Connection Information Completed";
					}
					connect();
					myFlag1="false";
					myFlag2="true";
				} catch (Exception ee) {
					message = "Connectivity Error";
					System.out.println("Connectivity Error");
					ee.printStackTrace();
				}

			}
		}

		catch (Exception e3) {
			message = "Exception: "+e3.getMessage();
			e3.printStackTrace();

		}
		addActionMessage(myFlag1);
		
		return SUCCESS;

	}

	private String myFlag1;
	private String myFlag2;
	public String doOk(){
		try {
			int n = Integer.parseInt(noOfNode);
			System.out.println("n:" + n);
			if (n > 0) {
				myFlag1="false";
				myFlag2="true";
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Please Enter Value");
		}
		return SUCCESS;
	}
	List totalpeer = new ArrayList();
	public List connect() {

		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<String> rs = session.createSQLQuery("select NodeName from NodeInformation").list();
			totalpeer.add("Select");
			Select select=null;
			for (String ni:rs) {
				select=new Select();
				select.setId(ni);
				select.setName(ni);
				sourceNodeList.add(select);
				totalpeer.add(ni);
				System.out.println("" + totalpeer);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return totalpeer;
	}
    public String add() {
        System.out.println(getNodeInformation());
        try {
            nodeManager.add(getNodeInformation());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.nodeInformationList = nodeManager.list();
        return SUCCESS;
    }
 
    public String delete() {
        nodeManager.delete(getId());
        return SUCCESS;
    }
 
    public NodeInformation getNodeInformation() {
        return nodeInformation;
    }
 
    public List<NodeInformation> getNodeInformationList() {
        return nodeInformationList;
    }
 
    public void setContact(NodeInformation nodeInformation) {
        this.nodeInformation = nodeInformation;
    }
 
    public void setContactList(List<NodeInformation> contactsList) {
        this.nodeInformationList = contactsList;
    }
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getNoOfNode() {
		return noOfNode==null || "".equals(noOfNode.trim())?nNode:noOfNode;
	}


	public void setNoOfNode(String noOfNode) {
		this.noOfNode = noOfNode;
	}


	public String getSourceNode() {
		return sourceNode;
	}
	public void setSourceNode(String sourceNode) {
		this.sourceNode = sourceNode;
	}
	public List<Select> getSourceNodeList() {
		return sourceNodeList;
	}
	public void setSourceNodeList(List<Select> sourceNodeList) {
		this.sourceNodeList = sourceNodeList;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}


	public String getNeighbourNode() {
		return neighbourNode;
	}


	public void setNeighbourNode(String neighbourNode) {
		this.neighbourNode = neighbourNode;
	}


	public String getMyFlag1() {
		return myFlag1;
	}


	public String getMyFlag2() {
		return myFlag2;
	}
}