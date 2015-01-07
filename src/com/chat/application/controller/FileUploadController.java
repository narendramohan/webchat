package com.chat.application.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.chat.application.bean.FileUpload;
import com.chat.application.domain.FileSharing;
import com.chat.application.domain.Friend;
import com.chat.application.domain.Node;
import com.chat.application.domain.SybilAttack;
import com.chat.application.domain.User;
import com.chat.application.form.LoginForm;
import com.chat.application.service.FileService;
import com.chat.application.service.NodeServie;
import com.chat.application.service.ReportService;
import com.chat.application.service.UserService;
import com.google.common.io.Files;
import com.sybildefender.util.SybilGuardUtil;

@Controller
public class FileUploadController {
	@Autowired
	private UserService userService;
	@Autowired
	private FileService fileService;
	@Autowired
	private NodeServie nodeServie;
	
	@Autowired
	private ReportService reportService;
	
	@RequestMapping(value="/sendFile", method=RequestMethod.GET)
	public ModelAndView fileUpload(@ModelAttribute("command") FileUpload fileUpload, BindingResult result, HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//model.put("node", nodeService.getNode(node.getId()));
		String userName = (String)request.getSession().getAttribute("username");
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (User user:userService.listUser()){
			map.put(user.getLoginId(), user.getLoginId());
		}
		model.put("users", map);
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		for (Node node:nodeServie.listNode()){
			map1.put(node.getNodeName(), node.getNodeName());
		}
		model.put("nodes", map1);
		model.put("filesRecieved", fileService.getFileRecieved(userName));
		model.put("filesSent", fileService.getFileSend(userName));
		model.put("fileUpload", new FileUpload());
		return new ModelAndView("FileUploadForm", model);
	}
 
