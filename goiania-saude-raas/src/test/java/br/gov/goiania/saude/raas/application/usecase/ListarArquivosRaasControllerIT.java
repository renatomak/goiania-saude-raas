package br.gov.goiania.saude.raas.application.usecase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ListarArquivosRaasControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listarDeveriaRetornarListaDeArquivosQuandoFiltrosSaoValidos() throws Exception {
        mockMvc.perform(get("/api/v1/raas")
                .param("mes", "5")
                .param("ano", "2026")
                .param("codigoEmpresa", "123"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].codigoEmpresa").value("123"));
    }

    @Test
    void listarDeveriaRetornarErro400QuandoMesForMaiorQueDoze() throws Exception {
        mockMvc.perform(get("/api/v1/raas")
                .param("mes", "11"))
            .andExpect(status().isBadRequest());
    }
}
