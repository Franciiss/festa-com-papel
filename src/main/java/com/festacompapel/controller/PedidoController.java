package com.festacompapel.controller;

import com.festacompapel.model.Pedido;
import com.festacompapel.model.Produto;
import com.festacompapel.model.StatusBasicos;
import com.festacompapel.service.ClienteService;
import com.festacompapel.service.PedidoService;
import com.festacompapel.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes(value = { "clientes", "produtos", "carrinho", "valorTotal" })
public class PedidoController {

	public static final String FORM_PEDIDO = "pedido/form-pedido";
	public static final String LISTA_PEDIDO = "pedido/lista-pedidos";

	private final PedidoService pedidoService;

	private final ProdutoService produtoService;

	private final ClienteService clienteService;

	@Autowired
	public PedidoController(PedidoService pedidoService, ProdutoService produtoService, ClienteService categoriaService){
		this.pedidoService = pedidoService;
		this.produtoService = produtoService;
		this.clienteService = categoriaService;
	}


	List<Produto> carrinho = new ArrayList<Produto>();

	double valorTotal = 0;

	@RequestMapping(value = "/lista-pedidos", method = RequestMethod.GET)
	public ModelAndView listaPedidos() {
		ModelAndView modelAndView = new ModelAndView(LISTA_PEDIDO);
		modelAndView.addObject("pedidos", pedidoService.todos());
		return modelAndView;
	}

	@RequestMapping(value = "/pedido/remover/{id}", method = RequestMethod.GET)
	public String excluirPedido(@PathVariable("id") Pedido pedido) {
		pedidoService.delete(pedido);
		return "redirect:/lista-pedidos";
	}

	@RequestMapping(value = "/form-pedido", method = RequestMethod.GET)
	public ModelAndView adicionarPedido(Pedido pedido) {
		ModelAndView modelAndView = new ModelAndView(FORM_PEDIDO);
		modelAndView.addObject("clientes", clienteService.todos());
		modelAndView.addObject("produtos", produtoService.todos());
		modelAndView.addObject("carrinho", this.carrinho);
		modelAndView.addObject("valorTotal", valorTotal);
		return modelAndView;
	}

	@RequestMapping(value = "/form-pedido", method = RequestMethod.POST)
	public ModelAndView adicionaAoPedido(@SessionAttribute("carrinho") List<Produto> carrinho, Pedido pedido,
			BindingResult bindingResult) {

		ModelAndView modelAndView = new ModelAndView(FORM_PEDIDO);
		carrinho.add(pedido.getProdutos().get(0));

		double valTotal = 0;

		for (Produto produto : carrinho) {
			valTotal += produto.getPreco();
		}

		modelAndView.addObject("valorTotal", valTotal);

		return modelAndView;
	}

	@RequestMapping(value = "/form-pedido/remover/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView removeDoPedido(Pedido pedido, @PathVariable("id") Produto produto,
			@SessionAttribute("carrinho") List<Produto> carrinho) {

		ModelAndView modelAndView = new ModelAndView(FORM_PEDIDO);

		for (Produto p: carrinho) {
			if(p.getNome().equals(produto.getNome())){
				carrinho.remove(p);

				double valTotal = 0;

				for (Produto produto2 : carrinho) {
					valTotal += produto2.getPreco();
				}

				modelAndView.addObject("valorTotal", valTotal);

				break;
			}
		}

		return modelAndView;

	}

	@RequestMapping(value = "/salva-pedido", method = RequestMethod.POST)
	public String postFormulario(@SessionAttribute("carrinho") List<Produto> carrinho, @Valid Pedido pedido,
			@SessionAttribute("valorTotal") double valorTotal, BindingResult bindingResult, HttpSession httpSession) {

		pedido.setProdutos(carrinho);
		pedido.setValorPedido(valorTotal);

		try {
			pedidoService.salva(pedido);
			carrinho.clear();
			valorTotal = 0;
			return "redirect:/lista-pedidos";
		} catch (Exception e) {
			e.printStackTrace();
		}

		carrinho.clear();

		return "redirect:/lista-pedidos";
	}

	@RequestMapping(value = "/pedido/edicao/{id}", method = RequestMethod.GET)
	public ModelAndView edicaoPedido(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView(FORM_PEDIDO);
		modelAndView.addObject("clientes", clienteService.findAllByStatus(StatusBasicos.ATIVO));
		modelAndView.addObject("produtos", produtoService.findAllByStatus(StatusBasicos.ATIVO));
		modelAndView.addObject("carrinho", this.carrinho);
		modelAndView.addObject("valorTotal", valorTotal);
		modelAndView.addObject("pedido", pedidoService.buscaPor(id));
		return modelAndView;
	}
}
