package com.bancoapi.banco_api.controllers;

import com.bancoapi.banco_api.dto.TransferenciaRequest;
import com.bancoapi.banco_api.dto.TransferenciaResponse;
import com.bancoapi.banco_api.models.Conta;
import com.bancoapi.banco_api.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<Conta> criarConta (@RequestBody Conta conta) {
        Conta novaConta = contaService.criarConta(conta);
        return ResponseEntity.ok(novaConta);
    }

    @PutMapping("/{id}/rendimento")
    public ResponseEntity<String> aplicarRendimentoNaConta (@PathVariable Long id) {
        contaService.aplicarRendimento(id);
        return ResponseEntity.ok("Rendimento aplicado com sucesso");
    }

    @GetMapping
    public List<Conta> listarContas () {
        return contaService.listarContas();
    }

    @GetMapping("/{id}")
    public Conta obterConta (@PathVariable Long id) { //@PathVariable Usa valores que estão na URL diretamente (como IDs).
        return contaService.obterConta(id);
    }

    @PutMapping("/{id}/deposito")
    public Conta depositar (@PathVariable Long id, @RequestParam double valor) { //@RequestParam Usa valores que estão após ? (como parâmetros de consulta).
        return contaService.depositar(id, valor);
    }

    @PutMapping("/{id}/sacar")
    public Conta sacar (@PathVariable Long id, @RequestParam double valor) {
        return contaService.sacar(id, valor);
    }

    @PutMapping("/transferir")
    public TransferenciaResponse transferir (@RequestBody TransferenciaRequest transferenciaRequest) {
        return contaService.transferir(
                transferenciaRequest.getIdOrigem(),
                transferenciaRequest.getIdDestino(),
                transferenciaRequest.getValor()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> encerrarConta (@PathVariable Long id) {
         contaService.encerrarConta(id);
         return ResponseEntity.ok().build();
    }
}
