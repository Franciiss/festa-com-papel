package com.festacompapel.controller;

import com.festacompapel.model.StatusPedido;
import com.festacompapel.service.ClienteService;
import com.festacompapel.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class IndexController {

    private final PedidoService pedidoService;

    private final ClienteService clienteService;

    @Autowired
    public IndexController(PedidoService pedidoService, ClienteService clienteService) {
        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
    }

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");

        modelAndView.addObject("dia", new Date());

        // Pedidos

        // ** Diario **
        modelAndView.addObject("quantidadeDeVendasDeHoje", pedidoService.quantidadeDeVendasDeHoje());
        modelAndView.addObject("valorVendasDeHoje", pedidoService.somaTotalDasVendasDeHoje());

        // ** De Ontem **
        modelAndView.addObject("quantidadeDeVendasOntem", pedidoService.buscarQuantidadeDeVendasDiasAtras(1));

        // ** Semanal **

        modelAndView.addObject("quantidadeVendaEstaSemana", pedidoService.buscarTodosOsPedidosDaSemana());
        modelAndView.addObject("quantidadeDePedidoSemanaPassada", pedidoService.buscarTodosOsPedidosDaSemanaPassada());
        modelAndView.addObject("quantidadeDeVendasTotaisConcluidas", pedidoService.findAllContagemByStatus(StatusPedido.CONCLUIDO));

        // ** Mensal **

        modelAndView.addObject("quantidadeVendaEsteMes", pedidoService.buscarQuantidadeDeVendasDoMes());

        modelAndView.addObject("pedidosDaSemanaConcluidos", pedidoService.findAllBySemana(StatusPedido.CONCLUIDO));

        try{
            modelAndView.addObject("valorTodasAsVendas", pedidoService.somaTotal(StatusPedido.CONCLUIDO));
        } catch(Exception e){
            e.printStackTrace();
        }

        // Clientes

        modelAndView.addObject("quantidadeClientesEstaSemana", clienteService.buscarTodosOsClientesDaSemana());
        modelAndView.addObject("quantidadeDeClientesSemanaPassada", clienteService.buscarTodosOsClientesDaSemanaPassada());




        return modelAndView;
    }

}