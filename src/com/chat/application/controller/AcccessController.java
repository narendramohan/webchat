package com.chat.application.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chat.application.bean.UserBean;
import com.chat.application.domain.User;
import com.chat.application.service.ReportService;
@Controller
public class AcccessController {
	
	@RequestMapping(value="/accessDenied", method = RequestMethod.GET)
	public String accessDenied(){
		return "accessDenied";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerUser(@ModelAttribute("command") UserBean userBean,
			BindingResult result) {
		return "register";
	}	
	
	
	@Autowired
	ReportService reportService;
	
	@RequestMapping(value = "/sybilreport", method = RequestMethod.GET)
	public ModelAndView sybilreport(@ModelAttribute("command") UserBean userBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("attackReports", reportService.getSybilAttackReport());
		return new ModelAndView("sybilreport", model);
	}
}
