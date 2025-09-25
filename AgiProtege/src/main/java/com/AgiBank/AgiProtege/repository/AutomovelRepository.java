package com.AgiBank.AgiProtege.repository;

import com.AgiBank.AgiProtege.model.Automovel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutomovelRepository extends JpaRepository<Automovel, UUID> {
}
