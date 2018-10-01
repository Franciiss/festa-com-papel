package com.festacompapel.controller;

import com.festacompapel.com.festacompapel.util.CloudinaryConfig;
import com.festacompapel.model.Produto;
import com.festacompapel.model.StatusBasicos;
import com.festacompapel.repository.CategoriaRepository;
import com.festacompapel.service.ProdutoService;
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
public class ProdutoController {

	public static final String FORM_PRODUTO = "produto/form-produto";
	public static final String LISTA_PRODUTOS = "produto/lista-produtos";

	@Autowired
    ProdutoService produtoService;

	@Autowired
	CategoriaRepository categoriaRepository;

    private final CloudinaryConfig cloudinary;

    public ProdutoController(ProdutoService produtoService, CloudinaryConfig cloudinary){
        this.produtoService = produtoService;
        this.cloudinary = cloudinary;
    }

    @RequestMapping(value = "/form-produto", method = RequestMethod.GET)
	public ModelAndView adicionarProduto(Produto produto) {
		ModelAndView modelAndView = new ModelAndView(FORM_PRODUTO);
		modelAndView.addObject("categorias", categoriaRepository.findAll());
		return modelAndView;
	}

	@RequestMapping(value = "/form-produto", method = RequestMethod.POST)
	public String postFormulario(@Valid Produto produto, @RequestParam(value = "arquivo", required = false) MultipartFile arquivo, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return FORM_PRODUTO;
		}

        if(arquivo == null || arquivo.isEmpty()){
            produto.setImagem("https://res.cloudinary.com/hkz3sajjn/image/upload/v1538362090/imagens/if_Product_Hunt_1298713.png");
        } else {

            Map config = new HashMap();
            config.put("public_id", arquivo.getOriginalFilename());
            config.put("folder", "imagens/produto/");

            try {
                Map uploadResult = cloudinary.cloudinary().uploader().upload(arquivo.getBytes(), config);
                produto.setImagem(uploadResult.get("url").toString());
            } catch (IOException e) {
                return FORM_PRODUTO;
            }

        }

		try {
			produtoService.salva(produto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/lista-produtos";
	}

	@RequestMapping(value = "/lista-produtos", method = RequestMethod.GET)
	public ModelAndView listaProdutos() {
		ModelAndView modelAndView = new ModelAndView(LISTA_PRODUTOS);
		modelAndView.addObject("produtos", produtoService.findAllByStatus(StatusBasicos.ATIVO));
		return modelAndView;
	}

	@RequestMapping(value = "/produto/edicao/{id}", method = RequestMethod.GET)
	public ModelAndView edicaoProduto(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView(FORM_PRODUTO);
        modelAndView.addObject("produto", produtoService.buscaPor(id));
        modelAndView.addObject("categorias", categoriaRepository.findAllByStatus(StatusBasicos.ATIVO));
        return modelAndView;
	}

	@RequestMapping(value = "/produto/remover/{id}", method = RequestMethod.GET)
	public String excluirProduto(@PathVariable("id") Long id) {

        produtoService.atualizarStatus(id, StatusBasicos.INATIVO);

		return "redirect:/lista-produtos";
	}
}
