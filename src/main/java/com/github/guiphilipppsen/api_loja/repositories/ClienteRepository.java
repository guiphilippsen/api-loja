package com.github.guiphilipppsen.api_loja.repositories;

import com.github.guiphilipppsen.api_loja.Entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}