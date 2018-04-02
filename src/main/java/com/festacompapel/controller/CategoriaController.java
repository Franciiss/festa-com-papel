package com.festacompapel.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.festacompapel.model.Categoria;
import com.festacompapel.repositry.CategoriaRepository;

@Controller
@RequestMapping("value = /herokuapp.com/")
public class CategoriaController {

	public static final String FORM_CATEGORIA = "/categoria/form-categoria";
	public static final String LISTA_CATEGORIA = "/categoria/lista-categoria";

	@Autowired
	CategoriaRepository categoriaRepository;

	@RequestMapping(value = "/lista-categoria", method = RequestMethod.GET)
	public ModelAndView listaCategoria(Categoria categoria) {
		ModelAndView modelAndView = new ModelAndView(LISTA_CATEGORIA);
		modelAndView.addObject("categorias", categoriaRepository.findAll());
		return modelAndView;
	}

	@RequestMapping(value = "/form-categoria", method = RequestMethod.GET)
	public ModelAndView getFormulario(Categoria categoria) {
		ModelAndView modelAndView = new ModelAndView(FORM_CATEGORIA);
		return modelAndView;
	}

	@RequestMapping(value = "/form-categoria", method = RequestMethod.POST)
	public String postFormulario(@Valid Categoria categoria, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return FORM_CATEGORIA;
		}

		categoriaRepository.save(categoria);

		return "redirect:/form-categoria";
	}

	@GetMapping("categoria/edicao/{id}")
	public ModelAndView edicao(@PathVariable("id") Categoria categoria) {
		ModelAndView modelAndView = new ModelAndView(FORM_CATEGORIA);
		modelAndView.addObject("categoria", categoria);
		return modelAndView;
	}

	@RequestMapping(value = "/categoria/remover/{id}", method = RequestMethod.GET)
	public String excluirCategoria(@PathVariable("id") Categoria categoria) {

		categoriaRepository.delete(categoria);

		return "redirect:/form-categoria";
	}
}
