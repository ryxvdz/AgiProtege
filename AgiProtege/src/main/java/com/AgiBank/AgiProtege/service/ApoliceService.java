package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.repository.ApoliceRepository;

public class ApoliceService {

    private final ApoliceRepository apoliceRepository;

    public ApoliceService(ApoliceRepository apoliceRepository) {
        this.apoliceRepository = apoliceRepository;
    }
}
