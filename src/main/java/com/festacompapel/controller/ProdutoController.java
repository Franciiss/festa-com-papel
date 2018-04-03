package com.festacompapel.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.festacompapel.model.Produto;
import com.festacompapel.repositry.CategoriaRepository;
import com.festacompapel.repositry.ProdutoRepository;

@Controller
public class ProdutoController {

	public static final String FORM_PRODUTO = "produto/form-produto";
	public static final String LISTA_PRODUTOS = "produto/lista-produtos";

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	CategoriaRepository categoriaRepository;

	@RequestMapping(value = "/form-produto", method = RequestMethod.GET)
	public ModelAndView getFormulario(Produto produto) {
		ModelAndView modelAndView = new ModelAndView(FORM_PRODUTO);
		modelAndView.addObject("categorias", categoriaRepository.findAll());
		return modelAndView;
	}

	@RequestMapping(value = "/form-produto", method = RequestMethod.POST)
	public String postFormulario(@Valid Produto produto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return FORM_PRODUTO;
		}

		try {
			produtoRepository.save(produto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/lista-produto";
	}

	@RequestMapping(value = "/lista-produtos", method = RequestMethod.GET)
	public ModelAndView listaProdutos(Produto produto) {
		ModelAndView modelAndView = new ModelAndView(LISTA_PRODUTOS);
		modelAndView.addObject("produtos", produtoRepository.findAll());
		return modelAndView;
	}

	@RequestMapping(value = "/produto/remover/{id}", method = RequestMethod.GET)
	public String excluirProduto(@PathVariable("id") Produto produto) {
		produtoRepository.delete(produto);
		return "redirect:/lista-produto";
	}
}
