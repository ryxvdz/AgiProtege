package com.AgiBank.AgiProtege.client;


import com.AgiBank.AgiProtege.dto.EnderecoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ViaCepClient {
    private final RestTemplate restTemplate;

    public EnderecoResponseDTO buscarEndereco(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return restTemplate.getForObject(url, EnderecoResponseDTO.class);
    }

}
