package com.AgiBank.AgiProtege.repository;

import com.AgiBank.AgiProtege.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
}
