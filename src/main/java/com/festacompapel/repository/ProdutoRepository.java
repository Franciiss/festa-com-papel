package com.festacompapel.repository;

import com.festacompapel.model.Produto;
import com.festacompapel.model.StatusBasicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT p FROM Produto p where p.status = ?1")
    Collection<Produto> findAllByStatus(StatusBasicos statusBasicos);
}
