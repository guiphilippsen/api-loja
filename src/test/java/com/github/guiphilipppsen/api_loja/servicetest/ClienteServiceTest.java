package com.github.guiphilipppsen.api_loja.service;

import com.github.guiphilipppsen.api_loja.Entities.Cliente;
import com.github.guiphilipppsen.api_loja.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    private Cliente cliente;
    private Cliente clienteUpdate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João");
        cliente.setIdade(30);
        cliente.setEmail("joao@example.com");
        cliente.setEndereco("Rua A");
        cliente.setTelefone("123456789");

        clienteUpdate = new Cliente();
        clienteUpdate.setNome("Maria");
        clienteUpdate.setIdade(25);
        clienteUpdate.setEmail("maria@example.com");
    }

    @Test
    @DisplayName("Deve salvar um cliente com sucesso")
    void testSave() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        String result = clienteService.save(cliente);
        assertEquals("Cliente salvo", result);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    @DisplayName("Deve atualizar um cliente existente")
    void testUpdate_ClientExists() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        String result = clienteService.update(clienteUpdate, 1L);
        assertEquals("Cliente atualizado", result);
        assertEquals("Maria", cliente.getNome());
        assertEquals(25, cliente.getIdade());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    @DisplayName("Não deve atualizar um cliente que não existe")
    void testUpdate_ClientDoesNotExist() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());
        String result = clienteService.update(clienteUpdate, 1L);
        assertEquals("Cliente atualizado", result);
        verify(clienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve retornar todos os clientes")
    void testFindAll() {
        clienteService.findAll();
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve encontrar um cliente pelo ID")
    void testFindById_ClientExists() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        Optional<Cliente> result = clienteService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(cliente, result.get());
    }

    @Test
    @DisplayName("Não deve encontrar um cliente que não existe")
    void testFindById_ClientDoesNotExist() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.findById(1L);
        });
        assertEquals("Cliente nao encontrado: 1", exception.getMessage());
    }

    @Test
    @DisplayName("Deve remover um cliente existente pelo ID")
    void testDeleteById_ClientExists() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        String result = clienteService.deleteById(1L);
        assertEquals("Cliente removido", result);
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Não deve remover um cliente que não existe")
    void testDeleteById_ClientDoesNotExist() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.deleteById(1L);
        });
        assertEquals("Cliente nao encontrado: 1", exception.getMessage());
        verify(clienteRepository, never()).deleteById(anyLong());
    }
}