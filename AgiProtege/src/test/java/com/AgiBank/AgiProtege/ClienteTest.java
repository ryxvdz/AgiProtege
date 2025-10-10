package com.AgiBank.AgiProtege;

import com.AgiBank.AgiProtege.model.Cliente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {
    @Test
    void deveCriarClienteComDadosValidos() {
        Cliente cliente = Cliente.builder()
                .nome("João")
                .cpf("12345678909") // CPF válido para teste
                .sexo("M")
                .email("joao@email.com")
                .build();
        assertEquals("João", cliente.getNome());
        assertEquals("12345678909", cliente.getCpf());
        assertEquals("M", cliente.getSexo());
        assertEquals("joao@email.com", cliente.getEmail());
    }

    @Test
    void deveFalharAoCriarClienteComCpfInvalido() {
        Exception exception = assertThrows(Exception.class, () -> {
            Cliente cliente = Cliente.builder()
                    .nome("Maria")
                    .cpf("11111111117771") // CPF inválido
                    .sexo("Fkkkk")
                    .email("maria@email.com")
                    .build();
            if (cliente.getCpf().matches("\\d{11}") || cliente.getCpf().equals("11111111111")) {
                throw new IllegalArgumentException("CPF inválido");
            }
        });
        assertEquals("CPF inválido", exception.getMessage());
    }
}

