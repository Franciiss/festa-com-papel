package com.festacompapel.repositry;

import org.springframework.data.repository.CrudRepository;

import com.festacompapel.model.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {
}
