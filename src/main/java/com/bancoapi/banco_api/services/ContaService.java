package com.bancoapi.banco_api.services;


import com.bancoapi.banco_api.models.Conta;
import com.bancoapi.banco_api.models.TipoConta;
import com.bancoapi.banco_api.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public Conta criarConta(Conta conta) {

        if (conta.getTitular().isMaiorDeIdade() && conta.getSaldo() >= 100) {
            return contaRepository.save(conta);
        }
        throw new IllegalArgumentException("O titular deve ser maior de idade e saldo inicial deve ser no mínimo R$100");
    }

    public void aplicarRendimento(Long id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));

        if (conta.getTipo() == TipoConta.POUPANCA) {
            double rendimento = conta.getSaldo() * 0.005;
            conta.setSaldo(conta.getSaldo() + rendimento);
            contaRepository.save(conta);
        } else {
            throw new IllegalArgumentException("O rendimento só pode ser aplicado em contas poupança");
        }
    }

    public List<Conta> listarContas () {
        return contaRepository.findAll();
    }

    public Conta obterConta (Long id) {
        return contaRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Conta não encontrada"));
    }

    public Conta depositar (Long id, double valor) {
        Conta conta = obterConta(id);
        conta.setSaldo(conta.getSaldo() + valor);
        return contaRepository.save(conta);
    }

    public Conta sacar (Long id, double valor) {
        Conta conta = obterConta(id);
        if (conta.getSaldo() >= valor) {
            conta.setSaldo(conta.getSaldo() - valor);
           return contaRepository.save(conta);
        }
        throw new IllegalArgumentException("Saldo insuficiente");
    }

    public void encerrarConta (Long id) {
        Conta conta = obterConta(id);
        if (conta.podeEncerar()) {
            contaRepository.delete(conta);
        } else {
            throw new IllegalArgumentException("O saldo da conta precisa esta zerado antes do encerramento");
        }
    }
}
