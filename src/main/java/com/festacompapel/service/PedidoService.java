package com.festacompapel.service;

import com.festacompapel.model.Pedido;
import com.festacompapel.model.StatusPedido;
import com.festacompapel.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository pedidoRepository;

	@Transactional
	public void salva(Pedido pedido) {
		pedidoRepository.save(pedido);
	}

	public List<Pedido> todos() {
		return pedidoRepository.findAll();
	}

	public Pedido buscaPor(Long id) {
		Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
		Pedido pedido = null;

		if (pedidoOptional.isPresent()) {
			pedido = pedidoOptional.get();
		}

		return pedido;
	}

	public void delete(Pedido pedido) {
		pedidoRepository.delete(pedido);
	}

	@Transactional
	public void excluirPelo(Long id) {

		if (id != null) {
			pedidoRepository.deleteById(id);
			pedidoRepository.flush();
		} else {
			throw new IllegalArgumentException("Informe um pedido válida para exclusão");
		}
	}

    @Transactional
    public double somaTotal(StatusPedido statusPedido) {
		try{
			double somaTotal = pedidoRepository.somaTotal(statusPedido);
			return somaTotal;
		} catch (Exception e){
			return 0;
		}
    }

    @Transactional
    public Collection<Pedido> findAllByStatus(StatusPedido statusPedido){
	    return pedidoRepository.findAllByStatus(statusPedido);
    }

    @Transactional
    public int findAllContagemByStatus(StatusPedido statusPedido){
        return 0;
    }

	@Transactional
	public int buscarTodosOsPedidosDaSemana() {
		return pedidoRepository.buscarTodosOsPedidosDaSemana();
	}

	@Transactional
	public int buscarTodosOsPedidosDaSemanaPassada(){
		return pedidoRepository.buscarTodosOsPedidosDaSemanaPassada();
	}

	@Transactional
	public int buscarQuantidadeDeVendasDoDia(){
		return pedidoRepository.buscarQuantidadeDeVendasDiasAtras(0);
	}

	@Transactional
	public double somaTotalDasVendasDeHoje(){

		try{
			double somaTotalDasVendasHoje = pedidoRepository.somaTotalDasVendasPorDia().get();
			return somaTotalDasVendasHoje;
		} catch (Exception e){
			return 0;
		}
	}

	public Collection<Pedido> findAllBySemana(StatusPedido statusPedido) {

		try{
			Collection<Pedido> pedidos = pedidoRepository.findAllBySemana(statusPedido);
			return pedidos;
		} catch (Exception e){
			return new ArrayList<>();
		}
	}

	public int buscarQuantidadeDeVendasDiasAtras(int quantidadeDeDiasAtras){
		return pedidoRepository.buscarQuantidadeDeVendasDiasAtras(quantidadeDeDiasAtras);
	}

	public int buscarQuantidadeDeVendasDoMes(){
		return pedidoRepository.buscarQuantidadeDeVendasDoMes();
	}

	public Collection<Object> getProdutosFromPedidoId(Long pedidoId){
		return pedidoRepository.getProdutosFromPedidoId(pedidoId);

	}
}