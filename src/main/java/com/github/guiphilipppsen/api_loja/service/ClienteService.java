package com.github.guiphilipppsen.api_loja.service;


import com.github.guiphilipppsen.api_loja.Entities.Cliente;
import com.github.guiphilipppsen.api_loja.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public String save(Cliente cliente){
        this.clienteRepository.save(cliente);
        return "Cliente salvo";
    }

    public String update(Cliente clienteUpdate, long id) {
        Optional<Cliente> clienteOptional =
                this.clienteRepository.findById(id);

        if(clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            cliente.setNome(
                    clienteUpdate.getNome() != null ?
                            clienteUpdate.getNome() :
                            cliente.getNome()
            );
            cliente.setIdade(
                    clienteUpdate.getIdade() != 0 ?
                            clienteUpdate.getIdade() :
                            cliente.getIdade()
            );
            cliente.setEmail(
                    clienteUpdate.getEmail() != null ?
                            clienteUpdate.getEmail() :
                            cliente.getEmail()
            );
            cliente.setEndereco(
                    clienteUpdate.getEndereco() != null ?
                            clienteUpdate.getEndereco() :
                            cliente.getEndereco()
            );
            cliente.setTelefone(
                    clienteUpdate.getTelefone() != null ?
                            clienteUpdate.getTelefone() :
                            cliente.getTelefone()
            );
            this.clienteRepository.save(cliente);
        }
        return "Cliente atualizado";
    }
    public List<Cliente> findAll() {
        return this.clienteRepository.findAll();
    }

    public Optional<Cliente> findById(long id) {
        if(this.clienteRepository.findById(id).isEmpty()){
            throw new RuntimeException("Cliente nao encontrado: " + id);
        }
        return this.clienteRepository.findById(id);
   }
    public String deleteById(long id) {
        if(this.clienteRepository.findById(id).isEmpty()){
            throw new RuntimeException("Cliente nao encontrado: " + id);
        }
        this.clienteRepository.deleteById(id);
        return "Cliente removido";
    }
}
