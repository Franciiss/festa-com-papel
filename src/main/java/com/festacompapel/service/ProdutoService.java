package com.festacompapel.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.festacompapel.model.Produto;
import com.festacompapel.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository produtoRepository;

	@Transactional
	public void salva(Produto produto) {
		produtoRepository.save(produto);
	}

	public List<Produto> todos() {
		return produtoRepository.findAll();
	}

	public Produto buscaPor(Long id) {
		Optional<Produto> produtoOptional = produtoRepository.findById(id);
		Produto produto = null;

		if (produtoOptional.isPresent()) {
			produto = produtoOptional.get();
		}

		return produto;
	}

	public void delete(Produto produto) {
		produtoRepository.delete(produto);
	}

	@Transactional
	public void excluirPelo(Long id) {

		if (id != null) {
			produtoRepository.deleteById(id);
			produtoRepository.flush();
		} else {
			throw new IllegalArgumentException("Informe um produto válida para exclusão");
		}
	}

}
