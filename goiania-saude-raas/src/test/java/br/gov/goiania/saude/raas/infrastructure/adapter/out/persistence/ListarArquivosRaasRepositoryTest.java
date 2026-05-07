package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class ListarArquivosRaasRepositoryTest {

    @Autowired
    private ListarArquivosRaasRepository repository;

    @Test
    @DisplayName("Deve persistir e recuperar entidade corretamente")
    void devePersistirERecuperarEntidadeCorretamente() {
        ListarArquivosRaasEntity entity = new ListarArquivosRaasEntity(
            null, 5, 2026, LocalDate.of(2026, 5, 7), "123", "Empresa Teste", "/caminho/arquivo.txt", "3", new BigDecimal("100.00")
        );
        ListarArquivosRaasEntity saved = repository.save(entity);
        assertNotNull(saved.getId());
        ListarArquivosRaasEntity found = repository.findById(saved.getId()).orElseThrow();
        assertEquals("123", found.getCodigoEmpresa());
        assertEquals("Empresa Teste", found.getNomeEmpresa());
        assertEquals("/caminho/arquivo.txt", found.getPath());
        assertEquals("3", found.getStatus());
        assertEquals(new BigDecimal("100.00"), found.getTotalFolha());
    }
}

