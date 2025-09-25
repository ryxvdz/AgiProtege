package com.AgiBank.AgiProtege.repository;

import com.AgiBank.AgiProtege.model.Apolice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApoliceRepository extends JpaRepository<Apolice, UUID> {
}
