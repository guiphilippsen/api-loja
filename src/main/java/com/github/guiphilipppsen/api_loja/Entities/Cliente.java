package com.github.guiphilipppsen.api_loja.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "t_clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    private String telefone;

    @NotNull
    private int idade;

    @NotBlank
    private String endereco;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties
    private List<Venda> venda;
}
