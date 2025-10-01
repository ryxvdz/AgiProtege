package com.AgiBank.AgiProtege.service;

import com.AgiBank.AgiProtege.dto.SinistroRequestDTO;
import com.AgiBank.AgiProtege.dto.SinistroResponseDTO;
import com.AgiBank.AgiProtege.model.Apolice;
import com.AgiBank.AgiProtege.model.Sinistro;
import com.AgiBank.AgiProtege.repository.ApoliceRepository;
import com.AgiBank.AgiProtege.repository.SinistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SinistroService {
    @Autowired
    private SinistroRepository sinistroRepository;
    @Autowired
    private ApoliceRepository apoliceRepository;

    public SinistroResponseDTO registrarSinistro(SinistroRequestDTO request, MultipartFile documentoFile) throws IOException {
        Optional<Apolice> apoliceOpt = apoliceRepository.findById(request.idApolice());
        if (apoliceOpt.isEmpty()) {
            return new SinistroResponseDTO(null, "Erro", "Apólice não encontrada");
        }
        String documento = salvarDocumento(documentoFile);
        Sinistro sinistro = new Sinistro();
        sinistro.setApolice(apoliceOpt.get());
        sinistro.setDescricao(request.descricao());
        sinistro.setDataOcorrencia(LocalDate.now());
        sinistro.setStatus("Em análise");
        sinistro.setDocumento(documento);
        sinistro = sinistroRepository.save(sinistro);
        return new SinistroResponseDTO(sinistro.getIdSinistro(), sinistro.getStatus(), "Sinistro registrado e em análise");
    }

    private String salvarDocumento(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;
        // Caminho absoluto relativo ao diretório do projeto
        String baseDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "sinistros";
        File dir = new File(baseDir);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new IOException("Não foi possível criar o diretório de uploads: " + dir.getAbsolutePath());
            }
        }
        String nomeArquivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File destino = new File(dir, nomeArquivo);
        file.transferTo(destino);
        return destino.getAbsolutePath();
    }

    public List<SinistroResponseDTO> listarSinistrosPorCliente(UUID idCliente) {
        return sinistroRepository.findByClienteId(idCliente)
            .stream()
            .map(s -> new SinistroResponseDTO(s.getIdSinistro(), s.getStatus(), s.getDescricao()))
            .collect(Collectors.toList());
    }
}
