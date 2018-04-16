package com.festacompapel.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.festacompapel.model.Pedido;
import com.festacompapel.repository.PedidoRepository;

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

	public List<Pedido> todas() {
		return pedidoRepository.findAll();
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

}
