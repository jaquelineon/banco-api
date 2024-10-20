package com.bancoapi.banco_api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.Period;

@Entity // indica que a classe é uma tabela no banco de dados.
public class Pessoa {

    @Id // indica que o id é uma chave primaria no banco de dados.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //gera o valor do id automaticamente no banco de dados.
    private Long id;
    private String nome;
    private LocalDate dataNascimento;

    public boolean isMaiorDeIdade() {
        return Period.between(this.dataNascimento, LocalDate.now()).getYears() >= 18;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
