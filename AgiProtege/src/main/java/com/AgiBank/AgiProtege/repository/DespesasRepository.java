package com.AgiBank.AgiProtege.repository;

import com.AgiBank.AgiProtege.model.DespesasEssenciais;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesasRepository extends JpaRepository<DespesasEssenciais, Long> {
}
