package com.festacompapel.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.festacompapel.model.Cliente;
import com.festacompapel.repositry.ClienteRepository;

@Controller
public class ClienteController {

	public static final String FORM_CLIENTE = "/cliente/form-cliente";

	@Autowired
	ClienteRepository clienteRepository;

	@RequestMapping("/form-cliente")
	public ModelAndView index(Cliente Cliente) {
		ModelAndView modelAndView = new ModelAndView(FORM_CLIENTE);
		return modelAndView;
	}

	@RequestMapping(value = "/form-cliente", method = RequestMethod.POST)
	public String postFormulario(@Valid Cliente cliente, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return FORM_CLIENTE;
		}

		try {
			clienteRepository.save(cliente);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/form-produto";
	}

}
