package com.bancoapi.banco_api.models;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity //indica que essa classe é uma entidade JPA, ou seja, será mapeada para uma tabela no banco de dados.
public class Conta {

    @Id //define o campo id como chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //faz com que o valor de id seja gerado automaticamente pelo banco de dados.
    private Long id;
    @Enumerated(EnumType.STRING) //define que o tipo de conta (corrente ou poupança) sera salva como uma String no banco de dados.
    private TipoConta tipo;
    private double saldo;
    private LocalDate dataCriacao;
    @ManyToOne(cascade = CascadeType.ALL) // Adicionando cascading(a entidade Conta salva automaticamente a entidade Pessoa quando você salva a Conta) //relaciona a conta a uma pessoa, uma pessoa pode ter várias contas.
    @JoinColumn(name = "pessoa_id") //relaciona a conta a uma pessoa, uma pessoa pode ter várias contas.
    private Pessoa titular;

    public Conta() {
        this.dataCriacao = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Pessoa getTitular() {
        return titular;
    }

    public void setTitular(Pessoa titular) {
        this.titular = titular;
    }

    public boolean podeEncerar() {
        return this.saldo == 0;
    }

    public void aplicarRendimentoMensal() {
        if (this.tipo == TipoConta.POUPANCA) {
            this.saldo += this.saldo * 0.005;
        }
    }
}
