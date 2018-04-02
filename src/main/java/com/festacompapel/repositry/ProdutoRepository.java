package com.festacompapel.repositry;

import org.springframework.data.repository.CrudRepository;

import com.festacompapel.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {
}
