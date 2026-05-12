package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.listar.arquivos;

import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.ArquivosRaasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListarArquivosRaasRepository extends JpaRepository<ArquivosRaasEntity, Long> {

    @Query(value = """
            SELECT
                r.cd_raas_processo AS id,
                r.mes,
                r.ano,
                r.dt_geracao,
                r.empresa AS empresa,
                e.descricao AS nome_empresa,
                r.path,
                r.status,
                r.total_folha
            FROM raas_processo r
            LEFT JOIN empresa e ON e.empresa = r.empresa
            WHERE r.total_folha > 0
              AND (:mes IS NULL OR r.mes = :mes)
              AND (:ano IS NULL OR r.ano = :ano)
              AND (:empresa IS NULL OR r.empresa = CAST(:empresa AS BIGINT))
              AND (:status IS NULL OR r.status = :status)
            ORDER BY r.dt_geracao DESC
            """,
            nativeQuery = true)
    List<ListarArquivosRaasProjection> buscarProcessosPorCompetenciaEmpresaEStatus(
            @Param("mes") Integer mes,
            @Param("ano") Integer ano,
            @Param("empresa") String empresa,
            @Param("status") Integer status
    );
}
