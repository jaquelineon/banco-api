package com.bancoapi.banco_api.repositories;

import com.bancoapi.banco_api.models.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
}
