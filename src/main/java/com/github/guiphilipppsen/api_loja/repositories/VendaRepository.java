package com.github.guiphilipppsen.api_loja.repositories;

import com.github.guiphilipppsen.api_loja.Entities.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByClientNameContaining(String nomeCliente);
    List<Venda> findByEmployeeNameContaining(String nomeFuncionario);
    List<Venda> findByOrderByValorTotalDesc();
}
