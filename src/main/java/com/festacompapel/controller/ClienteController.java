package com.festacompapel.controller;

import com.festacompapel.com.festacompapel.util.CloudinaryConfig;
import com.festacompapel.model.Cliente;
import com.festacompapel.model.StatusBasicos;
import com.festacompapel.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ClienteController {

	public static final String FORM_CLIENTE = "cliente/form-cliente";
	public static final String LISTA_CLIENTES = "cliente/lista-clientes";

	private final ClienteService clienteService;

	private final CloudinaryConfig cloudinary;


	@Autowired
	public ClienteController(ClienteService categoriaService, CloudinaryConfig cloudinary){
		this.clienteService = categoriaService;
		this.cloudinary = cloudinary;
	}

	@RequestMapping("/form-cliente")
	public ModelAndView formCliente(Cliente Cliente) {
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
	public ModelAndView edicaoCliente(Cliente cliente, @PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView(FORM_CLIENTE);
        modelAndView.addObject("cliente", clienteService.buscaPor(id));
        return modelAndView;
	}

	@RequestMapping(value = "/form-cliente/salva", method = RequestMethod.POST)
	public String postFormulario(@Valid Cliente cliente, BindingResult bindingResult, @RequestParam(value = "arquivo", required = false) MultipartFile arquivo) {

        System.out.println(bindingResult.getAllErrors());

		if (bindingResult.hasErrors()) {
			return FORM_CLIENTE;
		}

		Map config = new HashMap();
		config.put("public_id", arquivo.getOriginalFilename());
		config.put("folder", "imagens/cliente/");

		try {
		    if(arquivo == null || arquivo.isEmpty()){
                if(cliente.getSexo().equals("Masculino")){
                    cliente.setImagem("https://res.cloudinary.com/hkz3sajjn/image/upload/v1538357759/imagens/if_malecostume_403022.png");
                } else {
                    cliente.setImagem("https://res.cloudinary.com/hkz3sajjn/image/upload/v1538357758/imagens/if_female_628285.png");
                }
            } else {
                Map uploadResult = cloudinary.cloudinary().uploader().upload(arquivo.getBytes(), config);
                cliente.setImagem(uploadResult.get("url").toString());
            }
		} catch (IOException e) {
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