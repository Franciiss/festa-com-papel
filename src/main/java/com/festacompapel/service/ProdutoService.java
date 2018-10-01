package com.festacompapel.service;

import com.festacompapel.model.Produto;
import com.festacompapel.model.StatusBasicos;
import com.festacompapel.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

	public Collection<Produto> findAllByStatus(StatusBasicos statusBasicos) {
		return produtoRepository.findAllByStatus(statusBasicos);
	}


	@Transactional
	public void atualizarStatus(Long id, StatusBasicos statusBasicos) {

		if (id != null) {

			Produto produto = this.buscaPor(id);
			produto.setStatus(statusBasicos);
			this.salva(produto);

		} else {
			throw new IllegalArgumentException("Informe um produto válido");
		}
	}

}
