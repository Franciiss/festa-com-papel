package com.festacompapel.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.festacompapel.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
