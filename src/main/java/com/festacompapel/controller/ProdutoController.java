package com.festacompapel.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.festacompapel.model.Produto;
import com.festacompapel.repositry.CategoriaRepository;
import com.festacompapel.repositry.ProdutoRepository;

@Controller
public class ProdutoController {

	public static final String FORM_PRODUTO = "/produto/form-produto";

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
		return "redirect:/form-produto";
	}

}
