package com.github.guiphilipppsen.api_loja.servicetest;

import com.github.guiphilipppsen.api_loja.Entities.Cliente;
import com.github.guiphilipppsen.api_loja.Entities.Produto;
import com.github.guiphilipppsen.api_loja.Entities.Venda;
import com.github.guiphilipppsen.api_loja.repositories.ProdutoRepository;
import com.github.guiphilipppsen.api_loja.repositories.VendaRepository;
import com.github.guiphilipppsen.api_loja.service.VendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VendaServiceTest {

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private VendaService vendaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Salvar Venda - Deve salvar venda corretamente e calcular o valor total")
    void testSaveVenda() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setPreco(200.0);

        Cliente cliente = new Cliente();
        cliente.setIdade(20);

        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setProduto(Arrays.asList(produto));

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        String result = vendaService.save(venda);
        assertEquals("Venda salva", result);
        assertEquals(200.0, venda.getValorTotal());

        verify(vendaRepository, times(1)).save(venda);
        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Salvar Venda - Deve lançar exceção quando valor total exceder R$500,00 e cliente for menor de 18 anos")
    void testSaveVendaComClienteMenorDeIdadeComValorAcimaDeLimite() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setPreco(600.0);

        Cliente cliente = new Cliente();
        cliente.setIdade(17);

        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setProduto(Arrays.asList(produto));

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            vendaService.save(venda);
        });

        assertEquals("O valor total da venda não pode exceder R$500,00 para menores de 18 anos!! >:(", exception.getMessage());

        verify(vendaRepository, never()).save(venda);
    }

    @Test
    @DisplayName("Atualizar Venda - Deve atualizar venda corretamente")
    void testUpdateVenda() {
        Venda vendaExistente = new Venda();
        vendaExistente.setId(1L);

        Venda vendaAtualizada = new Venda();
        Cliente clienteAtualizado = new Cliente();
        vendaAtualizada.setCliente(clienteAtualizado);

        when(vendaRepository.findById(1L)).thenReturn(Optional.of(vendaExistente));

        String result = vendaService.update(vendaAtualizada, 1L);
        assertEquals("Venda atualizado", result);

        verify(vendaRepository, times(1)).findById(1L);
        verify(vendaRepository, times(1)).save(vendaExistente);
        assertEquals(clienteAtualizado, vendaExistente.getCliente());
    }

    @Test
    @DisplayName("Buscar Venda por ID - Deve retornar venda quando encontrada")
    void testFindByIdFound() {
        Venda venda = new Venda();
        venda.setId(1L);

        when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));

        Optional<Venda> result = vendaService.findById(1L);


        assertTrue(result.isPresent(), "Venda deveria estar presente");
        assertEquals(1L, result.get().getId(), "ID da venda não corresponde");

        verify(vendaRepository, times(1)).findById(1L);
    }


    @Test
    @DisplayName("Buscar Venda por ID - Deve lançar exceção quando venda não for encontrada")
    void testFindByIdNotFound() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            vendaService.findById(1L);
        });

        assertEquals("Venda nao encontrado: 1", exception.getMessage());

        verify(vendaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deletar Venda por ID - Deve remover venda corretamente")
    void testDeleteById() {
        Venda venda = new Venda();
        venda.setId(1L);

        when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));

        String result = vendaService.deleteById(1L);
        assertEquals("Venda removido", result);

        verify(vendaRepository, times(1)).findById(1L);
        verify(vendaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Buscar Vendas por Cliente - Deve retornar lista de vendas filtrada por cliente")
    void testBuscaVendaPorCliente() {
        Venda venda = new Venda();
        when(vendaRepository.findByClientNameContaining("Cliente")).thenReturn(Arrays.asList(venda));

        List<Venda> vendas = vendaService.buscaVendaPorCliente("Cliente");
        assertEquals(1, vendas.size());
        verify(vendaRepository, times(1)).findByClientNameContaining("Cliente");
    }

    @Test
    @DisplayName("Buscar Vendas por Funcionário - Deve retornar lista de vendas filtrada por funcionário")
    void testBuscaVendaPorFuncionario() {
        Venda venda = new Venda();
        when(vendaRepository.findByEmployeeNameContaining("Funcionario")).thenReturn(Arrays.asList(venda));

        List<Venda> vendas = vendaService.buscaVendaFuncionario("Funcionario");
        assertEquals(1, vendas.size());
        verify(vendaRepository, times(1)).findByEmployeeNameContaining("Funcionario");
    }

    @Test
    @DisplayName("Buscar Top 10 Vendas por Valor Total - Deve retornar lista de vendas ordenada por valor total")
    void testBuscarTop10VendasPorValorTotal() {
        Venda venda = new Venda();
        venda.setValorTotal(1000.0);

        when(vendaRepository.findByOrderByValorTotalDesc()).thenReturn(Arrays.asList(venda));

        List<Venda> vendas = vendaService.buscarTop10VendasPorValorTotal();
        assertEquals(1, vendas.size());
        assertEquals(1000.0, vendas.get(0).getValorTotal());

        verify(vendaRepository, times(1)).findByOrderByValorTotalDesc();
    }
}
