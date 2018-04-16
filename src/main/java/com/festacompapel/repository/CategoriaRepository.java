package com.festacompapel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.festacompapel.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
