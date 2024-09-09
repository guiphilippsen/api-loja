package com.github.guiphilipppsen.api_loja.service;

import com.github.guiphilipppsen.api_loja.Entities.Funcionario;
import com.github.guiphilipppsen.api_loja.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public String save(Funcionario funcionario) {
        this.funcionarioRepository.save(funcionario);
        return "Funcionario salvo";
    }

    public String update(Funcionario funcionarioUpdate, long id) {
        Optional<Funcionario> funcionarioOptional =
                this.funcionarioRepository.findById(id);

        if (funcionarioOptional.isPresent()) {
            Funcionario funcionario = funcionarioOptional.get();
            funcionario.setNome(
                    funcionarioUpdate.getNome() != null ?
                            funcionarioUpdate.getNome() :
                            funcionario.getNome()
            );
            funcionario.setIdade(
                    funcionarioUpdate.getIdade() != 0 ?
                            funcionarioUpdate.getIdade() :
                            funcionario.getIdade()
            );
            funcionario.setEmail(
                    funcionarioUpdate.getEmail() != null ?
                            funcionarioUpdate.getEmail() :
                            funcionario.getEmail()
            );
            funcionario.setEndereco(
                    funcionarioUpdate.getEndereco() != null ?
                            funcionarioUpdate.getEndereco() :
                            funcionario.getEndereco()
            );
            funcionario.setTelefone(
                    funcionarioUpdate.getTelefone() != null ?
                            funcionarioUpdate.getTelefone() :
                            funcionario.getTelefone()
            );
            funcionario.setFuncao(
                    funcionarioUpdate.getFuncao() != null ?
                            funcionarioUpdate.getFuncao() :
                            funcionario.getFuncao()
            );
            this.funcionarioRepository.save(funcionario);
        }
        return "Funcionario atualizado";
    }

    public List<Funcionario> findAll() {
        return this.funcionarioRepository.findAll();
    }

    public Optional<Funcionario> findById(long id) {
        if (this.funcionarioRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Funcionario nao encontrado: " + id);
        }
        return this.funcionarioRepository.findById(id);
    }

    public String deleteById(long id) {
        if (this.funcionarioRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Funcionario nao encontrado: " + id);
        }
        this.funcionarioRepository.deleteById(id);
        return "Funcionario removido";
    }
}