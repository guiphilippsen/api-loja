package com.github.guiphilipppsen.api_loja.servicetest;

import com.github.guiphilipppsen.api_loja.Entities.Funcionario;
import com.github.guiphilipppsen.api_loja.repositories.FuncionarioRepository;
import com.github.guiphilipppsen.api_loja.service.FuncionarioService;
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

@DisplayName("FuncionarioService Testes Unitários")
public class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private FuncionarioService funcionarioService;

    private Funcionario funcionario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("João Silva");
        funcionario.setIdade(30);
        funcionario.setEmail("joao.silva@email.com");
        funcionario.setEndereco("Rua A, 123");
        funcionario.setTelefone("99999-9999");
        funcionario.setFuncao("Gerente");
    }

    @Test
    @DisplayName("Salvar um novo funcionário com sucesso")
    public void testSaveFuncionario() {
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);

        String result = funcionarioService.save(funcionario);

        assertEquals("Funcionario salvo", result);
        verify(funcionarioRepository, times(1)).save(funcionario);
    }

    @Test
    @DisplayName("Atualizar um funcionário existente com novos dados")
    public void testUpdateFuncionario() {
        Funcionario funcionarioUpdate = new Funcionario();
        funcionarioUpdate.setNome("João Atualizado");
        funcionarioUpdate.setIdade(35);

        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);

        String result = funcionarioService.update(funcionarioUpdate, 1L);

        assertEquals("Funcionario atualizado", result);
        assertEquals("João Atualizado", funcionario.getNome());
        assertEquals(35, funcionario.getIdade());
        verify(funcionarioRepository, times(1)).findById(1L);
        verify(funcionarioRepository, times(1)).save(funcionario);
    }

    @Test
    @DisplayName("Listar todos os funcionários cadastrados")
    public void testFindAllFuncionarios() {
        List<Funcionario> funcionarios = Arrays.asList(funcionario);
        when(funcionarioRepository.findAll()).thenReturn(funcionarios);

        List<Funcionario> result = funcionarioService.findAll();

        assertEquals(1, result.size());
        assertEquals("João Silva", result.get(0).getNome());
        verify(funcionarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Lançar exceção ao remover ID inexistente")
    public void testRemoverIdInexistente() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.deleteById(1L);
        });

        assertEquals("Funcionario nao encontrado: 1", exception.getMessage());
        verify(funcionarioRepository, times(1)).findById(1L);
        verify(funcionarioRepository, times(0)).deleteById(1L); // Certifique-se de que deleteById não seja chamado
    }


    @Test
    @DisplayName("Lançar exceção ao buscar funcionário inexistente por ID")
    public void testFindByIdFuncionarioNotFound() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.findById(1L);
        });

        assertEquals("Funcionario nao encontrado: 1", exception.getMessage());
        verify(funcionarioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Remover funcionário por ID quando ele existir")
    public void testDeleteByIdFuncionarioExists() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));

        String result = funcionarioService.deleteById(1L);

        assertEquals("Funcionario removido", result);
        verify(funcionarioRepository, times(1)).findById(1L);
        verify(funcionarioRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Lançar exceção ao remover funcionário inexistente por ID")
    public void testDeleteByIdFuncionarioNotFound() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.deleteById(1L);
        });

        assertEquals("Funcionario nao encontrado: 1", exception.getMessage());
        verify(funcionarioRepository, times(1)).findById(1L);
    }
}
