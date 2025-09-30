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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SinistroService {
    @Autowired
    private SinistroRepository sinistroRepository;
    @Autowired
    private ApoliceRepository apoliceRepository;

    //comentario
    private static final String UPLOAD_DIR = "C:/uploads";

    public SinistroResponseDTO criarSinistro(SinistroRequestDTO request) {
        Apolice apolice = apoliceRepository.findById(request.idApolice())
                .orElseThrow(() -> new RuntimeException("Apolice não encontrada"));
        String fileName = saveFile(request.documento());
        Sinistro sinistro = new Sinistro();
        sinistro.setDescricao(request.descricao());
        sinistro.setApolice(apolice);
        sinistro.setDocumento(fileName);
        sinistro.setStatus("Em análise");
        sinistro.setDataOcorrencia(LocalDate.now());
        sinistro = sinistroRepository.save(sinistro);
        return toDTO(sinistro);
    }

    public List<SinistroResponseDTO> listarSinistros() {
        return sinistroRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private String saveFile(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;
        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();
            String filePath = UPLOAD_DIR + File.separator + UUID.randomUUID() + "_" + file.getOriginalFilename();
            file.transferTo(new File(filePath));
            return filePath;
        } catch (IOException e) {
            e.printStackTrace(); // Log detalhado para diagnóstico
            throw new RuntimeException("Erro ao salvar arquivo: " + e.getMessage(), e);
        }
    }

    private SinistroResponseDTO toDTO(Sinistro s) {
        return new SinistroResponseDTO(
                s.getIdSinistro(),
                s.getDescricao(),
                s.getStatus(),
                s.getDataOcorrencia(),
                s.getDocumento(),
                s.getApolice().getIdApolice()
        );
    }
}
