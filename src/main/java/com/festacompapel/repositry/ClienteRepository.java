package com.festacompapel.repositry;

import org.springframework.data.repository.CrudRepository;

import com.festacompapel.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
}
