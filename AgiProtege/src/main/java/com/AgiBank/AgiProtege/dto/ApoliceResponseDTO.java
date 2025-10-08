package com.AgiBank.AgiProtege.dto;

import com.AgiBank.AgiProtege.enums.StatusApolice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public record ApoliceResponseDTO (String nome,
                                  String email,
                                  String telefone,
                                  String cpf,
                                  LocalDate idade,
                                  String sexo,
                                  String estadoCivil,
                                  Double renda,
                                  String perfilRisco,
                                  LocalDate dataInicio,
                                  LocalDate dataFim,
                                  Double parcela,
                                  String tipoSeguro,
                                  Integer ano,
                                  Boolean assistencia,
                                  Boolean carroReserva,
                                  Boolean desastresNaturais,
                                  Double tabelaFipe,
                                  String categoria,
                                  String modelo,
                                  String placa,
                                  String marca,
                                  Double altura,
                                  Boolean coberturaHospitalar,
                                  Boolean fumante,
                                  Boolean historicoFamiliar,
                                  Double patrimonio,
                                  Double peso,
                                  Double valorIndenizacao,
                                  String profissao,
                                  List<DependenteResponseDTO> dependentes,
                                  Double gastosMensais,
                                  Double tempoRegistro,
                                  StatusApolice status){
}
