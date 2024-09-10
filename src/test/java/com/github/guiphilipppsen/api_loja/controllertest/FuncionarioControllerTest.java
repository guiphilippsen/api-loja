package com.github.guiphilipppsen.api_loja.controllertest;

import com.github.guiphilipppsen.api_loja.Entities.Funcionario;
import com.github.guiphilipppsen.api_loja.controllers.FuncionarioController;
import com.github.guiphilipppsen.api_loja.service.FuncionarioService;
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

public class FuncionarioControllerTest {

    @InjectMocks
    private FuncionarioController controller;

    @Mock
    private FuncionarioService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Salvar funcionário com sucesso")
    public void saveFuncionarioSuccess() {
        Funcionario funcionario = new Funcionario();
        when(service.save(any(Funcionario.class))).thenReturn("Funcionário salvo com sucesso");

        ResponseEntity<String> response = controller.save(funcionario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Funcionário salvo com sucesso", response.getBody());
    }

    @Test
    @DisplayName("Erro ao salvar funcionário")
    public void saveFuncionarioFailure() {
        Funcionario funcionario = new Funcionario();
        when(service.save(any(Funcionario.class))).thenThrow(new RuntimeException("Erro ao salvar"));

        ResponseEntity<String> response = controller.save(funcionario);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro:Erro ao salvar", response.getBody());
    }

    @Test
    @DisplayName("Atualizar funcionário com sucesso")
    public void updateFuncionarioSuccess() {
        Funcionario funcionario = new Funcionario();
        when(service.update(any(Funcionario.class), anyLong())).thenReturn("Funcionário atualizado com sucesso");

        ResponseEntity<String> response = controller.update(funcionario, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Funcionário atualizado com sucesso", response.getBody());
    }

    @Test
    @DisplayName("Erro ao atualizar funcionário")
    public void updateFuncionarioFailure() {
        Funcionario funcionario = new Funcionario();
        when(service.update(any(Funcionario.class), anyLong())).thenThrow(new RuntimeException("Erro ao atualizar"));

        ResponseEntity<String> response = controller.update(funcionario, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Deu erro!Erro ao atualizar", response.getBody());
    }

    @Test
    @DisplayName("Buscar funcionário por ID com sucesso")
    public void findByIdSuccess() {
        Funcionario funcionario = new Funcionario();
        when(service.findById(anyLong())).thenReturn(Optional.of(funcionario));

        ResponseEntity<Optional<Funcionario>> response = controller.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(funcionario), response.getBody());
    }

    @Test
    @DisplayName("Erro ao buscar funcionário por ID")
    public void findByIdFailure() {
        when(service.findById(anyLong())).thenThrow(new RuntimeException("Erro ao buscar funcionário"));

        ResponseEntity<Optional<Funcionario>> response = controller.findById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    @DisplayName("Listar todos os funcionários com sucesso")
    public void findAllSuccess() {
        Funcionario funcionario1 = new Funcionario();
        Funcionario funcionario2 = new Funcionario();
        List<Funcionario> funcionarios = Arrays.asList(funcionario1, funcionario2);

        when(service.findAll()).thenReturn(funcionarios);

        ResponseEntity<List<Funcionario>> response = controller.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(funcionarios, response.getBody());
    }

    @Test
    @DisplayName("Erro ao listar todos os funcionários")
    public void findAllFailure() {
        when(service.findAll()).thenThrow(new RuntimeException("Erro ao buscar funcionários"));

        ResponseEntity<List<Funcionario>> response = controller.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    @DisplayName("Deletar funcionário com sucesso")
    public void deleteFuncionarioSuccess() {
        when(service.deleteById(anyLong())).thenReturn("Funcionário deletado com sucesso");

        ResponseEntity<String> response = controller.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Funcionário deletado com sucesso", response.getBody());
    }

    @Test
    @DisplayName("Erro ao deletar funcionário")
    public void deleteFuncionarioFailure() {
        when(service.deleteById(anyLong())).thenThrow(new RuntimeException("Erro ao deletar funcionário"));

        ResponseEntity<String> response = controller.delete(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}
