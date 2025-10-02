package com.AgiBank.AgiProtege.repository;

import com.AgiBank.AgiProtege.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
}
