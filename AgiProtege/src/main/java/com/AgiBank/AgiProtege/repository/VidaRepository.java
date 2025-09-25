package com.AgiBank.AgiProtege.repository;

import com.AgiBank.AgiProtege.model.Vida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VidaRepository extends JpaRepository<Vida, UUID> {
}
