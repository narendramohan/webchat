package com.chat.application.controller;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chat.application.domain.Node;
import com.chat.application.domain.SybilAttack;
import com.chat.application.domain.User;
import com.chat.application.form.LoginForm;
import com.chat.application.service.EmailService;
import com.chat.application.service.NodeServie;
import com.chat.application.service.ReportService;
import com.chat.application.service.UserService;

@Controller
@RequestMapping("/node")
public class NodeManager {
	@Resource(name = "nodeService")
	private NodeServie nodeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value = "/nodes", method = RequestMethod.GET)
	public ModelAndView listUsers(HttpServletRequest request, HttpSession session, LoginForm loginForm) {
		User user1 = (User)session.getAttribute("user");
		if(user1.getType()!=1){
			Node node1 = nodeService.getNode((String)request.getSession().getAttribute("username"));
			SybilAttack s = new SybilAttack();
        	s.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        	s.setDestinationNode("unknown");
        	s.setSourceNode(node1!=null?node1.getNodeName():"unknown");
        	s.setIp(request.getRemoteAddr());
        	s.setUser((String)request.getSession().getAttribute("username"));
        	reportService.addReportData(s);
        	userService.blockUser((String)request.getSession().getAttribute("username"));
        	return new ModelAndView("login-page");
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nodes", nodeService.listNode());
		return new ModelAndView("listNode", model);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addUser(@RequestParam(value="error", required=false) boolean error, ModelMap model, @ModelAttribute("command") Node node,
			BindingResult result, LoginForm loginForm,HttpServletRequest request, HttpSession session) {
		User user1 = (User)session.getAttribute("user");
		if(user1.getType()!=1){
			Node node1 = nodeService.getNode((String)request.getSession().getAttribute("username"));
			SybilAttack s = new SybilAttack();
        	s.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        	s.setDestinationNode("unknown");
        	s.setSourceNode(node1!=null?node1.getNodeName():"unknown");
        	s.setIp(request.getRemoteAddr());
        	s.setUser((String)request.getSession().getAttribute("username"));
        	reportService.addReportData(s);
        	userService.blockUser((String)request.getSession().getAttribute("username"));
        	return new ModelAndView("login-page");
		}
		Node user = new Node();
		model.put("node", user);

		return new ModelAndView("addNode");
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveUser(@RequestParam(value="error", required=false) boolean error, ModelMap model, @ModelAttribute("command") Node node,
			BindingResult result, LoginForm loginForm, HttpServletRequest request, HttpSession session) {
		//User user = prepareModel(userBean);
		User user1 = (User)session.getAttribute("user");
		if(user1.getType()!=1){
			Node node1 = nodeService.getNode((String)request.getSession().getAttribute("username"));
			SybilAttack s = new SybilAttack();
        	s.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        	s.setDestinationNode("unknown");
        	s.setSourceNode(node1!=null?node1.getNodeName():"unknown");
        	s.setIp(request.getRemoteAddr());
        	s.setUser((String)request.getSession().getAttribute("username"));
        	reportService.addReportData(s);
        	userService.blockUser((String)request.getSession().getAttribute("username"));
        	return new ModelAndView("login-page");
		}
		boolean exists = nodeService.isNodeExists(node.getId());
		if(!exists)
			nodeService.addNode(node);
		else {
			nodeService.addNode(node);
			//return new ModelAndView("addUser"); 
		}
		return new ModelAndView("redirect:nodes");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView deleteUser(
			@ModelAttribute("command") Node node, BindingResult result, HttpServletRequest request, HttpSession session) {
		User user1 = (User)session.getAttribute("user");
		if(user1.getType()!=1){
			Node node1 = nodeService.getNode((String)request.getSession().getAttribute("username"));
			SybilAttack s = new SybilAttack();
        	s.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        	s.setDestinationNode("unknown");
        	s.setSourceNode(node1!=null?node1.getNodeName():"unknown");
        	s.setIp(request.getRemoteAddr());
        	s.setUser((String)request.getSession().getAttribute("username"));
        	reportService.addReportData(s);
        	userService.blockUser((String)request.getSession().getAttribute("username"));
        	return new ModelAndView("login-page");
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("node", nodeService.getNode(node.getId()));
		model.put("nodes", nodeService.listNode());
		return new ModelAndView("addNode", model);
	}

	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView editUser(@ModelAttribute("command") Node node,
			BindingResult result, HttpServletRequest request, HttpSession session) {
		User user1 = (User)session.getAttribute("user");
		if(user1.getType()!=1){
			Node node1 = nodeService.getNode((String)request.getSession().getAttribute("username"));
			SybilAttack s = new SybilAttack();
        	s.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        	s.setDestinationNode("unknown");
        	s.setSourceNode(node1!=null?node1.getNodeName():"unknown");
        	s.setIp(request.getRemoteAddr());
        	s.setUser((String)request.getSession().getAttribute("username"));
        	reportService.addReportData(s);
        	userService.blockUser((String)request.getSession().getAttribute("username"));
        	return new ModelAndView("login-page");
		}
		nodeService.deleteNode(node);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("node", null);
		model.put("nodes", nodeService.listNode());
		return new ModelAndView("listNode", model);
	}
	
	
	@RequestMapping(value = "/assignuser", method = RequestMethod.GET)
	public ModelAndView assignUser(@ModelAttribute("command") Node node,
			BindingResult result, HttpServletRequest request, HttpSession session) {
		User user1 = (User)session.getAttribute("user");
		if(user1.getType()!=1){
			Node node1 = nodeService.getNode((String)request.getSession().getAttribute("username"));
			SybilAttack s = new SybilAttack();
        	s.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        	s.setDestinationNode("unknown");
        	s.setSourceNode(node1!=null?node1.getNodeName():"unknown");
        	s.setIp(request.getRemoteAddr());
        	s.setUser((String)request.getSession().getAttribute("username"));
        	reportService.addReportData(s);
        	userService.blockUser((String)request.getSession().getAttribute("username"));
        	return new ModelAndView("login-page");
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("node", nodeService.getNode(node.getId()));
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (User user:userService.listUser()){
			map.put(user.getLoginId(), user.getName());
		}
		model.put("users", map);
		emailService.sendMailForNodeAssigned((String)request.getSession().getAttribute("username"));
		//model.put("nodes", nodeService.listNode());
		return new ModelAndView("assignUser", model);
	}

}
