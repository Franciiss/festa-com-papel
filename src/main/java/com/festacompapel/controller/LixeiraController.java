package com.festacompapel.controller;

import com.festacompapel.com.festacompapel.util.CloudinaryConfig;
import com.festacompapel.model.StatusBasicos;
import com.festacompapel.service.CategoriaService;
import com.festacompapel.service.ClienteService;
import com.festacompapel.service.PedidoService;
import com.festacompapel.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class LixeiraController {

	public static final String LIXEIRA = "lixeira";

	private final CategoriaService categoriaService;
	private final ClienteService clienteService;
	private final ProdutoService produtoService;
	private final CloudinaryConfig cloudinary;

	public LixeiraController(CategoriaService categoriaService, ClienteService clienteService,
							 PedidoService pedidoService, ProdutoService produtoService, CloudinaryConfig cloudinary){
		this.categoriaService = categoriaService;
		this.clienteService = clienteService;
		this.produtoService = produtoService;
		this.cloudinary = cloudinary;
	}

	@RequestMapping(value = "/lixeira", method = RequestMethod.GET)
	public ModelAndView getFormulario() {
		ModelAndView modelAndView = new ModelAndView(LIXEIRA);

		modelAndView.addObject("categorias", categoriaService.findAllByStatus(StatusBasicos.INATIVO));
		modelAndView.addObject("clientes", clienteService.findAllByStatus(StatusBasicos.INATIVO));
		modelAndView.addObject("produtos", produtoService.findAllByStatus(StatusBasicos.INATIVO));

        System.out.println(clienteService.findAllByStatus(StatusBasicos.INATIVO));

		return modelAndView;
	}


	@RequestMapping(value = "/lixeira/restaurar-categoria/{id}", method = RequestMethod.GET)
	public String restaurarCategoria(@PathVariable("id") Long id) {

        categoriaService.atualizarStatus(id, StatusBasicos.ATIVO);

        return "redirect:/lixeira";
    }

    @RequestMapping(value = "/lixeira/restaurar-cliente/{id}", method = RequestMethod.GET)
    public String restaurarCliente(@PathVariable("id") Long id) {

        clienteService.atualizarStatus(id, StatusBasicos.ATIVO);

        return "redirect:/lixeira";
    }

    @RequestMapping(value = "/lixeira/restaurar-produto/{id}", method = RequestMethod.GET)
    public String restaurarProduto(@PathVariable("id") Long id) {

        produtoService.atualizarStatus(id, StatusBasicos.ATIVO);

        return "redirect:/lixeira";
    }
}
