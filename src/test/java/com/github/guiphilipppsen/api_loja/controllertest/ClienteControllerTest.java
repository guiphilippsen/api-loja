package com.github.guiphilipppsen.api_loja.controllertest;

import com.github.guiphilipppsen.api_loja.Entities.Cliente;
import com.github.guiphilipppsen.api_loja.controllers.ClienteController;
import com.github.guiphilipppsen.api_loja.service.ClienteService;
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

public class ClienteControllerTest {

    @InjectMocks
    private ClienteController controller;

    @Mock
    private ClienteService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Salvar cliente com sucesso")
    public void saveClienteSuccess() {
        Cliente cliente = new Cliente();
        when(service.save(any(Cliente.class))).thenReturn("Cliente salvo com sucesso");

        ResponseEntity<String> response = controller.save(cliente);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente salvo com sucesso", response.getBody());
    }

    @Test
    @DisplayName("Erro ao salvar cliente")
    public void saveClienteFailure() {
        Cliente cliente = new Cliente();
        when(service.save(any(Cliente.class))).thenThrow(new RuntimeException("Erro ao salvar"));

        ResponseEntity<String> response = controller.save(cliente);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro:Erro ao salvar", response.getBody());
    }

    @Test
    @DisplayName("Atualizar cliente com sucesso")
    public void updateClienteSuccess() {
        Cliente cliente = new Cliente();
        when(service.update(any(Cliente.class), anyLong())).thenReturn("Cliente atualizado com sucesso");

        ResponseEntity<String> response = controller.update(cliente, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente atualizado com sucesso", response.getBody());
    }

    @Test
    @DisplayName("Erro ao atualizar cliente")
    public void updateClienteFailure() {
        Cliente cliente = new Cliente();
        when(service.update(any(Cliente.class), anyLong())).thenThrow(new RuntimeException("Erro ao atualizar"));

        ResponseEntity<String> response = controller.update(cliente, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Deu erro!Erro ao atualizar", response.getBody());
    }

    @Test
    @DisplayName("Buscar cliente por ID com sucesso")
    public void findByIdSuccess() {
        Cliente cliente = new Cliente();
        when(service.findById(anyLong())).thenReturn(Optional.of(cliente));

        ResponseEntity<Optional<Cliente>> response = controller.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(cliente), response.getBody());
    }

    @Test
    @DisplayName("Erro ao buscar cliente por ID")
    public void findByIdFailure() {
        when(service.findById(anyLong())).thenThrow(new RuntimeException("Erro ao buscar cliente"));

        ResponseEntity<Optional<Cliente>> response = controller.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    @DisplayName("Listar todos os clientes com sucesso")
    public void findAllSuccess() {
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        when(service.findAll()).thenReturn(clientes);

        ResponseEntity<List<Cliente>> response = controller.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientes, response.getBody());
    }

    @Test
    @DisplayName("Erro ao listar todos os clientes")
    public void findAllFailure() {
        when(service.findAll()).thenThrow(new RuntimeException("Erro ao buscar clientes"));

        ResponseEntity<List<Cliente>> response = controller.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    @DisplayName("Deletar cliente com sucesso")
    public void deleteClienteSuccess() {
        when(service.deleteById(anyLong())).thenReturn("Cliente deletado com sucesso");

        ResponseEntity<String> response = controller.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente deletado com sucesso", response.getBody());
    }

    @Test
    @DisplayName("Erro ao deletar cliente")
    public void deleteClienteFailure() {
        when(service.deleteById(anyLong())).thenThrow(new RuntimeException("Erro ao deletar cliente"));

        ResponseEntity<String> response = controller.delete(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}

