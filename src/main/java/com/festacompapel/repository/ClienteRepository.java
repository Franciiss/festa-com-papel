package com.festacompapel.repository;

import com.festacompapel.model.StatusBasicos;
import org.springframework.data.jpa.repository.JpaRepository;

import com.festacompapel.model.Cliente;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT cliente from Cliente cliente where cliente.status = ?1")
    Collection<Cliente> findAllByStatus(StatusBasicos statusBasicos);

    @Query(value = "Select count(*) from Cliente where YEARWEEK(data_cadastro, 1) = YEARWEEK(CURDATE(), 1)", nativeQuery = true)
    int buscarTodosOsClientesDaSemana();

    @Query(value = "Select count(*) from Cliente where WEEKOFYEAR(data_cadastro)=WEEKOFYEAR(NOW())-1", nativeQuery = true)
    int buscarTodosOsClientesDaSemanaPassada();
}
