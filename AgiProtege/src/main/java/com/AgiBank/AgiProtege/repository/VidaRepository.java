package com.AgiBank.AgiProtege.repository;

import com.AgiBank.AgiProtege.Enum.StatusSeguros;
import com.AgiBank.AgiProtege.model.Vida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface VidaRepository extends JpaRepository<Vida, UUID> {
    @Query("SELECT COUNT(v) > 0 FROM Vida v WHERE v.cliente.idCliente = :idCliente AND v.statusSeguros = :status")
    boolean existsByClienteIdAndStatus(@Param("idCliente") UUID idCliente, @Param("status") StatusSeguros status);
}
