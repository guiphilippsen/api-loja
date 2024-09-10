package com.github.guiphilipppsen.api_loja.controllertest;

import com.github.guiphilipppsen.api_loja.Entities.Venda;
import com.github.guiphilipppsen.api_loja.controllers.VendaController;
import com.github.guiphilipppsen.api_loja.service.VendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class VendaControllerTest {

    @InjectMocks
    private VendaController controller;

    @Mock
    private VendaService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Salvar venda com sucesso")
    public void saveVendaSuccess() {
        Venda venda = new Venda();
        when(service.save(any(Venda.class))).thenReturn("Venda salva com sucesso");

        ResponseEntity<String> response = controller.save(venda);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Venda salva com sucesso", response.getBody());
    }

    @Test
    @DisplayName("Erro ao salvar venda")
    public void saveVendaFailure() {
        Venda venda = new Venda();
        when(service.save(any(Venda.class))).thenThrow(new RuntimeException("Erro ao salvar venda"));

        ResponseEntity<String> response = controller.save(venda);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro:Erro ao salvar venda", response.getBody());
    }

    @Test
    @DisplayName("Salvar múltiplas vendas com sucesso")
    public void saveMultipleVendasSuccess() {
        List<Venda> vendas = Arrays.asList(new Venda(), new Venda());
        when(service.saveMultiple(any(List.class))).thenReturn("Vendas salvas com sucesso");

        ResponseEntity<String> response = controller.saveMultiple(vendas);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vendas salvas com sucesso", response.getBody());
    }

    @Test
    @DisplayName("Erro ao salvar múltiplas vendas")
    public void saveMultipleVendasFailure() {
        List<Venda> vendas = Arrays.asList(new Venda(), new Venda());
        when(service.saveMultiple(any(List.class))).thenThrow(new RuntimeException("Erro ao salvar múltiplas vendas"));

        ResponseEntity<String> response = controller.saveMultiple(vendas);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Erro ao salvar múltiplas vendas", response.getBody());
    }

    @Test
    @DisplayName("Atualizar venda com sucesso")
    public void updateVendaSuccess() {
        Venda venda = new Venda();
        when(service.update(any(Venda.class), anyLong())).thenReturn("Venda atualizada com sucesso");

        ResponseEntity<String> response = controller.update(venda, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Venda atualizada com sucesso", response.getBody());
    }

    @Test
    @DisplayName("Erro ao atualizar venda")
    public void updateVendaFailure() {
        Venda venda = new Venda();
        when(service.update(any(Venda.class), anyLong())).thenThrow(new RuntimeException("Erro ao atualizar venda"));

        ResponseEntity<String> response = controller.update(venda, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Deu erro!Erro ao atualizar venda", response.getBody());
    }

    @Test
    @DisplayName("Buscar venda por ID com sucesso")
    public void findByIdSuccess() {
        Venda venda = new Venda();
        when(service.findById(anyLong())).thenReturn(Optional.of(venda));

        ResponseEntity<Optional<Venda>> response = controller.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(venda), response.getBody());
    }

    @Test
    @DisplayName("Erro ao buscar venda por ID")
    public void findByIdFailure() {
        when(service.findById(anyLong())).thenThrow(new RuntimeException("Erro ao buscar venda"));

        ResponseEntity<Optional<Venda>> response = controller.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    @DisplayName("Listar todas as vendas com sucesso")
    public void findAllSuccess() {
        List<Venda> vendas = Arrays.asList(new Venda(), new Venda());
        when(service.findAll()).thenReturn(vendas);

        ResponseEntity<List<Venda>> response = controller.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vendas, response.getBody());
    }

    @Test
    @DisplayName("Erro ao listar todas as vendas")
    public void findAllFailure() {
        when(service.findAll()).thenThrow(new RuntimeException("Erro ao listar vendas"));

        ResponseEntity<List<Venda>> response = controller.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    @DisplayName("Buscar vendas por nome do cliente")
    public void buscarVendasPorNomeCliente() {
        List<Venda> vendas = Arrays.asList(new Venda(), new Venda());
        when(service.buscaVendaPorCliente("João")).thenReturn(vendas);

        List<Venda> result = controller.buscarVendasPorNomeCliente("João");

        assertEquals(vendas, result);
    }

    @Test
    @DisplayName("Buscar vendas por nome do funcionário")
    public void buscarVendasPorNomeFuncionario() {
        List<Venda> vendas = Arrays.asList(new Venda(), new Venda());
        when(service.buscaVendaFuncionario("Maria")).thenReturn(vendas);

        List<Venda> result = controller.buscarVendasPorNomeFuncionario("Maria");

        assertEquals(vendas, result);
    }

    @Test
    @DisplayName("Buscar top 10 vendas por valor total")
    public void buscarTop10VendasPorValorTotal() {
        List<Venda> vendas = Arrays.asList(new Venda(), new Venda());
        when(service.buscarTop10VendasPorValorTotal()).thenReturn(vendas);

        List<Venda> result = controller.buscarTop10VendasPorValorTotal();

        assertEquals(vendas, result);
    }

    @Test
    @DisplayName("Deletar venda com sucesso")
    public void deleteVendaSuccess() {
        when(service.deleteById(anyLong())).thenReturn("Venda deletada com sucesso");

        ResponseEntity<String> response = controller.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Venda deletada com sucesso", response.getBody());
    }

    @Test
    @DisplayName("Erro ao deletar venda")
    public void deleteVendaFailure() {
        when(service.deleteById(anyLong())).thenThrow(new RuntimeException("Erro ao deletar venda"));

        ResponseEntity<String> response = controller.delete(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}
