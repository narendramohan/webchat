package com.sybildefender.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.Thread.State;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.chat.application.domain.SybilAttack;
import com.chat.application.service.ReportService;
import com.chat.application.service.serviceImpl.ReportServiceImple;



public class SybilGuardUtil {
	
	public static void main(String agrs[]){
		try {
			final File file2 = new File("C:\\MyProject\\m\\n_test.txt");
			new Thread("server") {
	            public void run() {
	                try {
	                    SybilGuardUtil.server(file2);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }.start();

	        
	        SybilGuardUtil.client(new File ("C:\\MyProject\\n\\tes1.txt"), "user1", "user2",new ReportServiceImple(), "", "");
	        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
	        System.out.println(map);
	        for (Thread t: map.keySet()){
	        	String name=t.getName();
	        	if(name.equals("server")){
	        		System.out.println(name+" "+t.getState());
	        		
	        		if(t.getState().equals(State.RUNNABLE)){
	        			t.interrupt();
	        		}
	        	}
	        }
	        
	        File f = new File ("C:\\MyProject\\m\\n_test.txt");
	        System.out.println(f.length());
	       /*File f = new File("C:\\MyProject\\n\\m\\t");
	       if(f.exists())
	    	   f.mkdirs();*/
		} catch (Exception e){e.printStackTrace();};
	}
	
	/**
	 * Server
	 * @param file
	 * @throws IOException
	 */
    public static void server(File file) throws IOException {
        /*ServerSocket ss = new ServerSocket(3434);
        Socket socket = ss.accept();
        InputStream in = new FileInputStream(file);
        OutputStream out = socket.getOutputStream();
        copy(in, out);
        out.close();
        in.close();*/
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(4444);
			//serverSocket.setTimeout(100);
		} catch (IOException ex) {
			System.out.println("Can't setup server on this port number. ");
		}

		Socket socket = null;
		InputStream is = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		int bufferSize = 0;

		
		try {
			socket = serverSocket.accept();
		} catch (IOException ex) {
			System.out.println("Can't accept client connection. ");
		}
		
		try {
			sybilRegionDetection(socket);
			//System.out.println("Buffer size: " + bufferSize);
		} catch (IOException ex) {
			System.out.println("Can't get socket input stream. ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			is = socket.getInputStream();

			bufferSize = socket.getReceiveBufferSize();
			System.out.println("Buffer size: " + bufferSize);
		} catch (IOException ex) {
			System.out.println("Can't get socket input stream. ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);

		} catch (FileNotFoundException ex) {
			System.out.println("File not found. ");
		}

		byte[] bytes = new byte[bufferSize];

		int count;
		try {
			System.out.println("File write start:"+is.available());	
		//while (is.available() > 0 && (c = inStream.read()) != -1)
		while (is.available() > 0 &&(count = is.read(bytes)) != -1) {
			System.out.println("File write start:"+count);
			bos.write(bytes, 0, count);
			System.out.println("File write done"+count);
		}
		System.out.println("File write done");
		bos.flush();
		bos.close();
		is.close();
		socket.close();
		serverSocket.close();
		System.out.println("Server Done");
		} catch(Exception e) {
			System.out.println("exception: ");
			e.printStackTrace();
		}
		
    }

    public static void client(File file, String sname, String desname, ReportService reportService, String ip, String user) throws Exception {
        /*Socket socket = new Socket("localhost", 3434);
        sybilIdentification(socket, file, sname, desname);
        sybilRegionDetection(socket);
        InputStream in = socket.getInputStream();
        OutputStream out = new FileOutputStream(file);
        copy(in, out);
        out.close();
        in.close();*/
    	 Socket socket = null;
 	    String host = "127.0.0.1";

 	    socket = new Socket(host, 4444);
 	    
 	      ObjectOutputStream bos = new ObjectOutputStream(socket.getOutputStream());

 	      /** Instantiate an OutputStreamWriter object with the optional character
 	       * encoding.
 	       */
 	      OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
 	     //TimeStamp = new java.util.Date().toString();
 	      String process = "127.0.0.1&sname&desname";

 	      /** Write across the socket connection and flush the buffer */
 	      bos.writeObject(process);
 	      osw.flush();
 	      bos = new ObjectOutputStream(socket.getOutputStream());

	      /** Instantiate an OutputStreamWriter object with the optional character
	       * encoding.
	       */
	      osw = new OutputStreamWriter(bos, "US-ASCII");
 	      bos.writeObject(process);
 	      osw.flush();
 	      
 	      
 	     sybilIdentification(socket, file, sname, desname, reportService, ip, user);
 	      //osw.close();
 	   // File file = new File("C:\\MyProject\\n\\tes1.txt");
 	    // Get the size of the file
 	    long length = file.length();
 	    if (length > Integer.MAX_VALUE) {
 	        System.out.println("File is too large.");
 	    }
 	    byte[] bytes = new byte[(int) length];
 	    FileInputStream fis = new FileInputStream(file);
 	    BufferedInputStream bis = new BufferedInputStream(fis);
 	    BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());

 	    int count;
 	    System.out.println("file sent");
 	    while ((count = bis.read(bytes)) > 0) {
 	        out.write(bytes, 0, count);
 	    }

 	    out.flush();
 	    out.close();
 	    fis.close();
 	    bis.close();
 	    socket.close();
 	    System.out.println("Client done");
    }	
    
    public static void sybilIdentification(Socket soc,File file, String sname, String desname, ReportService reportService, String ip, String user)
			throws UnknownHostException, IOException, ClassNotFoundException {
    	String server = "127.0.0.1";
		if(soc==null)
			soc = new Socket(server, 4444);
		/*ObjectOutputStream oso = new ObjectOutputStream(soc.getOutputStream());
		
		System.out.println("sourceField: "+sname+"; destinationName: "+desname);
		oso.writeObject("path");
		oso.writeObject(sname + "&" + desname + "&" + sname);*/

		ObjectInputStream osi = new ObjectInputStream(soc.getInputStream());
		String result = (String) osi.readObject();
		System.out.println("SybilGuardUtil result "+result);
		String substr="";
		System.out.println("result:" + result);
		//Attacker data
		if (result.equals("attacker")) {
			System.out.println("You are Attacker");
			SybilAttack s = new SybilAttack();
			s.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        	s.setDestinationNode(sname);
        	s.setSourceNode(desname);
        	s.setIp(ip);
        	s.setUser(user);
			reportService.addReportData(s);
		} else {
			System.out.println("You are safe user");
		}
		
		
		/*else {
			String node[] = result.split(">");
			substr = weakLengthEstimation(substr, node);

			System.out.println(":" + node[1]);
			System.out.println("substr:" + substr);
			soc = new Socket(server, 1500);
			oso = new ObjectOutputStream(soc.getOutputStream());
			System.out.println("2");
			oso.writeObject("port");
			oso.writeObject(node[1]);
			System.out.println("3");
			osi = new ObjectInputStream(soc.getInputStream());
			String num = (String) osi.readObject();
			String ip = (String) osi.readObject();

			System.out.println("port:" + num);
			System.out.println("ip:" + ip);
			int port = Integer.parseInt(num);
			String data = file.getName();
			soc = new Socket(ip, port);
			oso = new ObjectOutputStream(soc.getOutputStream());
			oso.writeObject(substr);
			oso.writeObject(data);
			osi = new ObjectInputStream(soc.getInputStream());
			num = (String) osi.readObject();
			System.out.println("num:" + num);

		}*/
	}

	public static String weakLengthEstimation(String substr, String[] node) {
		File f = new File (node[0]);
        if(f.length()==0){
        	return "0";
        }
		for (int z = 1; z < node.length; z++) {
			if (z == (node.length) - 1) {
				substr = node[z];

			} else {
				substr = node[z] + ">";
			}
		}
		return substr;
	}    
	static String z1[];
	static String z2[];
	public static void sybilRegionDetection(Socket soc) throws Exception {

		System.out.println("recived1");
		ObjectInputStream dis = new ObjectInputStream(soc.getInputStream());
		System.out.println("recived2");
		String nname = (String) dis.readObject();
		System.out.println("recived:" + nname);
		z1 = nname.split("&");
		dis = new ObjectInputStream(soc.getInputStream());
		nname = (String) dis.readObject();
		System.out.println("recived 2:" + nname);
		z2 = nname.split("&");
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

	public static String IDPFMatching(String z1p, String z2p) throws Exception {
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
	
	static class Node {
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
	public static void adddat1(Node dat) {
		nodeList.add(dat);
	}

	public static void adddat2(Node dat1) {
		nodeList.add(dat1);
	}
    static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[8192];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
    }	
	public static void preProcessing(int G, int h){
		int J[] = new int []{h};
		int f = 0;
		for (int i = 1; i<f;i++) {
			//Perform a random walk with length ls = log n originating from 	h
			//J = J ∪ {the ending node of the random walk}
		//end for
		}
		int lmin = 0;
		int lmax = 0;
		int l = lmin;
		while( l <= lmax){
			for (int i = 0/*J.first()*/; i< lmax/*J.last()*/; i++){
				//Perform R random walks with length l originating from 	node i
			 //Get ni as the number of nodes with frequency no smaller	than t
			 //end for
			}
			//output (l, mean({ni : i ∈ J}), stdDeviation({ni : i ∈ J}));
			l = l + 100;
		 //end while
		}
	}
	
	/*public static void sybilIdentification()
	{
		int l0=0;
		int l = l0;
		int lmax=0;
		while (l <= lmax){
			//Perform R random walks with length l originating from u
			//m = the number of nodes whose frequency is no smaller than t
			//Let the tuple corresponding to length l in the outputs of Algorithm 1 be (l, mean, stdDeviation)
			int mean=0;
			int m=0;
			int stdDeviation=0;
			if ( mean - m > stdDeviation *1){
				//output u is sybil
				//end the algorithm
			//end if
			}
			l = l * 2;
		//end while
		}
		//output u is honest
	}*/
	
	public static void walkLengthEstimation(){
	/*
		l = l0/2
		deadWalkRatio = 0
		while deadWalkRatio < β do
		l = l ∗ 2
		deadWalkNum = 0
		for i = 1 to R do
		Perform a partial random walk originating from s with length l
		if the partial random walk is dead before it reaches l hops then
		deadWalkNum++
		end if
		end for
		deadWalkRatio = deadWalkNum / R
		end while
		output l
	 */
	}
	
	/*public static void sybilRegionDetection(){
		
		 	Set the frequency of all the nodes to be 0
			for i = 1 to R do
			Perform a partial random walk originating from node s with length l
			s.frequency++
			for j = 1 to l do
			Let the jth hop of the partial random walk be node k
			k.frequency++
			end for
			end for
			traversedList = Sort the traversed nodes by their frequency in decreasing order
			counter = 0
			S = ∅
			do
			counter = conductance(S)
			for i = traversedList.first() to traversedList.last() do
			if node i ∈ S then
			continue
			if conductance({i} ∪ S) <= conductance(S) then
			S = {i} ∪ S
			while (counter > conductance(S))
			output S
		 
	}*/

}
