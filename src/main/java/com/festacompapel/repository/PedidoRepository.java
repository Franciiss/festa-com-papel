package com.festacompapel.repository;

import com.festacompapel.model.Pedido;
import com.festacompapel.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT p FROM Pedido p where p.status = ?1")
    Collection<Pedido> findAllByStatus(StatusPedido statusPedido);

    @Query(value = "SELECT * FROM Pedido where YEARWEEK(data_pedido, 1) = YEARWEEK(CURDATE(), 1) and status= ?1", nativeQuery = true)
    Collection<Pedido> findAllBySemana(StatusPedido statusPedido);

    @Query(value = "Select pedido_id, produtos_id from pedido_produtos pp where pp.pedido_id = ?1", nativeQuery = true)
    Collection<Object[]> getProdutosFromPedidoId(Long pedidoId);

    @Query(value = "Select count(*) from Pedido", nativeQuery = true)
    int buscarContagemDeTodosOsPedidosFeitos();

    @Query(value = "Select count(*) from Pedido where YEARWEEK(data_pedido, 1) = YEARWEEK(CURDATE(), 1)", nativeQuery = true)
    int buscarTodosOsPedidosDaSemana();

    @Query(value = "Select count(*) from Pedido where YEARWEEK(data_pedido, 1) = YEARWEEK(CURDATE(), 1) and status = 'CONCLUIDO'", nativeQuery = true)
    int buscarTodosOsPedidosDaSemanaConcluidos();

    @Query(value = "Select count(*) from Pedido where WEEKOFYEAR(data_pedido)=WEEKOFYEAR(NOW())-1", nativeQuery = true)
    int buscarTodosOsPedidosDaSemanaPassada();

    @Query(value = "Select count(*) from Pedido where day(data_pedido) = day(data_pedido) - ?1", nativeQuery = true)
    int buscarQuantidadeDeVendasDiasAtras(int quantidadeDeDias);

    @Query(value = "Select count(*) from Pedido p where p.status = ?1", nativeQuery = true)
    int findAllContagemByStatus(StatusPedido statusPedido);

    @Query(value = "SELECT count(*) FROM pedido\n" +
            "WHERE YEAR(data_pedido) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH) " +
            "AND MONTH(data_pedido) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)", nativeQuery = true)
    int buscarQuantidadeDeVendasDoMes();

    @Query(value = "Select sum(valor_pedido) FROM Pedido where status = ?1", nativeQuery = true)
    double somaTotal(StatusPedido statusPedido);

    @Query(value = "Select sum(valor_pedido) FROM Pedido", nativeQuery = true)
    float somaTotal();

    @Query(value = "Select sum(valor_pedido) FROM Pedido where status = ?1 and data_pedido = day(data_pedido)", nativeQuery = true)
    float somaTotalDeHoje(StatusPedido statusPedido);

    @Query(value = "Select count(*) from pedido where day(data_pedido) = day(curdate())", nativeQuery = true)
    int quantidadeDeVendasDeHoje();

    @Query(value = "Select sum(valor_pedido) from Pedido where day(data_pedido) = day(curdate())", nativeQuery = true)
    Optional<Float> somaTotalDasVendasPorDia();
}
