package com.github.guiphilipppsen.api_loja.service;

import com.github.guiphilipppsen.api_loja.Entities.Cliente;
import com.github.guiphilipppsen.api_loja.Entities.Produto;
import com.github.guiphilipppsen.api_loja.Entities.Venda;
import com.github.guiphilipppsen.api_loja.repositories.ProdutoRepository;
import com.github.guiphilipppsen.api_loja.repositories.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Transactional
    public String save(Venda venda){
        double valorTotal = this.calcularTotalVenda(venda.getProduto());
        venda.setValorTotal(valorTotal);

        this.verificaIdadeCliente(venda.getCliente(), valorTotal);

        this.vendaRepository.save(venda);
        return "Venda salva";
    }

    public String saveMultiple(List<Venda> vendas) {
        this.vendaRepository.saveAll(vendas);
        return "Vendas salvadas";
    }

    public String update(Venda vendaUpdate, long id) {
        Optional<Venda> vendaOptional =
                this.vendaRepository.findById(id);

        if(vendaOptional.isPresent()) {
            Venda venda = vendaOptional.get();
            venda.setCliente(
                    vendaUpdate.getCliente() != null ?
                            vendaUpdate.getCliente() :
                            venda.getCliente()
            );
            venda.setFuncionario(
                    vendaUpdate.getFuncionario() != null ?
                            vendaUpdate.getFuncionario() :
                            venda.getFuncionario()
            );
            venda.setProduto(
                    vendaUpdate.getProduto() != null ?
                            vendaUpdate.getProduto() :
                            venda.getProduto()
            );
            this.vendaRepository.save(venda);
        }
        return "Venda atualizado";
    }
    public List<Venda> findAll() {
        return this.vendaRepository.findAll();
    }

    public Optional<Venda> findById(long id) {
        if(this.vendaRepository.findById(id).isEmpty()){
            throw new RuntimeException("Venda nao encontrado: " + id);
        }
        return this.vendaRepository.findById(id);
    }
    public String deleteById(long id) {
        if(this.vendaRepository.findById(id).isEmpty()){
            throw new RuntimeException("Venda nao encontrado: " + id);
        }
        this.vendaRepository.deleteById(id);
        return "Venda removido";
    }

    public List<Venda> buscaVendaPorCliente(String nomeCliente) {
        return this.vendaRepository.findByClientNameContaining(nomeCliente);
    }

    public List<Venda> buscaVendaFuncionario(String nomeFuncionario) {
        return this.vendaRepository.findByEmployeeNameContaining(nomeFuncionario);
    }

    public List<Venda> buscarTop10VendasPorValorTotal() {
        return this.vendaRepository.findByOrderByValorTotalDesc();
    }

    private double calcularTotalVenda(List<Produto>produtos) {
        double valorTotal = 0;
        for(Produto produto : produtos) {

            Produto produtoAUX = produtoRepository.findById(produto.getId()).get();

            valorTotal+=produtoAUX.getPreco();
        }
        return valorTotal;
    }

    private void verificaIdadeCliente(Cliente cliente, double valorTotal) {
        if (cliente.getIdade() < 18 && valorTotal > 500) {
            throw new IllegalArgumentException("O valor total da venda não pode exceder R$500,00 para menores de 18 anos!! >:(");
        }
    }
}
