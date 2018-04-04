package com.festacompapel.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.festacompapel.model.Pedido;
import com.festacompapel.model.Produto;
import com.festacompapel.repositry.ClienteRepository;
import com.festacompapel.repositry.PedidoRepository;
import com.festacompapel.repositry.ProdutoRepository;

@Controller
@SessionAttributes(value = { "clientes", "produtos", "carrinho", "valorTotal" })
public class PedidoController {

	public static final String FORM_PEDIDO = "pedido/form-pedido";
	public static final String LISTA_PEDIDO = "pedido/lista-pedidos";

	@Autowired
	PedidoRepository pedidoRepository;

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	ClienteRepository clienteRepository;

	List<Produto> carrinho = new ArrayList<Produto>();

	double valorTotal = 0;

	@RequestMapping(value = "/lista-pedidos", method = RequestMethod.GET)
	public ModelAndView listaPedidos(Pedido pedido) {
		ModelAndView modelAndView = new ModelAndView(LISTA_PEDIDO);
		modelAndView.addObject("pedidos", pedidoRepository.findAll());
		return modelAndView;
	}

	@RequestMapping(value = "/pedido/remover/{id}", method = RequestMethod.GET)
	public String excluirPedido(@PathVariable("id") Pedido pedido) {
		pedidoRepository.delete(pedido);
		return "redirect:/lista-pedidos";
	}

	@RequestMapping(value = "/form-pedido", method = RequestMethod.GET)
	public ModelAndView getFormulario(Pedido pedido) {
		ModelAndView modelAndView = new ModelAndView(FORM_PEDIDO);
		modelAndView.addObject("clientes", clienteRepository.findAll());
		modelAndView.addObject("produtos", produtoRepository.findAll());
		modelAndView.addObject("carrinho", this.carrinho);
		modelAndView.addObject("valorTotal", valorTotal);
		modelAndView.addObject("pedido", pedido);
		return modelAndView;
	}

	@RequestMapping(value = "/form-pedido", method = RequestMethod.POST)
	public ModelAndView adicionaAoPedido(@SessionAttribute("carrinho") List<Produto> carrinho, Pedido pedido,
			BindingResult bindingResult) {

		ModelAndView modelAndView = new ModelAndView(FORM_PEDIDO);
		carrinho.add(pedido.getProdutos().get(0));

		double valTotal = 0;

		for (Produto produto : carrinho) {
			valTotal += produto.getPrecoProduto();
		}

		modelAndView.addObject("valorTotal", valTotal);

		return modelAndView;
	}

	@RequestMapping(value = "/form-pedido/remover/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView removeDoPedido(Pedido pedido, @PathVariable("id") Produto produto,
			@SessionAttribute("carrinho") List<Produto> carrinho) {

		ModelAndView modelAndView = new ModelAndView(FORM_PEDIDO);

		for (int i = 0; i < carrinho.size(); i++) {
			if (carrinho.get(i).getNomeProduto().equals(produto.getNomeProduto())) {

				carrinho.remove(i);

				double valTotal = 0;

				for (Produto produto2 : carrinho) {
					valTotal += produto2.getPrecoProduto();
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
			pedidoRepository.save(pedido);
			httpSession.removeAttribute("clientes");
			httpSession.removeAttribute("produtos");
			httpSession.removeAttribute("carrinho");
			httpSession.removeAttribute("valorTotal");

			return "redirect:/lista-pedidos";
		} catch (Exception e) {
			e.printStackTrace();
		}

		carrinho.clear();

		return "redirect:/lista-pedidos";
	}

	@RequestMapping(value = "/pedido/edicao/{id}", method = RequestMethod.GET)
	public ModelAndView edicaoPedido(@PathVariable("id") Pedido pedido) {
		ModelAndView modelAndView = new ModelAndView(FORM_PEDIDO);
		modelAndView.addObject("pedido", pedido);
		pedidoRepository.saveAndFlush(pedido);
		return modelAndView;
	}
}
