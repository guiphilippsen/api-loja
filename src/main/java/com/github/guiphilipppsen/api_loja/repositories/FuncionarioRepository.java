package com.github.guiphilipppsen.api_loja.repositories;

import com.github.guiphilipppsen.api_loja.Entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
