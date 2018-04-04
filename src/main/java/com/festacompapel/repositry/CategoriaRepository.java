package com.festacompapel.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.festacompapel.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
