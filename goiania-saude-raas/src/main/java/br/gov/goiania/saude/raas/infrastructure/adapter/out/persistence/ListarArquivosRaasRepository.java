package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListarArquivosRaasRepository extends JpaRepository<ListarArquivosRaasEntity, Long> {

    @Query(value = """
            SELECT
                r.mes,
                r.ano,
                r.dt_geracao,
                r.empresa AS codigo_empresa,
                e.descricao AS nome_empresa,
                r.descricao AS descricao_processo,
                r.path,
                r.status,
                r.total_folha
            FROM raas_processo r
            LEFT JOIN empresa e ON e.empresa = r.empresa
            WHERE r.total_folha > 0
              AND r.mes = :mes
              AND r.ano = :ano
              AND r.empresa = :empresa
              AND r.status IN (:statusList)
            ORDER BY r.dt_geracao DESC
            """,
            nativeQuery = true)
    List<ListarArquivosRaasEntity> buscarProcessosPorCompetenciaEmpresaEStatus(
            @Param("mes") Integer mes,
            @Param("ano") Integer ano,
            @Param("empresa") String empresa,
            @Param("statusList") List<String> statusList
    );
}
