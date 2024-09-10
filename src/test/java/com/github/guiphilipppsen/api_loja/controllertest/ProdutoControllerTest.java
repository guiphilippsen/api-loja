package com.github.guiphilipppsen.api_loja.controllertest;

import com.github.guiphilipppsen.api_loja.Entities.Produto;
import com.github.guiphilipppsen.api_loja.controllers.ProdutoController;
import com.github.guiphilipppsen.api_loja.service.ProdutoService;
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

public class ProdutoControllerTest {

    @InjectMocks
    private ProdutoController controller;

    @Mock
    private ProdutoService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Salvar produto com sucesso")
    public void saveProdutoSuccess() {
        Produto produto = new Produto();
        when(service.save(any(Produto.class))).thenReturn("Produto salvo com sucesso");

        ResponseEntity<String> response = controller.save(produto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto salvo com sucesso", response.getBody());
    }

    @Test
    @DisplayName("Erro ao salvar produto")
    public void saveProdutoFailure() {
        Produto produto = new Produto();
        when(service.save(any(Produto.class))).thenThrow(new RuntimeException("Erro ao salvar"));

        ResponseEntity<String> response = controller.save(produto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro:Erro ao salvar", response.getBody());
    }

    @Test
    @DisplayName("Atualizar produto com sucesso")
    public void updateProdutoSuccess() {
        Produto produto = new Produto();
        when(service.update(any(Produto.class), anyLong())).thenReturn("Produto atualizado com sucesso");

        ResponseEntity<String> response = controller.update(produto, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto atualizado com sucesso", response.getBody());
    }

    @Test
    @DisplayName("Erro ao atualizar produto")
    public void updateProdutoFailure() {
        Produto produto = new Produto();
        when(service.update(any(Produto.class), anyLong())).thenThrow(new RuntimeException("Erro ao atualizar"));

        ResponseEntity<String> response = controller.update(produto, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Deu erro!Erro ao atualizar", response.getBody());
    }

    @Test
    @DisplayName("Buscar produto por ID com sucesso")
    public void findByIdSuccess() {
        Produto produto = new Produto();
        when(service.findById(anyLong())).thenReturn(Optional.of(produto));

        ResponseEntity<Optional<Produto>> response = controller.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(produto), response.getBody());
    }

    @Test
    @DisplayName("Erro ao buscar produto por ID")
    public void findByIdFailure() {
        when(service.findById(anyLong())).thenThrow(new RuntimeException("Erro ao buscar produto"));

        ResponseEntity<Optional<Produto>> response = controller.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    @DisplayName("Listar todos os produtos com sucesso")
    public void findAllSuccess() {
        Produto produto1 = new Produto();
        Produto produto2 = new Produto();
        List<Produto> produtos = Arrays.asList(produto1, produto2);

        when(service.findAll()).thenReturn(produtos);

        ResponseEntity<List<Produto>> response = controller.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtos, response.getBody());
    }

    @Test
    @DisplayName("Erro ao listar todos os produtos")
    public void findAllFailure() {
        when(service.findAll()).thenThrow(new RuntimeException("Erro ao buscar produtos"));

        ResponseEntity<List<Produto>> response = controller.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    @DisplayName("Deletar produto com sucesso")
    public void deleteProdutoSuccess() {
        when(service.deleteById(anyLong())).thenReturn("Produto deletado com sucesso");

        ResponseEntity<String> response = controller.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto deletado com sucesso", response.getBody());
    }

    @Test
    @DisplayName("Erro ao deletar produto")
    public void deleteProdutoFailure() {
        when(service.deleteById(anyLong())).thenThrow(new RuntimeException("Erro ao deletar produto"));

        ResponseEntity<String> response = controller.delete(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}
