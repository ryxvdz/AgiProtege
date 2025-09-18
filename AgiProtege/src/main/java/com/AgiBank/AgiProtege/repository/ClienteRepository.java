package com.AgiBank.AgiProtege.repository;

import com.AgiBank.AgiProtege.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
