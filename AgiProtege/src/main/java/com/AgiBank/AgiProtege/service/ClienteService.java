package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.dto.ClienteRequestDTO;
import com.AgiBank.AgiProtege.dto.ClienteResponseDTO;
import com.AgiBank.AgiProtege.dto.LoginRequestDTO;
import com.AgiBank.AgiProtege.enums.StatusApolice;
import com.AgiBank.AgiProtege.enums.StatusCliente;
import com.AgiBank.AgiProtege.exception.ResourceNotFoundException;
import com.AgiBank.AgiProtege.exception.ServiceUnavaliable;
import com.AgiBank.AgiProtege.model.Cliente;
import com.AgiBank.AgiProtege.repository.ClienteRepository;
import com.AgiBank.AgiProtege.security.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    private final EnderecoService enderecoService;

    public ClienteService(ClienteRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService, EnderecoService enderecoService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.enderecoService = enderecoService;
    }

    public ClienteResponseDTO cadastrarCliente(ClienteRequestDTO dto) {

        if(calcularIdadeCliente(dto.idade()) < 18) {
            throw new ServiceUnavaliable("ERRO! Idade minima 18 anos!");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setSexo(dto.sexo());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setRenda(dto.renda());
        cliente.setIdade(dto.idade());
        cliente.setEstadoCivil(dto.estadoCivil());
        cliente.setSenha(passwordEncoder.encode(dto.senha()));          //guarda a senha criptografada no banco

        Cliente clienteCadastrado = repository.save(cliente);

        if (dto.cep() != null && !dto.cep().isEmpty()) {
            enderecoService.adicionarEnderecoAoCliente(
                    cliente.getIdCliente(),
                    dto.cep(),
                    dto.numero(),
                    dto.logradouro(),
                    dto.bairro(),
                    dto.localidade(),
                    dto.uf()
            );
        }


        calcularPerfilDeRiscoInical(clienteCadastrado.getIdCliente(), dto);

        String token = tokenService.generateToken(clienteCadastrado);

        return toResponseDTO(clienteCadastrado, token);
    }

    public ClienteResponseDTO login(LoginRequestDTO dto) {
        Cliente cliente = repository.findByCpf(dto.cpf()).orElseThrow(
                () -> new ResourceNotFoundException("Cliente não encontrado!")
        );

        if(cliente.getStatus()== StatusCliente.Inativo) {
            throw new ServiceUnavaliable("Conta inexistente!");
        }

        if (passwordEncoder.matches(dto.senha(), cliente.getSenha())) {
            String token = this.tokenService.generateToken(cliente);
            return toResponseDTO(cliente, token);
        }

        throw new RuntimeException("Usuario ou senha incorreta!");
    }

    private int calcularIdadeCliente(LocalDate idade) {
        return Period.between(idade, LocalDate.now()).getYears();
    }

    public ClienteResponseDTO buscarClientePorId(UUID id) {
        Cliente cliente = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cliente não encontrado!")
        );

        return toResponseDTO(cliente, null);
    }

    public ClienteResponseDTO atualizarClientePorId(UUID id, ClienteRequestDTO dto) {
        Cliente clienteModel = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cliente não encontrado!")
        );

        Cliente clienteAtualizado = Cliente.builder()
                .nome(dto.nome() != null ? dto.nome() : clienteModel.getNome())
                .cpf(clienteModel.getCpf())
                .sexo(dto.sexo() != null ? dto.sexo() : clienteModel.getSexo())
                .email(dto.email() != null ? dto.email() : clienteModel.getEmail())
                .telefone(dto.telefone() != null ? dto.telefone() : clienteModel.getTelefone())
                .renda(dto.renda() != null ? dto.renda() : clienteModel.getRenda())
                .idade(dto.idade() != null ? dto.idade() : clienteModel.getIdade())
                .estadoCivil(dto.estadoCivil() != null ? dto.estadoCivil() : clienteModel.getEstadoCivil())
                .idCliente(clienteModel.getIdCliente())
                .build();

        repository.save(clienteAtualizado);

        return toResponseDTO(clienteAtualizado, null);
    }

    public void inativarClientePorId(UUID id) {
        Cliente cliente = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cliente não encontrado!")
        );

        boolean possuiApoliceAtiva = cliente.getApolices().stream()
                .anyMatch(apolice -> apolice.getStatus() == StatusApolice.Ativo);

        if (possuiApoliceAtiva) {
            throw new ServiceUnavaliable("Não é possível inativar o cliente. Existem apólices ativas.");
        }

        cliente.setStatus(StatusCliente.Inativo);
        repository.save(cliente);
    }

    public void calcularPerfilDeRiscoInical(UUID id, ClienteRequestDTO dto) {
        int idade;
        int renda;
        int estadoCivil;
        int sexo;
        int perfilRisco;

        Cliente clienteModel = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cliente não encontrado!")
        );

        idade = perfilRiscoIdade(clienteModel, dto);

        renda = perfilRiscoRenda(clienteModel);

        estadoCivil = perfilRiscoEstadoCivil(clienteModel);

        sexo = perfilRiscoSexo(clienteModel);

        perfilRisco = idade + renda + estadoCivil + sexo;

        if(perfilRisco <= 35) {
            clienteModel.setPerfilRisco("Baixo");
            repository.save(clienteModel);
        }

        if (perfilRisco > 35 && perfilRisco <= 60) {
            clienteModel.setPerfilRisco("Medio");
            repository.save(clienteModel);
        }

        if (perfilRisco > 60) {
            clienteModel.setPerfilRisco("Alto");
            repository.save(clienteModel);
        }
    }

    private int perfilRiscoIdade(Cliente cliente, ClienteRequestDTO dto) {
        int perfilRisco = 0;

        if(calcularIdadeCliente(dto.idade()) <= 25 || calcularIdadeCliente(dto.idade()) > 60) {
            perfilRisco = perfilRisco + 25;
        }

        if(calcularIdadeCliente(dto.idade()) > 25 && calcularIdadeCliente(dto.idade()) <=40) {
            perfilRisco = perfilRisco + 15;
        }

        if(calcularIdadeCliente(dto.idade()) > 40 && calcularIdadeCliente(dto.idade()) <=60) {
            perfilRisco = perfilRisco + 10;
        }

        return perfilRisco;
    }

    private int perfilRiscoRenda(Cliente cliente) {
        int perfilRisco = 0;

        if(cliente.getRenda() <= 2000) {
            perfilRisco = perfilRisco + 25;
        }

        if(cliente.getRenda() > 2000 && cliente.getRenda() < 5000) {
            perfilRisco = perfilRisco + 15;
        }

        if(cliente.getRenda() >= 5000) {
            perfilRisco = perfilRisco + 5;
        }

        return perfilRisco;
    }

    private int perfilRiscoEstadoCivil(Cliente cliente) {
        int perfilRisco = 0;

        if(cliente.getEstadoCivil().equalsIgnoreCase("solteiro")) {
            perfilRisco = perfilRisco + 20;
        }

        if(cliente.getEstadoCivil().equalsIgnoreCase("casado")) {
            perfilRisco = perfilRisco + 10;
        }

        return perfilRisco;
    }

    private int perfilRiscoSexo(Cliente cliente) {
        int perfilRisco = 0;

        if(cliente.getSexo().equalsIgnoreCase("Masculino")) {
            perfilRisco = perfilRisco + 10;
        }

        if (cliente.getSexo().equalsIgnoreCase("Femenino")) {
            perfilRisco = perfilRisco + 5;
        }

        return perfilRisco;
    }

    private ClienteResponseDTO toResponseDTO(Cliente cliente, String token) {
        return new ClienteResponseDTO(
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf(),
                cliente.getRenda(),
                cliente.getSexo(),
                cliente.getStatus(),
                cliente.getTelefone(),
                cliente.getIdade(),
                cliente.getEstadoCivil(),
                cliente.getEndereco().getBairro(),
                cliente.getEndereco().getLogradouro(),
                cliente.getEndereco().getNumero(),
                token
        );
    }
}
