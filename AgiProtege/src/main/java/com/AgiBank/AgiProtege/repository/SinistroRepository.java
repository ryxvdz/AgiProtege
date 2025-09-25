package com.AgiBank.AgiProtege.repository;

import com.AgiBank.AgiProtege.model.Sinistro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SinistroRepository extends JpaRepository<Sinistro, UUID> {
}
