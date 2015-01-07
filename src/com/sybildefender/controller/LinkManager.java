package com.sybildefender.controller;

import java.util.List;
import java.util.Vector;

import org.hibernate.Session;

import com.sybildefender.model.Connection;
import com.sybildefender.model.PDA;
import com.sybildefender.model.PossiblePath;
import com.sybildefender.util.HibernateUtil;

public class LinkManager extends HibernateUtil {
	private String printPath = "";
	private int index = -1;
	private String stack[] = new String[100];
	private boolean flip = true, flip1 = true, flip2 = false;
	public static Vector<String> vPathWeigth = new Vector<String>();
	private int weight = 0;
	private static String strDest = "";
	private static String strStart = "";

	public LinkManager( String start,String strDst ){
		try
		{
			strDest = strDst;
			strStart = start;
			flip = true;
			flip1 = true;
			flip2 = false;
				
			index = -1;
			weight = 0;
			getPath( start );

	   }
		catch( Exception e )
		{
			e.printStackTrace( );
		}  
	}
	public void getPath(String start) {
		try {

			Session session = HibernateUtil.getSessionFactory()
					.openSession();
			session.beginTransaction();

			List<Connection> list = session.createQuery(
					"from Connection where nodename like '" + start.trim()
							+ "'").list();

			for (Connection connection : list) {
				flip2 = true;
				if (flip == false) {
					for (int i = 0; i < index; i++) {
						stack[i] = stack[i + 1];
					}
					index--;
					flip = true;
				}

				String check = connection.getNeighbour();

				String strarray[] = strStart.split(">");

				for (int i = 0; i < strarray.length; i++) {
					if (strarray[i].equalsIgnoreCase(check)) {
						flip1 = false;
					}
				}

				if (flip1 == true) {
					stack[++index] = strStart + ">" + check;
				}

				flip1 = true;
			} // end of while loop

			if (flip2 == true) {
				flip2 = false;
				for (int i = 0; i <= index; i++) {
					findNode(stack[i]);
				}
			} else {
				for (int i = 0; i < index; i++) {
					stack[i] = stack[i + 1];
				}
				index--;
				flip2 = true;
				findNode(stack[0]);
			}
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void findNode(String nodePath) {
		strStart = nodePath;

		int end = nodePath.lastIndexOf(">");

		if (nodePath.substring(end + 1).equals(strDest)) {
			findWeight(nodePath);
			if (index != 0) {
				for (int i = 0; i <= index; i++) {
					stack[i] = stack[i + 1];
				}
				index--;
				flip = true;
				strStart = stack[0];
				findNode(stack[0]);
			}
		} else {
			flip = false;
			getPath(nodePath.substring(end + 1));

		}
	}

	public void findWeight(String nodePath) {

		String[] strArray = nodePath.split(">");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			for (int i = 0; i < strArray.length - 1; i++) {
				List<Connection> list = session.createSQLQuery(
						"select cost from Connection where nodename  = '"
								+ strArray[i].trim() + "' and neighbour = '"
								+ strArray[i + 1].trim() + "'").list();
				System.out.println(list.get(0));
				for (Object connection : list) {
					weight += Integer.parseInt(connection.toString());
				}
			}

			String pathweight = nodePath + "#" + weight;
			List<Connection> list = session.createSQLQuery(
					"select path from possiblepath where path  = '" + nodePath
							+ "'").list();
			if (list.size() > 0) {
				System.out.println("Already Exist");
			} else {
				PossiblePath ppath = new PossiblePath();
				ppath.setDestination(strDest);
				ppath.setPath(nodePath);
				ppath.setCost(new Long(weight));
				ppath.setDelay(0L);
				new PossiblePathManager().add(ppath);
				weight = 0;
			}

			vPathWeigth.add(pathweight);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String bestcost(int f, String n) throws Exception {
		float s1[] = new float[100];
		String s[] = new String[100];
		String path = "";
		int i = 0;
		List<PDA> list=null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		if (f == 4) {
			LimitManager a = new LimitManager();
			a.getval();
			list = session.createQuery(
					"from PDA where node='"+n+"' order by cost,delay ").list();
		}

		if (list!=null) {
			for(PDA pda: list){
	
				s[i] = pda.getPath();
	
				s1[i] = new Float(pda.getCost());
	
				i++;
			}
		}

		if (i == 1) {
			path = s[0];

		} else {
			float z = s1[0];
			path = s[0];
			for (int k = 0; k < i - 1; k++)

			{

				if (z > s1[k]) {
					System.out.println("bestlimit:" + k);
					System.out.println("bestpath:" + s[k]);
					path = s[k];
					z = s1[k];

				}
			}

		}
		session.getTransaction().commit();
		session.close();
		return path;
	}

}
