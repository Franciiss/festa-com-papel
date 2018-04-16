package com.festacompapel.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.festacompapel.model.Categoria;
import com.festacompapel.service.CategoriaService;

@Controller
@RequestMapping
public class CategoriaController {

	public static final String FORM_CATEGORIA = "categoria/form-categoria";
	public static final String LISTA_CATEGORIA = "categoria/lista-categorias";

	@Autowired
	CategoriaService categoriaService;

	@RequestMapping(value = "/form-categoria", method = RequestMethod.GET)
	public ModelAndView getFormulario(Categoria categoria) {
		ModelAndView modelAndView = new ModelAndView(FORM_CATEGORIA);
		return modelAndView;
	}

	@RequestMapping(value = "/lista-categorias", method = RequestMethod.GET)
	public ModelAndView listaCategoria(Categoria categoria) {
		ModelAndView modelAndView = new ModelAndView(LISTA_CATEGORIA);
		modelAndView.addObject("categorias", categoriaService.todas());
		return modelAndView;
	}

	@RequestMapping(value = "/form-categoria/salva", method = RequestMethod.POST)
	public String postFormulario(@Valid Categoria categoria, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return FORM_CATEGORIA;
		}

		categoriaService.salva(categoria);

		return "redirect:/lista-categorias";
	}

	@RequestMapping(value = "/categoria/edicao/{id}", method = RequestMethod.GET)
	public ModelAndView edicaoCategoria(@PathVariable("id") Long id) {
		Categoria categoria = categoriaService.buscaPor(id);

		System.out.println(categoria.getIdCategoria());
		System.out.println(categoria.getNome());
		System.out.println(categoria.getDescricao());

		ModelAndView modelAndView = new ModelAndView(FORM_CATEGORIA);
		modelAndView.addObject("categoria", categoria);

		return modelAndView;
	}

	@RequestMapping(value = "/categoria/remover/{id}", method = RequestMethod.GET)
	public String excluirCategoria(@PathVariable("id") Categoria categoria) {
		categoriaService.delete(categoria);
		return "redirect:/lista-categorias";
	}
}
