package com.festacompapel.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.festacompapel.model.Categoria;
import com.festacompapel.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Transactional
	public void salva(Categoria categoria) {
		categoriaRepository.save(categoria);
	}

	public List<Categoria> todos() {
		return categoriaRepository.findAll();
	}

	public Categoria buscaPor(Long id) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
		Categoria categoria = null;

		if (categoriaOptional.isPresent()) {
			categoria = categoriaOptional.get();
		}

		return categoria;
	}

	public void delete(Categoria categoria) {
		categoriaRepository.delete(categoria);
	}

	public List<Categoria> todas() {
		return categoriaRepository.findAll();
	}

	@Transactional
	public void excluirPelo(Long id) {

		if (id != null) {
			categoriaRepository.deleteById(id);
			categoriaRepository.flush();
		} else {
			throw new IllegalArgumentException("Informe uma categoria válida para exclusão");
		}
	}

}
