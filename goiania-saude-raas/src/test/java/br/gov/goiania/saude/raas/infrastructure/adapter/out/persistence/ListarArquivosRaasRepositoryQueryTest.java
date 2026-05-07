package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class ListarArquivosRaasRepositoryQueryTest {

    @Autowired
    private ListarArquivosRaasRepository repository;

    @Test
    void buscarProcessosPorCompetenciaEmpresaEStatusDeveMapearColunasCorretamente() {
        ListarArquivosRaasEntity entity = new ListarArquivosRaasEntity(
            null, 5, 2026, null, "123", "Empresa Teste", "/caminho/arquivo.txt", "3", null
        );
        repository.save(entity);
        List<ListarArquivosRaasEntity> result = repository.buscarProcessosPorCompetenciaEmpresaEStatus(5, 2026, "123", 3);
        assertFalse(result.isEmpty());
        ListarArquivosRaasEntity found = result.get(0);
        assertEquals("123", found.getCodigoEmpresa());
        assertEquals("Empresa Teste", found.getNomeEmpresa());
        assertEquals("/caminho/arquivo.txt", found.getPath());
        assertEquals("3", found.getStatus());
    }
}

