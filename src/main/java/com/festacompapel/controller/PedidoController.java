package com.festacompapel.controller;

import com.festacompapel.model.Pedido;
import com.festacompapel.model.Produto;
import com.festacompapel.model.StatusBasicos;
import com.festacompapel.model.StatusPedido;
import com.festacompapel.service.ClienteService;
import com.festacompapel.service.PedidoService;
import com.festacompapel.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes(value = { "clientes", "produtos", "carrinho", "valorTotal" })
public class PedidoController {

	private static final String FORM_PEDIDO = "pedido/form-pedido";
	private static final String LISTA_PEDIDO = "pedido/lista-pedidos";
    private static final String VISUALIZA_PEDIDO = "pedido/visualiza-pedido";

    private final PedidoService pedidoService;

	private final ProdutoService produtoService;

	private final ClienteService clienteService;

	@Autowired
	public PedidoController(PedidoService pedidoService, ProdutoService produtoService, ClienteService categoriaService){
		this.pedidoService = pedidoService;
		this.produtoService = produtoService;
		this.clienteService = categoriaService;
	}

    @ModelAttribute()
    public void addAttribute(Model model, HttpSession httpSession){
        model.addAttribute("produto", new Produto());
        model.addAttribute("pedido",  new Pedido());
        model.addAttribute("clientes", clienteService.todos());
        model.addAttribute("produtos", produtoService.todos());
    }

	@RequestMapping(value = "/form-pedido", method = RequestMethod.GET)
	public ModelAndView formPedido(Pedido pedido, BindingResult bindingResult, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView(FORM_PEDIDO);

        pedido = new Pedido();

        return modelAndView;
    }

	@RequestMapping(value = "/form-pedido/adiciona-produto", method = RequestMethod.POST)
	public String adicionaAoPedido(@Valid Produto produto, BindingResult bindingResult, HttpSession session) {

        Produto p = produtoService.buscaPor(produto.getId());

        if(session.getAttribute("pedido") == null){
            session.setAttribute("pedido", new Pedido());
        }

		if(session.getAttribute("carrinho") == null){
            session.setAttribute("carrinho", new ArrayList<Produto>());
            ((List<Produto>) session.getAttribute("carrinho")).add(p);
        } else {
            ((List<Produto>) session.getAttribute("carrinho")).add(p);
        }

        System.out.println(session.getAttribute("carrinho"));

        return "redirect:/form-pedido";
    }

	@RequestMapping(value = "/form-pedido/remover/{id}", method = RequestMethod.POST)
	public String removeDoPedido(@PathVariable("id") Long id, HttpSession session) {

	    Produto produto = produtoService.buscaPor(id);
        List<Produto> carrinho = (List<Produto>) session.getAttribute("carrinho");

        if(carrinho.contains(produto)){
            carrinho.remove(produto);
        }

        session.setAttribute("carrinho", carrinho);

        return "redirect:/form-pedido";
	}

	@RequestMapping(value = "/salva-pedido", method = RequestMethod.POST)
	public String postFormulario(@Valid Pedido pedido, Model model,
                                 BindingResult bindingResult, HttpSession httpSession) {

		pedido.setProdutos((List<Produto>) httpSession.getAttribute("carrinho"));
		pedido.setValorPedido(this.getValorTotal((List<Produto>) httpSession.getAttribute("carrinho")));

		try {
			pedidoService.salva(pedido);
		} catch (Exception e) {
			e.printStackTrace();
		}

        model.addAttribute("carrinho", new ArrayList<Produto>());

		return "redirect:/lista-pedidos";
	}

	public double getValorTotal(List<Produto> carrinho){
        double valorTotal = 0;

        for (Produto produto: carrinho) {
            valorTotal += produto.getPreco();
        }

        return valorTotal;

    }

	@RequestMapping(value = "/pedido/edicao/{id}", method = RequestMethod.GET)
	public ModelAndView edicaoPedido(@PathVariable("id") Long id, HttpSession httpSession) {

	    ModelAndView modelAndView = new ModelAndView(FORM_PEDIDO);

		Pedido pedido = pedidoService.buscaPor(id);

        modelAndView.addObject("pedido", pedido);
        modelAndView.addObject("clientes", clienteService.findAllByStatus(StatusBasicos.ATIVO));
        modelAndView.addObject("produtos", produtoService.findAllByStatus(StatusBasicos.ATIVO));
        modelAndView.addObject("valorTotal", pedido.getValorTotal());

        List<Object[]> produtosNoCarrinho = (List<Object[]>) pedidoService.getProdutosFromPedidoId(id);
        ArrayList<Produto> produtos = new ArrayList<Produto>();

        if(produtosNoCarrinho != null){
            for (Object[] object : produtosNoCarrinho) {
                produtos.add(produtoService.buscaPor(((BigInteger) object[1]).longValue()));
            }
        }

        modelAndView.addObject("carrinho", produtos);

        httpSession.setAttribute("carrinho", produtos);
        httpSession.setAttribute("pedido", pedido);


        return modelAndView;
	}

    @RequestMapping(value = "/pedido/alterarStatus/{id}/{statusPedido}", method = RequestMethod.GET)
    public String alterarStatus(@PathVariable("id") Long id, @PathVariable("statusPedido")StatusPedido statusPedido) {
        Pedido pedido = pedidoService.buscaPor(id);
        pedido.setStatus(statusPedido);
        pedidoService.salva(pedido);

        return "redirect:/";

	}

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

}