package com.festacompapel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@RequestMapping("/welcome")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("/index");

		return modelAndView;
	}

}
