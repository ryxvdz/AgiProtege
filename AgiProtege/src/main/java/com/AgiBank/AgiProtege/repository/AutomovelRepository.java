package com.AgiBank.AgiProtege.repository;

import com.AgiBank.AgiProtege.Enum.StatusSeguros;
import com.AgiBank.AgiProtege.model.Automovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AutomovelRepository extends JpaRepository<Automovel, UUID> {
    @Query("SELECT COUNT(a) > 0 FROM Automovel a WHERE a.cliente.idCliente = :idCliente AND a.statusSeguros = :status")
    boolean existsByClienteIdAndStatus(@Param("idCliente") UUID idCliente, @Param("status") StatusSeguros status);
    boolean existsByPlaca(String placa);
}
