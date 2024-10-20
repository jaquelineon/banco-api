package com.bancoapi.banco_api.repositories;

import com.bancoapi.banco_api.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
