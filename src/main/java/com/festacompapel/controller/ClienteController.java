package com.festacompapel.controller;

import javax.validation.Valid;

import com.festacompapel.model.StatusBasicos;
import com.festacompapel.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.festacompapel.model.Cliente;
import com.festacompapel.repository.ClienteRepository;

import java.util.Optional;

@Controller
public class ClienteController {

	public static final String FORM_CLIENTE = "cliente/form-cliente";
	public static final String LISTA_CLIENTES = "cliente/lista-clientes";

	private final ClienteService clienteService;

	@Autowired
	public ClienteController(ClienteService categoriaService){
		this.clienteService = categoriaService;
	}

	@RequestMapping("/form-cliente")
	public ModelAndView index(Cliente Cliente) {
		ModelAndView modelAndView = new ModelAndView(FORM_CLIENTE);
		return modelAndView;
	}

	@RequestMapping(value = "/lista-clientes", method = RequestMethod.GET)
	public ModelAndView listaClientes() {
		ModelAndView modelAndView = new ModelAndView(LISTA_CLIENTES);
		modelAndView.addObject("clientes", clienteService.findAllByStatus(StatusBasicos.ATIVO));
		return modelAndView;
	}

	@RequestMapping(value = "/cliente/remover/{id}", method = RequestMethod.GET)
	public String excluirCliente(@PathVariable("id") Long id) {

		clienteService.atualizarStatus(id, StatusBasicos.INATIVO);

		return "redirect:/lista-clientes";
	}

	@RequestMapping(value = "/cliente/edicao/{id}", method = RequestMethod.GET)
	public ModelAndView edicaoCliente(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView(FORM_CLIENTE);
		modelAndView.addObject("cliente", clienteService.buscaPor(id));
		return modelAndView;
	}

	@RequestMapping(value = "/form-cliente", method = RequestMethod.POST)
	public String postFormulario(@Valid Cliente cliente, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return FORM_CLIENTE;
		}

		try {
			clienteService.salva(cliente);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/lista-clientes";
	}
}