package com.github.guiphilipppsen.api_loja.servicetest;

import com.github.guiphilipppsen.api_loja.Entities.Produto;
import com.github.guiphilipppsen.api_loja.repositories.ProdutoRepository;
import com.github.guiphilipppsen.api_loja.service.ProdutoService;
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

class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Salvar Produto - Deve salvar produto corretamente e retornar mensagem de sucesso")
    void testSave() {
        Produto produto = new Produto();
        produto.setNome("Produto 1");

        when(produtoRepository.save(produto)).thenReturn(produto);

        String result = produtoService.save(produto);
        assertEquals("Produto salvo", result);

        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    @DisplayName("Atualizar Produto - Deve atualizar corretamente as propriedades de um produto")
    void testUpdate() {
        Produto produtoExistente = new Produto();
        produtoExistente.setId(1L);
        produtoExistente.setNome("Produto Existente");
        produtoExistente.setDescricao("Descricao Existente");
        produtoExistente.setPreco(100.0);

        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setNome("Novo Nome");
        produtoAtualizado.setDescricao("Nova Descricao");
        produtoAtualizado.setPreco(200.0);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoExistente));

        String result = produtoService.update(produtoAtualizado, 1L);
        assertEquals("Produto atualizado", result);

        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).save(produtoExistente);

        assertEquals("Novo Nome", produtoExistente.getNome());
        assertEquals("Nova Descricao", produtoExistente.getDescricao());
        assertEquals(200.0, produtoExistente.getPreco());
    }

    @Test
    @DisplayName("Buscar Todos os Produtos - Deve retornar uma lista de produtos")
    void testFindAll() {
        Produto produto1 = new Produto();
        Produto produto2 = new Produto();

        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto1, produto2));

        List<Produto> produtos = produtoService.findAll();
        assertEquals(2, produtos.size());

        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Buscar Produto por ID - Deve retornar produto quando encontrado")
    void testFindByIdFound() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto 1");

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Optional<Produto> result = produtoService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Produto 1", result.get().getNome());

        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Buscar Produto por ID - Deve lançar exceção quando produto não for encontrado")
    void testFindByIdNotFound() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            produtoService.findById(1L);
        });

        assertEquals("Produto nao encontrado: 1", exception.getMessage());

        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deletar Produto por ID - Deve remover produto quando encontrado")
    void testDeleteByIdFound() {
        Produto produto = new Produto();
        produto.setId(1L);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        String result = produtoService.deleteById(1L);
        assertEquals("Produto removido", result);

        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deletar Produto por ID - Deve lançar exceção quando produto não for encontrado")
    void testDeleteByIdNotFound() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            produtoService.deleteById(1L);
        });

        assertEquals("Produto nao encontrado: 1", exception.getMessage());

        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, never()).deleteById(1L);
    }
}
