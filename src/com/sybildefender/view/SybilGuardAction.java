package com.sybildefender.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



import com.opensymphony.xwork2.ActionSupport;

public class SybilGuardAction extends ActionSupport {
	static ServerSocket sersoc;
	static Socket soc;
	static networkThread nt=null;
	public String execute() {
		System.out.println("SybilGuardAction ");
		try {

			if (sersoc == null)
				sersoc = new ServerSocket(5000);
			if(nt==null) {
				nt = new networkThread();
				nt.start();
			}
			System.out.println(nodeList);
		} catch (Exception ex) {
			System.out.println(" ");
			System.out.println(ex);
		}
		return SUCCESS;
	}
	class networkThread extends Thread {
		public void run(){
			while (true) {
				System.out.println("Sybil guard thread running");
				try {
					soc = sersoc.accept();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				try {
					
					sybilRegionDetection();
					System.out.println("listening the port");
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		}
		
	}
	String z1[];
	String z2[];
	public void sybilRegionDetection() throws Exception {

		System.out.println("recived1");
		ObjectInputStream dis = new ObjectInputStream(soc.getInputStream());
		System.out.println("recived2");
		String nname = (String) dis.readObject();
		System.out.println("recived:" + nname);
		String z1[] = nname.split("&");
		dis = new ObjectInputStream(soc.getInputStream());
		nname = (String) dis.readObject();
		String z2[] = nname.split("&");
		String z1path = z1[1];
		String z2path = z2[1];

		String result = IDPFMatching(z1path, z2path);
		Node n = new Node();
		
		n.setNodeId(z1[0]);
		n.setHeader(z1[1]);
		n.setUser(z1[2]);

		adddat1(n);

		ObjectOutputStream dos = new ObjectOutputStream(soc.getOutputStream());
		dos.writeObject(result);

	}

	public String IDPFMatching(String z1p, String z2p) throws Exception {
		String zp1 = z1p;
		String zp2 = z2p;
		String result = "";
		Node n;
		if (zp1.equals(zp2)) {
			result = "yes";
			System.out.println("z2[2]: "+z2[2]+"--z1[2]: "+z1[2]);
			n = new Node();
			//n.setNodeId(z2[2]);
			n.setHeader(z1[2]);
			n.setUser("Normal User");
			adddat2(n);
		} else {
			result = "no";
			System.out.println("z2[2]: "+z2[2]+"--z1[2]: "+z1[2]);
			n = new Node();
			//n.setNodeId(z2[2]);
			n.setHeader(z1[2]);
			n.setUser("Attacker");
			adddat2(n);
		}

		return result;
	}
	
	class Node {
		String nodeId;
		String header;
		String user;
		public String getNodeId() {
			return nodeId;
		}
		public void setNodeId(String nodeId) {
			this.nodeId = nodeId;
		}
		public String getHeader() {
			return header;
		}
		public void setHeader(String header) {
			this.header = header;
		}
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		
	}
	private static List<Node> nodeList = new ArrayList();
	 
	public List<Node> getComboMeals() {
		return nodeList;
	}
 
	public void setComboMeals(List<Node> nodeList) {
		this.nodeList = nodeList;
	}
	public void adddat1(Node dat) {
		nodeList.add(dat);
	}

	public void adddat2(Node dat1) {
		nodeList.add(dat1);
	}

}
