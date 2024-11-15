package com.bancoapi.banco_api.services;


import com.bancoapi.banco_api.dto.TransferenciaResponse;
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
//    @Autowired
//    private TransferenciaResponse response;

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

    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }

    public Conta obterConta(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
    }

    public Conta depositar(Long id, double valor) {
        Conta conta = obterConta(id);
        conta.setSaldo(conta.getSaldo() + valor);
        return contaRepository.save(conta);
    }

    public Conta sacar(Long id, double valor) {
        Conta conta = obterConta(id);
        if (conta.getSaldo() >= valor) {
            conta.setSaldo(conta.getSaldo() - valor);
            return contaRepository.save(conta);
        }
        throw new IllegalArgumentException("Saldo insuficiente");
    }

    public TransferenciaResponse transferir(Long idOrigem, Long idDestino, double valor) {
        Conta contaOrigem = obterConta(idOrigem);
        Conta contaDestino = obterConta(idDestino);

        if (contaOrigem == null || contaDestino == null) {
            throw new IllegalArgumentException("Conta de origem ou destino inexistente");
        }

        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero");
        }

        if (contaOrigem.getSaldo() < valor) {
            throw new IllegalArgumentException("Saldo insufuciente");
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
        contaDestino.setSaldo(contaDestino.getSaldo() + valor);

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        TransferenciaResponse response = new TransferenciaResponse();
        response.setContaOrigem(contaOrigem);
        response.setContaDestino(contaDestino);
        response.setValor(valor);
        response.setStatus("Transferência realizada com sucesso!");

        return response;
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