	@RequestMapping(value="/uploadSuccess", method=RequestMethod.POST)
	protected ModelAndView onSubmit(@ModelAttribute("command") FileUpload fileUpload, BindingResult result, LoginForm loginForm, HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
 
		FileUpload file = fileUpload;
		
		MultipartFile multipartFile = file.getFile();
		multipartFile.getContentType();
		
		String fileName="";
		String uName = request.getParameter("userName");
		String nodeName = request.getParameter("nodeName");
		boolean isFriend = false;
		List<Friend> friends = userService.friends((String)request.getSession().getAttribute("username"));
		for(Friend f: friends){
			if(f.getFriendUserName().equals(uName)){
				isFriend = true;
				break;
			}
		}
		Node node = nodeServie.getNode(uName);
		Node node1 = nodeServie.getNode((String)request.getSession().getAttribute("username"));
		if(isFriend){
			
			if(node ==null || !node.getNodeName().equals(nodeName)){
				isFriend = false;
			}
		}
		
		// if not friend and not the same node
		//Add attacker data
		if(!isFriend){
			
			SybilAttack s = new SybilAttack();
        	s.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        	s.setDestinationNode(node!=null?node.getNodeName():"unknown");
        	s.setSourceNode(node1!=null?node1.getNodeName():"unknown");
        	s.setIp(request.getRemoteAddr());
        	s.setUser((String)request.getSession().getAttribute("username"));
        	reportService.addReportData(s);
        	userService.blockUser((String)request.getSession().getAttribute("username"));
        	return new ModelAndView("login-page");
		}
		FileSharing fileSharing = new FileSharing();
		fileSharing.setContenttype(multipartFile.getContentType());
		fileSharing.setFilename(multipartFile.getOriginalFilename());
		fileSharing.setToUser(uName);
		fileSharing.setFromUser((String)request.getSession().getAttribute("username"));
		fileSharing = fileService.storeFile(fileSharing);
		int fileId =fileSharing.getIdfilesharing();
		long size = multipartFile.getSize();
		System.out.println("File size: "+size);
		if(multipartFile!=null){
			fileName = multipartFile.getOriginalFilename();
			InputStream inputStream = null;
			OutputStream outputStream = null;
			 
			try {
				inputStream =multipartFile.getInputStream();
				File file1 = new File("c:\\temp\\"+fileId);
				
				multipartFile.transferTo(file1);
				File file1tmp = new File("c:\\temp\\tmp-"+fileId);
				Files.copy(file1, file1tmp);
				String webRootPath = request.getServletContext().getRealPath("/");		 
				// write the inputStream to a FileOutputStream
				System.out.println(webRootPath+"data"+File.separator+fileId);
				
				/*outputStream = 
		                    new FileOutputStream(file1);
		 
				int read = 0;
				byte[] bytes = new byte[1024];
		 
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				
				 outputStream.flush();*/
				try {
					final File file2 = file1tmp;
					new Thread() {
			            public void run() {
			                try {
			                    SybilGuardUtil.server(file2);
			                } catch (IOException e) {
			                    e.printStackTrace();
			                }
			            }
			        }.start();
	
			        SybilGuardUtil.client(new File ("c:\\temp\\node\\"+fileId), fileSharing.getFromUser(), fileSharing.getToUser(), reportService, request.getRemoteAddr(), (String)request.getSession().getAttribute("username"));
			        
			        String result1 = SybilGuardUtil.weakLengthEstimation(fileId+"", new String[]{"c:\\temp\\"+fileId});
			        
			      //Add attacker data
			        if(result1.equals("0")){
			        	SybilAttack s = new SybilAttack();
			        	s.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
			        	s.setDestinationNode(node!=null?node.getNodeName():"unknown");
			        	s.setSourceNode(node1!=null?node1.getNodeName():"unknown");
			        	s.setIp(request.getRemoteAddr());
			        	s.setUser((String)request.getSession().getAttribute("username"));
			        	reportService.addReportData(s);
			        	userService.blockUser((String)request.getSession().getAttribute("username"));
			        	return new ModelAndView("login-page");
			        }
			        
				} catch (Exception e){};
				
				file1tmp.delete();
				System.out.println("Done!");
		 
			} catch (IOException e) {
				e.printStackTrace();
				return new ModelAndView("FileUploadFailure","fileName",fileName);
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (outputStream != null) {
					try {
						// outputStream.flush();
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
		 
				}
			}
		}
		
		return new ModelAndView("FileUploadSuccess","fileName",fileName);
 
	}
	
	@RequestMapping(value="/downloadFile", method=RequestMethod.GET)
	public void downloadFile( HttpServletRequest request, HttpServletResponse response) {
		String fileName = request.getParameter("id");
	    try {
	    	File file = new File("c:\\temp\\"+fileName);
	    	FileSharing f = fileService.getFile(Integer.parseInt(fileName), (String)request.getSession().getAttribute("username"));
	    	
	    	if(f!=null){	
	
		    	// get your file as InputStream
			    InputStream is = new FileInputStream(file);
			    // copy it to response's OutputStream
			    response.setContentType(f.getContenttype()); //in my example this was an xls file
		        response.setContentLength(new Long(file.length()).intValue());
		        response.setHeader("Content-Disposition","attachment; filename="+f.getFilename());
		        IOUtils.copy(is, response.getOutputStream());
		        response.flushBuffer();
		    } else {
		    	//if file not found then its assume its an attack.
	    		Node node = null;
	    		Node node1 = nodeServie.getNode((String)request.getSession().getAttribute("username"));
	    		SybilAttack s = new SybilAttack();
	        	s.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
	        	s.setDestinationNode(node!=null?node.getNodeName():"unknown");
	        	s.setSourceNode(node1!=null?node1.getNodeName():"unknown");
	        	s.setIp(request.getRemoteAddr());
	        	s.setUser((String)request.getSession().getAttribute("username"));
	        	reportService.addReportData(s);
	        	userService.blockUser((String)request.getSession().getAttribute("username"));
	        	//return new ModelAndView("login-page");
	    	}
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	      System.out.println("Error writing file to output stream. Filename was '" + fileName + "'");
	      //throw new RuntimeException("IOError writing file to output stream");
	    }
	}
	@RequestMapping(value="/downloadRecievedFile", method=RequestMethod.GET)
	public void downloadRecievedFile( HttpServletRequest request, HttpServletResponse response) {
		String fileName = request.getParameter("id");
	    try {
	    	
	    	File file = new File("c:\\temp\\"+fileName);
	    	FileSharing f = fileService.getFileToUser(Integer.parseInt(fileName), (String)request.getSession().getAttribute("username"));
	    	if(f!=null){
		    	// get your file as InputStream
			    InputStream is = new FileInputStream(file);
			    // copy it to response's OutputStream
			    response.setContentType(f.getContenttype()); //in my example this was an xls file
		        response.setContentLength(new Long(file.length()).intValue());
		        response.setHeader("Content-Disposition","attachment; filename="+f.getFilename());
		        IOUtils.copy(is, response.getOutputStream());
		        response.flushBuffer();
	    	} else {
		    	//if file not found then its assume its an attack.	    		
	    		Node node = null;
	    		Node node1 = nodeServie.getNode((String)request.getSession().getAttribute("username"));
	    		SybilAttack s = new SybilAttack();
	        	s.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
	        	s.setDestinationNode(node!=null?node.getNodeName():"unknown");
	        	s.setSourceNode(node1!=null?node1.getNodeName():"unknown");
	        	s.setIp(request.getRemoteAddr());
	        	s.setUser((String)request.getSession().getAttribute("username"));
	        	reportService.addReportData(s);
	        	userService.blockUser((String)request.getSession().getAttribute("username"));
	        	//return new ModelAndView("login-page");
	    	}
	    } catch (IOException ex) {
	      System.out.println("Error writing file to output stream. Filename was '" + fileName + "'");
	      //throw new RuntimeException("IOError writing file to output stream");
	      ex.printStackTrace();
	    }
	}	
	
	
}
