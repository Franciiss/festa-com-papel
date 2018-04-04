package com.festacompapel.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.festacompapel.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
