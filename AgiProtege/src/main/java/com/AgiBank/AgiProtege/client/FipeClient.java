package com.AgiBank.AgiProtege.client;

import com.AgiBank.AgiProtege.dto.FipeDTO;
import com.AgiBank.AgiProtege.dto.MarcaFipeDTO;
import com.AgiBank.AgiProtege.dto.ModeloFipeDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class FipeClient {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://parallelum.com.br/fipe/api/v1/carros";

    public FipeClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .requestFactory(() -> new org.springframework.http.client.SimpleClientHttpRequestFactory() {
                    @Override
                    protected void prepareConnection(java.net.HttpURLConnection connection, String httpMethod) throws IOException {
                        if (connection instanceof HttpsURLConnection httpsConnection) {
                            try {
                                SSLContext sslContext = SSLContext.getInstance("TLS");
                                sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                                    public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                                    public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                                }}, new java.security.SecureRandom());

                                httpsConnection.setSSLSocketFactory(sslContext.getSocketFactory());
                                httpsConnection.setHostnameVerifier((hostname, session) -> true);

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                        super.prepareConnection(connection, httpMethod);
                    }
                })
                .build();
    }

    public List<MarcaFipeDTO> listarMarcas() {
        ResponseEntity<MarcaFipeDTO[]> response =
                restTemplate.getForEntity(BASE_URL + "/marcas", MarcaFipeDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public List<ModeloFipeDTO> listarModelosPorMarca(String codigoMarca) {
        ResponseEntity<Map> response =
                restTemplate.getForEntity(BASE_URL + "/marcas/" + codigoMarca + "/modelos", Map.class);
        List<Map<String, Object>> lista = (List<Map<String, Object>>) response.getBody().get("modelos");
        return lista.stream()
                .map(m -> new ModeloFipeDTO(m.get("codigo").toString(), m.get("nome").toString()))
                .toList();
    }

    public List<Map<String, String>> listarAnosPorModelos(String codigoMarca, String codigoModelo) {
        ResponseEntity<List> response =
                restTemplate.getForEntity(
                        BASE_URL + "/marcas/" + codigoMarca + "/modelos/" + codigoModelo + "/anos", List.class);
        return response.getBody();
    }

    public FipeDTO buscarDetalhes(String codigoMarca, String codigoModelo, String codigoAno) {
        ResponseEntity<FipeDTO> response =
                restTemplate.getForEntity(
                        BASE_URL + "/marcas/" + codigoMarca + "/modelos/" + codigoModelo + "/anos/" + codigoAno, FipeDTO.class);
        return response.getBody();
    }

    public FipeDTO buscarPorNome(String nomeMarca, String nomeModelo, String ano) {

        List<MarcaFipeDTO> marcas = listarMarcas();
        MarcaFipeDTO marca = marcas.stream()
                .filter(m -> m.nome().equalsIgnoreCase(nomeMarca))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Marca não encontrada: "+ nomeMarca));
        String codigoMarca = String.valueOf(marca.codigo());

        ResponseEntity<Map> responseModelos =
                restTemplate.getForEntity(BASE_URL + "/marcas/" + codigoMarca + "/modelos/", Map.class);
        List<Map<String, String>> modelos = (List<Map<String, String>>) responseModelos.getBody().get("modelos");
        Map<String, String> modelo = modelos.stream()
                .filter(m -> m.get("nome").toLowerCase().contains(nomeModelo.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Modelo não encontrado: " + nomeModelo));
        String codigoModelo = String.valueOf(modelo.get("codigo"));

        ResponseEntity<List> responseAnos = restTemplate.getForEntity(
                BASE_URL + "/marcas/" + codigoMarca + "/modelos/" + codigoModelo + "/anos", List.class);
        List<Map<String, String>> anos = responseAnos.getBody();
        Map<String, String> anoEncontrado = anos.stream()
                .filter(a -> a.get("nome").startsWith(ano))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ano não encontrado: " + ano));
        String codigoAno = String.valueOf(anoEncontrado.get("codigo"));

        return buscarDetalhes(codigoMarca, codigoModelo, codigoAno);
    }
}
