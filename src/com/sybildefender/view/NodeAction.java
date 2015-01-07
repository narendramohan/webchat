package com.sybildefender.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class NodeAction extends ActionSupport implements SessionAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;
	String server = "127.0.0.11";
	public String getFileUploadContentType() {
		return fileUploadContentType;
	}
 
	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}
 
	public String getFileUploadFileName() {
		return fileUploadFileName;
	}
 
	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}
 
	public File getFileUpload() {
		return fileUpload;
	}
 
	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}
	
	public String filePath;
	
	private String sourceField;
	private String sourceName;
	private String destinationName;
	private String fileSize;
	private String recievedData;
	public static ServerSocket ssoc1;

	public static Socket sousoc1, ss1;
	static Socket soc;
	
	public int connection() throws Exception {
		sousoc1 = new Socket(server, 1500);
		oso = new ObjectOutputStream(sousoc1.getOutputStream());

		oso.writeObject("hai");

		osi = new ObjectInputStream(sousoc1.getInputStream());
		String port = (String) osi.readObject();

		System.out.println("port:" + port);

		int p = Integer.parseInt(port);

		return p;

	}
	ObjectOutputStream oso;	
	ObjectInputStream osi;	
	public void user(String username, int p) throws Exception {
		sousoc1 = new Socket(server, 1500);
		oso = new ObjectOutputStream(sousoc1.getOutputStream());
		oso.writeObject("user");
		sourceName = username;
		String ia = InetAddress.getLocalHost().getHostName();
		oso = new ObjectOutputStream(sousoc1.getOutputStream());
		oso.writeObject(username + "&" + ia + "&" + String.valueOf(p));
		System.out.println("username:" + username);

	}
	
	public void receiver() throws Exception {
		osi = new ObjectInputStream(ss1.getInputStream());
		String request = (String) osi.readObject();
		// String sname=(String)osi.readObject();
		// System.out.println("recived:"+sname);
		System.out.println("recived1:" + request);
		String requestres[] = request.split(">");
		String request1;
		if (requestres[0].equals(sourceName)) {
			System.out.println("rec");

			request1 = (String) osi.readObject();
			System.out.println("rec");
			recievedData = request1;
			System.out.println("rec");
		}

		else {
			String substr="";
			request1 = (String) osi.readObject();

			String node[] = request.split(">");
			for (int z = 1; z < node.length; z++) {
				if (z == (node.length) - 1) {
					substr = node[z];
				} else {
					substr = node[z] + ">";
				}
			}

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
			soc = new Socket(ip, port);
			oso = new ObjectOutputStream(soc.getOutputStream());
			oso.writeObject(substr);
			oso.writeObject(request1);
			osi = new ObjectInputStream(soc.getInputStream());
			num = (String) osi.readObject();
			System.out.println("num:" + num);

		}
		oso = new ObjectOutputStream(ss1.getOutputStream());
		oso.writeObject("received");
	}
	public String execute() throws Exception{
		try {
			
			System.out.println("session: "+session);
			String username = (String) ServletActionContext.getContext().getSession().get("softuser");
			int p = connection();
			System.out.println("server port:" + p);
			if (username!=null && username.length()>0)
				user(username, p);
			if(ssoc1==null)
				ssoc1 = new ServerSocket(p);
			ThreadClass tc = new ThreadClass();
			tc.start();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			System.out.println("uploading files and socket "+soc);
			String sname = sourceName;
			String desname = destinationName;
			//listen(sourceName, destinationName);
			networkThread nt = new networkThread();
			nt.start();
			myFlag2="true";
			myFlag1="block";
		}

		catch (Exception e1) {
			e1.printStackTrace();
		}
		
		fileSize = fileUpload.length()+"";
		filePath = fileUpload.getAbsolutePath();
		fileContent = readFile(fileUpload);
		return SUCCESS;
 
	}
	class networkThread extends Thread {
		public void run(){
			while (true) {
				
				try {
					
					sybilIdentification(sourceName, destinationName);
					System.out.println("listening the port");
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		}
		
	}
	public void sybilIdentification(String sname, String desname)
			throws UnknownHostException, IOException, ClassNotFoundException {
		if(soc==null)
			soc = new Socket(server, 1500);
		ObjectOutputStream oso = new ObjectOutputStream(soc.getOutputStream());
		
		System.out.println("sourceField: "+sourceField+"; destinationName: "+destinationName);
		oso.writeObject("path");
		oso.writeObject(sourceName + "&" + desname + "&" + sname);

		ObjectInputStream osi = new ObjectInputStream(soc.getInputStream());
		String result = (String) osi.readObject();
		System.out.println("NodeAction result "+result);
		String substr="";
		System.out.println("result:" + result);
		if (result.equals("attacker")) {
			System.out.println("You are Attacker");
		} else {
			String node[] = result.split(">");
			for (int z = 1; z < node.length; z++) {
				if (z == (node.length) - 1) {
					substr = node[z];

				} else {
					substr = node[z] + ">";
				}
			}

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
			String data = getFileUploadFileName();
			soc = new Socket(ip, port);
			oso = new ObjectOutputStream(soc.getOutputStream());
			oso.writeObject(substr);
			oso.writeObject(data);
			osi = new ObjectInputStream(soc.getInputStream());
			num = (String) osi.readObject();
			System.out.println("num:" + num);

		}
	}
	
	public class ThreadClass extends Thread {
		public void run(){
			while (true) {
				try {
					ss1 = ssoc1.accept();
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					receiver();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	String readFile(File fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
	public String recieveFile(){
		/*FormFile uploadFile = frm.getUploadFile(); 
		int size = uploadFile.getFileSize(); 
		char[] theChars = new char[size]; 
		byte[] bytes = uploadFile.getFileData(); 

		for (int i = 0; i < size;) 
		theChars[i] = (char)(bytes[i++]&0xff); 

		String fileContent = new String(theChars); */
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append('\n');
	            line = br.readLine();
	        }
	        String fileContent = sb.toString();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(br!=null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    }
		return SUCCESS;
	}
	private String myFlag2;
	private String myFlag1="none";
	private String fileContent;
	
 
	public String getMyFlag2() {
		return myFlag2;
	}

	public void setMyFlag2(String myFlag2) {
		this.myFlag2 = myFlag2;
	}

	public String getMyFlag1() {
		return myFlag1;
	}

	public void setMyFlag1(String myFlag1) {
		this.myFlag1 = myFlag1;
	}

	public String sendFile() {
		return SUCCESS;
	}
	public String display() {
		return NONE;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	private Map<String, Object> session;

	public void setSession(Map<String, Object> session) {
		this.session =session;
		
	}

	public String getRecievedData() {
		return recievedData;
	}

	public void setRecievedData(String recievedData) {
		this.recievedData = recievedData;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

}
