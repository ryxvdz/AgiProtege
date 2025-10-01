package com.AgiBank.AgiProtege.repository;

import com.AgiBank.AgiProtege.model.Sinistro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SinistroRepository extends JpaRepository<Sinistro, UUID> {
    @Query("SELECT s FROM Sinistro s WHERE s.apolice.cliente.idCliente = :idCliente")
    List<Sinistro> findByClienteId(@Param("idCliente") UUID idCliente);
}
