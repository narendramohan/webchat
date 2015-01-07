package com.sybildefender.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;



import org.hibernate.Session;

import com.opensymphony.xwork2.ActionSupport;
import com.sybildefender.controller.LinkManager;
import com.sybildefender.model.Connection;
import com.sybildefender.model.NodeInformation;
import com.sybildefender.util.HibernateUtil;

public class NetworkAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static ServerSocket sersoc;
	static Socket soc, soc1;
	ObjectInputStream dis, dis1;
	ObjectOutputStream dos, dos1;
	InputStream is;
	OutputStream os;

	String n = "", ip = "";
	String a[] = new String[100];

	int portno = 5000, port;
	LinkManager find;
	int number;
	
	String bgp = "127.0.0.1";
	public String execute() throws Exception{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			session.createQuery("delete NodeInformation").executeUpdate();
			session.createQuery("delete Connection").executeUpdate();
			session.createQuery("delete PDA").executeUpdate();
			session.createQuery("delete PossiblePath").executeUpdate();
			session.getTransaction().commit();
		
			if(sersoc==null) {
				sersoc = new ServerSocket(1500);
				System.out.println("Openitn server socket on port 1500");
				networkThread nt =	new networkThread();
				nt.start();
			}
			
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception ex) {
			System.out.println("Failed loading L&F: ");
			System.out.println(ex);
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return SUCCESS;
	}
	
	class networkThread extends Thread {
		public void run(){
			while (true) {
				try {
					soc = sersoc.accept();
					System.out.println("Socket is open");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				try {
					
					listen();
					System.out.println("listening the port");
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		}
		
	}
	List list=null;
	Vector v, v1, v2;
	
	public void listen() throws Exception {

		int port = 0;
		String nodeid1 = "", nodeid2 = "";
		dis = new ObjectInputStream(soc.getInputStream());
		String request = (String) dis.readObject();
		System.out.println("recived:" + request);
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		System.out.println("begin transaction:" );
		try {
		if (request.equals("path")){
			System.out.println("path" );
			String nodes = (String) dis.readObject();
			String array[] = nodes.split("&");
			System.out.println("nodes:" + nodes);
			
			session.createQuery("delete PossiblePath").executeUpdate();
			session.createQuery("delete PDA").executeUpdate();
			session.getTransaction().commit();
			session.beginTransaction();
			find = new LinkManager(array[0], array[1]);
			list = session.createQuery("from NodeInformation where Status='ON' ").list();
			if((list.size() > 0)){
				list = session.createQuery("from PossiblePath where destination='"
						+ array[1] + "' ").list();
				v1 = new Vector();
				for (Object obj :list) {

					v1.add(obj.toString());
				}
				System.out.println(" v1" + v1);
			}
			System.out.println("send v1");

			System.out.println(" v1" + v1);

			System.out.println(" v1 size" + v1.size());

			String path = find.bestcost(4, array[1]);

			System.out.println("path:" + path);

			// -----------------------------------
			session.createQuery("delete PossiblePath").executeUpdate();
			session.createQuery("delete PDA").executeUpdate();
			session.getTransaction().commit();
			session.beginTransaction();
			find = new LinkManager(array[2], array[1]);
			list = session.createQuery("from NodeInformation where Status='ON' ").list();
			if (list.size()>0) {
				list = session.createQuery("Select path from PossiblePath where destination='"
						+ array[1] + "' ").list();
				v2 = new Vector();
				for (Object obj :list) {

					v2.add(obj.toString());
				}
				System.out.println(" v1" + v2);
			}
			System.out.println("send v2");

			list = session.createQuery("from NodeInformation where nodename='"
					+ array[0] + "' ").list();
			if (list.size()>0) {
				nodeid1 = ((NodeInformation)list.get(0)).getNodeId();
			}
			list = session.createQuery("from NodeInformation where nodename='"
					+ array[2] + "' ").list();
			if (list.size()>0) {
				nodeid2 = ((NodeInformation)list.get(0)).getNodeId();
			}

			System.out.println(" v2" + v2);
			/*
			 * dos=new ObjectOutputStream(soc.getOutputStream());
			 * 
			 * dos.writeObject(v1);
			 */

			System.out.println(" v2 size" + v2.size());

			String path1 = find.bestcost(4, array[1]);

			System.out.println("path1:" + path1);

			Vector vz = new Vector();
			vz.add(array[0]);
			vz.add(path);
			System.out.println("send v1");
			soc1 = new Socket(bgp, 5000);
			dos1 = new ObjectOutputStream(soc1.getOutputStream());
			String ph = array[2] + "&" + path1 + "&" + nodeid2;
			dos1.writeObject(ph);
			dos1 = new ObjectOutputStream(soc1.getOutputStream());
			String ph1 = array[0] + "&" + path + "&" + nodeid1;
			dos1.writeObject(ph1);
			dis1 = new ObjectInputStream(soc1.getInputStream());
			String result = (String) dis1.readObject();
			System.out.println("result:" + result);
			if (result.equals("yes")) {

				dos = new ObjectOutputStream(soc.getOutputStream());

				dos.writeObject(path);
			} else {
				dos = new ObjectOutputStream(soc.getOutputStream());

				dos.writeObject("attacker");
			}

		} else if (request.equals("portno")) {
			String nodes = (String) dis.readObject();

			System.out.println("nodes:" + nodes);

			list = session.createQuery("from NodeInformation where NodeName='"
					+ nodes + "' ").list();

			if (list.size()>0) {
				// System.out.println("p:"+list.getString(1));
				port = ((NodeInformation)list.get(0)).getPortNo().intValue();
			}
			list = session.createQuery("from NodeInformation where NodeName='"
					+ nodes + "' ").list();

			if (list.size()>0) {
				ip = ((NodeInformation)list.get(0)).getSystemName();
			}
			dos = new ObjectOutputStream(soc.getOutputStream());

			String p = String.valueOf(port);
			System.out.println("p:" + p);
			System.out.println("ip:" + ip);
			dos.writeObject(p);
			dos.writeObject(ip);
			System.out.println("send 1");

		} else if (request.equals("port")) {

			System.out.println("1");
			String nodes = (String) dis.readObject();

			System.out.println("nodes:" + nodes);

			list = session.createQuery("from NodeInformation where NodeName='"
					+ nodes + "' ").list();

			if (list.size()>0) {
				// System.out.println("p:"+list.getString(1));
				port = ((NodeInformation)list.get(0)).getPortNo().intValue();
			}
			list = session.createQuery("from NodeInformation where NodeName='"
					+ nodes + "' ").list();

			if (list.size()>0) {
				ip = ((NodeInformation)list.get(0)).getSystemName();
			}
			dos = new ObjectOutputStream(soc.getOutputStream());

			String p = String.valueOf(port);
			System.out.println("p:" + p);
			System.out.println("ip:" + ip);
			dos.writeObject(p);
			dos.writeObject(ip);
			System.out.println("send 1");
			
		} else if (request.equals("neighbours")) {
			Vector nv = new Vector();
			dis = new ObjectInputStream(soc.getInputStream());
			String neigh = (String) dis.readObject();
			list = session.createQuery("from Connection where NodeName='"
					+ neigh + "'").list();
			//while (list.next()) {
			for (Object obj:list){
				nv.addElement(((Connection)obj).getNeighbour());
			}
			dos = new ObjectOutputStream(soc.getOutputStream());

			dos.writeObject(nv);
		} else if (request.equals("path1")) {
			Vector nv1 = new Vector();
			// dis=new ObjectInputStream(soc.getInputStream());
			String neigh1 = (String) dis.readObject();
			list = session.createQuery("from Connection where NodeName='"+ neigh1 + "'").list();
			for (Object obj:list){
				nv1.addElement(((Connection)obj).getNeighbour());
			}
			
			dos = new ObjectOutputStream(soc.getOutputStream());

			dos.writeObject(nv1);

		} else if (request.equals("exit")) {
			dis = new ObjectInputStream(soc.getInputStream());
			String nn = (String) dis.readObject();
			System.out.println("z:" + nn);

			session.createQuery("update NodeInformation set PortNo='0' where NodeName='"
					+ nn + "'").executeUpdate();
			session.getTransaction().commit();
		}

		else if (request.equals("user")) {

			dis = new ObjectInputStream(soc.getInputStream());
			System.out.println("1");
			String details = (String) dis.readObject();
			System.out.println("2");
			String z[] = details.split("&");
			System.out.println("3");
			String nodeid = z[0] + z[2];
			list = session.createQuery("from NodeInformation where NodeName='"
					+ z[0] + "' ").list();

			if (list.size()>0) {
				// System.out.println("p:"+list.getString(1));
				number = ((NodeInformation)list.get(0)).getPortNo().intValue();
			}
			if (number == 0) {
				session.createQuery("update NodeInformation set PortNo='" + z[2]
						+ "' where NodeName='" + z[0] + "'").executeUpdate();
				System.out.println("4");
				session.createQuery("update NodeInformation set SystemName='"
						+ z[1] + "' where NodeName='" + z[0] + "'").executeUpdate();
				session.createQuery("update NodeInformation set NodeId='" + nodeid
						+ "' where NodeName='" + z[0] + "'").executeUpdate();

				System.out.println("5");

			} else {
				System.out.println(number);
			}

			// dos=new ObjectOutputStream(soc.getOutputStream());
			// port=port+1;

			System.out.println("8");
		} else {
			dos = new ObjectOutputStream(soc.getOutputStream());
			portno = portno + 1;

			String p = String.valueOf(portno);
			System.out.println("p:" + p);

			dos.writeObject(p);

			System.out.println("send");
		}
		
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
	}

}
