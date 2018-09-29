package com.festacompapel.repository;

import com.festacompapel.model.StatusBasicos;
import com.festacompapel.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import com.festacompapel.model.Pedido;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT p FROM Pedido p where p.cliente.status = ?1")
    Collection<Pedido> findAllByStatus(StatusPedido statusPedido);

    @Query(value = "SELECT * FROM Pedido where YEARWEEK(data_pedido, 1) = YEARWEEK(CURDATE(), 1)", nativeQuery = true)
    Collection<Pedido> findAllBySemana(StatusPedido statusPedido);

    @Query(value = "Select count(*) from Pedido where YEARWEEK(data_pedido, 1) = YEARWEEK(CURDATE(), 1) and status = 'CONCLUIDO'", nativeQuery = true)
    int buscarTodosOsPedidosDaSemana();

    @Query(value = "Select count(*) from Pedido where WEEKOFYEAR(data_pedido)=WEEKOFYEAR(NOW())-1", nativeQuery = true)
    int buscarTodosOsPedidosDaSemanaPassada();

    @Query(value = "Select count(*) from festacompapel.pedido where day(data_pedido) = day(curdate())", nativeQuery = true)
    int buscarQuantidadeDeVendasDoDia();

    @Query("Select sum(p.valorPedido) FROM Pedido p where p.status = ?1")
    double somaTotal(StatusPedido statusPedido);

    @Query(value = "Select sum(valor_pedido) from Pedido where day(data_pedido) = day(curdate())", nativeQuery = true)
    Optional<Double> somaTotalDasVendasPorDia();
}
