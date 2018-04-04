package com.festacompapel.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.festacompapel.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
