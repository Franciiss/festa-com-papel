package com.festacompapel.repositry;

import org.springframework.data.repository.CrudRepository;

import com.festacompapel.model.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Long> {
}
