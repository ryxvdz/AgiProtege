package com.AgiBank.AgiProtege.repository;

import com.AgiBank.AgiProtege.enums.StatusApolice;
import com.AgiBank.AgiProtege.model.Apolice;
import com.AgiBank.AgiProtege.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApoliceRepository extends JpaRepository<Apolice, UUID> {

    List<Apolice> findByClienteAndStatus(Cliente cliente, StatusApolice status);
}
