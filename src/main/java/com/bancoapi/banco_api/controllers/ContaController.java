package com.bancoapi.banco_api.controllers;

import com.bancoapi.banco_api.models.Conta;
import com.bancoapi.banco_api.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //indica que essa classe é um controlador REST, ou seja, vai lidar com requisições HTTP (GET, POST, PUT, DELETE).
@RequestMapping("/contas") // define a URL base para todas as requisições desse controlador.
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping //define o endpoint para criar uma nova conta.
    public ResponseEntity<Conta> criarConta (@RequestBody Conta conta) {
        Conta novaConta = contaService.criarConta(conta);
        return ResponseEntity.ok(novaConta);
    }

        @PutMapping("/{id}/rendimento")
    public ResponseEntity<String> aplicarRendimentoNaConta (@PathVariable Long id) {
        contaService.aplicarRendimento(id);
        return ResponseEntity.ok("Rendimento aplicado com sucesso");
    }

    @GetMapping //define o endpoint para listar e buscar contas especificas.
    public List<Conta> listarContas () {
        return contaService.listarContas();
    }

    @GetMapping("/{id}") //define o endpoint para listar e buscar contas especificas.
    public Conta obterConta (@PathVariable Long id) { //@PathVariable Usa valores que estão na URL diretamente (como IDs).
        return contaService.obterConta(id);
    }

    @PutMapping("/{id}/deposito") //define os endpoints para depositar e sacar.
    public Conta depositar (@PathVariable Long id, @RequestParam double valor) { //@RequestParam Usa valores que estão após ? (como parâmetros de consulta).
        return contaService.depositar(id, valor);
    }

    @PutMapping("/{id}/sacar") //define os endpoints para depositar e sacar.
    public Conta sacar (@PathVariable Long id, @RequestParam double valor) {
        return contaService.sacar(id, valor);
    }

    @DeleteMapping("/{id}") //endpoint para deletar uma conta
    public ResponseEntity<?> encerrarConta (@PathVariable Long id) {
         contaService.encerrarConta(id);
         return ResponseEntity.ok().build();
    }
}
