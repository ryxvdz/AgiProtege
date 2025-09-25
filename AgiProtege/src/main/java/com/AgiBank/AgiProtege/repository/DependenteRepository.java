package com.AgiBank.AgiProtege.repository;

import com.AgiBank.AgiProtege.model.Dependente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DependenteRepository extends JpaRepository<Dependente, UUID> {
}
