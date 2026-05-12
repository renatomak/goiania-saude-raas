package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.listar.unidades;

import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.ArquivosRaasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ListarUnidadesRepository extends JpaRepository<ArquivosRaasEntity, Long> {

    @Query(value = """
            SELECT DISTINCT
                r.empresa AS id,
                e.descricao AS nome
            FROM raas_processo r
            LEFT JOIN empresa e ON e.empresa = r.empresa
            WHERE r.empresa IS NOT NULL
              AND e.descricao IS NOT NULL
            ORDER BY e.descricao ASC
            """, nativeQuery = true)
    List<ListarUnidadesProjection> listarTodasAsUnidades();
}

