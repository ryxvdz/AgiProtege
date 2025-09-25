package com.AgiBank.AgiProtege.repository;

import com.AgiBank.AgiProtege.model.DespesasEssenciais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DespesasRepository extends JpaRepository<DespesasEssenciais, UUID> {
}
