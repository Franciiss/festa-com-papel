package com.festacompapel.repository;

import com.festacompapel.model.StatusBasicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.festacompapel.model.Categoria;

import java.util.Collection;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT c FROM Categoria c where c.status = ?1")
    Collection<Categoria> findAllByStatus(StatusBasicos statusBasicos);

}
