package com.AgiBank.AgiProtege.repository;

import com.AgiBank.AgiProtege.Enum.StatusSeguros;
import com.AgiBank.AgiProtege.model.DespesasEssenciais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface DespesasRepository extends JpaRepository<DespesasEssenciais, UUID> {
    @Query("SELECT COUNT(d) > 0 FROM DespesasEssenciais d WHERE d.cliente.idCliente = :idCliente AND d.statusSeguros = :status")
    boolean existsByClienteIdAndStatus(@Param("idCliente") UUID idCliente, @Param("status") StatusSeguros status);
}
