package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.repository.AutomovelRepository;

public class AutomovelService {

    private final AutomovelRepository automovelRepository;

    public AutomovelService(AutomovelRepository automovelRepository) {
        this.automovelRepository = automovelRepository;
    }
}
