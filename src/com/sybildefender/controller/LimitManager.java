package com.sybildefender.controller;

import java.util.List;




import org.hibernate.Session;

import com.sybildefender.model.PDA;
import com.sybildefender.model.PossiblePath;
import com.sybildefender.util.HibernateUtil;

public class LimitManager extends HibernateUtil {
	double val1, val2, result1, result2;
	int i, count = 0;
	double res1[] = new double[10];
	double res2[] = new double[10];
	String path[] = new String[10];
	String n[] = new String[10];

	public void getval() {
		try {
			// connect();
			Session session = HibernateUtil.getSessionFactory()
					.openSession();
			session.beginTransaction();
			List<PossiblePath> list = session.createQuery("from PossiblePath")
					.list();
			// rs = st.executeQuery("select * from possiblepath");

			// while(rs.next())
			for (PossiblePath ppath : list) {

				n[i] = ppath.getDestination();
				path[i] = ppath.getPath(); // rs.getString(2);
				val1 = ppath.getCost();
				val2 = ppath.getDelay();
				System.out.println("rtc path:" + path);
				System.out.println("\t" + val1 + "\t" + val2);
				result1 = Math.ceil(val1);
				result2 = Math.ceil(val2);
				System.out.println("\t" + result1 + "\t" + result2);
				res1[i] = result1;
				res2[i] = result2;
				i++;
			}
			try {
				// rs2 = st.executeQuery("select count(*) from possiblepath");
				List<PossiblePath> list1 = session.createQuery(
						"from PossiblePath").list();

				// while(rs2.next())
				for (PossiblePath path1 : list1) {
					count = list1.size();
					System.out.println("Count :\t" + count);

				}

			} catch (Exception ex1) {
				ex1.printStackTrace();
			}

			// st.execute("create table RTC (path varchar(20),limit varchar(20),delay varchar(20))");
			PDA pda;
			for (i = 0; i < count; i++) {
				pda = new PDA();
				pda.setPath(path[i]);
				pda.setCost(res1[i]);
				pda.setDelay(res2[i]);
				pda.setNode(n[i]);
				System.out.println("inside for loop" + path[i] + "\t" + res1[i]
						+ "\t" + res2[i]);
				// st.executeUpdate("insert into pda values ('"+path[i]+"','"+res1[i]+"','"+res2[i]+"','"+n[i]+"')");
				new PDAManager().add(pda);

			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
