package com.festacompapel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.festacompapel.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
