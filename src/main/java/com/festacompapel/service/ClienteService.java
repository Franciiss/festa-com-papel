package com.festacompapel.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.festacompapel.model.Cliente;
import com.festacompapel.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	@Transactional
	public void salva(Cliente cliente) {
		clienteRepository.save(cliente);
	}

	public List<Cliente> todos() {
		return clienteRepository.findAll();
	}

	public Cliente buscaPor(Long id) {
		Optional<Cliente> clienteOptional = clienteRepository.findById(id);
		Cliente cliente = null;

		if (clienteOptional.isPresent()) {
			cliente = clienteOptional.get();
		}

		return cliente;
	}

	public void delete(Cliente cliente) {
		clienteRepository.delete(cliente);
	}

	public List<Cliente> todas() {
		return clienteRepository.findAll();
	}

	@Transactional
	public void excluirPelo(Long id) {

		if (id != null) {
			clienteRepository.deleteById(id);
			clienteRepository.flush();
		} else {
			throw new IllegalArgumentException("Informe um cliente válido para exclusão");
		}
	}

}
