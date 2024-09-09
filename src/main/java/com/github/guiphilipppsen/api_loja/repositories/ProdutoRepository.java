package com.github.guiphilipppsen.api_loja.repositories;

import com.github.guiphilipppsen.api_loja.Entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
