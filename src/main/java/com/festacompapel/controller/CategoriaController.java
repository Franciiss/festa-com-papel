package com.festacompapel.controller;

import com.festacompapel.com.festacompapel.util.CloudinaryConfig;
import com.festacompapel.model.Categoria;
import com.festacompapel.model.StatusBasicos;
import com.festacompapel.service.CategoriaService;
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
@RequestMapping
public class CategoriaController {

	public static final String FORM_CATEGORIA = "categoria/form-categoria";
	public static final String LISTA_CATEGORIA = "categoria/lista-categorias";

	private final CategoriaService categoriaService;

	private final CloudinaryConfig cloudinary;

	public CategoriaController(CategoriaService categoriaService, CloudinaryConfig cloudinary){
		this.categoriaService = categoriaService;
		this.cloudinary = cloudinary;
	}

	@RequestMapping(value = "/form-categoria", method = RequestMethod.GET)
	public ModelAndView formCategoria(Categoria categoria) {
		ModelAndView modelAndView = new ModelAndView(FORM_CATEGORIA);
		return modelAndView;
	}

	@RequestMapping(value = "/lista-categorias", method = RequestMethod.GET)
	public ModelAndView listaCategoria(Categoria categoria) {
		ModelAndView modelAndView = new ModelAndView(LISTA_CATEGORIA);
		modelAndView.addObject("categorias", categoriaService.findAllByStatus(StatusBasicos.ATIVO));
		return modelAndView;
	}

	@RequestMapping(value = "/form-categoria/salva", method = RequestMethod.POST)
	public String postFormulario(@Valid Categoria categoria, BindingResult bindingResult, @RequestParam(value = "arquivo", required = false) MultipartFile arquivo) {

		if (bindingResult.hasErrors()) {
			return FORM_CATEGORIA;
		}

		if(arquivo == null || arquivo.isEmpty()){
		    categoria.setImagem("https://res.cloudinary.com/hkz3sajjn/image/upload/v1538360420/imagens/if_Attribute_category_label_shop_price_price_tag_tag_1886315.png");
        } else {

            Map config = new HashMap();
            config.put("public_id", arquivo.getOriginalFilename());
            config.put("folder", "imagens/categoria/");

            try {
                Map uploadResult = cloudinary.cloudinary().uploader().upload(arquivo.getBytes(), config);
                categoria.setImagem(uploadResult.get("url").toString());
            } catch (IOException e) {
                return FORM_CATEGORIA;
            }

        }

		categoriaService.salva(categoria);

		return "redirect:/lista-categorias";
	}

	@RequestMapping(value = "/categoria/edicao/{id}", method = RequestMethod.GET)
	public ModelAndView edicaoCategoria(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView(FORM_CATEGORIA);
        modelAndView.addObject("categoria", categoriaService.buscaPor(id));
        return modelAndView;
	}

	@RequestMapping(value = "/categoria/remover/{id}", method = RequestMethod.GET)
	public String excluirCategoria(@PathVariable("id") Long id) {

		Categoria categoriaBanco = categoriaService.buscaPor(id);
		categoriaBanco.setStatus(StatusBasicos.INATIVO);
		categoriaService.salva(categoriaBanco);

		return "redirect:/lista-categorias";
	}
}
