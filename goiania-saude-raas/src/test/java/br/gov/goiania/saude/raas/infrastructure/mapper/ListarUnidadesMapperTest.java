package br.gov.goiania.saude.raas.infrastructure.mapper;

import br.gov.goiania.saude.raas.domain.model.ListarUnidades;
import br.gov.goiania.saude.raas.mock.ListarUnidadesProjectionMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ListarUnidadesMapperTest {

    private ListarUnidadesMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(ListarUnidadesMapper.class);
    }

    @Test
    @DisplayName("Deve mapear projeção para domínio com dados válidos")
    void deveMapearProjecaoParaDominioComDadosValidos() {
        var projection = ListarUnidadesProjectionMock.valido();

        var domain = mapper.toDomain(projection);

        assertNotNull(domain);
        assertEquals(123, domain.getId());
        assertEquals("Secretaria Municipal de Saúde", domain.getNome());
    }

    @Test
    @DisplayName("Deve mapear domínio para resposta com dados válidos")
    void deveMapearDominioParaRespostaComDadosValidos() {
        var domain = new ListarUnidades(456L, "Unidade Básica");

        var response = mapper.toResponse(domain);

        assertNotNull(response);
        assertEquals(456, response.id());
        assertEquals("Unidade Básica", response.nome());
    }

    @Test
    @DisplayName("Deve retornar null quando domínio null no toResponse")
    void deveRetornarNullQuandoDominioNullEmToResponse() {
        var response = mapper.toResponse(null);

        assertNull(response);
    }

    @Test
    @DisplayName("Deve retornar null quando projeção null no toDomain")
    void deveRetornarNullQuandoProjecaoNullEmToDomain() {
        var domain = mapper.toDomain(null);

        assertNull(domain);
    }
}

