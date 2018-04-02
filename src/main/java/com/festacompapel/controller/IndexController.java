package com.festacompapel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.festacompapel.model.Cliente;
import com.festacompapel.repositry.ClienteRepository;

@Controller
public class IndexController {

	@Autowired
	ClienteRepository clienteRepository;

	@RequestMapping("/welcome")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("/index");
		
		Cliente c = new Cliente();
		c.setNome("Francisco");
		clienteRepository.save(c);
		return modelAndView;
	}

}
